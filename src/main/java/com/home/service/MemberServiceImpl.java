package com.home.service;

import com.home.dto.MemberDto;
import com.home.dto.MemberJoinDto;
import com.home.enums.role.UserRole;
import com.home.mapper.MemberMapper;
import com.home.mapper.RefreshTokenMapper;
import com.home.security.jwt.JwtDtoProvider;
import com.home.security.jwt.dto.AccessTokenDto;
import com.home.security.jwt.dto.JwtDto;
import com.home.security.jwt.dto.RefreshTokenDto;
import com.home.security.redis.service.JwtBlacklistService;
import com.home.util.snowflake.SnowFlake;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private static final String UPLOAD_DIR = "uploads/";
    private final MemberMapper memberMapper;
    private final RefreshTokenMapper refreshTokenMapper;
    private final SnowFlake snowFlake;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtDtoProvider jwtDtoProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtBlacklistService jwtBlacklistService;

    private static boolean patternMatches(String pattern, String regexPattern) throws IllegalArgumentException{
        if (pattern == null) {
            throw new IllegalArgumentException("항목이 존재하지 않습니다.");
        }
        return !Pattern.compile(regexPattern)
                .matcher(pattern)
                .matches();
    }

    @Transactional
    public Long join(MemberJoinDto memberJoinDto) throws IllegalArgumentException {

        validateEmail(memberJoinDto.getEmail());
        validatePassword(memberJoinDto.getPassword());
        validatePasswordSame(memberJoinDto.getPassword(), memberJoinDto.getConfirmPassword());
//        validateAddress(memberJoinDto.getAddress());
        validatePhoneNumber(memberJoinDto.getPhoneNumber());

        MemberDto memberDto = memberJoinDto.toMemberDto();
        validateDuplicateMember(memberDto);

        memberDto.setId(snowFlake.generateSnowFlake());
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberDto.setRole(UserRole.ROLE_USER);
        memberMapper.insertMember(memberDto);
        memberMapper.insertRole(memberDto);
//        memberMapper.save(memberDto);
        return memberJoinDto.getId();
    }

    private void validatePasswordSame(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void validateEmail(String email) {
        if (patternMatches(email,
                "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("이메일이 유효하지 않습니다.");
        }

    }

    private void validatePassword(String password) {
        if (patternMatches(password,
                "^(?=.*[A-Za-z])(?=.*\\d)(?=(.*[!@#$%^&*(),.?\":{}|<>]){2,}).{8,}$")) {
            throw new IllegalArgumentException("비밀번호가 유효하지 않습니다.");
        }
    }

    private void validateAddress(String address) {
        if (address.isEmpty()) {
            return;
        }
        // "우편번호5자리, 주소, 상세 주소"
        if (patternMatches(address,
                "^\\d{5}$\n")) {
            throw new IllegalArgumentException("주소가 유효하지 않습니다.");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.isEmpty()) {
            return;
        }
        // "우편번호5자리, 주소, 상세 주소"
        if (patternMatches(phoneNumber,
                "^010-[0-9]{4}-[0-9]{4}$")) {
            throw new IllegalArgumentException("휴대폰 번호가 유효하지 않습니다.");
        }
    }

    @Override
    public JwtDto login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);

        validateDuplicateMember(username);
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        JwtDto jwtDto = jwtDtoProvider.generateToken(authentication);
        AccessTokenDto accessTokenDto = jwtDtoProvider.getAccessToken(jwtDto);
        RefreshTokenDto refreshTokenDto = jwtDtoProvider.getRefreshToken(jwtDto);

        MemberDto memberDto = memberMapper.findByEmail(username);
        long id = memberDto.getId();

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("token", refreshTokenDto.getRefreshToken());

        memberMapper.deleteRefreshToken(id);
        memberMapper.insertRefreshToken(params);
        return jwtDto;
    }

    @Override
    public void logout(String token) {

        if (token != null && jwtDtoProvider.validateToken(token)) {
            long expirationTime =
                    jwtDtoProvider.getExpiration(token).getTime() - new Date().getTime();
            jwtBlacklistService.blacklistToken(token, expirationTime);
            String name = jwtDtoProvider.getName(token);
            MemberDto memberDto = memberMapper.findByEmail(name);
            memberMapper.deleteRefreshToken(memberDto.getId());
        } else {
            throw new IllegalArgumentException("로그아웃 할 수 없습니다.");
        }

    }


    public List<MemberDto> findMembers() {
        return memberMapper.findAll();
    }

    public MemberDto findById(long memberId) {
        return memberMapper.findById(memberId);
    }

    @Override
    public MemberDto findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }

    public void update(long memberId, MemberDto memberDto) {
        memberDto.setId(memberId);
        memberMapper.update(memberDto);
    }

    public void updateByEmail(String username, MemberDto memberDto) {
        if (!username.equals(memberDto.getEmail())) {
            throw new IllegalArgumentException("회원 이름이 일치하지 않습니다.");
        }

        memberMapper.updateByEmail(memberDto);
    }

    public void delete(long memberId) {
        memberMapper.delete(memberId);
    }

    public void deleteByEmail(String username) {
        memberMapper.deleteByEmail(username);
    }

    @Override
    public String uploadImg(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        return fileName;
    }

    @Override
    public Resource serveFile(String filename) throws RuntimeException {
        try {
            Path file = Paths.get(UPLOAD_DIR).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not serve the file!", e);
        }
    }

    private void validateDuplicateMember(MemberDto memberDto) {
        MemberDto member = memberMapper.findByEmail(memberDto.getEmail());
        if (member != null) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    private void validateDuplicateMember(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        if (authentication.getPrincipal().equals(username)) {
            throw new IllegalArgumentException("이미 로그인한 회원입니다.");
        }
    }

    @Override
    public JwtDto refreshToken(String requestRefreshToken) throws IllegalArgumentException {
        try {
            if (jwtDtoProvider.validateToken(requestRefreshToken)) {
                String refreshToken = refreshTokenMapper.findByToken(requestRefreshToken);
                if (refreshToken != null) {
                    return jwtDtoProvider.generateJwtDtoByRefreshToken(refreshToken);
                } else {
                    throw new IllegalArgumentException("해당 Refresh 토큰 없음");
                }
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            throw new IllegalArgumentException("Refresh 만료");
        }
        return null;
    }
}

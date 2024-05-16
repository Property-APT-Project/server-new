package com.home.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.home.dto.MemberDto;
import com.home.enums.role.UserRole;
import com.home.mapper.MemberMapper;
import com.home.mapper.RefreshTokenMapper;
import com.home.security.jwt.JwtDtoProvider;
import com.home.security.jwt.dto.AccessTokenDto;
import com.home.security.jwt.dto.JwtDto;
import com.home.security.jwt.dto.RefreshTokenDto;
import com.home.util.snowflake.SnowFlake;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final RefreshTokenMapper refreshTokenMapper;
    private final SnowFlake snowFlake;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtDtoProvider jwtDtoProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(MemberDto memberDto) throws IllegalArgumentException {

        validateDuplicateMember(memberDto);
        memberDto.setId(snowFlake.generateSnowFlake());
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberDto.setRole(UserRole.ROLE_USER);
        memberMapper.insertMember(memberDto);
        memberMapper.insertRole(memberDto);
//        memberMapper.save(memberDto);
        return memberDto.getId();
    }

    @Override
    public JwtDto login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);

        validateDuplicateMember(username);
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        JwtDto jwtDto = jwtDtoProvider.generateToken(authentication);
        AccessTokenDto accessTokenDto =  jwtDtoProvider.getAccessToken(jwtDto);
        RefreshTokenDto refreshTokenDto =  jwtDtoProvider.getRefreshToken(jwtDto);
        
        MemberDto meberDto = memberMapper.findByEmail(username);
        long id = meberDto.getId();
        
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("token", refreshTokenDto.getRefreshToken());
        memberMapper.insertRefreshToken(params);
        return jwtDto;
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

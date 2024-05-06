package com.home.service;

import com.home.dto.MemberDto;
import com.home.enums.role.UserRole;
import com.home.mapper.MemberMapper;
import com.home.util.jwt.JwtDto;
import com.home.util.jwt.JwtDtoProvider;
import com.home.util.snowflake.SnowFlake;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
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

        return jwtDtoProvider.generateToken(authentication);
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
}

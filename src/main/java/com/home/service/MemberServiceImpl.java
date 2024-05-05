package com.home.service;

import com.home.dto.MemberDto;
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
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final SnowFlake snowFlake;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtDtoProvider jwtDtoProvider;

    public Long join(MemberDto memberDto) throws IllegalArgumentException {

        validateDuplicateMember(memberDto);
        memberDto.setId(snowFlake.generateSnowFlake());
        memberDto.getRoles().add("user");
        memberMapper.save(memberDto);
        return memberDto.getId();
    }

    @Override
    public JwtDto login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);

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

    public void delete(long memberId) {
        memberMapper.delete(memberId);
    }

    private void validateDuplicateMember(MemberDto memberDto) {
        MemberDto member = memberMapper.findByEmail(memberDto.getEmail());
        if (member != null) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }
}

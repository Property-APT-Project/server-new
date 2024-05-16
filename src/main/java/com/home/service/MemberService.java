package com.home.service;

import java.util.List;

import com.home.dto.MemberDto;
import com.home.security.jwt.dto.JwtDto;

public interface MemberService {


    public Long join(MemberDto memberDto) throws IllegalArgumentException;

    public JwtDto login(String username, String password);
    
    public JwtDto refreshToken(String refreshToken);

    public List<MemberDto> findMembers();

    public MemberDto findById(long memberId);

    public MemberDto findByEmail(String email);

    public void update(long memberId, MemberDto memberDto);

    public void updateByEmail(String username, MemberDto memberDto);

    public void delete(long memberId);

    public void deleteByEmail(String username);

}

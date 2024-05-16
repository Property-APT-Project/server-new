package com.home.service;

import com.home.dto.MemberDto;
import com.home.security.jwt.JwtDto;

import java.util.List;

public interface MemberService {


    public Long join(MemberDto memberDto) throws IllegalArgumentException;

    public JwtDto login(String username, String password);

    public List<MemberDto> findMembers();

    public MemberDto findById(long memberId);

    public MemberDto findByEmail(String email);

    public void update(long memberId, MemberDto memberDto);

    public void updateByEmail(String username, MemberDto memberDto);

    public void delete(long memberId);

    public void deleteByEmail(String username);

}

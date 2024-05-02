package com.home.service;

import com.home.dto.MemberDto;
import com.home.util.jwt.JwtDto;
import java.util.List;
import java.util.Optional;

public interface MemberService {


    public Long join(MemberDto memberDto) throws IllegalArgumentException;

    public JwtDto login(String username, String password);

    public List<MemberDto> findMembers();

    public MemberDto findById(long memberId);

    public Optional<MemberDto> findByEmail(String email);

    public void update(long memberId, MemberDto memberDto);

    public void delete(long memberId);

}

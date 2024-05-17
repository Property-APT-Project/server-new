package com.home.service;

import com.home.dto.MemberDto;
import com.home.security.jwt.dto.JwtDto;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {


    public Long join(MemberDto memberDto) throws IllegalArgumentException;

    public JwtDto login(String username, String password);

    public void logout();

    public JwtDto refreshToken(String refreshToken);

    public List<MemberDto> findMembers();

    public MemberDto findById(long memberId);

    public MemberDto findByEmail(String email);

    public void update(long memberId, MemberDto memberDto);

    public void updateByEmail(String username, MemberDto memberDto);

    public void delete(long memberId);

    public void deleteByEmail(String username);

    public String uploadImg(MultipartFile file) throws IOException;

    public Resource serveFile(String filename);
}

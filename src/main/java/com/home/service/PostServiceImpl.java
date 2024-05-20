package com.home.service;

import com.home.dto.MemberDto;
import com.home.dto.PostDetailDto;
import com.home.dto.PostDto;
import com.home.mapper.MemberMapper;
import com.home.mapper.PostMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final MemberMapper memberMapper;

    @Override
    public long write(PostDto postDto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String username = (String) authentication.getPrincipal();
        MemberDto memberDto = memberMapper.findByEmail(username);

        postDto.setUserId(memberDto.getId());
        return postMapper.create(postDto);
    }

    @Override
    public PostDto findById(long id) {
        return postMapper.findById(id);
    }

    @Override
    public PostDetailDto findPostDetailById(long id) {
        return postMapper.findPostDetailById(id);
    }

    @Override
    public List<PostDto> findByUserId(long userId) {
        return postMapper.findByUserId(userId);
    }

    @Override
    public List<PostDto> findAll() {
        return postMapper.findAll();
    }

    @Override
    public List<PostDetailDto> findAllPostDetail() {
        return postMapper.findAllPostDetail();
    }

    @Override
    public void modify(PostDto postDto) {
        postMapper.update(postDto);
    }

    @Override
    public void delete(long id) {
        postMapper.delete(id);
    }

    @Override
    public void hit(long id) {
        PostDto postDto = postMapper.findById(id);
        postDto.setHit(postDto.getHit()+1);
        postMapper.update(postDto);
    }

    @Override
    public int like(long id) {
        PostDto postDto = postMapper.findById(id);
        System.out.println(postDto);
        postDto.setLike(postDto.getLike()+1);
        postMapper.update(postDto);

        return postMapper.findById(id).getLike();
    }
}

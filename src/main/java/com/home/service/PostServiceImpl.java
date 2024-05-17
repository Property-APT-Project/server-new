package com.home.service;

import com.home.dto.PostDto;
import com.home.mapper.PostMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    @Override
    public long write(PostDto postDto) {
        return postMapper.create(postDto);
    }

    @Override
    public PostDto findById(long id) {
        return postMapper.findById(id);
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
    public void like(long id) {
        PostDto postDto = postMapper.findById(id);
        postDto.setLike(postDto.getLike()+1);
        postMapper.update(postDto);
    }
}

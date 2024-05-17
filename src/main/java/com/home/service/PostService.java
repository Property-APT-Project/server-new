package com.home.service;

import com.home.dto.PostDto;
import java.util.List;

public interface PostService {

    long write(PostDto postDto);

    PostDto findById(long id);

    List<PostDto> findByUserId(long userId);

    List<PostDto> findAll();

    void modify(PostDto postDto);

    void delete(long id);

    void hit(long id);

    void like(long id);
}

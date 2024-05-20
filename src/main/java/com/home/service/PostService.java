package com.home.service;

import com.home.dto.PostDetailDto;
import com.home.dto.PostDto;
import java.util.List;

public interface PostService {

    long write(PostDto postDto);

    PostDto findById(long id);

    PostDetailDto findPostDetailById(long id);

    List<PostDto> findByUserId(long userId);

    List<PostDto> findAll();

    List<PostDetailDto> findAllPostDetail();

    void modify(PostDto postDto);

    void delete(long id);

    void hit(long id);

    int like(long id);
}

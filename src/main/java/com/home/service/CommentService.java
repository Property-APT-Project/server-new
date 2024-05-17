package com.home.service;

import com.home.dto.CommentDto;
import java.util.List;

public interface CommentService {

    long write(CommentDto commentDto);

    CommentDto findById(long id) throws IllegalArgumentException;

    List<CommentDto> findByPostId(long postId) throws IllegalArgumentException;

    List<CommentDto> findAll();

    void modify(CommentDto commentDto);

    void delete(long id);

    void like(long id) throws IllegalArgumentException;
}

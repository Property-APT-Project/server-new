package com.home.service;

import com.home.dto.CommentDto;
import com.home.mapper.CommentMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentMapper commentMapper;

    @Override
    public long write(CommentDto commentDto) {
        return commentMapper.create(commentDto);
    }

    @Override
    public CommentDto findById(long id) {
        return commentMapper.findById(id);
    }

    @Override
    public List<CommentDto> findByPostId(long postId) {
        return commentMapper.findByPostId(postId);
    }

    @Override
    public List<CommentDto> findAll() {
        return commentMapper.findAll();
    }

    @Override
    public void modify(CommentDto commentDto) {
        commentMapper.update(commentDto);
    }


    @Override
    public void delete(long id) {
        commentMapper.delete(id);
    }

    @Override
    public void like(long id) {
        CommentDto commentDto = commentMapper.findById(id);
        commentDto.setLike(commentDto.getLike() + 1);
    }
}

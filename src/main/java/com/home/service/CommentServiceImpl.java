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
    public CommentDto findById(long id) throws IllegalArgumentException{
        CommentDto commentDto = commentMapper.findById(id);
        if (commentDto == null) {
            throw new IllegalArgumentException("해당 댓글이 없습니다.");
        }
        return commentDto;
    }

    @Override
    public List<CommentDto> findByPostId(long postId) throws IllegalArgumentException {
        List<CommentDto> comments = commentMapper.findByPostId(postId);
        if (comments.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글에 대한 댓글이 없습니다.");
        }
        return comments;
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
    public void like(long id) throws IllegalArgumentException {
        CommentDto commentDto = commentMapper.findById(id);
        if (commentDto == null) {
            throw new IllegalArgumentException("해당 댓글이 없습니다.");
        }
        commentDto.setId(id);
        commentDto.setLike(commentDto.getLike() + 1);
        commentMapper.update(commentDto);
    }
}

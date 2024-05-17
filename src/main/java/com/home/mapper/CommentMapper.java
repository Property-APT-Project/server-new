package com.home.mapper;

import com.home.dto.CommentDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    long create(CommentDto commentDto);

    CommentDto findById(long id);

    List<CommentDto> findByPostId(long postId);

    List<CommentDto> findAll();

    void update(CommentDto commentDto);

    void delete(long id);
}

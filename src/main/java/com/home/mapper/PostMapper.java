package com.home.mapper;

import com.home.dto.PostDetailDto;
import com.home.dto.PostDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

    long create(PostDto postDto);

    PostDto findById(long id);

    PostDetailDto findPostDetailById(long id);

    List<PostDto> findByUserId(long userId);

    List<PostDto> findAll();

    void update(PostDto postDto);

    void delete(long id);
}

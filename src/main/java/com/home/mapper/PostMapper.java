package com.home.mapper;

import com.home.dto.PostDetailDto;
import com.home.dto.PostDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {

    void create(PostDto postDto);

    PostDto findById(long id);

    PostDetailDto findPostDetailById(long id);

    List<PostDetailDto> findByUserId(long userId);

    List<PostDetailDto> findByEmail(String email);

    List<PostDto> findAll();

    List<PostDetailDto> findAllPostDetail();

    List<PostDetailDto> findAllPostDetailWithPagination(
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("sort") String sort,
            @Param("order") String order);

    void update(PostDto postDto);

    void delete(long id);
}

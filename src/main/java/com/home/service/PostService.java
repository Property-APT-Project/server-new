package com.home.service;

import com.home.dto.PostDetailDto;
import com.home.dto.PostDto;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    long write(PostDto postDto);

    PostDto findById(long id);

    PostDetailDto findPostDetailById(long id);

    List<PostDto> findByUserId(long userId);

    List<PostDto> findAll();

    List<PostDetailDto> findAllPostDetail();

    List<PostDetailDto> findAllPostDetail(int page, int limit, String sort, String order);

    void modify(PostDto postDto);

    void delete(long id);

    void hit(long id);

    int like(long id);

    String uploadImg(MultipartFile file) throws IOException;

    Resource serveFile(String filename);
}

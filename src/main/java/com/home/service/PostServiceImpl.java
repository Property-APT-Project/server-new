package com.home.service;

import com.home.dto.MemberDto;
import com.home.dto.PostDetailDto;
import com.home.dto.PostDto;
import com.home.mapper.MemberMapper;
import com.home.mapper.PostMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final String UPLOAD_DIR = "uploads/community/";
    private final PostMapper postMapper;
    private final MemberMapper memberMapper;

    @Override
    public long write(PostDto postDto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String username = (String) authentication.getPrincipal();
        
       System.out.println("test: " + username);
        MemberDto memberDto = memberMapper.findByEmail(username);

        postDto.setUserId(memberDto.getId());
        postMapper.create(postDto);
        return postDto.getId();
    }

    @Override
    public PostDto findById(long id) {
        return postMapper.findById(id);
    }

    @Override
    public PostDetailDto findPostDetailById(long id) {
        return postMapper.findPostDetailById(id);
    }

    @Override
    public List<PostDetailDto> findByUserId(long userId) {
        return postMapper.findByUserId(userId);
    }

    @Override
    public List<PostDetailDto> findByEmail(String email) {
        return postMapper.findByEmail(email);
    }

    @Override
    public List<PostDto> findAll() {
        return postMapper.findAll();
    }

    @Override
    public List<PostDetailDto> findAllPostDetail() {
        return postMapper.findAllPostDetail();
    }

    @Override
    public List<PostDetailDto> findAllPostDetail(int page, int limit, String sort, String order) {
        int offset = (page - 1) * limit;
        return postMapper.findAllPostDetailWithPagination(offset, limit, sort, order);
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
    public void deletePostById(String username, Long id) throws IllegalArgumentException {
        List<PostDetailDto> posts = findByEmail(username);

        Optional<PostDetailDto> postToDelete = posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst();

        if (postToDelete.isPresent()) {
            delete(id);
        } else {
            throw new IllegalArgumentException("Post with id " + id + " does not exist for user " + username);
        }
    }


    @Override
    public void hit(long id) {
        PostDto postDto = postMapper.findById(id);
        postDto.setHit(postDto.getHit()+1);
        postMapper.update(postDto);
    }

    @Override
    public int like(long id) {
        PostDto postDto = postMapper.findById(id);
        System.out.println(postDto);
        postDto.setLike(postDto.getLike()+1);
        postMapper.update(postDto);

        return postMapper.findById(id).getLike();
    }

    @Override
    public String uploadImg(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        return fileName;
    }

    @Override
    public Resource serveFile(String filename) throws RuntimeException {
        try {
            Path file = Paths.get(UPLOAD_DIR).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not serve the file!", e);
        }
    }
}

package com.home.controller;

import com.home.dto.PostDetailDto;
import com.home.dto.PostDto;
import com.home.service.PostService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<?> findAll(
            @RequestParam(name = "_page", defaultValue = "1") int page,
            @RequestParam(name = "_limit", defaultValue = "5") int limit,
            @RequestParam(name = "_sort", defaultValue = "createdAt") String sort,
            @RequestParam(name = "_order", defaultValue = "desc") String order) {
        try {
//            List<PostDto> posts = postService.findAll();
            System.out.println("page = " + page);
            System.out.println("limit = " + limit);
            System.out.println("sort = " + sort);
            System.out.println("order = " + order);
            List<PostDetailDto> posts = postService.findAllPostDetail(page, limit, sort, order);
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/new")
    public ResponseEntity<?> write(@RequestBody PostDto postDto) {
        try {
            long id = postService.write(postDto);
            PostDetailDto postDetailById = postService.findPostDetailById(id);
            return ResponseEntity.status(HttpStatus.OK).body(postDetailById);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 작성 실패하였습니다.");
        }
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<?> like(@PathVariable("id") long id) {
        try {
            int like = postService.like(id);
            return ResponseEntity.status(HttpStatus.OK).body(like);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        try {
            postService.hit(id);
            PostDetailDto postDto = postService.findPostDetailById(id);
            return ResponseEntity.status(HttpStatus.OK).body(postDto);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modify(@PathVariable("id") long id, @RequestBody PostDto postDto) {
        try {
            postDto.setId(id);
            postService.modify(postDto);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 게시글 수정하였습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 게시글 실패하였습니다.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            postService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 게시글 삭제하였습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 삭제 실패하였습니다.");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file == null) {
            return new ResponseEntity<>("apt-default.png", HttpStatus.OK);
//            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = postService.uploadImg(file);
            return new ResponseEntity<>(fileName, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/upload/{filename:.+}")
    public ResponseEntity<?> serveFile(@PathVariable String filename) {
        try {
            Resource resource = postService.serveFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미지 조회 실패");
        }
    }
}

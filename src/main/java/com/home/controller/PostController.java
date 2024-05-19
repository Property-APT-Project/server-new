package com.home.controller;

import com.home.dto.PostDetailDto;
import com.home.dto.PostDto;
import com.home.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            List<PostDto> posts = postService.findAll();
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
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 게시글 작성하였습니다.");
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
}

package com.home.controller;

import com.home.dto.CommentDto;
import com.home.service.CommentService;
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
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/new")
    public ResponseEntity<?> write(@RequestBody CommentDto commentDto) {
        try {
            long id = commentService.write(commentDto);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 댓글 작성하였습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 작성 실패하였습니다.");
        }
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<?> like(@PathVariable("id") long id) {
        try {
            commentService.like(id);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 좋아요 하였습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long id) {
        try {
            CommentDto commentDto = commentService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(commentDto);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modify(@PathVariable("id") long id, @RequestBody CommentDto commentDto) {
        try {
            commentDto.setId(id);
            commentService.modify(commentDto);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 댓글 수정하였습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 수정 실패하였습니다.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            commentService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 댓글 삭제하였습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("댓글 삭제 실패하였습니다.");
        }
    }
}

package com.home.controller;

import com.home.dto.AnnouncementDto;
import com.home.service.AnnouncementService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping("/health")
    public ResponseEntity<String> status(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(
                String.format("%s %d port Connected!", "announcement main",  request.getServerPort()));
    }

    @PostMapping("/new-announcement")
    public ResponseEntity<?> write(@RequestBody AnnouncementDto announcementDto) {
        announcementService.write(announcementDto);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 등록되었습니다.");
    }

    @GetMapping("")
    public ResponseEntity<?> list() {
        List<AnnouncementDto> list = announcementService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PutMapping("/{announcementNo}")
    public ResponseEntity<?> modify(@PathVariable("announcementNo") int announcementNo,
            @RequestBody AnnouncementDto announcementDto) {
        announcementDto.setAnnouncementNo(announcementNo);
        announcementService.modify(announcementDto);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/{announcementNo}")
    public ResponseEntity<?> delete(@PathVariable("announcementNo") int announcementNo) {
        announcementService.delete(announcementNo);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 삭제되었습니다.");
    }
}

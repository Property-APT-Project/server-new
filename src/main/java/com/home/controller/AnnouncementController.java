package com.home.controller;

import com.home.dto.AnnouncementDto;
import com.home.dto.AnnouncementViewDto;
import com.home.dto.MemberDto;
import com.home.service.AnnouncementService;
import com.home.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final MemberService memberService;

    @GetMapping("/health")
    public ResponseEntity<String> status(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(
                String.format("%s %d port Connected!", "announcement main",  request.getServerPort()));
    }

    @PostMapping("/new-announcement")
    public ResponseEntity<?> write(@RequestBody AnnouncementDto announcementDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = (String) authentication.getPrincipal();
            MemberDto memberDto = memberService.findByEmail(username);

            announcementDto.setMemberId(memberDto.getId());
            announcementService.write(announcementDto);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 등록되었습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("등록에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> list() {
        try {
            List<AnnouncementDto> list = announcementService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("조회에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{announcementNo}")
    public ResponseEntity<?> getAnnouncement(@PathVariable("announcementNo") int announcementNo) {
        try {
            AnnouncementDto announcementDto = announcementService.findById(announcementNo);
            AnnouncementViewDto announcementViewDto = AnnouncementViewDto.builder()
                    .subject(announcementDto.getSubject())
                    .content(announcementDto.getContent())
                    .memberName(announcementDto.getMemberName())
                    .hit(announcementDto.getHit())
                    .registerTime(announcementDto.getRegisterTime())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(announcementViewDto);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("조회에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{announcementNo}")
    public ResponseEntity<?> modify(@PathVariable("announcementNo") int announcementNo,
            @RequestBody AnnouncementDto announcementDto) {
        try {
            announcementDto.setAnnouncementNo(announcementNo);
            announcementService.modify(announcementDto);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 수정되었습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("수정에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{announcementNo}")
    public ResponseEntity<?> delete(@PathVariable("announcementNo") int announcementNo) {
        try {
            announcementService.delete(announcementNo);
            return ResponseEntity.status(HttpStatus.OK).body("성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("삭제에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}

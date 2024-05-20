package com.home.service;

import com.home.dto.AnnouncementDto;
import com.home.mapper.AnnouncementMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementMapper announcementMapper;

    @Override
    public void write(AnnouncementDto announcementDto) {
        announcementMapper.create(announcementDto);
    }

    @Override
    public AnnouncementDto findById(int announcementNo) {
        return announcementMapper.findById(announcementNo);
    }

    @Override
    public List<AnnouncementDto> findAll() {
        return announcementMapper.findAll();
    }

    @Override
    public void modify(AnnouncementDto announcementDto) {
        int announcementNo = announcementDto.getAnnouncementNo();
        if (findById(announcementNo) == null) {
            throw new IllegalArgumentException("변경하고자 하는 게시물이 없습니다.");
        }
        announcementMapper.update(announcementDto);
    }

    @Override
    public void delete(int announcementNo) {
        if (findById(announcementNo) == null) {
            throw new IllegalArgumentException("변경하고자 하는 게시물이 없습니다.");
        }
        announcementMapper.delete(announcementNo);
    }
}

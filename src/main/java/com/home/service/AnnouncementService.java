package com.home.service;

import com.home.dto.AnnouncementDto;
import com.home.mapper.AnnouncementMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;

public interface AnnouncementService {

    void write(AnnouncementDto announcementDto);

    AnnouncementDto findById(int announcementNo);

    List<AnnouncementDto> findAll();

    void modify(AnnouncementDto announcementDto);

    void delete(int announcementNo);
}

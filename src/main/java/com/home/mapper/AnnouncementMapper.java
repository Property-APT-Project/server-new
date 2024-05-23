package com.home.mapper;

import com.home.dto.AnnouncementDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnnouncementMapper {

    void create(AnnouncementDto announcementDto);

    AnnouncementDto findById(int announcementNo);

    List<AnnouncementDto> findAll();

    void update(AnnouncementDto announcementDto);

    void delete(int announcementNo);
}

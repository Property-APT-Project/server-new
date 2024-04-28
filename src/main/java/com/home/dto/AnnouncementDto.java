package com.home.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class AnnouncementDto {

    private int announcementNo;
    private String userId;
    private String subject;
    private String content;
    private int hit;
    private String registerTime;

}

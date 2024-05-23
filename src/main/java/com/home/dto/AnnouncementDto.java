package com.home.dto;

import java.sql.Date;
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
    private long memberId;
    private String memberName;
    private boolean isAdmin;
    private String subject;
    private String content;
    private int hit;
    private Date registerTime;

}

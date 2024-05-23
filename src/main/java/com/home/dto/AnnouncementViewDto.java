package com.home.dto;

import java.sql.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnnouncementViewDto {
    private String memberName;
    private String subject;
    private String content;
    private int hit;
    private Date registerTime;
}

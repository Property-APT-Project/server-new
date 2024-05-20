package com.home.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDetailDto {
    private long id;
    private long userId;
    private String name;
    private String profileImgURL;
    private String title;
    private String content;
    private String imgURL;
    private Integer like;
    private Integer hit;
    private LocalDateTime createTime;
    private List<CommentDetailDto> comments;
}

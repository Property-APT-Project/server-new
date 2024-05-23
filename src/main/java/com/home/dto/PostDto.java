package com.home.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostDto {

    private long id;
    private long userId;
    private String title;
    private String content;
    private String imgURL;
    private Integer like;
    private Integer hit;
    private LocalDateTime createTime;
    private List<CommentDto> comments;
}

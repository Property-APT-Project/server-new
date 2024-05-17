package com.home.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private long id;
    private long userId;
    private String title;
    private String content;
    private String imgURL;
    private int like;
    private int hit;
    private LocalDateTime createTime;
    private List<CommentDto> comments;
}

package com.home.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private long id;
    private long userId;
    private long postId;
    private String content;
    private Integer like;
    private LocalDateTime createTime;
}

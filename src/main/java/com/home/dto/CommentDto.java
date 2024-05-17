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
    private int like;
    private LocalDateTime createTime;
}

package com.home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProfileDto {
    private long id;
    private String email;
    private String name;
    private String address;
    private String phoneNumber;
    private String imgURL;
}

package com.home.dto;

import com.home.enums.role.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String confirmPassword;
    private String address;
    private String phoneNumber;
    private String imgURL;
    private UserRole role;

    public MemberDto toMemberDto() {
        return MemberDto.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .address(this.address)
                .imgURL(this.imgURL)
                .phoneNumber(this.phoneNumber)
                .role(this.role)
                .build();
    }
}

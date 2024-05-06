package com.home.dto;

import com.home.enums.role.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberDto {
	private Long id;
	private String email;
	private String name;
	private String password;
	private String address;
	private String phoneNumber;
	private UserRole Role;

}

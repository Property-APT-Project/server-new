package com.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InterestDto {
	private int id;
	private long userId;
	private int category; // 1: sale, 2: complex
	private String interestId;
	private String lng;
	private String lat;

}

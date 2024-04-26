package com.home.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class HouseInfoDto {
	private int buildYear;
	private String roadName;
	private String dong;
	private String dongCode;
	private String aptName;
	private String jibun;
	private String lng;
	private String lat;
	
	

}

package com.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// complexName, cortarNo, address,  latitude, longitude, images1

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseSimpleDto {
	private String aptName;
	private String dongCode;
	private String address;
	private String lng;
	private String lat;
	private String thumbImg;
	
	
	
	
}

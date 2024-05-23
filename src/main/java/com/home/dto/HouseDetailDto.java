package com.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseDetailDto {
	
	private String complexNo;
	private String aptName;
	private String dongCode;
	private String address;
	private String dongAddress;
	private String roadAddress;
	private String houseHoldCount;
	private String highFloor;
	private String lowFloor;
	private String useApproveYmd;
	private String totalDongCount; 
	private String parkingPossibleCount;
	private String parkingCountByHousehold;
	private String heatMethodTypeCode;
	private String heatFuelTypeCode;
	private String pyoengNames;
	private String lat;
	private String lng;
	private String images;
	private String images2;
	private String images3;
	private String images4;
	private String images5;

}

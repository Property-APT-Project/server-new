package com.home.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DongCodeDto {
	private String dongCode;
	private String sidoName;
	private String gugunName;
	private String dongName;
	private List<HouseSimpleDto> aptList;
	private int aptListCount;
	
	

}

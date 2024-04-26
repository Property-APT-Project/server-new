package com.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseDealDto {
	
	private String dealAmount;
	private int dealYear;
	private int dealMonth;
	private String area;
	private String floor;
	private String apartmentName;
	
	
	
	

}

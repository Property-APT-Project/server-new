package com.home.service;

import java.util.List;

import com.home.dto.InterestDto;

public interface InterestService {
	List<InterestDto> findAllUserInterestComplex();
	List<InterestDto> findAllUserInterestSale();
	
	
	void insertInterest(InterestDto interestDto);
	boolean deleteInterest(String interestId);

}

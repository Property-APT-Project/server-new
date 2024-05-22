package com.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.home.dto.InterestDto;


@Mapper
public interface InterestMapper {
	
	
	List<InterestDto> findAllUserInterestComplex(long userId);
	List<InterestDto> findAllUserInterestSale(long userId);
	InterestDto findInterestByUserAndInterestId(@Param("userId") long userId, @Param("interestId") String string);
	
	InterestDto findInterestById(int id);
	
	void insertInterest(InterestDto interestDto);
	void deleteInterest(int id);
	
	
	

}

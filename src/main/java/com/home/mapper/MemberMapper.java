package com.home.mapper;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.home.dto.MemberDto;

@Mapper
public interface MemberMapper {
	
	public void save(MemberDto memberDto);
	
	public MemberDto findById(@Param("uuid") UUID id);
	
	public List<MemberDto> findByEmail(String email);
	
	public List<MemberDto> findAll();
	
	public void update(MemberDto memberDto);
	
	public void delete(@Param("uuid") UUID id);
}

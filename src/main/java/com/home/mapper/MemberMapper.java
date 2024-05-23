package com.home.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.home.dto.MemberDto;

@Mapper
public interface MemberMapper {
	
	public void insertMember(MemberDto memberDto);

	public void insertRole(MemberDto memberDto);
	
	public void insertRefreshToken(Map<String, Object> params);

	public void deleteRefreshToken(long userId);
	
	public MemberDto findById(long id);
	
	public MemberDto findByEmail(String email);
	
	public List<MemberDto> findAll();
	
	public void update(MemberDto memberDto);

	public void updateByEmail(MemberDto memberDto);
	
	public void delete(long id);

	public void deleteByEmail(String email);
	
}

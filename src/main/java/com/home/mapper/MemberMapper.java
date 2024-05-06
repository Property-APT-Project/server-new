package com.home.mapper;

import com.home.dto.MemberDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	
	public void save(MemberDto memberDto);
	
	public MemberDto findById(long id);
	
	public MemberDto findByEmail(String email);
	
	public List<MemberDto> findAll();
	
	public void update(MemberDto memberDto);

	public void updateByEmail(MemberDto memberDto);
	
	public void delete(long id);

	public void deleteByEmail(String email);
}

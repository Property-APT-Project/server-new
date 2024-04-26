package com.home.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.home.dto.MemberDto;
import com.home.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberMapper memberMapper;
	
	public UUID join(MemberDto memberDto) {
		
		validateDuplicateMember(memberDto);
		memberMapper.save(memberDto);
		return memberDto.getId();
	}
	
	public List<MemberDto> findMembers() {
		return memberMapper.findAll();
	}
	
	public MemberDto findById(UUID memberId) {
		return memberMapper.findById(memberId);
	}
	
	public void update(UUID memberId, MemberDto memberDto) {
		memberMapper.update(memberDto);
	}
	
	public void delete(UUID memberId) {
		memberMapper.delete(memberId);
	}

	private void validateDuplicateMember(MemberDto memberDto) {
		List<MemberDto> members = memberMapper.findByEmail(memberDto.getEmail());
		if(!members.isEmpty()) {
			throw new IllegalArgumentException("이미 존재하는 회원입니다.");
		}
		
	}
}

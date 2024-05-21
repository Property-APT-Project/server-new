package com.home.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.home.dto.InterestDto;
import com.home.dto.MemberDto;
import com.home.mapper.InterestMapper;
import com.home.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {
	private final InterestMapper interestMapper;
	private final MemberMapper memberMapper;
	

	@Override
	public List<InterestDto> findAllUserInterestComplex() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		
		
		String username = (String) authentication.getPrincipal();
		MemberDto memberDto = memberMapper.findByEmail(username);
		
		
		return interestMapper.findAllUserInterestComplex(memberDto.getId());

	}

	@Override
	public List<InterestDto> findAllUserInterestSale() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		
		
		String username = (String) authentication.getPrincipal();
		MemberDto memberDto = memberMapper.findByEmail(username);
		
		return interestMapper.findAllUserInterestSale(memberDto.getId());
	}

	@Override
	public void insertInterest(InterestDto interestDto) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		
		
		String username = (String) authentication.getPrincipal();
		MemberDto memberDto = memberMapper.findByEmail(username);
		
		interestDto.setUserId(memberDto.getId());
		
		interestMapper.insertInterest(interestDto);
		

	}

	@Override
	public boolean deleteInterest(int id) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		
		
		String username = (String) authentication.getPrincipal();
		MemberDto memberDto = memberMapper.findByEmail(username);
		
		InterestDto interestDto = interestMapper.findInterestById(id);
		
		if(interestDto.getUserId() != memberDto.getId())
			return false;
		
		interestMapper.deleteInterest(id);
		
		return true;
		

	}

}

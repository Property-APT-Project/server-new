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
		
		System.out.println("user Name insterest: " + username);
		
		
		return interestMapper.findAllUserInterestComplex(memberDto.getId());

	}

	@Override
	public List<InterestDto> findAllUserInterestSale() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		
		
		String username = (String) authentication.getPrincipal();
		MemberDto memberDto = memberMapper.findByEmail(username);
		
		System.out.println("user Name insterest: " + memberDto.getId());
		
		return interestMapper.findAllUserInterestSale(memberDto.getId());
	}

	@Override
	public void insertInterest(InterestDto interestDto) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		System.out.println("Interest Dto: " + interestDto);
		
		String username = (String) authentication.getPrincipal();
		
		System.out.println("Interest username: " + username);
		MemberDto memberDto = memberMapper.findByEmail(username);
		
		
		if(interestMapper.findInterestByUserAndInterestId(memberDto.getId(), interestDto.getInterestId()) != null)
			return;
			
		
		interestDto.setUserId(memberDto.getId());
		
		System.out.println("Interest Dto: " + interestDto);
		
		interestMapper.insertInterest(interestDto);
		

	}

	@Override
	public boolean deleteInterest(String interestId) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		
		
		String username = (String) authentication.getPrincipal();
		MemberDto memberDto = memberMapper.findByEmail(username);
		
		InterestDto interestDto = interestMapper.findInterestByUserAndInterestId(memberDto.getId(), interestId);
		
		
		if(interestDto == null)
			return false;
		
		interestMapper.deleteInterest(interestDto.getId());
		
		return true;
		

	}

}

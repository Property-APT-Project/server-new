package com.home.security.jwt.detail;

import com.home.dto.MemberDto;
import com.home.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDto memberDto = memberMapper.findByEmail(username);
        return createUserDetails(memberDto);
//                .map(this::createUserDetails)
//                .orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(MemberDto memberDto) {
        return User.builder()
                .username(memberDto.getEmail())
                .password(memberDto.getPassword())
                .authorities(memberDto.getRole().toString())
//                .roles(memberDto.getRoles().toArray(new String[0]))
                .build();
    }
}

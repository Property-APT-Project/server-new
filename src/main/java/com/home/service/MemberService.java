package com.home.service;

import com.home.dto.MemberDto;
import com.home.mapper.MemberMapper;
import com.home.util.snowflake.SnowFlake;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final SnowFlake snowFlake;

    public Long join(MemberDto memberDto) throws IllegalArgumentException {

        validateDuplicateMember(memberDto);
        memberDto.setId(snowFlake.generateSnowFlake());
        memberMapper.save(memberDto);
        return memberDto.getId();
    }

    public List<MemberDto> findMembers() {
        return memberMapper.findAll();
    }

    public MemberDto findById(long memberId) {
        return memberMapper.findById(memberId);
    }

    public void update(long memberId, MemberDto memberDto) {
        memberMapper.update(memberDto);
    }

    public void delete(long memberId) {
        memberMapper.delete(memberId);
    }

    private void validateDuplicateMember(MemberDto memberDto) {
        List<MemberDto> members = memberMapper.findByEmail(memberDto.getEmail());
        if (!members.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }
}

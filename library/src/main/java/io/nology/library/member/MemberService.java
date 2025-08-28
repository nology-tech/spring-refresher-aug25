package io.nology.library.member;

import java.util.Optional;

import org.springframework.stereotype.Service;

import io.nology.library.member.entities.Member;

@Service
public class MemberService {
    private MemberRepository memberRepo;

    public MemberService(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    public Optional<Member> findById(Long id) {
        return this.memberRepo.findById(id);
    }

}

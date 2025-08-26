package io.nology.library.member;

import org.springframework.data.jpa.repository.JpaRepository;

import io.nology.library.member.entities.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}

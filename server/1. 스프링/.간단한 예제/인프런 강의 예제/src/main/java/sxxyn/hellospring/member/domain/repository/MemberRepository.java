package sxxyn.hellospring.member.domain.repository;

import org.springframework.stereotype.Repository;
import sxxyn.hellospring.member.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id); //Optional로 감싸서 반환
    Optional<Member> findByName(String name);
    List<Member> findAll();
}

package sxxyn.hellospring.member.domain.repository;

import org.springframework.data.repository.CrudRepository;
import sxxyn.hellospring.member.domain.Member;

import java.util.Optional;

//인터페이스에서 인터페이스를 상속받을 때는 extends
public interface SpringDataJpaMemberRepository extends CrudRepository<Member,Long>,MemberRepository {

    //JPQL select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);

}

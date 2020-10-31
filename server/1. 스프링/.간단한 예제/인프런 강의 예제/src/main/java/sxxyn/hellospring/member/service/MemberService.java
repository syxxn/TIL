package sxxyn.hellospring.member.service;

import sxxyn.hellospring.member.domain.Member;
import sxxyn.hellospring.member.domain.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//@Service //스프링 컨테이너에 등록
@Transactional
public class MemberService {
    //서비스는 비즈니스에 의존적 /역할에 맞도록 네이밍
    //레포지토리는 기계적인 처리/

    private final MemberRepository memberRepository;

    //@Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    //회원가입
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member){
        memberRepository.findByName(member.getName()) //중복 회원 검증
                .ifPresent(m->{
                    throw new IllegalStateException();
                });
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}

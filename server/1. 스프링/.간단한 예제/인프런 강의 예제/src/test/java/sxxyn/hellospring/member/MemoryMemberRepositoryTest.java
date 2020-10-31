package sxxyn.hellospring.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import sxxyn.hellospring.member.domain.Member;
import sxxyn.hellospring.member.domain.repository.MemoryMemberRepository;


import static org.assertj.core.api.Assertions.*;


class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository=new MemoryMemberRepository();

    @AfterEach
    //test는 의존관계 없이 실행되어야 함함
   //test 끝날 때마다 코드를 지워줘야 함/ 그렇지 않으면 기록이 남아서 오류가 날 수 있음
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member=new Member();
        member.setName("spring");

        repository.save(member);

        Member result=repository.findById(member.getId()).get();
        //Assertions.assertEquals(member,result);
        assertThat(member).isEqualTo(result);
        // System.out.println("result = "+(result==member));
    }

    @Test
    public void findByName(){
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 =new Member();
        member2.setName("spring1");
        repository.save(member2);

        Member result=repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

}

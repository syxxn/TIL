package sxxyn.hellospring.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sxxyn.hellospring.member.domain.repository.MemberRepository;
import sxxyn.hellospring.member.service.MemberService;


@Configuration
public class MemberConfig {

    /*private EntityManager em;

    @Autowired
    public MemberConfig(EntityManager em){
        this.em=em;
    }*/
    private final MemberRepository memberRepository;

    public MemberConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }


    /*@Bean
    public MemberRepository memberRepository(){
        return new JpaMemberJepository(em);
    }*/


}

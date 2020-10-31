package sxxyn.hellospring.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sxxyn.hellospring.member.domain.Member;
import sxxyn.hellospring.member.service.MemberService;

import java.util.List;

//@Component 어노테이션을 스프링 빈으로 자동 등록시켜준다
@Controller//컨테이너에 멤버 컨트롤러 객체를 생성해서 넣어두고 스프링이 관리를 한다 -> 스프링 빈이 관리된다.
public class MemberController {

    //객체를 스프링 빈에 등록해서 관리해서 쓸 수 있도록
    //하나만 생성해서 공용으로 쓰도록 빈에 등록
    private final MemberService memberService;

    @Autowired //자동으로 연결시켜준다 / 사용하는 파일이 빈에 등록되어있을 때만 적용됨
    //스프링 빈에 등록되어 있던 멤버 서비스 객체를 주입해줌 ->  DI
    public MemberController(MemberService memberService){
        this.memberService=memberService;
    }
/*
스프링 빈을 등록하는 방법
+ 컴포넌트 스캔(Component와 Component 어노테이션을 포함하는 어노테이션)과 자동 의존관계 설정(Autowired)
+ 자바 코드로 직접 스프링 빈 등록하기
+ @_ArgsConstructor 사용
 */

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member=new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members=memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
/*
스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다(유일하게 하나만 등록해서 공유한다)
따라서 같은 스프링 빈이면 모두 같은 인스턴스이다. 설정을 통해 변경할 수 있지만,
특별한 경우가 아니라면 대부분 싱글톤을 사용한다.
 */

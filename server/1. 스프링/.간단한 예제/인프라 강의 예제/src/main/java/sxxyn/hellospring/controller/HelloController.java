package sxxyn.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model){ //spring이 model을 만들어서 넣어줌
        model.addAttribute("data","hello!");
        return "hello";
    }

    @GetMapping("/hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }

    @GetMapping("/hello-string")
    @ResponseBody //http에서 body부에 이 데이터를 직접 넣어주겠다.
    public String helloString(@RequestParam("name") String name){
        return "hello "+name; // View가 없음. 문자 그대로 감
        //html 코드 X
    }

    //데이터를 필요로 할 때 API를 많이 사용함
    @GetMapping("/hello-api")
    @ResponseBody/*
    + HTTP의 body에 문자 내용을 직접 반환
    + `viewResolver` 대신에 `HttpMessageConverter`가 동작
    + 기본 문자처리 : `StringHttpMessageConverter`
    + 기본 객체처리 : `MappingJackson2HttpMessageConverter`
    + byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음
     */
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello; //JSON의 형태로 출력됨
    }
    static class Hello{
        private String name;

        //프로퍼티 접근 방식
        public String getName(){
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

}

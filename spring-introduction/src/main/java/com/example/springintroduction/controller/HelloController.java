package com.example.springintroduction.controller;

import com.example.springintroduction.model.Hello;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// MVC Pattern (Model - View - Model)
// @Controller annotation을 사용하면 해당 클래스를 Controller로서 사용하도록 해줌
// Controller에서는 사용자의 요청에 따라 Model을 가공, 전달함
@Controller
public class HelloController {


    // << MVC 템플릿 엔진 >>
    // api에서 /hello로 들어올 경우 해당 함수를 호출함
    // 사용자가 /hello로 접속할 경우 View는 hello.html을 연결하고,
    // Model클래스를 사용하여 "data"라는 키 값으로 "hello!"라는 데이터를 전달하는 역할을 수행함(Controller)
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!");
        return "hello";

        /* 컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버(viewResolver)가 화면을 찾아서 처리한다
        *  스프링 부트 탬플릿엔진 기본 viewName 매핑(resources - templates - hello.html)
        *  resources:templates/ + (ViewName) + '.html'*/
    }

    // 외부에서 url parameter로 name을 전달받음
    // @RequestParam("name") String name => String name은 외부에서 파라미터로 전달받을 값이라는 의미?
    // Command + p => 파라미터 정보 보기

    // 예시 : 파라미터로 name = spring! 을 전달하는 사례
    // locallhost:8080/hello-mvc?name=spring!

    @GetMapping("hello-mvc")
    public String helloMVC(@RequestParam(value = "name", required = false) String name, Model model){
        model.addAttribute("name",name); // "name" 키로 데이터 전달
        // 뷰 리졸버를 통해 hello-template.html을 찾아 화면을 렌더링한다.
        return "hello-template";
    }

    // << API 방식 예시 >>
    // @ResponseBody : html통신 프로토콜의 body부에 응답 내용을 직접 넣어주겠다는 의미
    // ResponseBody annotation이 있으면 뷰 리졸버로 연결하지 않는다.
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        // ex) name = spring => "hello spring"
        // ResponseBody annotation 으로 선언함으로서 요청한 클라이언트에 데이터("hello spring")가 그대로 전달됨
        return "hello " + name;
    }

    // Command + Shift + Enter => ; 자동완성
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();

        hello.setName(name);

        return hello;
    }


}

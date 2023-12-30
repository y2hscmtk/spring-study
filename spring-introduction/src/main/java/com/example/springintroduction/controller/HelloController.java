package com.example.springintroduction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// MVC Pattern (Model - View - Model)
// @Controller annotation을 사용하면 해당 클래스를 Controller로서 사용하도록 해줌
// Controller에서는 사용자의 요청에 따라 Model을 가공, 전달함
@Controller
public class HelloController {

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

    // 외부에서 url parameter로 name을 전달받음 => String name
    @GetMapping("hello-mvc")
    public String helloMVC(@RequestParam("name") String name, Model model){
        model.addAttribute("name",name); // "name" 키로 데이터 전달
        // 뷰 리졸버를 통해 hello-template.html을 찾아 화면을 렌더링한다.
        return "hello-template";
    }
}

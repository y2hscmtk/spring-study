package spring.springcorebasic.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.springcorebasic.common.MyLogger;

@Controller
@RequiredArgsConstructor // 멤버변수에 대한 의존관계 자동 주입
public class LogDemoController {

    private final LogDemoService logDemoService;
    //private final MyLogger myLogger;
    // 현재 request 스콥으로 웹스코프 빈으로 설정되어있음 => 요청이 들어와야 생성이됨 => RequireArgsConstructor 오류발생
    // => 없는 빈을 내놓으라고 하기 때문이다.
    // => Provider를 사용하여 해결 가능
    private final ObjectProvider<MyLogger> myLoggersProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        MyLogger myLogger = myLoggersProvider.getObject();
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("contorller test");
        logDemoService.logic("testId");

        return "OK";
    }
}

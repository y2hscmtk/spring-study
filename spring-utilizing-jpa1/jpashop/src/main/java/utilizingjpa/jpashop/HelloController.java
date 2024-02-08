package utilizingjpa.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) { // Model : 데이터를 실어서 넘기는 용도로 사용됨
        model.addAttribute("data", "hello!!!"); // data키로 hello!!값 넘김
        return "hello"; // resource - template - hello.html을 리턴한다.
    }
}

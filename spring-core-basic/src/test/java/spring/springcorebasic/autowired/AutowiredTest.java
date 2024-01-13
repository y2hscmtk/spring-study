package spring.springcorebasic.autowired;

import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import spring.springcorebasic.member.Member;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    // 임의의 테스트 클래스
    @Component
    static class TestBean {
        // Member 클래스는 스프링 빈으로 등록되어 있지 않기 때문에 컴포넌트 스캔시
        // setNoBean1~setNoBean3에서 매개변수로 Member를 받으면 오류가 발생한다. (Member == null)

        // false로 되어있다면 호출 자체가 되지 않는다. (컴포넌트 스캔 대상에서 예외로 처리한다.)
        @Autowired(required = false)
        public void setNoBean1(Member member) {
            System.out.println("noBean1 = " + member);
        }

        // Nullabel일 경우 컴포넌트 스캔시 스프링빈에 없는 인스턴스 일지라도 호출은 허용한다.
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }
    }


}

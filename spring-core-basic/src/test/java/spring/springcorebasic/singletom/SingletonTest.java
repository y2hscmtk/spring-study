package spring.springcorebasic.singletom;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.AppConfig;
import spring.springcorebasic.member.MemberService;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 수수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다른 것을 확인(다른 객체임을 확인)
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // 검증
        // memberService1 != memberService2
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);

        // => 스프링을 사용하지 않는 순수한 DI 컨테이너는 요청할 때마다 객체를 새로 생성한다.
        // => 메모리 낭비가 심하다! => 해결 방안은 싱글톤 패턴을 적용하는 것
    }
}

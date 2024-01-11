package spring.springcorebasic.singletom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.AppConfig;
import spring.springcorebasic.member.MemberService;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(memberService1).isNotSameAs(memberService2);

        // => 스프링을 사용하지 않는 순수한 DI 컨테이너는 요청할 때마다 객체를 새로 생성한다.
        // => 메모리 낭비가 심하다! => 해결 방안은 싱글톤 패턴을 적용하는 것
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        System.out.println("singletonService1 = " + singletonService1);
        SingletonService singletonService2 = SingletonService.getInstance();
        System.out.println("singletonService2 = " + singletonService2);

        // => 같은 인스턴스를 반환하는 것을 확인 할 수 있다.
        assertThat(singletonService1).isSameAs(singletonService2);
        // isEqual : 내부까지 전부 같은지 확인
        // isSame : 인스턴스가 같은지 비교
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        // 스프링 컨테이너는 구성정보(AppConfig.class)를 바탕으로 싱글톤 객체를 생성하여 스프링 빈으로 등록한다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 참조 값이 같은 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 == memberService2
        assertThat(memberService1).isSameAs(memberService2);
    }
}

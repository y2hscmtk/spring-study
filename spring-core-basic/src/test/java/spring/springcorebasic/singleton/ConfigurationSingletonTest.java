package spring.springcorebasic.singleton;


import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.AppConfig;
import spring.springcorebasic.member.MemberRepository;
import spring.springcorebasic.member.MemberServiceImpl;
import spring.springcorebasic.order.OrderServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);


        // 같은 객체가 생성되는지 확인
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        System.out.println("memberService -> memberRepository = " + memberRepository1);
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        System.out.println("orderService -> memberRepository = " + memberRepository2);

        System.out.println("memberRepository = " + memberRepository);
        // 영상이랑 달리 다른 인스턴스가 리턴됨 => 원인 파악 필요
        // 원인 => AppConfig의 memberRepository()함수가 private static으로 선언되어있었음
        // static 메소드로 선언할경우, 매번 다른 객체 인스턴스를 생성할 수 있다.

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean.getClass() = " + bean.getClass());

        // 출력값 : bean.getClass() = class spring.springcorebasic.AppConfig$$SpringCGLIB$$0
        // 순수 클래스가 아닌 $$SpringCGLIB$$0가 붙은 CGLIB을 상속받은 프록시 클래스가 생성됨을 확인할 수 있다.
        // CGLIB는 바이트코드조작 라이브러리로, 이 프록시 클래스로 인해 다른 클래스들의 싱글톤 객체 생성을 보장한다.
    }
}

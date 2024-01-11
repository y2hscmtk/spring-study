package spring.springcorebasic.singletom;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.AppConfig;
import spring.springcorebasic.member.MemberRepository;
import spring.springcorebasic.member.MemberServiceImpl;
import spring.springcorebasic.order.OrderServiceImpl;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberServiceImpl memberService = annotationConfigApplicationContext.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = annotationConfigApplicationContext.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = annotationConfigApplicationContext.getBean("memberRepository", MemberRepository.class);


        // 같은 객체가 생성되는지 확인
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        System.out.println("memberService -> memberRepository = " + memberRepository1);
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        System.out.println("orderService -> memberRepository = " + memberRepository2);

        System.out.println("memberRepository = " + memberRepository);
        // 영상이랑 달리 다른 인스턴스가 리턴됨 => 원인 파악 필요
    }
}

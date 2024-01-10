package spring.springcorebasic.beanfind;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.AppConfig;
import spring.springcorebasic.member.MemberService;
import spring.springcorebasic.member.MemberServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        // 같은 인스턴스인지 검증
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }


    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        // 타입으로 빈 조회
        MemberService memberService = ac.getBean(MemberService.class);
        // 같은 인스턴스인지 검증
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2() {
        // 구체 타입으로 빈 조회하기 => 구체적으로 적는것은 좋은 방식은 아님(구현에 의존하지 않고 역할에 의존해야함)
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        // 같은 인스턴스인지 검증
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회 실패")
    void findBeanByNameX() {
        //MemberService memberService = ac.getBean("xxxx", MemberService.class);
        // Exception :  No bean named 'xxxx' available

        // 람다식을 수행하면 NoSuchBeanDefinitionException이 발생해야 한다는 의미
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("xxxx", MemberService.class));
    }
}


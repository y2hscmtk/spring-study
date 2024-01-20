package spring.springcorebasic.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @Test
    void prototypeBeanTest() {
        // 싱글톤 빈튼 스프링 컨테이너의 생성 시점에 초기화 메서드가 실행되지만,
        // 프로토타입 빈은 스프링 컨테이너에서 빈을 조회할때 생성되고, 초기화 메서드도 실행된다.
        // 프로토타입 빈은 싱글톤 컨테이너가 생성과 초기화까지만 관여한다.
        // 즉 프로토타입은 싱글톤으로 생성되지 않으며, 프로토타입 빈에 대한 책임은 빈을 반환받은 클라이언트에게 있다.
        // 따라서 @PreDestory가 동작하지 않는다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean bean = ac.getBean(PrototypeBean.class);
        System.out.println("bean = " + bean);
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        System.out.println("bean2 = " + bean2);

        // 싱글톤이 아님을 검증
        Assertions.assertThat(bean).isNotSameAs(bean2);

        // ApplicationContext가 종료되어도, 프로토타입 빈에 대한 책임은 스프링 컨테이너에 있지 않으므로
        // @Destroy가 되지 않는다.
        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }

    }

}

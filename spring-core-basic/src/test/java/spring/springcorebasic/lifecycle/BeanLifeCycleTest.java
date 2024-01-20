package spring.springcorebasic.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        // 1. 스프링 빈 등록시 NetworkClient()가 호출됨 => url은 당연히 null
        // 2. connect 호출 -> url 여전히 null

        // 모종의 이유로 외부에서 값을 먼저 세팅한 후, 객체를 초기화 할 일이 존재함
        // 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야함 -> 개발자가 어떻게 그 시점을 알 수 있나

        // 스프링에서는 의존관계 주입이 완료되면 스프링 빈에게 초기화 시점을 알려주는 기능을 제공함(콜백)

        // 객체의 생성과 초기화를 분리하자. => 단일 책임의 원칙
        // 생성자에서 해도 되는 일 : 객체 내부에 어떤 값을 세팅
        // 초기화 : 외부연결, 무거운 작업

        // 1. 인터페이스
        // 2. 설정정보에 초기화 메서드, 종료 메서드 지정
        // 3. @PoistConstruct, @PreDestory 애노테이션 지원

        // 빈 등록 초기화, 소멸 메소드 지정(networkClient에 init, close라는 이름의 함수를 등록)
        //@Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("https://hello-spring.dev");
            return networkClient;
        }
    }

}

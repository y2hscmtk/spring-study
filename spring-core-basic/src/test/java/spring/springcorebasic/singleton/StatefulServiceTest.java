package spring.springcorebasic.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {


    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //다중 쓰레드 환경이라고 가정
        //Thread1 : A사용자 10000원 주문
        int userAPrice = statefulService1.order("userA",10000);
        //Thread2 : B사용자 20000원 주문
        int userBPrice = statefulService2.order("userB",20000);

        //ThreadA : 사용자A 주문 금액 조회
        //int price = statefulService1.getPrice();

        //같은 객체 인스턴스를 사용하기 때문에, price의 값이 유지되어 사용자A의 price가 사용자B의 price로 덮어씌워진다.
        System.out.println("price = " + userAPrice);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }


}
package spring.springcorebasic;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;
import spring.springcorebasic.member.MemberService;
import spring.springcorebasic.member.MemberServiceImpl;
import spring.springcorebasic.order.Order;
import spring.springcorebasic.order.OrderService;
import spring.springcorebasic.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        //MemberService memberService = new MemberServiceImpl();
        //OrderService orderService = new OrderServiceImpl();

        // appConfig를 사용해서 의존관계를 주입하도록 변경
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();


        // 전체 프로그램의 문맥(applicationContext)를 스프링 컨테이너라고 한다.
        // 기존에는 개발자가 직접 AppConfig을 생성하고 DI를 하였으나, 이제부터는 스프링 컨테이너를 통해서 사용한다.
        // 스프링 컨테이너를 통해서 필요한 객체를 찾아서 가져올 수 있다.
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // 필요한 빈 찾아와서 사용 => 스프링 컨테이너(DI)에서 이미 생성된 객체가 존재함
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 1000);

        System.out.println("order = " + order);
    }
}

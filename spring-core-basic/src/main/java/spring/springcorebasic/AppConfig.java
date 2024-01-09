package spring.springcorebasic;

import spring.springcorebasic.discount.FixDiscountPolicy;
import spring.springcorebasic.member.MemberService;
import spring.springcorebasic.member.MemberServiceImpl;
import spring.springcorebasic.member.MemoryMemberRepository;
import spring.springcorebasic.order.OrderService;
import spring.springcorebasic.order.OrderServiceImpl;

// 어플리케이션의 전반적인 부분을 설정(config)하고 조정하는 클래스
// 더이상 Impl에서 역할 배정을 하지 않고, 이 클래스에서 역할 배정을 수행한다.
public class AppConfig {

    // 멤버 서비스 역할 배정
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    // 주문 서비스 역할 배정
    // DIP를 만족하도록 OrderServiceImpl을 생성자 주입으로 의존관계 주입 설정
    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(),new FixDiscountPolicy());
    }



}

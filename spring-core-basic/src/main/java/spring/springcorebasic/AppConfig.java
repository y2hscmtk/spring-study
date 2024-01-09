package spring.springcorebasic;

import spring.springcorebasic.discount.DiscountPolicy;
import spring.springcorebasic.discount.FixDiscountPolicy;
import spring.springcorebasic.discount.RateDiscountPolicy;
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
        // Command + Option + m => 부분 함수화
        return new MemberServiceImpl(memberRepository());
    }

    private static MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    // 주문 서비스 역할 배정
    // DIP를 만족하도록 OrderServiceImpl을 생성자 주입으로 의존관계 주입 설정
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(),discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy(); // 할인 정책 변경시, 이정도의 코드 수정으로 충분하다.
    }



}

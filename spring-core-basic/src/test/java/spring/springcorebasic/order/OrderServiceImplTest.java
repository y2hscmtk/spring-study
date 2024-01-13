package spring.springcorebasic.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import spring.springcorebasic.discount.FixDiscountPolicy;
import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;
import spring.springcorebasic.member.MemoryMemberRepository;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    void createOrder() {
        // 생성자 주입을 사용하면 임의의 객체를 직접 넣어서 테스트 가능하다.
        // 수정자 주입일 경우, 오류의 원인을 파악하기 어렵다.
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L,"name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository,new FixDiscountPolicy());
        Order order = orderService.createOrder(1L,"itemA",10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}
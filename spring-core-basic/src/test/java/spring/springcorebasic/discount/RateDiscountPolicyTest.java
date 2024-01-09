package spring.springcorebasic.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;
import static org.assertj.core.api.Assertions.assertThat;

// 실제로 10프로 할인이 되는지 검증
class RateDiscountPolicyTest {

    // 비율에 따른 할인 정책으로 구현체 설정
    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다.") // 로그에 표시되는 문구 설정 가능
    void vip_o() {
        //given
        Member member = new Member(1L, "vip", Grade.VIP);
        //when
        int discout = discountPolicy.discount(member, 10000);
        //then
        assertThat(discout).isEqualTo(1000); // 1000원이 할인되는지 확인 Option + Enter -> Static Import
    }

    @Test
    @DisplayName("VIP가 아닌 회원은 할인이 적용되지 않아야 한다.")
    void vip_x() {
        //given
        Member member = new Member(2L, "memberBasic", Grade.BASIC);
        //when
        int discount = discountPolicy.discount(member, 1000);
        //then
        assertThat(discount).isEqualTo(0); // 할인금액이 0원 이어야함
    }

}
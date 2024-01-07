package spring.springcorebasic.discount;

import spring.springcorebasic.member.Member;

public interface DiscountPolicy {

    /**
     * @return 할인 대상 금액 => 사용자의 등급에 따라 얼마나 할인할것인지
     * */
    int discount(Member member, int price);
}

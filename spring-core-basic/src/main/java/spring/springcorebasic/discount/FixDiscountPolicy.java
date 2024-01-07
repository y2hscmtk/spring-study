package spring.springcorebasic.discount;

import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;

// 무조건 1000원을 할인해주는 정책
public class FixDiscountPolicy implements DiscountPolicy{
    private int discountFixAmount = 1000; // 1000원으로 고정할인
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) { // VIP 등급인 경우에만 1000원 할인
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}

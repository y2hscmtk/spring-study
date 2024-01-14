package spring.springcorebasic.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spring.springcorebasic.annotation.MainDiscountPolicy;
import spring.springcorebasic.member.Grade;
import spring.springcorebasic.member.Member;

// 무조건 1000원을 할인해주는 정책
@Component // Fix와 Rate 모두 스프링 빈으로 등록한 상황 (조회하는 빈이 2개 이상)
//@Qualifier("mainDiscountPolicy") // Qualifier는 조회할 빈이 여러개일때 추가 구분자를 지정한다. 같은 구분자끼리 연결된다.(OrderServiceImpl)
@MainDiscountPolicy // 커스텀으로 제작 위 어노테이션과 동일한 기능을 수행
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

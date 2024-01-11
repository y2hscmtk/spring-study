package spring.springcorebasic.order;

import spring.springcorebasic.discount.DiscountPolicy;
import spring.springcorebasic.discount.FixDiscountPolicy;
import spring.springcorebasic.discount.RateDiscountPolicy;
import spring.springcorebasic.member.Member;
import spring.springcorebasic.member.MemberRepository;
import spring.springcorebasic.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    // 회원의 등급을 확인하기 위함 => 할인정책 적용을 위해
    private final MemberRepository memberRepository;
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    // 현재 상황에서 할인 정책을 변경하려면 실제 소스코드(구현체)를 변경해야한다.
    // new 키워드를 사용하여 구현체를 직접 의존관계로 설정해주었기 때문에, 추상에 의존하지 않고 구현에 의존함으로서 DIP를 위반한다.
    private final DiscountPolicy discountPolicy;

    // 생성자 주입
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); // 사용자의 등급을 통해 할인정책을 달리 하기 위함
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice); // 주문을 생성하여 반환
    }

    // @Configuration 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

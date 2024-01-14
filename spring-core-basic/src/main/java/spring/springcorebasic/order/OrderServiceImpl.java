package spring.springcorebasic.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spring.springcorebasic.discount.DiscountPolicy;
import spring.springcorebasic.member.Member;
import spring.springcorebasic.member.MemberRepository;

@Component // 컴포넌트 스캔 대상으로 설정
// @RequiredArgsConstructor // final 키워드가 붙은 매개변수를 대상으로 생성자를 자동으로 만들어준다.(final = Required)
// 롬복이 자바의 어노테이션 프로세서 기능을 이용하여 컴파일 시점에 생성자 코드를 자동으로 생성한다.
public class OrderServiceImpl implements OrderService{

    // 회원의 등급을 확인하기 위함 => 할인정책 적용을 위해
    private final MemberRepository memberRepository; // 생성자 주입의 장점 => final 키워드 사용 가능(수정 불가)
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    // 현재 상황에서 할인 정책을 변경하려면 실제 소스코드(구현체)를 변경해야한다.
    // new 키워드를 사용하여 구현체를 직접 의존관계로 설정해주었기 때문에, 추상에 의존하지 않고 구현에 의존함으로서 DIP를 위반한다.
    private final DiscountPolicy discountPolicy;

    // Autowired 를 사용하면, 스프링 컨테이너에서 동일한 '타입'의 인스턴스를 찾아 의존관계를 주입한다.
    @Autowired // 의존관계 자동 주입 // 생성자 주입
    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
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

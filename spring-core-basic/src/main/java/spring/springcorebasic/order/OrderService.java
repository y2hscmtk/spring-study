package spring.springcorebasic.order;

// 주문 서비스 역할(인터페이스)
public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
    // 최종 오더 결과를 반환한다.(실제 서비스에서는 db에 저장해야함)
}

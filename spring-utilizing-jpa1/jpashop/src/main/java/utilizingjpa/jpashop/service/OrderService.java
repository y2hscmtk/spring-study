package utilizingjpa.jpashop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilizingjpa.jpashop.domain.Delivery;
import utilizingjpa.jpashop.domain.Member;
import utilizingjpa.jpashop.domain.Order;
import utilizingjpa.jpashop.domain.OrderItem;
import utilizingjpa.jpashop.domain.item.Item;
import utilizingjpa.jpashop.repository.ItemRepository;
import utilizingjpa.jpashop.repository.MemberRepository;
import utilizingjpa.jpashop.repository.OrderRepository;
import utilizingjpa.jpashop.repository.OrderSearch;

import java.util.List;

@Service
@Transactional(readOnly = true) // 트랜젝션이 필요 없는 모든 작업에 대해서는 readOnly모드로 활성화
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional // readOnly = false
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem); // 연관관계 설정까지 모두 완료됨

        //주문 저장 => Cascade 설정을 했기 때문에 order를 persist하면 order의 컬렉션인 orderItems도 모두 persist를 해준다.
        //order만 저장해도 orderItem과 delivery가 모두 persist된다.
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    /**
     * 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

}

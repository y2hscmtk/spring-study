package utilizingjpa.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import utilizingjpa.jpashop.api.OrderSimpleApiController;
import utilizingjpa.jpashop.domain.Member;
import utilizingjpa.jpashop.domain.Order;

import java.util.ArrayList;
import java.util.List;

// 레파지토리는 가급적 순수한 엔티티를 조회할 때 사용
@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    // 저장
    public void save(Order order) {
        em.persist(order);
    }

    // 조회
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    // 모든 상품 조회 - 방법 1 : 동적 쿼리
    public List<Order> findAllByString(OrderSearch orderSearch) {

        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    /**
     * 방법 2 : JPA Criteria
     * 표준 기술이지만 권장되지 않는다. (너무 복잡하다, 유지보수성이 높지 않다.)
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    // Fetch Join
    // N+1 문제를 해결하기 위해 한번의 조회시, 연관되어 있는 Member와 Delivery 모두 조인해서 가져온다.
    // 이러한 경우 Lazy 설정이 되어있더라도 값을 모두 가져온다.
    // xToOne 관계로 되어있는 값들에 대해서는 페치 조인을 하더라도 Row수가 증가되지 않는다.
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.member m" + // fetch는 JPA에만 존재
                                " join fetch o.delivery d", Order.class)
                .getResultList();
    }

    // 패치 조인
    // 한번에 연관되어 있는 테이블을 조회한 뒤 한번에 반환한다.
    // => 조인으로 인해 중복되는 값이 발생할 수 있다.(DB 입장에서)
    // => o가 2개고, 종속되어있는 값이 각각 2개라면 4개가 반환된다.
    // => JPA의 distinct를 사용하면 id가 같은(인스턴스가 같은) 엔티티에 대해 중복값을 합쳐서 반환하다.
    // 데이터베이스의 distinct는 한 줄의 모든 값이 전부 동일한 경우에만 중복이 제거되지만
    // JPA에서는 id가 같다면 같은 인스턴스로 취급하여 중복을 제거해준다.
    // 주의 : 컬렉션 패치조인(일대다 패치조인)은 페이징이 불가능해진다는 단점이 있다.
    // => 메모리 올린후 메모리 페이징이 이뤄지기 때문(메모리 초과 위험이 있다.)
    public List<Order> findAllWithItem() {
        return em.createQuery(
                        "select distinct o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d" +
                                " join fetch o.orderItems oi" +
                                " join fetch oi.item i", Order.class)
                .getResultList();
    }

    // 페이징 쿼리 작성
    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}

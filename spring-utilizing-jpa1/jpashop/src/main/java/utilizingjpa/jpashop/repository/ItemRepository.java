package utilizingjpa.jpashop.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import utilizingjpa.jpashop.domain.item.Item;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em; // by RequiredArgsConstructor

    public void save(Item item) {
        if (item.getId() == null) { // 아이디가 없다 -> 새로 생성된 객체라는 뜻
            em.persist(item); // 신규 등록
        } else { // 아이디가 존재한다
            em.merge(item); // 업데이트 처리
        }
    }

    // 하나 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    // 모든 상품 조회
    public List<Item> findAll() {
        return em.createQuery("select  i from Item i", Item.class)
                .getResultList();
    }

}

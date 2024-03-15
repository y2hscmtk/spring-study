package utilizingjpa.jpashop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utilizingjpa.jpashop.domain.item.Item;
import utilizingjpa.jpashop.repository.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 1. 변경 감지 기능 사용
    @Transactional
    public Item updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId); // id를 기반으로 DB에서 값 찾아옴
        // => DB에 존재하는 값을 가져왔으므로 영속 상태가 됨
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        // 영속 상태는 값을 변경하기만 하면, JQuery에 의해 관리되므로 변경감지 매커니즘에 의해
        // 트랜젝션이 끝난 이후 flush될 때 변경된 값을 변경하는 업데이트 쿼리가 생성되어 값이 변경된다.
        return findItem; // 변경된 엔티티 반환
    }

    // 2. 병합 사용

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}

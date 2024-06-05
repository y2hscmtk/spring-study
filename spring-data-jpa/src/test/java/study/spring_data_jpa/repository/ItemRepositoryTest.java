package study.spring_data_jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.spring_data_jpa.entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void save() {
        Item item = new Item("a");
        // Spring Data JPA save() 알고리즘
        // - 새로운 엔티티면 저장 persist
        // - 새로운 엔티티가 아니라면 병합 merge

        // 새로운 엔티티를 판단하는 기본 전략
        // 식별자가 객체일 때는 id가 null인지 아닌지로 판단(@GeneratedValue Lond id)
        // => GeneratedValue가 활성화되어있지 않다면 => merge로 넘어간 후, DB에서 값이 존재하는지 검색 후 저장한다.(비효율적)
        // 식별자가 자바 기본 타입(String,등)일 때 0으로 판단
        // Persistable 인터페이스를 구현하여 판단 로직 변경 가능

        itemRepository.save(item);
    }

}
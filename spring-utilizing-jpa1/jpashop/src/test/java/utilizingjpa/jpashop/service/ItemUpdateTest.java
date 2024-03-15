package utilizingjpa.jpashop.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utilizingjpa.jpashop.domain.item.Book;

@SpringBootTest
public class ItemUpdateTest {
    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        //given
        Book book = em.find(Book.class, 1L);

        //when
        book.setName("changedName"); // 변경된다면 => JQuery가 변경 감지 수행(dirty checking)
        // DB에 업데이트 쿼리를 따로 날린적 없으나, 상태 변경을 감지하고 JQuery가 commit시점에 변경 쿼리를 생성함

        //then
    }

}

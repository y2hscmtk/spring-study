package utilizingjpa.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

// 데이터를 받아오기 위한 형태
@Getter @Setter
public class BookForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;
}

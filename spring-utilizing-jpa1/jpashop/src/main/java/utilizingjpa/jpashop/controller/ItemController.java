package utilizingjpa.jpashop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import utilizingjpa.jpashop.domain.item.Book;
import utilizingjpa.jpashop.service.ItemService;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    // 폼 생성
    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        // 실무에서는 setter를 사용하지 않고 생성 메소드를 Book 엔티티 내부에 만들어 두는 것이 좋음
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/"; // 초기 화면으로 이동
    }
}

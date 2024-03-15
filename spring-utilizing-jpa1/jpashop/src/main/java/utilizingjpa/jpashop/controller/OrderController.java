package utilizingjpa.jpashop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utilizingjpa.jpashop.domain.Member;
import utilizingjpa.jpashop.domain.item.Item;
import utilizingjpa.jpashop.service.ItemService;
import utilizingjpa.jpashop.service.MemberService;
import utilizingjpa.jpashop.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        // 어떤 고객이 어떤 아이템을 몇개 구매할 것인지
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }
}

package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.service.ItemService;
import org.springframework.ui.Model;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book = new Book();

        book.setName(form.getTitle());
        book.setAuthor(form.getAuthor());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemID}/edit")
    public String updateItemForm(@PathVariable("itemID") Long itemID, Model model) {
        Book item = (Book) itemRepository.findOne(itemID);

        BookForm form = new BookForm();
        form.setId(itemID);
        form.setTitle(item.getName());
        form.setAuthor(item.getAuthor());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/editItemForm";
    }



    @PostMapping("items/{itemID}/edit")
    public String update(BookForm form, @PathVariable("itemID") Long itemID) {

        Book book = (Book) itemRepository.findOne(itemID);

        book.setId(itemID);
        book.setName(form.getTitle());
        book.setAuthor(form.getAuthor());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());

        itemService.updateItem(itemID, book);

        return "redirect:/items";
    }

    @PostMapping("/items/{itemId}/delete")
    public String delete(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
        return "redirect:/items";
    }



}

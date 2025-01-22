package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.NotEnoughStockException;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Item.Book;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.Assert.*;

public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    OrderService orderService;
    OrderRepository orderRepository;

    @Test
    public void orderService() throws Exception {

        //Given
        Member member = createMember();
        Item item = createBook("JPA complete", 10000, 10);
        int orderCount = 2;

        //When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("orderStatus should be ORDER", OrderStatus.Order, getOrder.getStatus());
        assertEquals("number of merchandise should be correct", 1, getOrder.getOrderItems().size());
        assertEquals("orderprice is price * amount of items", 10000*2, getOrder.getTotalPrice());
        assertEquals("stock should be minus with order quantity", 8, item.getStockQuantity());
    }

    @Test
    public void cancelOrder() {
        //Given
        Member member = createMember();
        Item item = createBook("JPA complete", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //When
        orderService.cancelOrder(orderId);

        //Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("when cancel the order, orderStatus should be CANCEL", getOrder.getStatus(), OrderStatus.Cancel);
        assertEquals("number of merchandise should be correct", 10, item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void order_overflow() throws Exception {

        //Given
        Member member = createMember();
        Item item = createBook("JPA complete", 10000, 10);
        int orderCount = 11;

        //When
        orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        fail("error occurred");

    }


    private Member createMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("Seoul", "hannam", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String title, int price, int quantity) {
        Book book = new Book();
        book.setName(title);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }
}
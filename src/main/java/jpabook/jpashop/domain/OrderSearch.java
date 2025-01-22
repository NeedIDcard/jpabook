package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getMemberName() {
        return memberName;
    }
    //Getter, Setter
}

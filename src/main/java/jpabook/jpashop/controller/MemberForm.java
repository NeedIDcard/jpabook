package jpabook.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.validation.annotation.Validated;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "name is necessary")
    private String name;

    private String city;
    private String street;
    private String zip;

}

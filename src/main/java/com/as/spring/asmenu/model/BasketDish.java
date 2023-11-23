package com.as.spring.asmenu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "basket_dish")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BasketDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "quantity")
    private Integer quantity;


    public BasketDish(Basket basket, Dish dish, Integer quantity) {
        this.basket = basket;
        this.dish = dish;
        this.quantity = quantity;
    }

}

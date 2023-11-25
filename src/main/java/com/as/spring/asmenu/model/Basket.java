package com.as.spring.asmenu.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "basket")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_is_ready")
    private boolean orderIsReady;

    @Column(name = "total_quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private Double totalPrice;


    @ToString.Exclude
    @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<BasketDish> basketDishes;

    @OneToOne(mappedBy = "basket",
            cascade = CascadeType.ALL)
    private User user;

    public Basket(boolean orderIsReady, Integer quantity, Double totalPrice) {
        this.orderIsReady = orderIsReady;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Basket(boolean orderIsReady, Integer quantity, Double totalPrice, List<BasketDish> basketDishes) {
        this.orderIsReady = orderIsReady;
        this.quantity = quantity;
        this.totalPrice=totalPrice;
        this.basketDishes = basketDishes;
    }



}

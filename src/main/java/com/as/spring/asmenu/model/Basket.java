package com.as.spring.asmenu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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


    @Column(name = "total_quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private Double totalPrice;


    @ToString.Exclude
    @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<BasketDish> basketDishes;

    @ToString.Exclude
    @OneToOne(mappedBy = "basket",
            cascade = CascadeType.ALL)
    private User user;

    public Basket(Integer quantity, Double totalPrice) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }





}

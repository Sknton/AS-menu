package com.as.spring.asmenu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "order_dish")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "order_id")
    private Order order;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "dish_id")
    private Dish dish;

    public OrderDish(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderDish(Integer quantity, Order order, Dish dish) {
        this.quantity = quantity;
        this.order = order;
        this.dish = dish;
    }
}

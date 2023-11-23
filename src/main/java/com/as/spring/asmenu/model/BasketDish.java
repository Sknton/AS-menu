package com.as.spring.asmenu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "basket_dish")
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

    public BasketDish() {
    }

    public BasketDish(Basket basket, Dish dish, Integer quantity) {
        this.basket = basket;
        this.dish = dish;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BasketDish{" +
                "id=" + id +
                ", basket=" + basket +
                ", dish=" + dish +
                ", quantity=" + quantity +
                '}';
    }
}

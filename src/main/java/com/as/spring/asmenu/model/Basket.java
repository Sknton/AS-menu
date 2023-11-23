package com.as.spring.asmenu.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "basket")
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

    @OneToMany(mappedBy = "basket", fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    private List<BasketDish> basketDishes;

    public Basket() {
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isOrderIsReady() {
        return orderIsReady;
    }

    public void setOrderIsReady(boolean orderIsReady) {
        this.orderIsReady = orderIsReady;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<BasketDish> getBasketDishes() {
        return basketDishes;
    }

    public void setBasketDishes(List<BasketDish> basketDishes) {
        this.basketDishes = basketDishes;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", orderIsReady=" + orderIsReady +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

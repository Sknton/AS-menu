package com.as.spring.asmenu.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

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
    private float totalPrice;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(name = "basket_dish",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private Collection<Dish> dishes;

    public Basket() {
    }

    public Basket(boolean orderIsReady, Integer quantity, float totalPrice) {
        this.orderIsReady = orderIsReady;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Basket(boolean orderIsReady, Integer quantity, float totalPrice, Collection<Dish> dishes) {
        this.orderIsReady = orderIsReady;
        this.quantity = quantity;
        this.totalPrice=totalPrice;
        this.dishes = dishes;
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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Collection<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Collection<Dish> dishes) {
        this.dishes = dishes;
    }

    public void addDish(Dish dish){
        if (this.dishes == null){
            dishes = new ArrayList<>();
        }
        dishes.add(dish);
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

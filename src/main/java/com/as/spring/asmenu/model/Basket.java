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

    @Column(name = "quantity")
    private int quantity;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(name = "dish_basket",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private Collection<Dish> dishes;

    public Basket() {
    }

    public Basket(boolean orderIsReady, int quantity) {
        this.orderIsReady = orderIsReady;
        this.quantity = quantity;
    }

    public Basket(boolean orderIsReady, int quantity, Collection<Dish> dishes) {
        this.orderIsReady = orderIsReady;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
        this.quantity+=1;

    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", orderIsReady=" + orderIsReady +
                ", quantity=" + quantity +
                '}';
    }
}

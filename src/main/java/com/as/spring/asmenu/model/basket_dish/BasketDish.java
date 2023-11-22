package com.as.spring.asmenu.model.basket_dish;

import jakarta.persistence.*;

@Entity
@Table(name = "basket_dish")
public class BasketDish {
    @EmbeddedId
    private BasketDishId basketDishId;

    @Column(name = "quantity")
    private Integer quantity;

    public BasketDish() {
    }

    public BasketDish(Integer quantity) {
        this.quantity = quantity;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BasketDishId getBasketDishId() {
        return basketDishId;
    }

    public void setBasketDishId(BasketDishId basketDishId) {
        this.basketDishId = basketDishId;
    }

    @Override
    public String toString() {
        return "BasketDish{" +
                "basketDishId=" + basketDishId +
                ", quantity=" + quantity +
                '}';
    }
}

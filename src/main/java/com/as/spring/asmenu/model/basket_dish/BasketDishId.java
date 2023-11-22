package com.as.spring.asmenu.model.basket_dish;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BasketDishId implements Serializable {
    @Column(name = "dish_id")
    private Long dishId;

    @Column(name = "basket_id")
    private Long basketId;

    public BasketDishId() {
    }

    public BasketDishId(Long dishId, Long basketId) {
        this.dishId = dishId;
        this.basketId = basketId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }


    @Override
    public String toString() {
        return "BasketDishId{" +
                "dishId=" + dishId +
                ", basketId=" + basketId +
                '}';
    }
}

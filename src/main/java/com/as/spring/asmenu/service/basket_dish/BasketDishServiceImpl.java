package com.as.spring.asmenu.service.basket_dish;

import com.as.spring.asmenu.dao.BasketDishRepository;
import com.as.spring.asmenu.model.basket_dish.BasketDish;
import com.as.spring.asmenu.model.basket_dish.BasketDishId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketDishServiceImpl implements BasketDishService {

    private BasketDishRepository basketDishRepository;

    @Autowired
    public BasketDishServiceImpl(BasketDishRepository basketDishRepository) {
        this.basketDishRepository = basketDishRepository;
    }

    //Set quantity of the dish in basket
    @Override
    public void setQuantityOfDishInBasket(Long dishId, Long basketId, Integer quantity) {
        System.out.println(basketDishRepository.findAll());
        BasketDishId basketDishId = new BasketDishId(dishId, basketId);
        BasketDish basketDish = basketDishRepository.findById(basketDishId)
                .orElseThrow(() -> new EntityNotFoundException("BasketDish not found with id: " + basketDishId));
        Integer totalNumberOfDishes;
        if (basketDish.getQuantity() == 0){
            totalNumberOfDishes=quantity;
        }else {
            totalNumberOfDishes = basketDish.getQuantity() + quantity;
        }

        basketDish.setQuantity(totalNumberOfDishes);
        basketDishRepository.save(basketDish);
        System.out.println(basketDishRepository.findAll());
    }

    @Override
    public List<BasketDish> findAll() {
        return basketDishRepository.findAll();
    }
}

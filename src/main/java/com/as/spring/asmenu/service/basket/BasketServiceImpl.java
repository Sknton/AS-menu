package com.as.spring.asmenu.service.basket;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.BasketDish;
import com.as.spring.asmenu.model.Dish;
import com.as.spring.asmenu.repository.BasketDishRepository;
import com.as.spring.asmenu.repository.BasketRepository;
import com.as.spring.asmenu.repository.DishRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService{

    private final BasketRepository basketRepository;
    private final DishRepository dishRepository;
    private final BasketDishRepository basketDishRepository;



    @Override
    public void save(Basket basket) {
        basketRepository.save(basket);
    }


    @Override
    public Basket findById(Long id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No such Basket with id " + id));
    }

    @Override
    @Transactional
    public void addToBasket(Long basketId, Long dishId, Integer quantity) {

        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new EntityNotFoundException("Basket not found with id: " + basketId));

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found with id: " + dishId));


        basket.setQuantity(basket.getQuantity()+quantity);
        basket.setTotalPrice(basket.getTotalPrice()+quantity*dish.getPrice());
        basketRepository.save(basket);

        saveBasketDish(basket, dish, quantity);

    }

    //Save dish in basket
    private void saveBasketDish(Basket basket, Dish dish, Integer quantity){
        BasketDish basketDish = basketDishRepository.findByBasketAndDish(basket, dish);
        if (basketDish == null) {
            basketDish = new BasketDish(basket, dish, 0);
        }
        basketDish.setQuantity(basketDish.getQuantity()+quantity);
        basketDishRepository.save(basketDish);
    }



    @Override
    @Transactional
    public void deleteFromBasket(Long basketId, Long dishId, Integer quantity) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new EntityNotFoundException("Basket not found with id: " + basketId));

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found with id: " + dishId));

        basket.setQuantity(basket.getQuantity()-quantity);
        basket.setTotalPrice(basket.getTotalPrice()-quantity*dish.getPrice());
        basketRepository.save(basket);

        deleteBasketDish(basket, dish, quantity);
    }

    private void deleteBasketDish(Basket basket, Dish dish, Integer quantity) {
        BasketDish basketDish = basketDishRepository.findByBasketAndDish(basket, dish);
        if(basketDish.getQuantity()<=quantity){
            basketDishRepository.delete(basketDish);
        }else {
            basketDish.setQuantity(basketDish.getQuantity()-quantity);
            basketDishRepository.save(basketDish);
        }

    }
}

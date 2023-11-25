package com.as.spring.asmenu.service.basket;

import com.as.spring.asmenu.repository.BasketRepository;
import com.as.spring.asmenu.repository.BasketDishRepository;
import com.as.spring.asmenu.repository.DishRepository;
import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.BasketDish;
import com.as.spring.asmenu.model.Dish;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService{
    private BasketRepository basketRepository;
    private DishRepository dishRepository;

    private BasketDishRepository basketDishRepository;

    @Autowired
    public BasketServiceImpl(BasketRepository basketRepository, DishRepository dishRepository, BasketDishRepository basketDishRepository){
        this.basketRepository=basketRepository;
        this.dishRepository=dishRepository;
        this.basketDishRepository = basketDishRepository;
    }

    @Override
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
    public Basket findById(Long id) {
        Optional<Basket> basket = basketRepository.findById(id);

        if (basket.isPresent()){
            return basket.get();
        }else {
            throw new RuntimeException("No such Basket with id " + id);
        }
    }

    @Override
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

package com.as.spring.asmenu.service.basket;

import com.as.spring.asmenu.dao.BasketRepository;
import com.as.spring.asmenu.dao.BasketDishRepository;
import com.as.spring.asmenu.dao.DishRepository;
import com.as.spring.asmenu.model.Basket;
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
    public void addToBasket(Long dishId, Long basketId, Integer quantity) {
        System.out.println(basketDishRepository.findAll());

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found with id: " + dishId));

        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new EntityNotFoundException("Basket not found with id: " + basketId));
        //Check if basket already has the dish
        if (!basket.getDishes().contains(dish)) {
            basket.addDish(dish);
        }
        basket.setQuantity(basket.getQuantity()+quantity);
        basket.setTotalPrice(basket.getTotalPrice()+quantity*dish.getPrice());
        basketRepository.save(basket);

        System.out.println(basketDishRepository.findAll());


    }




    @Override
    public Basket findBasketById(long id) {
        Optional<Basket> basket = basketRepository.findById(id);

        if (basket.isPresent()){
            return basket.get();
        }else {
            throw new RuntimeException("No such Basket with id " + id);
        }
    }


}

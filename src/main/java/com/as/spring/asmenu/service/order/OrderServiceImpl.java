package com.as.spring.asmenu.service.order;

import com.as.spring.asmenu.model.*;
import com.as.spring.asmenu.repository.BasketDishRepository;
import com.as.spring.asmenu.repository.BasketRepository;
import com.as.spring.asmenu.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final BasketDishRepository basketDishRepository;


    @Override
    @Transactional
    public void save(Order order) {
        User user = order.getUser();
        order.setOrderDishes(createOrderDishes(order, user.getBasket().getBasketDishes()));
        orderRepository.save(order);

        clearBasket(order.getUser().getBasket());
    }


    private List<OrderDish> createOrderDishes(Order order, List<BasketDish> basketDishes) {
        List<OrderDish> orderDishes = new ArrayList<>();
        for (BasketDish basketDish : basketDishes) {
            OrderDish orderDish = new OrderDish(basketDish.getQuantity(), order, basketDish.getDish());
            orderDishes.add(orderDish);
        }
        return orderDishes;
    }

    private void clearBasket(Basket basket) {
        List<BasketDish> basketDishes = new ArrayList<>(basket.getBasketDishes());
        for (BasketDish basketDish : basketDishes) {
            basket.removeBasketDish(basketDish);
            basketDishRepository.delete(basketDish);
        }
        basket.setQuantity(0);
        basket.setTotalPrice(0D);
        basketRepository.save(basket);
    }

}

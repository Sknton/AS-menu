package com.as.spring.asmenu.service.order;

import com.as.spring.asmenu.model.*;
import com.as.spring.asmenu.repository.BasketDishRepository;
import com.as.spring.asmenu.repository.BasketRepository;
import com.as.spring.asmenu.repository.OrderRepository;
import com.as.spring.asmenu.service.mail.MailSender;
import jakarta.persistence.EntityNotFoundException;
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
    private final MailSender mailSender;



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

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
    }

    @Override
    public void orderIsDelivered(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
        User user = order.getUser();
        sendMessage(user);
        orderRepository.delete(order);
    }

    private void sendMessage(User user) {
        if (!user.getEmail().isEmpty()) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Your order was delivered. \n\n Thank you for choosing us and enjoy your meal! ",
                    user.getFirstName()
            );

            mailSender.send(user.getEmail(), "Order delivered", message);
        }
    }
}

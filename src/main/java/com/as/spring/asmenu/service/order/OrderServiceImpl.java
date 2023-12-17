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
import java.util.stream.Collectors;

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
        Basket basket = order.getUser().getBasket();
        order.setOrderDishes(createOrderDishes(order, basket.getBasketDishes()));
        orderRepository.save(order);

        clearBasket(basket);
    }


    private List<OrderDish> createOrderDishes(Order order, List<BasketDish> basketDishes) {
        return basketDishes.stream()
                .map(basketDish -> new OrderDish(basketDish.getQuantity(), order, basketDish.getDish()))
                .collect(Collectors.toList());
    }

    private void clearBasket(Basket basket) {
        basketDishRepository.deleteAll(basket.getBasketDishes());
        basket.setBasketDishes(new ArrayList<>());
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
    @Transactional
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

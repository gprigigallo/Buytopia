package com.apuliadigitalmaker.buytopia.order;

import com.apuliadigitalmaker.buytopia.category.Category;
import com.apuliadigitalmaker.buytopia.product.Product;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    private static final String notFoundMessage = "Product not found";


    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findNotDeleted();
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findByIdNotDeleted(id);
    }

    public Order addOrder(Order order) {

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(int id) {
        Order order = orderRepository
                .findByIdNotDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));

        order.softDelete();
        orderRepository.save(order);
    }

    public List<Order> searchOrder(String query) {
        return orderRepository.findByNameStartsWithIgnoreCaseAndDeletedIsNull(query);
    }

    @Transactional
    public Order updateOrder(int id, Map<String, Object> update) {
        Optional<Order> optionalOrder = orderRepository.findByIdNotDeleted(id);
        Order Order = optionalOrder.get(); ;
        update.forEach((key, value) -> {
            switch (key) {
                case "quantity":

                    break;

            }
        });



        return orderRepository.save(Order);
    }














}

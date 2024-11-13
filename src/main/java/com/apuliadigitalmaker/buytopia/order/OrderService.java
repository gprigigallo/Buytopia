package com.apuliadigitalmaker.buytopia.order;

import com.apuliadigitalmaker.buytopia.orderproduct.OrderProduct;
import com.apuliadigitalmaker.buytopia.orderproduct.OrderProductRepository;
import com.apuliadigitalmaker.buytopia.orderproduct.OrderProductService;
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
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderProductService orderProductService;

    public List<Order> getAllOrders() {
        return orderRepository.findNotDeleted();
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findByIdNotDeleted(id);
    }

    public Order addOrder(Order order) {

        List<OrderProduct> orderProducts = orderProductRepository.findOrderProductByOrderId(order.getId());

        BigDecimal totalPrice = BigDecimal.ZERO;

        for(var temp : orderProducts){
             totalPrice = totalPrice.add(temp.getPrice());
        }

        totalPrice =  totalPrice.add(order.getShippingPrice()).add(order.getCommission());
        order.setTotalPrice(totalPrice);

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

    public List<Order> searchOrder(int id) {
        return orderRepository.findAllWhereUserIdIsEqual(id);
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

    public List<Order> getOrderByUserId(int id) {
        return orderRepository.findAllWhereUserIdIsEqual(id);
    }












}

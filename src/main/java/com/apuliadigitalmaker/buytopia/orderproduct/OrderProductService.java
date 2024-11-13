package com.apuliadigitalmaker.buytopia.orderproduct;

import com.apuliadigitalmaker.buytopia.order.Order;
import com.apuliadigitalmaker.buytopia.order.OrderRepository;
import com.apuliadigitalmaker.buytopia.product.Product;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }

    public OrderProduct getOrderProductById(Integer id) {
        return orderProductRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("OrderProduct not found"));
    }

    public OrderProduct addOrderProduct(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Transactional
    public OrderProduct updateOrderProduct(Integer id, Map<String, Object> updates) {
        OrderProduct orderProduct = getOrderProductById(id);

        if (updates.containsKey("product")) {
            orderProduct.setProduct((Product) updates.get("product"));
        }
        if (updates.containsKey("order")) {
            orderProduct.setOrder((Order) updates.get("order"));
        }

        return orderProductRepository.save(orderProduct);
    }

    public void deleteOrderProduct(int id) {
        if (!orderProductRepository.existsById(id)) {
            throw new EntityNotFoundException("OrderProduct not found");
        }
        orderProductRepository.deleteById(id);
    }

    @Transactional
    public void deleteLimitedOrderProducts(int productId, int orderId, int limit) {
        List<OrderProduct> orderProducts = orderProductRepository.findLimitedOrderProducts(productId, orderId);
OrderProduct tmp = null;
        for(int c = 0; c<limit; c++){
            tmp = orderProducts.get(c);
            orderProductRepository.delete(tmp);

        }
    }

    public List<OrderProduct> getOrderProductsByOrderId(Integer orderId) {
        return orderProductRepository.findOrderProductByOrderId(orderId);
    }
}

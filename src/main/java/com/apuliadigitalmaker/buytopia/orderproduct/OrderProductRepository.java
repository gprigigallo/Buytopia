package com.apuliadigitalmaker.buytopia.orderproduct;

import com.apuliadigitalmaker.buytopia.order.Order;
import com.apuliadigitalmaker.buytopia.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

    List<OrderProduct> findOrderProductByOrderId(Integer id);


}

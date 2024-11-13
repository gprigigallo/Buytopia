package com.apuliadigitalmaker.buytopia.orderproduct;

import com.apuliadigitalmaker.buytopia.order.Order;
import com.apuliadigitalmaker.buytopia.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

    @Query("SELECT o FROM OrderProduct o WHERE o.product.id = :idp AND o.order.id = :ido")
    List<OrderProduct> findLimitedOrderProducts(@Param("idp") int productId, @Param("ido") int orderId);


    List<OrderProduct> findOrderProductByOrderId(Integer id);


}
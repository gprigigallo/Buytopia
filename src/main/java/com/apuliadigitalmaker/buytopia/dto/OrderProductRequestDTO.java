package com.apuliadigitalmaker.buytopia.dto;

import java.math.BigDecimal;

public class OrderProductRequestDTO {
    private int orderId;
    private int productId;
    //private BigDecimal price; // se il prezzo è un attributo necessario per OrderProduct

    // Getters e Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
}

package com.apuliadigitalmaker.buytopia.dto;

import com.apuliadigitalmaker.buytopia.order.Order;

import java.time.Instant;
import java.util.Set;

public class UserDto {


    private String username;
    private String email;
    private Set<OrderDto> orders;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderDto> orders) {
        this.orders = orders;
    }
}

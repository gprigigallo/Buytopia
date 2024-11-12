package com.apuliadigitalmaker.buytopia.dto;

import com.apuliadigitalmaker.buytopia.order.Order;
import com.apuliadigitalmaker.buytopia.orderproduct.OrderProduct;
import com.apuliadigitalmaker.buytopia.product.Product;
import com.apuliadigitalmaker.buytopia.user.User;
import com.apuliadigitalmaker.buytopia.user.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MapperRepository {

    // Trasforma i DTO in Entità

    UserRepository userRepository;

    public ProductDto toDTO(Product product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setAvailableQuantity(product.getAvailableQuantity());
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
        dto.setSize(product.getSize());
        dto.setColor(product.getColor());
        dto.setImageUrl(product.getImageUrl());
        dto.setAvailable(product.getAvailable());
        return dto;
    }

    public Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setAvailableQuantity(dto.getAvailableQuantity());
        product.setSize(dto.getSize());
        product.setColor(dto.getColor());
        product.setImageUrl(dto.getImageUrl());
        product.setAvailable(dto.getAvailable());
        return product;
    }

    public OrderProductDto toDTO(OrderProduct orderProduct) {
        OrderProductDto dto = new OrderProductDto();
        dto.setId(orderProduct.getId());
        dto.setOrderId(orderProduct.getOrder().getId());
        dto.setProductId(orderProduct.getProduct().getId());
        dto.setProductName(orderProduct.getProduct().getName());
        dto.setPrice(orderProduct.getPrice());
        return dto;
    }

    public Order toEntity(OrderDto dto) {
        Order order = new Order();
        Optional<User> tuser = userRepository.findByIdNotDeleted(dto.getUserId());
        User user = tuser.orElseGet(User::new);
        order.setId(dto.getId());
        order.setUser(user);
        order.setOrderDate(dto.getOrderDate());
        order.setQuantity(dto.getQuantity());
        order.setCommission(dto.getCommission());
        order.setShippingPrice(dto.getShippingPrice());
        order.setShippingAddress(dto.getShippingAddress());
        order.setTotalPrice(dto.getTotalPrice());
        return order;
    }

    public OrderDto toDTO(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setQuantity(order.getQuantity());
        dto.setCommission(order.getCommission());
        dto.setShippingPrice(order.getShippingPrice());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setDeletedAt(order.getDeletedAt());

        // Modifica il codice per usare il metodo toDTO all'interno della stessa classe
        dto.setOrderProducts(order.getOrderProducts().stream()
                .map(this::toDTO) // Usa il metodo toDTO direttamente
                .collect(Collectors.toList()));

        return dto;
    }
}
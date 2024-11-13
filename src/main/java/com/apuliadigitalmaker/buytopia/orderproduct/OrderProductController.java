package com.apuliadigitalmaker.buytopia.orderproduct;

import com.apuliadigitalmaker.buytopia.common.ResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order-products")
@Tag(name = "Order Products", description = "List of Order Product endpoints")
public class OrderProductController {

    @Autowired
    private OrderProductService orderProductService;

    @Operation(summary = "Get the list of all order products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderProduct.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrderProducts() {
        try {
            List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
            return orderProducts.isEmpty() ? ResponseBuilder.notFound("Order products not found") : ResponseBuilder.success(orderProducts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderProduct(@PathVariable Integer id) {
        try {
            return ResponseBuilder.success(orderProductService.getOrderProductById(id));
        } catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrderProduct(@RequestBody OrderProduct orderProduct) {
        try {
            return ResponseBuilder.success(orderProductService.addOrderProduct(orderProduct));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateOrderProduct(@PathVariable Integer id, @RequestBody Map<String, Object> update) {
        try {
            return ResponseBuilder.success(orderProductService.updateOrderProduct(id, update));
        } catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderProduct(@PathVariable int id) {
        try {
            orderProductService.deleteOrderProduct(id);
            return ResponseBuilder.deleted("Order product deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @DeleteMapping("/limited-delete")
    public ResponseEntity<?> deleteLimitedOrderProducts(@RequestParam int productId, @RequestParam int orderId, @RequestParam int limit) {
        try {
            orderProductService.deleteLimitedOrderProducts(productId, orderId, limit);
            return ResponseBuilder.deleted("Limited order products deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/byorder/{orderId}")
    public ResponseEntity<?> getOrderProductsByOrderId(@PathVariable Integer orderId) {
        try {
            List<OrderProduct> orderProducts = orderProductService.getOrderProductsByOrderId(orderId);
            return orderProducts.isEmpty() ? ResponseBuilder.notFound("Order products not found for the order") : ResponseBuilder.success(orderProducts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }
}

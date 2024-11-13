package com.apuliadigitalmaker.buytopia.order;

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
@RequestMapping("/orders")
@Tag(name = "Orders", description = "List of Orders endpoints")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Operation(summary = "Get the list of all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class))) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrder() {
        try {
            List<Order> order = orderService.getAllOrders();

            if (order.isEmpty()) {
                return ResponseBuilder.notFound("Order not found");
            } else {
                return ResponseBuilder.success(order);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Integer id) {
        try {
            return ResponseBuilder.success(orderService.getOrderById(id));
        }
        catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody Order order) {
        try {
            return ResponseBuilder.success(orderService.addOrder(order));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id, @RequestBody Map<String, Object> update) {
        try {
            return ResponseBuilder.success(orderService.updateOrder(id, update));
        }
        catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable int id) {

        try {
            orderService.deleteOrder(id);
            return ResponseBuilder.deleted("Order deleted successfully");
        }
        catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

//    @GetMapping("/search")
//    public ResponseEntity<?> searchOrder(@RequestParam Integer userId) {
//
//        if (userId <= 0 ) {
//            return ResponseBuilder.badRequest("id must be greater than 0");
//        }
//
//        List<Order> searchResults = orderService.searchOrder(userId);
//        if (searchResults.isEmpty()) {
//            return ResponseBuilder.notFound("Search has no results");
//        }
//
//        return ResponseBuilder.searchResults(searchResults, searchResults.size());
//    }

}

package com.apuliadigitalmaker.buytopia.product;


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
@RequestMapping("/products")
@Tag(name = "Products", description = "List of Products endpoints")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get the list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class))) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllProduct() {
        try {
            List<Product> products = productService.getAllProducts();

            if (products.isEmpty()) {
                return ResponseBuilder.notFound("Product not found");
            } else {
                return ResponseBuilder.success(products);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id) {
        try {
            return ResponseBuilder.success(productService.getProductById(id));
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
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            return ResponseBuilder.success(productService.addProduct(product));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Map<String, Object> update) {
        try {
            return ResponseBuilder.success(productService.updateProduct(id, update));
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
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {

        try {
            productService.deleteProduct(id);
            return ResponseBuilder.deleted("Product deleted successfully");
        }
        catch (EntityNotFoundException e) {
            return ResponseBuilder.notFound(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam String query) {

        if (query.length() < 3) {
            return ResponseBuilder.badRequest("Required at least 3 characters");
        }

        List<Product> searchResults = productService.searchProduct(query);
        if (searchResults.isEmpty()) {
            return ResponseBuilder.notFound("Search has no results");
        }

        return ResponseBuilder.searchResults(searchResults, searchResults.size());
    }

}

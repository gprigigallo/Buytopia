package com.apuliadigitalmaker.buytopia.product;

import com.apuliadigitalmaker.buytopia.category.Category;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private static final String notFoundMessage = "Product not found";


    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findNotDeleted();
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findByIdNotDeleted(id);
    }

    public Product addProduct(Product product) {

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(int id) {
        Product product = productRepository
                .findByIdNotDeleted(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));

        product.softDelete();
        productRepository.save(product);
    }

    public List<Product> searchProduct(String query) {
        return productRepository.findByNameStartsWithIgnoreCaseAndDeletedAtIsNull(query);
    }

    @Transactional
    public Product updateProduct(int id, Map<String, Object> update) {
        Optional<Product> optionalProduct = productRepository.findByIdNotDeleted(id);


        Product product = optionalProduct.get();

        update.forEach((key, value) -> {
            switch (key) {
                case "name":

                    product.setName((String) value);
                    break;
                case "description":
                    product.setDescription((String) value);
                    break;
                case "price":
                    if (value instanceof BigDecimal) {
                        product.setPrice((BigDecimal) value);
                    } else if (value instanceof Integer) {
                        product.setPrice(BigDecimal.valueOf((Integer) value));
                    } else if (value instanceof Long) {
                        product.setPrice(BigDecimal.valueOf((Long) value));
                    } else if (value instanceof Double) {
                        product.setPrice(BigDecimal.valueOf((Double) value));
                    } else {
                        throw new IllegalArgumentException("Unsupported type for price: " + value.getClass().getName());
                    }
                    break;
                case "quantity":
                    product.setAvailableQuantity((Integer) value);
                    break;
                case "size":
                    product.setSize((String) value);
                    break;
                case "color":
                    product.setColor((String) value);
                    break;
                case "imageUrl":
                    product.setImageUrl((String) value);
                    break;
                case "avaiable":
                    product.setAvailable((Boolean) value);
                    break;
                case "category":
                    product.setCategory((Category) value);

            }
        });


        return productRepository.save(product);
    }
    public void hardDeleteProduct(Integer id){
        productRepository.deleteById(id);
    }
}

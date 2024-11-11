package com.apuliadigitalmaker.buytopia.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("SELECT e FROM Product e WHERE e.deletedAt IS NULL")
    List<Product> findNotDeleted();
    //findAllDeletedIsNull

    @Query("SELECT e FROM Product e WHERE e.id = :id AND e.deletedAt IS NULL")
    Optional<Product> findByIdNotDeleted(@Param("id") Integer id);
    //findByIdDeletedIsNull(Integer id)

    List<Product> findByNameStartsWithIgnoreCaseAndDeletedAtIsNull(String name);
}

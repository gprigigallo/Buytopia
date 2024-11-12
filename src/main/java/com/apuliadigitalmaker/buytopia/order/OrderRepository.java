package com.apuliadigitalmaker.buytopia.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{




        @Query("SELECT e FROM Order e WHERE e.deletedAt IS NULL")
        List<Order> findNotDeleted();

        @Query("SELECT e FROM Order e WHERE e.id = :id AND e.deletedAt IS NULL")
        Optional<Order> findByIdNotDeleted(@Param("id") int id);

        List<Order> findByNameStartsWithIgnoreCaseAndDeletedIsNull(String name);


    List<Order> findByNameContainingIgnoreCase(String query);
}

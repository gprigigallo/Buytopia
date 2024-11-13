package com.apuliadigitalmaker.buytopia.order;
import com.apuliadigitalmaker.buytopia.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{




        @Query("SELECT e FROM Order e WHERE e.deletedAt IS NULL")
        List<Order> findAllNotDeleted();

        @Query("SELECT e FROM Order e WHERE e.id = :id AND e.deletedAt IS NULL")
        Optional<Order> findByIdNotDeleted(@Param("id") int id);

        @Query("SELECT o FROM Order o WHERE o.user.id = :id AND o.deletedAt IS NULL ")
        List<Order> findAllWhereUserIdIsEqual(@Param("id") int id);



}

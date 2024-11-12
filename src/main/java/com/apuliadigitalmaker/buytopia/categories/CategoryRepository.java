package com.apuliadigitalmaker.buytopia.categories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query("SELECT e FROM Category e WHERE e.deletedAt IS NULL")
    List<Category> findNotDeleted();
    //findAllDeletedIsNull

    @Query("SELECT e FROM Category e WHERE e.id = :id AND e.deletedAt IS NULL")
    Optional<Category> findByIdNotDeleted(@Param("id") Long id);
    //findByIdDeletedIsNull(Long id)

    List<Category> findByNameStartsWithIgnoreCaseAndDeletedAtIsNull(String name);
}


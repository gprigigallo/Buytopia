package com.apuliadigitalmaker.buytopia.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT e FROM User e WHERE e.deletedAt IS NULL")
    List<User> findAllNotDeleted();

    @Query("SELECT e FROM User e WHERE e.id = :id AND e.deletedAt IS NULL")
    Optional<User> findByIdNotDeleted(Integer id);

    List<User> findByUsernameStartsWithIgnoreCaseAndDeletedAtIsNull(String name);

    Optional<User> findByUsername(String username);



}

package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ru.geekbrains.persist.User, Long>, JpaSpecificationExecutor<ru.geekbrains.persist.User> {


    @Query("select u from User u join fetch u.role where u.username = :username")
    Optional<ru.geekbrains.persist.User> findByName(@Param("username") String username);
}

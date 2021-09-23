package ru.geekbrains.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.geekbrains.persist.model.Product;

import javax.persistence.NamedAttributeNode;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @EntityGraph("product-list")
    Page<Product> findAll(Specification<Product> specification, Pageable pageable);

    @EntityGraph("product-list")
    List<Product> findAll();
}

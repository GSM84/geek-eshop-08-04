package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.persist.model.Picture;

import java.util.List;
import java.util.Optional;


public interface PictureRepository extends JpaRepository<Picture, Long> {

    @Query(value = "select p from Picture p where p.product.id = :productId"
            , countQuery = "select count(p) from Picture p where p.product.id = :productId")
    List<Picture> findAllByProductId(@Param("productId")Long _productId);

}

package russianlight.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import russianlight.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query(value = "SELECT * FROM products AS p WHERE p.category_id = :id", nativeQuery = true)
    List<Product> findProductByCategoryId(@Param("id") int id);


    List<Product> findByNameContainingIgnoreCase(String name);

    @Query(value = "FROM Product p WHERE p.price BETWEEN :from AND :to")
    List<Product> findByPriceRange(@Param("from") int from, @Param("to") int to);


}

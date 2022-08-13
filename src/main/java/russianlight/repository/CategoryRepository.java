package russianlight.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import russianlight.model.Category;
import russianlight.model.Product;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Modifying
    @Query(value = "UPDATE products AS p SET status = false WHERE p.category_id = :id ", nativeQuery = true)
    void updateStatusToFalse(@Param("id") int id);

    @Modifying
    @Query(value = "UPDATE products AS p SET category = null WHERE p.category_id = :id ", nativeQuery = true)
    void setCategoryNull(@Param("id") int id);
}

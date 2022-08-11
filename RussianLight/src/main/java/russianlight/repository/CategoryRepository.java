package russianlight.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import russianlight.model.Category;
import russianlight.model.Product;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Modifying
    @Query(value = "update Product set status = false")
    void updateStatusToFalse(Product product);
}

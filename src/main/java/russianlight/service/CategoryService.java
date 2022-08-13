package russianlight.service;

import org.springframework.stereotype.Service;
import russianlight.model.Category;
import russianlight.repository.CategoryRepository;
import russianlight.repository.ProductRepository;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements IService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository repository, ProductRepository productRepository) {
        this.categoryRepository = repository;
        this.productRepository = productRepository;
    }

    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(int id) {
        categoryRepository.updateStatusToFalse(id);
        categoryRepository.setCategoryNull(id);
        categoryRepository.deleteById(id);

    }

    public Category patch(int id, Category category) throws InvocationTargetException, IllegalAccessException {
        return (Category) patch(categoryRepository, id, category);
    }

}

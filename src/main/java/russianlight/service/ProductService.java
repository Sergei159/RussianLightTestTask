package russianlight.service;

import org.springframework.stereotype.Service;
import russianlight.model.Category;
import russianlight.model.Product;
import russianlight.repository.CategoryRepository;
import russianlight.repository.ProductRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public Product patch(int id, Product product) throws InvocationTargetException, IllegalAccessException {
        return (Product) patch(productRepository, id, product);
    }


    //TODO
    //нужен ли NPE

    /**
     * check the product and save changes
     * @param product to be checked
     */
    public void updateProduct(Product product) {
        Optional<Product> optProduct = productRepository.findById(product.getId());
        if (optProduct.isPresent()) {
            if (product.getCategory() != null) {
                Optional<Category> optCategory = categoryRepository.findById(optProduct.get().getCategory().getId());
                optCategory.ifPresent(product::setCategory);
            } else {
                throw new NullPointerException("category must not be null!");
            }
            if (product.getCreated() == null) {
                product.setCreated(optProduct.get().getCreated());
            }
            saveProduct(product);
        }
    }

    public List<Product> findByCategoryId(int id) {
       return productRepository.findProductByCategoryId(id);
    }

    public List<Product> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> findByPriceRange(int from, int to) {
        return productRepository.findByPriceRange(from, to);
    }
}

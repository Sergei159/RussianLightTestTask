package russianlight.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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

    /**
     * partially change the product.
     * product status with  category must be true;
     */
    public Product patch(int id, Product product) throws InvocationTargetException, IllegalAccessException {
        Optional<Product> optProduct = productRepository.findById(id);
        if (!optProduct.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Optional<Category> optCategory = categoryRepository.findById(product.getCategory().getId());
        if (!optCategory.isPresent()) {
            throw new IllegalArgumentException("category with this ID is not present");
        }
        product.setCategory(optCategory.get());
        product.setStatus(true);
        return (Product) patch(productRepository, id, product);
    }


    /**
     * check the product and save changes
     * product status with  category must be true;
     * @param product to be checked
     */
    public boolean updateProduct(Product product, int id) {
        boolean result = false;
        Optional<Product> optProduct = productRepository.findById(id);
        if (optProduct.isPresent()) {
            product.setId(id);
            if (product.getCreated() == null) {
                product.setCreated(optProduct.get().getCreated());
            }
            if (product.getCategory() != null) {
                Optional<Category> optCategory = categoryRepository.findById(product.getCategory().getId());
                if (optCategory.isPresent()) {
                    product.setCategory(optCategory.get());
                    product.setStatus(true);
                }
            } else {
                throw new NullPointerException("category must not be null!");
            }
            saveProduct(product);
            result = true;
        }
        return result;
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

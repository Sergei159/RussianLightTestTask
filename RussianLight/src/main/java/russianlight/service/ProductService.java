package russianlight.service;

import org.springframework.stereotype.Service;
import russianlight.model.Product;
import russianlight.repository.ProductRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    public Optional<Product> findById(int id) {
        return repository.findById(id);
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }

    public Product patch(int id, Product product) throws InvocationTargetException, IllegalAccessException {
        return (Product) patch(repository, id, product);
    }

    /**
     * check the product and save changes
     * @param product to be checked
     */
    public void updateProduct(Product product) {
        if (product.getName() == null
        || product.getDescription() == null
        || product.getPrice() < 1) {
            throw new NullPointerException();
        }
        if (product.getPhoto() == null) {
           product.setPhoto(findById(product.getId()).get().getPhoto());
        }
        if (product.getCategory() == null) {
            product.setStatus(false);
        }
        if (product.getCreated() == null) {
            product.setCreated(findById(product.getId()).get().getCreated());
        }
        saveProduct(product);
    }

    public List<Product> findByCategoryId(int id) {
       return repository.findProductByCategoryId(id);
    }

    public List<Product> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> findByPriceRange(int from, int to) {
        return repository.findByPriceRange(from, to);
    }
}

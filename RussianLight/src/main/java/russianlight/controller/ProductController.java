package russianlight.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import russianlight.model.Product;
import russianlight.service.ProductService;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * show all products
     */
    @GetMapping("/")
    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    /**
     * show product by id
     */

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable int id) {
        Optional<Product> message = productService.findById(id);
        return new ResponseEntity<>(
                message.orElse(new Product()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /**
     * find product by category
     */
    @GetMapping("/filter/category/{id}")
    public List<Product> findProductByCategoryId(@PathVariable int id) {
        return productService.findByCategoryId(id);
    }

    /**
     * find product by name
     */
    @GetMapping("/filter/name/{name}")
    public List<Product> findProductByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    /**
     * find product by price range
     */
    @GetMapping("/filter/price/{from}-{to}")
    public List<Product> findProductByPriceRange(@PathVariable int from, @PathVariable int to) {
        return productService.findByPriceRange(from, to);
    }

    /**
     * create a new product
     */
    @PostMapping("/")
    public ResponseEntity save(@Valid @RequestBody Product product,
                               @RequestParam("file") MultipartFile file) throws IOException {
        Optional<Product> result = Optional.ofNullable(product);
        if (result.isPresent()) {
            if (result.get().getCategory() == null) {
                throw new NullPointerException("Category must not be null!");
            }
            result.get().setPhoto(file.getBytes());
            productService.saveProduct(product);
        }
        return new ResponseEntity<>(
                result.orElse(new Product()),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND
        );
    }

    /**
     * completely update product
     */
    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Product product) {
        Optional<Product> result = Optional.ofNullable(product);
        if (result.isPresent()) {
            productService.updateProduct(product);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * delete product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }


    /**
     * partially update product
     */
    @PatchMapping("/patch/{id}")
    public Product patch(@PathVariable int id, @Valid @RequestBody Product product) throws InvocationTargetException, IllegalAccessException {
        return productService.patch(id, product);
    }


}

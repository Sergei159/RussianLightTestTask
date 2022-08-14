package russianlight.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import russianlight.handlers.Operation;
import russianlight.model.Product;
import russianlight.service.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    @ApiOperation("show all products")
    public List<Product> findAllProducts() {
        return productService.findAll();
    }


    @GetMapping("/{id}")
    @ApiOperation("show product by id")
    public ResponseEntity<Product> findProductById(@PathVariable int id) {
        Optional<Product> message = productService.findById(id);
        return new ResponseEntity<>(
                message.orElse(new Product()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/filter/category/{id}")
    @ApiOperation("find product by category")
    public List<Product> findProductByCategoryId(@PathVariable int id) {
        return productService.findByCategoryId(id);
    }


    @GetMapping("/filter/name/{name}")
    @ApiOperation("find product by name")
    public List<Product> findProductByName(@PathVariable String name) {
        return productService.findByName(name);
    }


    @GetMapping("/filter/price/{from}-{to}")
    @ApiOperation("find product by price range")
    public List<Product> findProductByPriceRange(
            @Valid @PathVariable @Min(value = 1, message = "price must be greater than 0") int from,
            @Valid @PathVariable @Min(value = 1, message = "price must be greater than 0") int to) {
        return productService.findByPriceRange(from, to);
    }

//    @PostMapping("/")
//    @Validated(Operation.OnCreate.class)
//    public ResponseEntity save(@Valid @RequestBody Product product,
//                               @RequestParam("file") MultipartFile file) throws IOException {
//        Optional<Product> result = Optional.ofNullable(product);
//        if (result.isPresent()) {
//            if (result.get().getCategory() == null) {
//                throw new NullPointerException("Category must not be null!");
//            }
//            result.get().setPhoto(file.getBytes());
//            productService.saveProduct(product);
//        }
//        return new ResponseEntity<>(
//                result.orElse(new Product()),
//                result.isPresent() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND
//        );
//    }
    @PostMapping("/")
    @ApiOperation("create a new product")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity save(@Valid @RequestBody Product product) {
        Optional<Product> result = Optional.ofNullable(product);
        if (result.isPresent()) {
            if (result.get().getCategory() == null) {
                throw new NullPointerException("Category must not be null!");
            }
            product.setCreated(LocalDateTime.now());
            productService.saveProduct(product);
        }
        return new ResponseEntity<>(
                result.orElse(new Product()),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("/{id}")
    @ApiOperation("completely update product")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@PathVariable int id, @Valid @RequestBody Product product) {
            return productService.updateProduct(product, id)
                    ? ResponseEntity.ok().build()
                    : ResponseEntity.notFound().build();
        }


    @DeleteMapping("/{id}")
    @ApiOperation("delete product")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/patch/{id}")
    @ApiOperation("partially update product")
    public Product patch(@PathVariable int id,@RequestBody Product product) throws InvocationTargetException, IllegalAccessException {
        return productService.patch(id, product);
    }


}

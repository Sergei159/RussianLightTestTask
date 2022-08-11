package russianlight.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import russianlight.model.Category;
import russianlight.service.CategoryService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    /**
     * show all categories
     */
    @GetMapping("/")
    public List<Category> findAllCategories() {
        return categoryService.findAll();
    }

    /**
     * show category by id
     */

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable int id) {
        Optional<Category> message = categoryService.findById(id);
        return new ResponseEntity<>(
                message.orElse(new Category()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    //TODO
    /**
     * create a new category
     */
    @PostMapping("/")
    public ResponseEntity save(@Valid @RequestBody Category category) {
        Optional<Category> result = Optional.ofNullable(category);
        if (result.isPresent()) {
//            if (category.getId() != 0) {
//                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "redundant param: id MUST be 0");
//            }
//        if (category.getName() == null || category.getName().trim().length() == 0) {
//            throw new NullPointerException("missed param: name");
//        }
            categoryService.saveCategory(category);
        }
        return new ResponseEntity<>(
                result.orElse(new Category()),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND
        );
    }

    /**
     * completely update category
     */
    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Category category) {
        Optional<Category> result = Optional.ofNullable(category);
        if (result.isPresent()) {
            categoryService.saveCategory(category);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * delete category
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }


    /**
     * partially update category
     */
    @PatchMapping("/patch/{id}")
    public Category patch(@PathVariable int id, @Valid @RequestBody Category category) throws InvocationTargetException, IllegalAccessException {
        return categoryService.patch(id, category);
    }

}

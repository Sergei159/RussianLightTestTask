package russianlight.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import russianlight.handlers.Operation;
import russianlight.model.Category;
import russianlight.service.CategoryService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products/categories")
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/")
    @ApiOperation("show all categories")
    public List<Category> findAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("show category by id")
    public ResponseEntity<Category> findCategoryById(@PathVariable int id) {
        Optional<Category> message = categoryService.findById(id);
        return new ResponseEntity<>(
                message.orElse(new Category()),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    @ApiOperation("create a new category")
    public ResponseEntity save(@Valid @RequestBody Category category) {
        Optional<Category> result = Optional.ofNullable(category);
        if (result.isPresent()) {
            categoryService.saveCategory(category);
        }
        return new ResponseEntity<>(
                result.orElse(new Category()),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("/{id}")
    @ApiOperation("completely update category")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@PathVariable int id, @Valid @RequestBody Category category) {
        Optional<Category> result = Optional.ofNullable(category);
        if (result.isPresent()) {
            category.setId(id);
            categoryService.saveCategory(category);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete category")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@PathVariable int id) {
        this.categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch/{id}")
    @ApiOperation("partially update category")
    public Category patch(@PathVariable int id,@RequestBody Category category) throws InvocationTargetException, IllegalAccessException {
        return categoryService.patch(id, category);
    }

}

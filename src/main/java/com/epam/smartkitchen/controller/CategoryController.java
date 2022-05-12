package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.CategoryDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<Response<ErrorResponse, CategoryDto>> add(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.add(categoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ErrorResponse,CategoryDto>> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable("id") String id){
        return ResponseEntity.ok(categoryService.update(categoryDto,id));
    }


    @GetMapping("")
    public ResponseEntity<Response<ErrorResponse,Page<CategoryDto>>> getAll(@RequestParam(
            value = "deleted",
            required = false,
            defaultValue = "false"
    ) boolean deleted,Pageable pageable){
        Response<ErrorResponse, Page<CategoryDto>> all = categoryService.getAll(pageable, deleted);
        return ResponseEntity.ok(all);
    }
}

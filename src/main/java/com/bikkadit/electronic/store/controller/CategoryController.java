package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @author Shital
     * @apiNote This Api is used to Create Category
     * @param categoryDto
     * @return
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Initiated Request for Create Category");
        CategoryDto category = this.categoryService.createCategory(categoryDto);
        log.info("Completed Request for Create Category");
        return new ResponseEntity<>(category, HttpStatus.CREATED);

    }

    /**
     * @apiNote This Api is used to update Category
     * @param categoryDto
     * @param categoryId
     * @return
     */
    @PutMapping("{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        log.info("Initiated Request for Update Category with categoryId : {} " , categoryId);
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        log.info("Completed Request for Update Category with categoryId : {} ", categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);

    }

    /**
     * @apiNote This Api is used to Delete Category
     * @param categoryId
     * @return
     */
    @DeleteMapping("{categoryId}")
    public ResponseEntity<String> delete(@PathVariable String categoryId) {
        log.info("Initiated Request for Delete Category with categoryId : {} ", categoryId);
        this.categoryService.delete(categoryId);
        log.info("Completed Request for Delete Category with categoryId : {} ", categoryId);
        return new ResponseEntity<>(AppConstant.CATEGORY_DELETE, HttpStatus.OK);

    }

    /**
     * @apiNote This Api is used to get All Category
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        log.info("Initiated Request for Get All Category Details");
        PageableResponse<CategoryDto> allCategory = this.categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed Request for Get All Category Details");
        return new ResponseEntity<>(allCategory, HttpStatus.OK);

    }

    /**
     * @apiNote This Api is used to get Single Category
     * @param categoryId
     * @return
     */
    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId) {

        log.info("Initiated Request for Get Single Category with categoryId : {} ", categoryId);
        CategoryDto singleCategory = this.categoryService.getSingle(categoryId);
        log.info("Completed Request for Get Single Category with categoryId : {} ", categoryId);
        return new ResponseEntity<>(singleCategory, HttpStatus.OK);

    }

}

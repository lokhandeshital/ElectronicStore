package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.dtos.ImageResponse;
import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.dtos.ProductDto;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.service.CategoryService;
import com.bikkadit.electronic.store.service.FileService;
import com.bikkadit.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/category/")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;
    @Value("${category.profile.image.path}")
    private String imageUploadPath;

    @Autowired
    private ProductService productService;

    /**
     * @param categoryDto
     * @return
     * @author Shital
     * @apiNote This Api is used to Create Category
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Initiated Request for Create Category");
        CategoryDto category = this.categoryService.createCategory(categoryDto);
        log.info("Completed Request for Create Category");
        return new ResponseEntity<>(category, HttpStatus.CREATED);

    }

    /**
     * @param categoryDto
     * @param categoryId
     * @return
     * @apiNote This Api is used to update Category
     */
    @PutMapping("{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        log.info("Initiated Request for Update Category with categoryId : {} ", categoryId);
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        log.info("Completed Request for Update Category with categoryId : {} ", categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);

    }

    /**
     * @param categoryId
     * @return
     * @apiNote This Api is used to Delete Category
     */
    @DeleteMapping("{categoryId}")
    public ResponseEntity<String> delete(@PathVariable String categoryId) {
        log.info("Initiated Request for Delete Category with categoryId : {} ", categoryId);
        this.categoryService.delete(categoryId);
        log.info("Completed Request for Delete Category with categoryId : {} ", categoryId);
        return new ResponseEntity<>(AppConstant.CATEGORY_DELETE, HttpStatus.OK);

    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @apiNote This Api is used to get All Category
     */
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        log.info("Initiated Request for Get All Category Details with pageNumber : {},pageSize : {},sortBy : {},sortDir : {}", pageNumber, pageSize, sortBy, sortDir);
        PageableResponse<CategoryDto> allCategory = this.categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed Request for Get All Category Details with pageNumber : {},pageSize : {},sortBy : {},sortDir : {}", pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allCategory, HttpStatus.OK);

    }

    /**
     * @param categoryId
     * @return
     * @apiNote This Api is used to get Single Category
     */
    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId) {

        log.info("Initiated Request for Get Single Category with categoryId : {} ", categoryId);
        CategoryDto singleCategory = this.categoryService.getSingle(categoryId);
        log.info("Completed Request for Get Single Category with categoryId : {} ", categoryId);
        return new ResponseEntity<>(singleCategory, HttpStatus.OK);

    }

    //Upload Category Cover Image
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCoverImage(
            @RequestParam("coverImage") MultipartFile image,
            @PathVariable String categoryId
    ) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadPath);
        CategoryDto category = categoryService.getSingle(categoryId);
        category.setCoverImage(imageName);
        CategoryDto categoryDto = categoryService.updateCategory(category, categoryId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);

    }

    //Serve Category Image
    @GetMapping("/image/{categoryId}")
    public void serveImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {

        CategoryDto category = categoryService.getSingle(categoryId);
        log.info("Category cover image name : {}", category.getCoverImage());
        InputStream resource = fileService.getResource(imageUploadPath, category.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }

    // Create Product With Category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto productDto
    ) {
        ProductDto productWithCategory = productService.createWithCategory(productDto, categoryId);
        return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);

    }

    //Update Category Of Product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(
            @PathVariable String categoryId,
            @PathVariable String productId
    ) {
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);

    }

    //Get Products Of Category
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {

        PageableResponse<ProductDto> response = productService.getAllOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}

package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.ApiResponse;
import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.dtos.ProductDto;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products/")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * @author Shital Lokhande
     * @apiNote This Api is Used to Create Product
     * @param productDto
     * @return
     */
    //Create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {

        ProductDto product = this.productService.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    /**
     * @apiNote This Api is Used to Update Product
     * @param productDto
     * @param productId
     * @return
     */
    //Update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {

        ProductDto updateProduct = this.productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    /**
     * @apiNote This Api is used to Delete Product
     * @param productId
     * @return
     */
    //Delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String productId) {

        this.productService.deleteProduct(productId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstant.PRODUCT_DELETE).success(true).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    /**
     * @apiNote This Api is used to Get Single Product
     * @param productId
     * @return
     */
    //Get By Id
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getById(@PathVariable String productId) {

        ProductDto singleProduct = this.productService.findById(productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);

    }

    /**
     * @apiNote This Api is used to Get All Products
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    //Get All Product
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {

        PageableResponse<ProductDto> allProduct = this.productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct, HttpStatus.OK);

    }

    /**
     * @implNote This Api is used to Get All Live
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    //Get All Live
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageableResponse<ProductDto> allLive = this.productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allLive, HttpStatus.OK);
    }

    /**
     * @apiNote This Api is used to Search Products
     * @param query
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    //Search Product
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageableResponse<ProductDto> allLive = this.productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allLive, HttpStatus.OK);
    }

}

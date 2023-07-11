package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.*;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.service.FileService;
import com.bikkadit.electronic.store.service.ProductService;
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
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/products/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    /**
     * @param productDto
     * @return
     * @author Shital Lokhande
     * @apiNote This Api is Used to Create Product
     */
    //Create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {

        ProductDto product = this.productService.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    /**
     * @param productDto
     * @param productId
     * @return
     * @apiNote This Api is Used to Update Product
     */
    //Update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {

        ProductDto updateProduct = this.productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    /**
     * @param productId
     * @return
     * @apiNote This Api is used to Delete Product
     */
    //Delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String productId) {

        this.productService.deleteProduct(productId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstant.PRODUCT_DELETE).success(true).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    /**
     * @param productId
     * @return
     * @apiNote This Api is used to Get Single Product
     */
    //Get By Id
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getById(@PathVariable String productId) {

        ProductDto singleProduct = this.productService.findById(productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);

    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @apiNote This Api is used to Get All Products
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
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @implNote This Api is used to Get All Live
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
     * @param query
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @apiNote This Api is used to Search Products
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

    //Upload Image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage") MultipartFile image
    ) throws IOException {

        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productService.findById(productId);
        productDto.setProductImageName(fileName);
        ProductDto updateProduct = productService.updateProduct(productDto, productId);

        ImageResponse response = ImageResponse.builder().imageName(updateProduct.getProductImageName()).message(AppConstant.PRODUCT_IMAGE_UPLOAD).success(true).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    //Serve Image
    @GetMapping("/image/{productId}")
    public void serveUserImage(
            @PathVariable String productId,
            HttpServletResponse response
    ) throws IOException {

        ProductDto productDto = productService.findById(productId);
        InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());


    }

}

package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.dtos.ProductDto;
import com.bikkadit.electronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.Helper;
import com.bikkadit.electronic.store.model.Category;
import com.bikkadit.electronic.store.model.Product;
import com.bikkadit.electronic.store.repository.CategoryRepository;
import com.bikkadit.electronic.store.repository.ProductRepository;
import com.bikkadit.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * @param productDto
     * @return
     * @author Shital Lokhande
     * @implNote This Impl is used to Create Product
     */
    @Override
    public ProductDto createProduct(ProductDto productDto) {

        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);

        log.info("Initiated Request for save the Product Details");
        Product newProduct = this.mapper.map(productDto, Product.class);
        Product saveProduct = this.productRepository.save(newProduct);
        ProductDto createProduct = this.mapper.map(saveProduct, ProductDto.class);
        log.info("Completed Request For Save the Product Details");
        return createProduct;
    }

    /**
     * @param productDto
     * @param productId
     * @return
     * @implNote This Impl is used to Update Product
     */
    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {

        log.info("Initiated Request for Update the Product Details with productId : {}" + productId);
        Product newProduct = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND + productId));

        newProduct.setTitle(productDto.getTitle());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setDiscountedPrice(productDto.getDiscountedPrice());
        newProduct.setLive(productDto.getLive());
        newProduct.setQuantity(productDto.getQuantity());
        newProduct.setStock(productDto.getStock());
        newProduct.setProductImageName(productDto.getProductImageName());

        log.info("Completed Request for Update the Product Details with productId : {}" + productId);
        ProductDto updateProduct = this.mapper.map(newProduct, ProductDto.class);
        return updateProduct;
    }

    /**
     * @param productId
     * @implNote This Impl is used to Delete Product
     */
    @Override
    public void deleteProduct(String productId) {

        log.info("Initiated Request for Delete the Product with productId : {} " + productId);
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_DELETE + productId));

        log.info("Completed Request for Delete the Product with productId : {} " + productId);
        this.productRepository.delete(product);

    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @implNote This Impl is Used to Get All Products
     */
    @Override
    public PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Initiated Request for Get All Product ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findAll(pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        log.info("Completed Request For Get All Product");
        return pageableResponse;
    }

    /**
     * @param productId
     * @return
     * @implNote This Impl is used to Get Single Product
     */
    @Override
    public ProductDto findById(String productId) {

        log.info("Initiated Request for Get Single Product with productId : {} ", productId);
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND + productId));

        ProductDto singleProduct = this.mapper.map(product, ProductDto.class);
        log.info("Completed Request For Get Single Product with productId", productId);
        return singleProduct;
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @implNote This Impl is used to Get All Live
     */
    @Override
    public PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiated Request for Get All Live ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByLiveTrue(pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        log.info("Completed Request for Get All Live ");
        return pageableResponse;
    }

    /**
     * @param subTitle
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @implNote This Impl is used to Search Product By Title
     */
    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiated Request for Search By Title ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByTitleContaining(subTitle, pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        log.info("Completed Request for Search By Title ");
        return pageableResponse;
    }

    /**
     * @param productDto
     * @param categoryId
     * @return
     * @implNote This Impl is Used to Create product with Category
     */
    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {

        log.info("Initiated Request for Create with Category categoryId : {}" + categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));
        Product product = mapper.map(productDto, Product.class);

        // product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        // Added
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        log.info("Completed Request for Create with Category categoryId : {}" + categoryId);
        return mapper.map(saveProduct, ProductDto.class);
    }

    /**
     * @param productId
     * @param categoryId
     * @return
     * @implNote This Impl is Used to Update Category with Product
     */
    @Override
    public ProductDto updateCategory(String productId, String categoryId) {

        log.info("Initiated Request for Update Category with categoryId : {}" + categoryId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND + productId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        log.info("Completed Request for Update Category with categoryId : {}" + categoryId);
        return mapper.map(saveProduct, ProductDto.class);
    }

    /**
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @implNote This Impl is Used to Get All Category of Product
     */
    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Initiated Request for Get All of Category ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));
        Page<Product> page = productRepository.findByCategory(category, pageable);
        log.info("Completed Request for Get All of Category ");
        return Helper.getPageableResponse(page, ProductDto.class);
    }
}

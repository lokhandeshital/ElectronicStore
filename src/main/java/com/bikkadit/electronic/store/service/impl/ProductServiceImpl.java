package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.dtos.ProductDto;
import com.bikkadit.electronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.Helper;
import com.bikkadit.electronic.store.model.Product;
import com.bikkadit.electronic.store.repository.ProductRepository;
import com.bikkadit.electronic.store.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);

        Product newProduct = this.mapper.map(productDto, Product.class);
        Product saveProduct = this.productRepository.save(newProduct);
        ProductDto createProduct = this.mapper.map(saveProduct, ProductDto.class);
        return createProduct;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {

        Product newProduct = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND + productId));

        newProduct.setTitle(productDto.getTitle());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setDiscountedPrice(productDto.getDiscountedPrice());
        newProduct.setLive(productDto.getLive());
        newProduct.setQuantity(productDto.getQuantity());
        newProduct.setStock(productDto.getStock());

        ProductDto updateProduct = this.mapper.map(newProduct, ProductDto.class);
        return updateProduct;
    }

    @Override
    public void deleteProduct(String productId) {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_DELETE + productId));

        this.productRepository.delete(product);

    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findAll(pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public ProductDto findById(String productId) {

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_NOT_FOUND + productId));

        ProductDto singleProduct = this.mapper.map(product, ProductDto.class);
        return singleProduct;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByLiveTrue(pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByTitleContaining(subTitle, pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }
}

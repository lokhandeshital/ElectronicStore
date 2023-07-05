package com.bikkadit.electronic.store.services;

import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.dtos.ProductDto;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.model.Product;
import com.bikkadit.electronic.store.repository.ProductRepository;
import com.bikkadit.electronic.store.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    Product product;

    @BeforeEach
    public void init() {

        product = Product.builder().title("Washing Machine")
                .description("This Product contain many feature ")
                .stock(true)
                .live(true)
                .quantity(10l)
                .price(40000.00)
                .discountedPrice(38000.00)
                .build();

    }

    // Create Product Test
    @Test
    public void createProductTest() {

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto product1 = productService.createProduct(mapper.map(product, ProductDto.class));
        System.out.println(product1.getTitle());
        Assertions.assertNotNull(product1);
        Assertions.assertEquals("Washing Machine", product1.getTitle());

    }

    // Update Product Test
    @Test
    public void updateProductTest() {

        String productId = "abcdef";
        ProductDto productDto = ProductDto.builder().title("Samsung Tv")
                .description("This Tv Contain many Feature")
                .price(60000.00)
                .discountedPrice(50000.00)
                .quantity(10l)
                .live(true)
                .stock(true)
                .build();

        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto updateProduct = productService.updateProduct(productDto, productId);
        System.out.println(updateProduct.getTitle());
        Assertions.assertNotNull(productDto);

    }

    // Delete Product Test
    @Test
    public void deleteProductTest() {

        String productId = "productIdabc";

        Mockito.when(productRepository.findById("productIdabc")).thenReturn(Optional.of(product));
        productService.deleteProduct(productId);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
        System.out.println(AppConstant.PRODUCT_DELETE);

    }

    // Get All Product Test
    @Test
    public void getAllProductTest() {

        Product product1 = Product.builder().title("OnePlus Tv")
                .description("This Tv Contain many Feature")
                .price(80000.00)
                .discountedPrice(70000.00)
                .quantity(10l)
                .live(true)
                .stock(true)
                .build();

        Product product2 = Product.builder().title("Realme Tv")
                .description("This Tv Contain many Feature")
                .price(60000.00)
                .discountedPrice(50000.00)
                .quantity(10l)
                .live(true)
                .stock(true)
                .build();

        List<Product> productList = Arrays.asList(product, product1, product2);
        Page<Product> page = new PageImpl<>(productList);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(1, 2, "title", "asc");
        Assertions.assertEquals(3, allProduct.getContent().size());


    }

    // Get Single Product Test

    // Get All Live Test

    // Search By Title Test
}

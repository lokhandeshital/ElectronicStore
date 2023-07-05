package com.bikkadit.electronic.store.services;

import com.bikkadit.electronic.store.dtos.ProductDto;
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

    // Delete Product Test

    // Get All Product Test

    // Get Single Product Test

    // Get All Live Test

    // Sarch By Title Test
}

package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.ProductDto;
import com.bikkadit.electronic.store.model.Product;
import com.bikkadit.electronic.store.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    private Product product;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {

        product = Product.builder().title("Samsung Tv")
                .description("This Tv Contain many Feature")
                .quantity(10l)
                .price(60000.00)
                .discountedPrice(55000.00)
                .stock(true)
                .live(true)
                .build();
    }

    // Create Product Test
    @Test
    public void createProductTest() throws Exception {

        ProductDto productDto = mapper.map(product, ProductDto.class);
        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(productDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(converObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }

    private String converObjectToJsonString(Object product) {

        try {
            return new ObjectMapper().writeValueAsString(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update Product Test
    @Test
    public void updateProductTest() throws Exception {

        String productId = "acdvfdf";

        Mockito.when(productService.updateProduct(Mockito.any(), Mockito.anyString())).thenReturn(mapper.map(product, ProductDto.class));
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/" + productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(converObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    // Delete Product Test
    @Test
    public void deleteProductTest() throws Exception {

        String productId = "avdhdb";

        Mockito.doNothing().when(productService).deleteProduct(productId);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    // Get All Product Test


}

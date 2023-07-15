package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.PageableResponse;
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

import java.util.Arrays;

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
    @Test
    public void getAllProductTest() throws Exception {

        ProductDto productDto1 = ProductDto.builder().title("Samsung Tv")
                .description("This Tv Contain many Feature")
                .quantity(10l)
                .price(60000.00)
                .discountedPrice(55000.00)
                .stock(true)
                .live(true)
                .build();

        ProductDto productDto2 = ProductDto.builder().title("Samsung Tv")
                .description("This Tv Contain many Feature")
                .quantity(10l)
                .price(60000.00)
                .discountedPrice(55000.00)
                .stock(true)
                .live(true)
                .build();

        ProductDto productDto3 = ProductDto.builder().title("Samsung Tv")
                .description("This Tv Contain many Feature")
                .quantity(10l)
                .price(60000.00)
                .discountedPrice(55000.00)
                .stock(true)
                .live(true)
                .build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(productDto1, productDto2, productDto3));

        Mockito.when(productService.getAllProduct(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    // Get Single Product
    @Test
    public void getSingleProductTest() throws Exception {

        String productId = "avcjdbcc12";

        ProductDto productDto = this.mapper.map(product, ProductDto.class);
        Mockito.when(productService.findById(Mockito.any())).thenReturn(productDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products/" + productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    // Get All Live Test
    @Test
    public void getAllLiveTest() throws Exception {

        ProductDto productDto1 = ProductDto.builder().title("Samsung Tv")
                .description("This Tv Contain many Feature")
                .quantity(10l)
                .price(60000.00)
                .discountedPrice(55000.00)
                .stock(true)
                .live(true)
                .build();

        ProductDto productDto2 = ProductDto.builder().title("OnePlus Tv")
                .description("This Tv Contain many Feature")
                .quantity(10l)
                .price(80000.00)
                .discountedPrice(75000.00)
                .stock(true)
                .live(true)
                .build();

        ProductDto productDto3 = ProductDto.builder().title("LG Tv")
                .description("This Tv Contain many Feature")
                .quantity(10l)
                .price(70000.00)
                .discountedPrice(65000.00)
                .stock(true)
                .live(true)
                .build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(productDto1, productDto2, productDto3));

        Mockito.when(productService.getAllLive(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products/" + true)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //Search By Title Test
    @Test
    public void searchByTitleTest() throws Exception {

        ProductDto productDto1 = ProductDto.builder().title("Samsung Tv")
                .description("This Tv Contain many Feature")
                .quantity(10l)
                .price(60000.00)
                .discountedPrice(55000.00)
                .stock(true)
                .live(true)
                .build();

        ProductDto productDto2 = ProductDto.builder().title("OnePlus Tv")
                .description("This Tv Contain many Feature")
                .quantity(10l)
                .price(80000.00)
                .discountedPrice(75000.00)
                .stock(true)
                .live(true)
                .build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000l);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(productDto1, productDto2));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(productService.searchByTitle(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products/" + true)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    //Create With Category Test

    //Update Category Test

    //Get All Category


}

package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.model.Category;
import com.bikkadit.electronic.store.service.CategoryService;
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
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    private Category category;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {

        category = Category.builder().title("Mobile Phone")
                .description("This Category Contain Diff Type of Mobile Phone")
                .coverImage("Phone.png")
                .build();

    }

    //Create Category Test
    @Test
    public void createCategoryTest() throws Exception {

        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);

        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(categoryDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/category/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(converObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());


    }

    private String converObjectToJsonString(Object category) {

        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Update Category Test
    @Test
    public void updateCategoryTest() throws Exception {

        String categoryId = "axsd123";

        Mockito.when(categoryService.updateCategory(Mockito.any(), Mockito.anyString())).thenReturn(mapper.map(category, CategoryDto.class));
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/category/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(converObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    //Delete Category Test
    @Test
    public void deleteCategoryTest() throws Exception {

        String categoryId = "abdhc123";

        Mockito.doNothing().when(categoryService).delete(categoryId);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/category/" + categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}

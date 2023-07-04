package com.bikkadit.electronic.store.services;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.model.Category;
import com.bikkadit.electronic.store.repository.CategoryRepository;
import com.bikkadit.electronic.store.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    Category category;

    @BeforeEach
    public void init() {

        category = Category.builder().title("Mixer")
                .description("This Category containing diff type of Mixer")
                .coverImage("mixer.png")
                .build();
    }

    // Create Category Test
    @Test
    public void createCategoryTest() {

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto category1 = categoryService.createCategory(mapper.map(category, CategoryDto.class));
        System.out.println(category1.getTitle());
        Assertions.assertNotNull(category1);
        Assertions.assertEquals("Mixer", category1.getTitle());


    }

    // Update Category Test
    @Test
    public void updateCategoryTest() {

        String categoryId = "assghh";
        CategoryDto categoryDto = CategoryDto.builder().title("Mixer")
                .description("This Category containing diff type of Mixer")
                .coverImage("mixer.png")
                .build();

        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        System.out.println(updateCategory.getTitle());
        Assertions.assertNotNull(categoryDto);

    }

    // Delete Category Test
    @Test
    public void deleteCategoryTest() {

        String categoryId = "categoryIdabc";

        Mockito.when(categoryRepository.findById("categoryIdabc")).thenReturn(Optional.of(category));
        categoryService.delete(categoryId);
        Mockito.verify(categoryRepository, Mockito.times(1)).delete(category);
    }


    // Get All Category

    // Get Single Category


}

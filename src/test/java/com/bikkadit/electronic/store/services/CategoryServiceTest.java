package com.bikkadit.electronic.store.services;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.dtos.PageableResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
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
    @Test
    public void getAllCategoryTest() {

        Category category1 = Category.builder().title("Computer")
                .description("This Category containing diff type of Computer")
                .coverImage("computer.png")
                .build();

        Category category2 = Category.builder().title("Mobile Phone")
                .description("This Category containing diff type of Mobile Phone")
                .coverImage("mobile.png")
                .build();

        List<Category> categoryList = Arrays.asList(category, category1, category2);
        Page<Category> page = new PageImpl<>(categoryList);
        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<CategoryDto> allCategory = categoryService.getAll(1, 2, "title", "asc");
        Assertions.assertEquals(3, allCategory.getContent().size());


    }

    // Get Single Category
    @Test
    public void getSingleCategoryTest() {

        String categoryId = "categoryIdTest";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDto categoryDto = categoryService.getSingle(categoryId);
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(category.getTitle(), categoryDto.getTitle(), "Title Not Found");

    }


}

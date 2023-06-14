package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.Helper;
import com.bikkadit.electronic.store.model.Category;
import com.bikkadit.electronic.store.repository.CategoryRepository;
import com.bikkadit.electronic.store.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    /**
     * @author Shital Lokhande
     * @implNote This Impl is used to create new Category
     * @param categoryDto
     * @return
     */
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        //Generating unique ID in string format
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        log.info("Initiated Request for save the Category Details");
        Category newCategory = this.mapper.map(categoryDto, Category.class);
        Category saveCategory = this.categoryRepository.save(newCategory);
        log.info("Completed Request for save the Category Details");
        CategoryDto createCotegory = this.mapper.map(saveCategory, CategoryDto.class);
        return createCotegory;
    }

    /**
     * @implNote This Impl is used to update category
     * @param categoryDto
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

        log.info("Initiated Request for Update the Category Details with categoryId : {} ", categoryId);
        Category newCategory = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));

        newCategory.setTitle(categoryDto.getTitle());
        newCategory.setDescription(categoryDto.getDescription());
        newCategory.setCoverImage(categoryDto.getCoverImage());
        Category saveCategory = this.categoryRepository.save(newCategory);
        log.info("Completed Request for Update the Category Details with categoryId : {} ", categoryId);
        CategoryDto updateCategory = this.mapper.map(saveCategory, CategoryDto.class);
        return updateCategory;
    }

    /**
     * @implNote This Impl is used to delete category
     * @param categoryId
     */
    @Override
    public void delete(String categoryId) {

        log.info("Initiated Request for Delete the Category with categoryId : {} ", categoryId);
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_DELETE + categoryId));
        log.info("Initiated Request for Delete the Category with categoryId : {} ", categoryId);
        this.categoryRepository.delete(category);

    }

    /**
     * @implNote This Impl is used to get All Category
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @Override
    public PageableResponse<CategoryDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Initiated Request for Get All Category ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        //PageNumber Default Start From 0
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = this.categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        log.info("Completed Request for Get All Category ");
        return pageableResponse;
    }

    /**
     * @implNote This Impl is used to get Single Category
     * @param categoryId
     * @return
     */
    @Override
    public CategoryDto getSingle(String categoryId) {

        log.info("Initiated Request for Get Single Category with categoryId : {} ", categoryId);
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));
        log.info("Completed Request for Get Single Category with categoryId : {} ", categoryId);
        CategoryDto singleCategory = this.mapper.map(category, CategoryDto.class);
        return singleCategory;
    }
}

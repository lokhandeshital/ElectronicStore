package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.dtos.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);

    //delete
    void delete(String categoryId);

    //get All
    PageableResponse<CategoryDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get single Category
    CategoryDto getSingle(String categoryId);


}

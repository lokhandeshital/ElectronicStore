package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dtos.PageableResponse;
import com.bikkadit.electronic.store.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    //Create
    ProductDto createProduct(ProductDto productDto);

    //Update
    ProductDto updateProduct(ProductDto productDto, String productId);

    //Delete
    void deleteProduct(String productId);

    //Get All
    PageableResponse<ProductDto> getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //Find By Id
    ProductDto findById(String productId);

    //Get All Live
    PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //Search Product
    PageableResponse<ProductDto> searchByTitle(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //Create Product With Category
    ProductDto createWithCategory(ProductDto productDto, String categoryId);

    //Update Category Of Product
    ProductDto updateCategory(String productId, String categoryId);

    //Return Product Of Given Category
    PageableResponse<ProductDto> getAllOfCategory(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


}

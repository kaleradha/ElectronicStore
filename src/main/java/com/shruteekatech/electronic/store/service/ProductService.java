package com.shruteekatech.electronic.store.service;

import com.shruteekatech.electronic.store.dto.ProductDto;
import com.shruteekatech.electronic.store.helper.PageableResponse;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto ,long productId);

    ProductDto getProduct(long productId);

    void deleteProduct(long productId);
    PageableResponse<ProductDto>getAll(int pageSize, int pageNumber, String sortBy, String sortDir);

    PageableResponse<ProductDto>getAllLive(boolean b, int pageSize, int pageNumber, String sortBy, String sortDir);

    PageableResponse<ProductDto>searchProductByTitle(String subTitle,int pageSize, int pageNumber, String sortBy, String sortDir);

ProductDto createWithCategory(ProductDto productDto,Long id);
}

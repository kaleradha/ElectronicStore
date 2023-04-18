package com.shruteekatech.electronic.store.service.impl;

import com.shruteekatech.electronic.store.dto.ProductDto;
import com.shruteekatech.electronic.store.entity.Product;
import com.shruteekatech.electronic.store.exception.ResourceNotFoundException;
import com.shruteekatech.electronic.store.helper.AppConstant;
import com.shruteekatech.electronic.store.helper.PageableResponse;
import com.shruteekatech.electronic.store.repository.ProductRepository;
import com.shruteekatech.electronic.store.service.ProductService;
import com.shruteekatech.electronic.store.utility.CustomPageHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Intiating Dao call for save product details: ");
        Product product = this.mapper.map(productDto, Product.class);
        product.setLive(AppConstant.LIVE);
        product.setStock(AppConstant.LIVE);
        Product saveproduct = this.productRepository.save(product);
        log.info("Completed Dao call for save Product details: ");
        ProductDto productDto1 = this.mapper.map(saveproduct, ProductDto.class);
        return productDto1;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, long productId) {
        log.info("Intiating Dao call for Update the existing product details: " + productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.RESOURCE_NOT_FOUND));
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.isStock());
        product.setDiscountedprice(productDto.getDiscountedprice());
        Product updatedproduct1 = this.productRepository.save(product);
        ProductDto productDto1 = this.mapper.map(updatedproduct1, ProductDto.class);

        return productDto1;
    }

    @Override
    public ProductDto getProduct(long productId) {
        log.info("Intiating Dao call for save product details: ");
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.RESOURCE_NOT_FOUND + productId));
        ProductDto productDto = this.mapper.map(product, ProductDto.class);
        return productDto;
    }

    @Override
    public void deleteProduct(long productId) {
        log.info("Intiating Dao call for save product details: ");
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.RESOURCE_NOT_FOUND));
        this.productRepository.delete(product);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageSize, int pageNumber, String sortBy, String sortDir) {
        log.info("Intiating Dao call for save product details: ");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = this.productRepository.findAll(pageRequest);
        PageableResponse<ProductDto> pageResponse = CustomPageHelper.getPageResponse(productPage, ProductDto.class);
        return pageResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageSize, int pageNumber, String sortBy, String sortDir) {
        log.info("Intiating Dao call for save product details: ");

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        PageRequest request = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = this.productRepository.findByliveTrue(request);
        PageableResponse<ProductDto> pageResponse =  CustomPageHelper.getPageResponse(productPage,ProductDto.class);

        
        return pageResponse;
    }

    @Override
    public PageableResponse<ProductDto> searchProductByTitle(String subTitle,int pageSize, int pageNumber, String sortBy, String sortDir) {
        log.info("Intiating Dao call for save product details: ");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> byTitleContaining = this.productRepository.findByTitleContaining(subTitle,pageRequest);
        PageableResponse<ProductDto> pageResponse = CustomPageHelper.getPageResponse(byTitleContaining, ProductDto.class);
        return pageResponse;
    }
}

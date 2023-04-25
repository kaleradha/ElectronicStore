package com.shruteekatech.electronic.store.services;

import com.shruteekatech.electronic.store.dto.ProductDto;
import com.shruteekatech.electronic.store.entity.Product;
import com.shruteekatech.electronic.store.exception.ResourceNotFoundException;
import com.shruteekatech.electronic.store.helper.PageableResponse;
import com.shruteekatech.electronic.store.repository.ProductRepository;
import com.shruteekatech.electronic.store.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class ProductImplTest extends BaseTest {
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    Product product;
    Product product1;
    List<Product> productList;
    ProductDto productDto;

    @BeforeEach
    public void init() {
        product = Product.builder().price(344).addedaDte(new Date()).live(true).stock(true).description("product name is ac").discountedprice(200)
                .quantity(1).title("The company of the ac is very good").build();

        product1 = Product.builder().price(500).addedaDte(new Date()).live(true).stock(true).description("product name is cooler").discountedprice(250)
                .quantity(2).title("The company of the ac is very good").build();

        productDto = ProductDto.builder().price(700).addedaDte(new Date()).live(true).stock(true).
                description("this is ").
                title("this product comp is good")
                .quantity(1).discountedprice(350).build();

        productList = new ArrayList<>();
        productList.add(product);
        productList.add(product1);

    }

    @Test
    public void createProTest() {
        //arrange
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        //act
        ProductDto product2 = productService.createProduct(modelMapper.map(product, ProductDto.class));
        //assert
        Assertions.assertEquals(344, product2.getPrice(), "product is not matched");
    }

    @Test
    public void getProductTest() {
        Long proid = 32L;
        //arrange
        Mockito.when(productRepository.findById(proid)).thenReturn(Optional.of(product1));
        //act
        ProductDto product2 = productService.getProduct(proid);
        //assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.getProduct(43l));

    }

    @Test
    public void updateProduct() {
        //arrange
        Long id = 45L;
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        //act
        ProductDto updateProduct = productService.updateProduct(productDto, id);
        //assert
        Assertions.assertNotNull(updateProduct);
        Assertions.assertEquals(productDto.getDescription(), updateProduct.getDescription());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productDto, 4));
    }

    @Test
    public void deleteTest() {
        //arrange
        Long id = 41l;
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        //act
        productService.deleteProduct(id);
        //assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(55l));
    }

    @Test
    public void getAllTest() {
        Page<Product> page = new PageImpl<>(productList);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> all = productService.getAll(2, 1, "title", "asc");
        Assertions.assertEquals(2, all.getContent().size());

    }

    @Test
    public void getAllLiveTest() {
        boolean live = true;
        Page<Product> page = new PageImpl<>(productList);
        Mockito.when(productRepository.findByliveTrue((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> all = productService.getAllLive(Mockito.anyBoolean(), 1, 2, "live", "asc");
        Assertions.assertEquals(2, all.getContent().size());
    }

    @Test
    public void searchbyKeywordTest() {
        //arrange
        String subtitle= "radha";
        Page<Product> page = new PageImpl<>(productList);

        Pageable Pageable = null;
        Mockito.when(productRepository.findByTitleContaining(subtitle, (org.springframework.data.domain.Pageable) page));

        //act

     //   Assertions.assertEquals(2, productDtoPageableResponse);

    }
}

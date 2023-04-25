package com.shruteekatech.electronic.store.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shruteekatech.electronic.store.controller.ProductController;
import com.shruteekatech.electronic.store.dto.CatagoryDto;
import com.shruteekatech.electronic.store.dto.ProductDto;
import com.shruteekatech.electronic.store.dto.UserDto;
import com.shruteekatech.electronic.store.entity.Product;
import com.shruteekatech.electronic.store.helper.PageableResponse;
import com.shruteekatech.electronic.store.service.ProductService;
import com.shruteekatech.electronic.store.services.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
public class ProductControllerTest extends BaseTest {

    @InjectMocks
    private ProductController productController;
    @MockBean
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    MockMvc mockMvc;

    Product product;
    ProductDto productDto, productDto1, productDto2;
    List<ProductDto> productDtos;

    @BeforeEach
    public void init() {
        product = Product.builder().title("TV").quantity(20).price(20000).live(true)
                .stock(true).discountedprice(100000).description("The quality of the Tv is good")
                .build();

        productDto = ProductDto.builder().title("AC").quantity(20).price(20000).live(true)
                .stock(true).discountedprice(100000).description("The quality of the Tv is good").addedaDte(new Date())
                .productImage("tv.jpg").build();
        productDto1 = ProductDto.builder().title("AC").quantity(20).price(20000).live(true)
                .stock(true).discountedprice(100000).description("The quality of the Tv is good").addedaDte(new Date())
                .productImage("tv.jpg").build();

        productDto2 = ProductDto.builder().title("AC").quantity(20).price(20000).live(true)
                .stock(true).discountedprice(100000).description("The quality of the Tv is good").addedaDte(new Date())
                .productImage("tv.jpg").build();

        productDtos = new ArrayList<>();
        productDtos.add(productDto);
        productDtos.add(productDto1);
        productDtos.add(productDto2);
    }

    @Test
    @DisplayName("[201] POST /api/products/create - Create new Product !")
    public void CreateProductTest() throws Exception {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);

        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(productDto);

        //acutul request for url
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonstring(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }

    private String convertObjectToJsonstring(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

//    @Test
//    @DisplayName("[201] PUT /api/products/1l - Update Product Details By ProductId :  ")
//    public void updateproductTest() throws Exception {
//        long productId = 10L;
//        ProductDto productDto = modelMapper.map(product, ProductDto.class);
//        Mockito.when(productService.updateProduct(Mockito.any(), Mockito.anyLong())).thenReturn(productDto);
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/products" + productId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(convertObjectToJsonstring(product))
//                        .accept(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.description").exists());
////                .andDo(print())
////                .andExpect(status().isCreated())
////                .andExpect(jsonPath("$.title").exists());

    @Test
    @DisplayName("[200] GET /api/products/get/ - Get Single Product by Id :")
    public void getSingleUserTest() throws Exception {
        long productId = 1l;
        //  UserDto dto = this.modelMapper.map(, UserDto.class);

        Mockito.when(productService.getProduct(Mockito.anyLong())).thenReturn(productDto);
//        ResponseEntity<UserDto> user1 = userController.getUser(12l);
//        long actual = user1.getBody().getUserId();
//        Assertions.assertEquals(uId,actual);
//        Assertions.assertEquals(HttpStatus.OK,user1.getStatusCode());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products/get/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    @DisplayName("[200] GET /api/products/getAll/ - Get all Products ! ")
    public void getAllCategoriesTest() throws Exception {

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();

        pageableResponse.setContent(productDtos);
        pageableResponse.setLastpage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(1);
        pageableResponse.setTotalElement(100);
        Mockito.when(productService.getAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products/getAll/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] DELETE /categories/delete/1l - Delete products by Product Id !")
    public void deleteCategoryTest() throws Exception {
        doNothing().when(productService).deleteProduct(Mockito.anyLong());

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/categories/delete/" + 1l)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] GET /api/products/search/radha - Searchall Products by keyword !")
    public void searchProductsTest() throws Exception {
        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();

        pageableResponse.setContent(productDtos);
        pageableResponse.setLastpage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(1);
        pageableResponse.setTotalElement(100);

        Mockito.when(productService.searchProductByTitle(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products/search/" + "TV")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).
                andDo(print()).andExpect(status().isOk());
    }

//    @Test
//    //@DisplayName("[200] GET /api/products/getAllLive/true - get All Live products !")
//    public void getAllLiveProductsTest() throws Exception {
//        Boolean live=true;
//        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
//
//        pageableResponse.setContent(productDtos);
//        pageableResponse.setLastpage(false);
//        pageableResponse.setPageSize(2);
//        pageableResponse.setPageNumber(0);
//        pageableResponse.setTotalElement(10);
//
//        Mockito.when(productService.getAllLive(Mockito.anyBoolean(),Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products/getAllLive/true"+live)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)).
//                andDo(print()).andExpect(status().isOk());
//    }
}
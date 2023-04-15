package com.shruteekatech.electronic.store.controller;

import com.shruteekatech.electronic.store.dto.ProductDto;
import com.shruteekatech.electronic.store.helper.*;
import com.shruteekatech.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> saveproduct(@RequestBody ProductDto productDto) {
        log.info("Initiated request for Save the Products: {} ");
        ProductDto productDto1 = this.productService.createProduct(productDto);
        log.info("Completed request for Save the Products: {} ");
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);

    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductDto> getproduct(@PathVariable long productId) {
        log.info("Initiated request for get Single Product: {} " + productId);
        ProductDto product = this.productService.getProduct(productId);
        log.info("Completed request for get Single Product: {} " + productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProdcut(@RequestBody ProductDto productDto, Long productId) {
        log.info("Initiated request for Update Products: {} " + productId);
        ProductDto productDto1 = this.productService.updateProduct(productDto, productId);
        log.info("Completed request for Update Products: {} " + productId);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<ProductDto>> getAllproduct(
            @RequestParam(value = "pageSize", defaultValue = PaginationConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "pageNumber", defaultValue = PaginationConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "sortBy", defaultValue = PaginationConstant.CATE_SORTBY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PaginationConstant.SORT_DIR, required = false) String sortDir) {
        log.info("Initiated request for get All  Products: {} ");
        PageableResponse<ProductDto> productServiceAll = this.productService.getAll(pageSize, pageNumber, sortBy, sortDir);
        log.info("Completed request for get All Products: {} ");
        return new ResponseEntity<>(productServiceAll, HttpStatus.OK);

    }

    @GetMapping("/getAllLive")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProd(
            @RequestParam(value = "pageSize", defaultValue = PaginationConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "pageNumber", defaultValue = PaginationConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "sortBy", defaultValue = PaginationConstant.CATE_SORTBY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PaginationConstant.SORT_DIR, required = false) String sortDir) {
        log.info("Initiated request for get All live Products: {} ");
        PageableResponse<ProductDto> allLive = this.productService.getAllLive(pageSize, pageNumber, sortBy, sortDir);
        log.info("completed request for get All live Products: {} ");
        return new ResponseEntity<>(allLive, HttpStatus.OK);

    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String keywords,
            @RequestParam(value = "pageSize", defaultValue = PaginationConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "pageNumber", defaultValue = PaginationConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "sortBy", defaultValue = PaginationConstant.CATE_SORTBY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PaginationConstant.SORT_DIR, required = false) String sortDir) {
        log.info("Initiated request for serach the Products based on title: {} " + keywords);
        PageableResponse<ProductDto> response = this.productService.searchProductByTitle(keywords, pageSize, pageNumber, sortBy, sortDir);

        log.info("Completed request for Search  the Product based on title: {} " + keywords);
        return new ResponseEntity<>(response, HttpStatus.OK);


    }
@DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable long productId) {
        this.productService.deleteProduct(productId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(AppConstant.DELETE_PRODUCT)
                .currentDate(new Date())
                .success(true).status(HttpStatus.OK).errorCode(ErrorCode.DELETED).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

}
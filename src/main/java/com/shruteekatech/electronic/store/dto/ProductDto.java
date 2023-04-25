package com.shruteekatech.electronic.store.dto;

import com.shruteekatech.electronic.store.entity.Catagory;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProductDto {

    private Long productId;
    @NotEmpty(message = "Enter product title!")
    private String title;
    @NotEmpty(message = "Write product Description!")
    private String description;

    @NotEmpty(message = "Enter Amount!")
    private Integer price;
    private Integer discountedprice;
    private Integer quantity;
    private Date addedaDte;
    private boolean live;
    private boolean stock;
    private String productImage;
    private CatagoryDto catagory;
}

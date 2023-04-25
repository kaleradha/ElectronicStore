package com.shruteekatech.electronic.store.entity;

import jdk.jfr.Timestamp;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Fetch;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "product_details")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String title;
    private String description;
    private Integer price;
    private Integer discountedprice;
    private Integer quantity;
    @CreationTimestamp
    private Date addedaDte;
    private boolean live;
    private boolean stock;
    private String productImage;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cat_id")
     private Catagory catagory;

}

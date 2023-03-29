package com.shruteekatech.electronic.store.entity;

import com.shruteekatech.electronic.store.validators.ImageNameValid;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories")
public class Catagory extends CustomeFields{
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "category_title")
    private String title;
    @Column(name = "description")
    private String description;
    @ImageNameValid
    @Column(name = "background_image")
    private String coverImage;

}

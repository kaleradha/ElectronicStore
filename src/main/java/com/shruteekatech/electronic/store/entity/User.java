package com.shruteekatech.electronic.store.entity;

import com.shruteekatech.electronic.store.dto.CustomFieldDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_dtls")
public class User extends CustomeFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name")
    private String name;
    @Email
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_password", length = 10)
    private String password;

    private String gender;

    @Column(name = "write_yourself")
    private String about;
    @Column(name = "user_image_name")
    private String imageName;
//    @Embedded
//    private CustomeFields customeFields;


}


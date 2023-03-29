package com.shruteekatech.electronic.store.dto;


import com.shruteekatech.electronic.store.validators.ImageNameValid;
import lombok.*;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends CustomFieldDto {
    private long userId;
    @NotEmpty
    @Size(min = 4, message = "Name should be in format!! Correct your name!")
    private String name;

    @NotEmpty
    @Pattern( regexp="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}",message = "Email should be in format! Enter valid EmailId!")
    private String email;
    @NotEmpty
    @Pattern(regexp = "^(?=.*\\d).{4,8}$", message = "The password size must be 4 digit or char!.")
    private String password;
    @NotEmpty
    @Size(min = 4, max = 6, message = "Please enter valid Information!" )
    private String gender;
    @NotEmpty
    @Size(min =30,max = 1000, message = "Write yourself in more than 30 words!")
    private String about;
    @ImageNameValid
    private String imageName;
//@Embedded
//private CustomFieldDto customFieldDto;

}


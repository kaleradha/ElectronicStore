package com.shruteekatech.electronic.store.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatagoryDto extends CustomFieldDto {

    private long id;
    @NotEmpty
    @Size(min = 10, max = 300, message = "Write The Title between 10 to 300 characters!")
    private String title;
    @NotEmpty
    @Size(max = 500,message = "Write Description upto 500 char!")
    private String description;

    private String coverImage;
}

package com.shruteekatech.electronic.store.helper;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor@AllArgsConstructor
@Builder
public class ImageResponse {
    private String imageName;
    private String message;
    private boolean success;
    private HttpStatus status;

    private Date imgUploaddate;

}

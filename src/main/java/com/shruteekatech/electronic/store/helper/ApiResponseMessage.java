package com.shruteekatech.electronic.store.helper;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseMessage {
    private String message;
    private boolean success;
    private String errorCode;
    private HttpStatus status;
    private Date currentDate;
}

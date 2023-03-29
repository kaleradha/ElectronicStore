package com.shruteekatech.electronic.store.exception;

import com.shruteekatech.electronic.store.helper.ApiResponseMessage;
import com.shruteekatech.electronic.store.helper.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class GloballyHandledExceeption {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourcenotfoundException(ResourceNotFoundException rs) {
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(rs.getMessage())
                .success(true).status(HttpStatus.NOT_FOUND).currentDate(new Date()).errorCode(ErrorCode.USER_CODE).build();
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotFoundApiValidationException(MethodArgumentNotValidException ex) {
        List<ObjectError> Errors = ex.getBindingResult().getAllErrors();
        Map<String,Object>response=new HashMap<>();
        Errors.stream().forEach(objectError -> {
            String defaultMessage = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field,defaultMessage);

        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage>badRequestHandlerException(BadApiRequestException b){
        log.info("BadApi request!!");
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(b.getMessage())
                .currentDate(new Date()).status(HttpStatus.BAD_REQUEST).success(false).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);
    }
}

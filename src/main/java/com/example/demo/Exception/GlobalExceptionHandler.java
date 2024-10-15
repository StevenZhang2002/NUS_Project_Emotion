package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.demo.Utils.Result;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Result result = new Result();
        result.setCode(1); // 自定义错误码
        result.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}

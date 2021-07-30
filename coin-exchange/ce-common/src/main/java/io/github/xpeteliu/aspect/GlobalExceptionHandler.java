package io.github.xpeteliu.aspect;

import io.github.xpeteliu.model.R;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            return R.failure(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return R.failure(exception.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public R handleBindException(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            return R.failure(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return R.failure(exception.getMessage());
    }
}

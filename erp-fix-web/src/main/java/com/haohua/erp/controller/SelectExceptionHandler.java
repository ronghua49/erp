package com.haohua.erp.controller;    /*
 * @author  Administrator
 * @date 2018/7/24
 */
import com.haohua.erp.exception.DatasourceAccessException;
import com.haohua.erp.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SelectExceptionHandler {

    @ExceptionHandler({NotFoundException.class,DatasourceAccessException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String notFoundException(){
        return "error/404";
    }

}

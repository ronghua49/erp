package com.haohua.erp.exception;    /*
 * @author  Administrator
 * @date 2018/7/24
 */

public class NotFoundException extends RuntimeException {

    public NotFoundException(){

    }
    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(Throwable throwable){
            super(throwable);
    }
}

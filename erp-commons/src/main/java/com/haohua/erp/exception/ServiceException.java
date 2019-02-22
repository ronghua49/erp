package com.haohua.erp.exception;    /*
 * @author  Administrator
 * @date 2018/7/26
 */

public class ServiceException extends RuntimeException {

    public ServiceException(){

    }

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(Throwable throwable){
        super(throwable);
    }
}

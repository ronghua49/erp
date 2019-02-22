package com.haohua.erp.exception;    /*
 * @author  Administrator
 * @date 2018/7/24
 */

public class DatasourceAccessException extends RuntimeException {

    public DatasourceAccessException(){

    }
    public DatasourceAccessException(String message){
        super(message);
    }

    public DatasourceAccessException(Throwable throwable){
        super(throwable);
    }
}

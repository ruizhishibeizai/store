package com.cy.store.service.ex;

/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-16 13:28
 **/
/**业务层异常的基类，throw ServiceException("异常的信息")**/
public class ServiceException extends RuntimeException{

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.cy.store.service.ex;

/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-20 19:44
 **/
/**用于表示用户在更新数据时产生的未知异常**/
public class UpdateException extends ServiceException{
    public UpdateException() {
        super();
    }

    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateException(Throwable cause) {
        super(cause);
    }

    protected UpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

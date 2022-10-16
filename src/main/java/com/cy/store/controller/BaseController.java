package com.cy.store.controller;

/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-16 19:18
 **/

import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**控制层的基类**/
public class BaseController {
    //操作成功的状态码
    public static final int OK = 200;

    /**
     * @ExceptionHandler 用于统一处理抛出的异常
     * 注解的参数是异常的类型,所有这种类型的异常都会被这个方法捕获
     * 若当前项目发生异常，此方法充当请求处理方法，方法的返回值是传递给前端的数据
     * 可以自动将异常对象传给此方法的参数列表上
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handException(Throwable e){
       /**
        * Controller映射并调用Service层方法时，
        * 若有异常则会抛出已经有message的异常，
        * 则JsonResult<Void> result = new JsonResult<>(e)构造方法，
        * 已经给message赋值了
        * **/
        JsonResult<Void> result = new JsonResult<>(e);

        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
//            result.setMessage("用户名被占用");
        }else if(e instanceof InsertException){
            result.setState(5000);
//            result.setMessage("注册产生未知异常");
        }
        return result;
    }
}

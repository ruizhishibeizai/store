package com.cy.store.controller;

/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-16 19:18
 **/

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

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
    @ExceptionHandler({ServiceException.class,FileUploadException.class})
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
        }
        else if(e instanceof UserNotFoundException){
            result.setState(4001);
//            result.setMessage("用户没有找到");
        }
        else if(e instanceof PasswordNotMatchException){
            result.setState(4002);
//            result.setMessage("密码错误");
        }else if(e instanceof AddressCountLimitException) {
            result.setState(4003);
//            result.setMessage("用户收获地址超出上限");
        }else if(e instanceof AddressNotFoundException) {
            result.setState(4004);
//            result.setMessage("用户收获地址不存在");
        }else if(e instanceof AccessDeniedException) {
            result.setState(4005);
//            result.setMessage("收获地址数据非法访问");
        }else if(e instanceof ProductNotFoundException) {
            result.setState(4006);
//            result.setMessage("收获地址数据非法访问");else if(e instanceof InsertException){
            result.setState(5000);
//            result.setMessage("注册产生未知异常");
        }else if(e instanceof UpdateException){
            result.setState(5001);
//            result.setMessage("更新数据时产生未知异常");
        }else if(e instanceof DeleteException){
            result.setState(5002);
//            result.setMessage("删除用户地址产生未知异常");
        }else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }

        return result;
    }

    /**
     *  获取session对象中的uid
     * @param session session对象
     * @return uid
     */
    protected final Integer getuidFromSession(HttpSession session){
        Object uid = session.getAttribute("uid");
        return Integer.valueOf(uid.toString());
    }

    /**
     *  获取session对象中的uid
     * @param session session对象
     * @return username
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();

    }
}

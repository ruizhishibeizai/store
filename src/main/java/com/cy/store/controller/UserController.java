package com.cy.store.controller;

import com.cy.store.entity.BaseEntitiy;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-16 16:56
 **/

//@Controller和@Service一样
//@Controller
@RestController //@Controller + @ResponseBody
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    public IUserService iUserService;

    /*
    @RequestMapping("reg")
    //@ResponseBody //表示此方法的响应结果以json方式给到前端
    public JsonResult<Void> reg(User user){
        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<Void>();
        try {
            iUserService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");

        }catch (InsertException e){
            result.setState(5000);
            result.setMessage("注册产生未知异常");
        }
        return result;
    }
    */
    /**
     * springboot约定大于配置，省略很多配置和注解
     * 1.接收数据的方式
     * 请求处理的参数为pojo类型（user）接收前端数据，
     * springboot会将前端url传来的参数名和pojo的属性名对比
     * 如果相等则注入到相应的属性上
     * **/
    @RequestMapping("reg")
    //@ResponseBody //表示此方法的响应结果以json方式给到前端
    public JsonResult<Void> reg(User user){

        iUserService.reg(user);
        //若有异常则跳到BaseController中的异常捕获方法
        return  new JsonResult<>(OK);
    }
    /**
     * 2.接收数据的方式
     * 请求处理的参数为 非 pojo类型（user）接收前端数据，
     * springboot会直接将前端url传来的参数名和方法的参数名对比
     * 如果相等则自动依赖注入到相应的参数上
     *
     * 3.HttpSession对象的自动注入
     * 在服务器开启时，会自动创建一个全局的httpsession对象，
     * springboot会把该对象放入ioc容器中，
     * 当请求处理方法中有httpsession参数时，会自动将此对象注入
     * **/
    @RequestMapping("login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session){
        User data = iUserService.login(username, password);
        /**
         * 向session对象中完成数据的绑定 （session是全局的）
         * **/
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());

        //获取session对象中的数据,BaseController已经封装了方法，避免冗余
        //this.getuidFromSession
        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return  new JsonResult<User>(OK,data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(HttpSession session,
                                           String oldPassword,
                                           String newPassword){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);

        iUserService.changePassword(uid,username,
                oldPassword,newPassword);
        return new JsonResult<>(OK);

    }
}

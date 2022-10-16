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
    @RequestMapping("reg")
    //@ResponseBody //表示此方法的响应结果以json方式给到前端
    public JsonResult<Void> reg(User user){

        iUserService.reg(user);
        //若有异常则跳到BaseController中的异常捕获方法
        return  new JsonResult<>(OK);
    }
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

}

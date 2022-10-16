package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-16 15:23
 **/
//@SpringBootTest 表示标注当前类是测试类，不会随同项目一起打包发送
@SpringBootTest
//@RunWith表示启动这个单元测试否则自己创建的无法运行，参数是SpringRunner.class
@RunWith(SpringRunner.class)
public class UserServiceTests {
//    idea有检查机制，接口是不能只能创建Bean的(通过动态代理给的实现类)
//   idea认为在容器中寻找这个接口是错误的
    @Autowired
    private IUserService iUserService;

    /**
     *测试注册模块
     */
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("tim6");
            user.setPassword("123");
            iUserService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            //拿到异常类的对象，获取异常类的名称
            System.out.println(e.getClass().getSimpleName());
            //拿到异常类名的具体信息
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void login(){
        User user = iUserService.login("tim09", "123");
        System.out.println(user);
    }


}
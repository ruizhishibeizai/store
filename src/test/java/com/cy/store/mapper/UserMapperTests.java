package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jws.soap.SOAPBinding;
import java.util.Date;


/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-15 20:10
 **/
//@SpringBootTest 表示标注当前类是测试类，不会随同项目一起打包发送
@SpringBootTest
//@RunWith表示启动这个单元测试否则自己创建的无法运行，参数是SpringRunner.class
@RunWith(SpringRunner.class)
public class UserMapperTests {
//    idea有检查机制，接口是不能只能创建Bean的(通过动态代理给的实现类)
//   idea认为在容器中寻找这个接口是错误的
    @Autowired
    private UserMapper userMapper;

    /**
     * 单元测试方法，满足以下四个条件，可以不用启动项目单独进行单元测试，提升测试效率
     * 1.必须被@Test修饰
     * 2.返回值必须是void
     * 3.方法的参数不知道任何类型
     * 4.方法的访问修饰符必须是public
     */
    //测试插入一个新用户
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("tim4");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(10));
    }


//    Integer updatePasswordByUid(
//            Integer uid,
//            String password,
//            String modifiedUser,
//            Date modifiedTime);
    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(
                10,
                "123",
                "管理员",
                new Date());
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(10);
        user.setGender(1);
        user.setPhone("13249186666");
        user.setEmail("@qq.com");
        userMapper.updateInfoByUid(user);
    }
}

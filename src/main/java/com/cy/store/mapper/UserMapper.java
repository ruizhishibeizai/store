package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-15 15:34
 **/
@Mapper
public interface UserMapper {

    /**
     * 插入用户的数据
     * @param user
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名来查找用户是否存在
     * @param username 用户名
     * @return 有则返回用户数据，无则返回null
     */
    User findByUsername(String username);

    /**
     * \根据用户uid查询用户
     * @param uid
     * @return 有则返回用户数据，无则返回null
     */
    User findByUid(Integer uid);

    /**
     * 根据uid更新用户的密码
     * @param uid 用户的id
     * @param password 新密码
     * @param modifiedUser 最后修改执行人
     * @param modifiedTime 最后修改时间
     * @return 受影响的行数
     */
    /** 多个参数要用@Param("uid")注解，
     * 外部想要取出传入的id值，只需要取它的参数名("uid")就可以了。
     * 将参数值传如SQL语句中，通过#{uid}进行取值给SQL的参数赋值。 **/
    Integer updatePasswordByUid(
            @Param("uid") Integer uid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);
    /** 下面这种写法会导致找不到参数 **/
//    Integer updatePasswordByUid(
//            Integer uid,
//            String password,
//            String modifiedUser,
//            Date modifiedTime);

    /**
     * 修改电话号等用户信息
     * @param user
     * @return
     */
    Integer updateInfoByUid(User user);

}

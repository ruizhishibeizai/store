package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;

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


}

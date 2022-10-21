package com.cy.store.service;

/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-16 14:27
 **/

import com.cy.store.entity.User;

/** 用户模块的接口 **/
public interface IUserService {
    /**
     * 注册功能
     * @param user
     */
    void reg(User user);

    /**
     * 登录功能
     * @param username
     * @param password
     * @return 返回user对象方便以后获取user的属性值，
     * 避免反复查询user信息的冗余
     */
    User login(String username, String password);

    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassoword);




}

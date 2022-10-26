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

    /**
     * 获取当前登录的用户的信息
     * @param uid 当前登录的用户的id
     * @return 当前登录的用户的信息
     */
    User getByUid(Integer uid);

    /**
     * 修改用户资料
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @param user 用户的新的数据
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 修改用户头像
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @param avatar 用户的新头像的路径
     */
    void changeAvatar(Integer uid, String username, String avatar);
}

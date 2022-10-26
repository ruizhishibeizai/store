package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-16 14:39
 **/
/** 用户模块的实现类**/
/**
 *  @Service注解
 *  将当前类的交给spring容器管理，会自动创建对象并加入容器中
 *  类似与@Mapper
 * **/
@Service
public class  UserServiceImpl implements IUserService {


    @Autowired
    UserMapper userMapper;

    /**
     * 登录功能
     * @param user
     */
    @Override
    public void reg(User user) {
        String username = user.getUsername();
        //先查询用户是否存在
        User result = userMapper.findByUsername(username);
        //存在则不能创建
        if(result != null){
            throw new UsernameDuplicatedException("尝试注册的用户名[" + username + "]已经被占用");
        }
        // 创建当前时间对象
        Date now = new Date();
        // 补全数据：isDelete(0)
        user.setIsDelete(0);

        /** 补全数据：加密后的密码 **/
        //随机生成盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        //调用加密方法进行加密
        String md5Password = getMD5Password(user.getPassword(), salt);
        //把加密后的密码补全到user
        user.setPassword(md5Password);
        // 补全数据：盐值
        user.setSalt(salt);
        // 补全数据：isDelete(0)
        user.setIsDelete(0);


        // 补全数据：4项日志属性
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);

        // 表示用户名没有被占用，则允许注册
        // 调用持久层Integer insert(User user)方法，执行注册并获取返回值(受影响的行数)
        Integer rows = userMapper.insert(user);
        if(rows != 1){
            throw new InsertException("添加用户数据出现未知错误");
        }
    }
    /** md5加密方法 **/
    public String getMD5Password(String password, String salt){
        for(int i = 0 ; i < 3 ; i++){
            //md5加密算法的调用，加密三次
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

    /**
     * 实现父接口的抽象登录方法
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        //根据用户名查询用户是否存在，不存在则抛出异常
        User result = userMapper.findByUsername(username);
        if(result == null){
            throw new UserNotFoundException("用户数据不存在");
        }
        //检测传进来的密码和用户的密码是否一致
        //1.获取数据库中的加密密码
        String oldPassword = result.getPassword();
        //2.对比是否一致
            //2.1获取盐值
            //2.2将传进来的密码用相同的md5加密规则加密
        String salt = result.getSalt();
        String newMd5Password = getMD5Password(password, salt);
            //2.3对比是否一致
        if(!newMd5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        //判断is_delete字段是否为1，是则表示已删除
        if(result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }


        /** 只接收了三个属性，数据压缩，提高传输速度 **/
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        /**
         * 返回当前用户数据，是为了辅助其他页面展示用的
         * 如uid username avatar等
         * **/
        return user;
    }

    /**
     * 实现父接口的修改密码方法，username参数不需要？
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassoword
     */
    @Override
    public void changePassword(Integer uid,
                               String username,
                               String oldPassword,
                               String newPassoword) {
        User result = userMapper.findByUid(uid);
        //不存在或者标记已删除则返回用户不存在
        if(result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户不存在");
        }
        //对比老密码和数据库密码是否一致
        if(!result.getPassword().equals(getMD5Password(oldPassword,result.getSalt()))){
            throw new PasswordNotMatchException("密码错误");
        }
        //先加密新密码，再修改进数据库
        String newMD5Password = getMD5Password(newPassoword,result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMD5Password, "管理员", new Date());

        if (rows != 1){
            throw new UpdateException("密码修改时出现未知异常");
        }

    }
    @Override
    public User getByUid(Integer uid) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建新的User对象
        User user = new User();
        // 将以上查询结果中的username/phone/email/gender封装到新User对象中
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        // 返回新的User对象
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        // 向参数user中补全数据：uid
        user.setUid(uid);
        // 向参数user中补全数据：modifiedUser(username)
        user.setModifiedUser(username);
        // 向参数user中补全数据：modifiedTime(new Date())
        user.setModifiedTime(new Date());
        // 调用userMapper的updateInfoByUid(User user)方法执行修改，并获取返回值
        Integer rows = userMapper.updateInfoByUid(user);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException异常
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 检查查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 检查查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建当前时间对象
        Date now = new Date();
        // 调用userMapper的updateAvatarByUid()方法执行更新，并获取返回值
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, now);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }
}

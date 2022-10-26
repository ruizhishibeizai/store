package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.BaseEntitiy;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        // 从HttpSession对象中获取uid
        Integer uid = getuidFromSession(session);
        // 调用业务对象执行获取数据
        User data = iUserService.getByUid(uid);
        // 响应成功和数据
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        // 从HttpSession对象中获取uid和username
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务对象执行修改用户资料
        iUserService.changeInfo(uid, username, user);
        // 响应成功
        return new JsonResult<Void>(OK);
    }


    /** 头像文件大小的上限值(10MB) */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /** 允许上传的头像的文件类型 */
    public static final List<String> AVATAR_TYPES = new ArrayList<String>();

    /** 初始化允许上传的头像的文件类型 */
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }
    /**
     * MultipartFile接口是springmvc提供的接口，可以接收任何类型的file
     * springboot整合了springmvc 只需要在参数列表中声明multipartfile
     * springboot会自动将传递给服务的文件数据 赋值给该参数
     *
     * @RequestParam 把请求中的指定名称的参数传递给控制器中的形参赋值
     * 例如参数名称不一致时可以用该注解强行绑定
     * @param file
     * @param session
     * @return
     */
    @PostMapping("change_avatar")
    public JsonResult<String> changeAvatar(
            @RequestParam("file") MultipartFile file,
                                  HttpSession session) {
        // 判断上传的文件是否为空
        if (file.isEmpty()) {
            // 是：抛出异常
            throw new FileEmptyException("上传的头像文件不允许为空");
        }

        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE) { // getSize()：返回文件的大小，以字节为单位
            // 是：抛出异常
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的头像文件");
        }

        // 判断上传的文件类型是否超出限制
        String contentType = file.getContentType();
        // boolean contains(Object o)：当前列表若包含某元素，返回结果为true；若不包含该元素，返回结果为false
        if (!AVATAR_TYPES.contains(contentType)) {
            // 是：抛出异常
            throw new FileTypeException("不支持使用该类型的文件作为头像，允许的文件类型：" + AVATAR_TYPES);
        }

        // 获取当前项目的绝对磁盘路径
        String parent = session.getServletContext().getRealPath("upload");
        System.out.println(parent);
        // 保存头像文件的文件夹
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 保存的头像文件的文件名
        String suffix = "";
        String originalFilename = file.getOriginalFilename();

        System.out.println(originalFilename);

        int beginIndex = originalFilename.lastIndexOf(".");
        if (beginIndex > 0) {
            suffix = originalFilename.substring(beginIndex);
        }
        String filename = UUID.randomUUID().toString() + suffix;

        // 创建文件对象，表示保存的头像文件
        File dest = new File(dir, filename);
        // 执行保存头像文件
        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            // 抛出异常
            throw new FileStateException("文件状态异常，可能文件已被移动或删除");
        } catch (IOException e) {
            // 抛出异常
            throw new FileUploadIOException("上传文件时读写错误，请稍后重新尝试");
        }

        // 头像路径
        String avatar = "/upload/" + filename;
        // 从Session中获取uid和username
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        // 将头像写入到数据库中
        iUserService.changeAvatar(uid, username, avatar);

        // 返回成功头像路径
        return new JsonResult<String>(OK, avatar);
    }
}

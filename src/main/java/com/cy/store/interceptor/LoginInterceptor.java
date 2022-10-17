package com.cy.store.interceptor;

/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-17 20:50
 **/

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**定义一个拦截器**/
public class LoginInterceptor implements HandlerInterceptor {

    //在前端控制器之前执行

    /**
     * 检测全局session对象中有没有uid数据，有则放行，
     * 无则重定向/转发到login.html
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器（Controller + url的映射）
     * @return 为true则放行，false则拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
//      HttpServletRequest对象来获取全局session对象
        Object obj = request.getSession().getAttribute("uid");
        if (obj == null){
            //说明用户尚未登录，重定向到login.html
            response.sendRedirect("/web/login.html");
            //拦截，结束后续的调用
            return false;
        }
        //请求放行
        return true;
    }

    //在ModelAndView对象返回之后被调用
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }

    //在整个请求所关联的资源被执行完毕后执行
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
}

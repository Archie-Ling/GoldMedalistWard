package org.example.config;

import org.example.handler.exceptionhandler.JavaWebException;
import org.example.utils.R;
import org.example.utils.TokenUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
* 登陆拦截器
* */
public class LoginInterceptor implements HandlerInterceptor {


    /**
     * 在controller执行之前进行拦截
     * @param request
     * @param response
     * @param handler
     * @return  是否放行  true就行放行  false拦截
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        //1.获取前端携带的token
        String token = request.getHeader("token");

        if(token==null||token.trim().equals("")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;

        }


        //3.携带token校验合法性
        if(!TokenUtils.verify(token)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        //合法  放行
        return true;
    }

}

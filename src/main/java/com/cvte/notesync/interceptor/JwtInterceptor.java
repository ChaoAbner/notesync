package com.cvte.notesync.interceptor;


import com.cvte.notesync.annotation.IgnoreJwt;
import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.common.exception.NoteException;
import com.cvte.notesync.entity.Audience;
import com.cvte.notesync.utils.HolderUtil;
import com.cvte.notesync.utils.JwtUtil;
import com.cvte.notesync.utils.SpringContextUtil;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptorAdapter.class);

    @Autowired
    private Audience audience;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 先判断请求是否不需要过滤
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            IgnoreJwt jwtIgnore  = handlerMethod.getMethodAnnotation(IgnoreJwt.class);
            if (jwtIgnore != null) {
                return true;
            }
        }
//        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//            return true;
//        }
        // 获取设置的auth
        String authHeader = request.getHeader(JwtUtil.AUTH_HEADER_KEY);
        logger.info(JwtUtil.AUTH_HEADER_KEY + ": " + authHeader);
        // 判断token的合法性
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
            throw new NoteException(NoteHttpStatus.USER_NOT_LOGIN);
        }
        // 拿到token
        String token = authHeader.substring(7);
//        if(audience == null){
//            BeanFactory factory = WebApplicationContextUtils.
//                    getRequiredWebApplicationContext(request.getServletContext());
//            audience = (Audience) factory.getBean("audience");
//        }
        audience = (Audience) SpringContextUtil.getBean("audience");
        Assert.notNull(audience, "audience注入失败");
        // 解析的时候会判断token是否过期以及其他异常，如果有异常会全局抛出
        JwtUtil.parseJwt(token, this.audience.getBase64Secret());
        logger.info("拦截token");
        // TODO 存储访问用户的信息
        HolderUtil.setUserId(JwtUtil.getUserId(token, this.audience.getBase64Secret()));
        logger.info(HolderUtil.getUserId() + "");
        return true;
    }
}

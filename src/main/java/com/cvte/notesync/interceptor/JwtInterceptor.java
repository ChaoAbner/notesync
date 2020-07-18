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

    /**
     * 前置拦截，拦截token并且验证，并且保存用户信息
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        // 先判断请求是否不需要过滤
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            IgnoreJwt jwtIgnore  = handlerMethod.getMethodAnnotation(IgnoreJwt.class);
            if (jwtIgnore != null) {
                return true;
            }
        }
        // 获取设置的auth
        String token = request.getHeader(JwtUtil.AUTH_HEADER_KEY);
        // 判断token的合法性
        if (StringUtils.isBlank(token)) {
            throw new NoteException(NoteHttpStatus.USER_NOT_LOGIN);
        }
        // 拿到token
        audience = (Audience) SpringContextUtil.getBean("audience");
        Assert.notNull(audience, "audience注入失败");
        // 解析的时候会判断token是否过期以及其他异常，如果有异常会抛出
        JwtUtil.parseJwt(token, this.audience.getBase64Secret());
        // 解析出用户Id并且存储
        HolderUtil.setUserId(JwtUtil.getUserId(token, this.audience.getBase64Secret()));
        logger.info("用户id为" + HolderUtil.getUserId() + "的请求访问");
        return true;
    }

    /**
     * 请求结束，清除user
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HolderUtil.clear();
    }
}

package com.cvte.notesync.common.aop.aspect;

import com.cvte.notesync.constant.RestfulPath;
import com.cvte.notesync.utils.CheckValidUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * aop校验数据接口参数
 */
@Aspect
@Component
public class ValidParameterAspect {

    private static final Logger logger = LoggerFactory.getLogger(ValidParameterAspect.class);

    @Pointcut("@annotation(com.cvte.notesync.annotation.ValidParameter)")
    public void annotationValidParameter() {}

    @Before("annotationValidParameter()")
    public void doBefore(JoinPoint joinPoint) {
//        String name = joinPoint.getSignature().getName();
//        logger.info("前置拦截的方法名：" + name);
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取请求url
//        String url = request.getRequestURL().toString();
        // 获取拦截的方法
//        String method = request.getMethod();
        // 获取请求的参数
//        String queryString = request.getQueryString();

        // 获取uri
        String uri = request.getRequestURI();

        Object[] args = joinPoint.getArgs();
        if (uri.startsWith(RestfulPath.SESSION_PATH) ||
                uri.startsWith(RestfulPath.USER_PATH)) {
            CheckValidUtil.checkUser(args);
        } else if (uri.startsWith(RestfulPath.NOTE_PATH) ||
                uri.startsWith(RestfulPath.SYNC_PATH)) {
            CheckValidUtil.checkNote(args);
        }
        logger.info("url: {}, 方法: {}, 接口参数合法性校验通过", request.getRequestURL(), request.getMethod());
    }
}

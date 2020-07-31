package com.cvte.notesync.common.aop.aspect;

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
 * 校验数据接口参数的合法性
 */
@Aspect
@Component
public class ValidParameterAspect {

    private static final Logger logger = LoggerFactory.getLogger(ValidParameterAspect.class);

    @Pointcut("@annotation(com.cvte.notesync.annotation.ValidParameter)")
    public void annotationValidParameter() {}

    @Before("annotationValidParameter()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        // 参数
        Object[] args = joinPoint.getArgs();
        // uri
        String uri = request.getRequestURI();
        CheckValidUtil.checkValidParameter(args, uri);

        logger.info("url: {}, 方法: {}, 接口参数合法性校验通过", request.getRequestURL(), request.getMethod());
    }
}

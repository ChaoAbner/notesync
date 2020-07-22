package com.cvte.notesync.common.aop.aspect;

import com.cvte.notesync.utils.RequestWrapperUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 对包含注解@ValidNote的方法进行拦截，进行内容的校验
 */
@SuppressWarnings("ALL")
@Aspect
//@Component
public class ValidNoteAspect {

    private static final Logger logger = LoggerFactory.getLogger(ValidNoteAspect.class);

    @Before(value = "@annotation(com.cvte.notesync.annotation.ValidNote)")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("进入拦截");
        String name = joinPoint.getSignature().getName();
        logger.info("被拦截的方法名：" + name);

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null || requestAttributes.getRequest() == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        RequestWrapperUtil requestWrapperUtil = new RequestWrapperUtil(request);
        String body = requestWrapperUtil.getBody();

        System.out.println(body);

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : parameterMap.keySet()) {
            System.out.println(s);
            System.out.println(parameterMap.get(s));
        }
    }
}

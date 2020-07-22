package com.cvte.notesync.interceptor;

import com.cvte.notesync.annotation.ValidNote;
import com.cvte.notesync.utils.CheckValidUtil;
import com.cvte.notesync.utils.RequestWrapperUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ValidCheckInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ValidNote annotation = handlerMethod.getMethodAnnotation(ValidNote.class);
            if (annotation == null) {
                return true;
            }
        } else {
            return true;
        }
        RequestWrapperUtil requestWrapperUtil = new RequestWrapperUtil(request);
        // 检查参数的合法性, 如果不合法内部会抛出参数错误
        CheckValidUtil.check(request, requestWrapperUtil.getJsonBody());
        return true;
    }
}

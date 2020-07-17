package com.cvte.notesync.common.advice;

import com.cvte.notesync.common.exception.NoteException;
import com.cvte.notesync.common.response.Result;
import com.cvte.notesync.constant.NoteHttpCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 */

@ControllerAdvice
public class GlobalExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);
    /**
     * 处理NoteException的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = NoteException.class)
    @ResponseBody
    public Result noteException(HttpServletRequest request, NoteException e){
        // 记录到日志
        logger.error(e.toString());
        return Result.error(e.getCode(), e.getMsg());
    }

    /**
     * 处理assert的非法异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result illegalException(IllegalArgumentException e) {
        logger.error(e.getMessage());
        return Result.error(NoteHttpCode.ILLEGAL_ERROR, e.getMessage());
    }

    /**
     * 处理空指针异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public Result nullPointerExeption(NullPointerException e) {
        logger.error(e.getMessage());
        return Result.error(NoteHttpCode.ILLEGAL_ERROR, e.getMessage());
    }

    /**
     * 处理可能发生的异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exception(HttpServletRequest request, Exception e){
        // 记录到日志
        logger.error(e.toString());
        return Result.error(NoteHttpCode.ORDER_ERROR, e.toString());
    }
}

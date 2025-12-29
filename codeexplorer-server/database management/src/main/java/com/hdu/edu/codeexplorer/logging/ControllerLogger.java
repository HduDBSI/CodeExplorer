package com.hdu.edu.codeexplorer.logging;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 日志打印请求体数据和返回数据
 */
@Component
@Aspect
public class ControllerLogger {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    private static Logger logger = LoggerFactory.getLogger(ControllerLogger.class);

    @Before("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void before(JoinPoint joinPoint) throws IOException {
        Object[] args = joinPoint.getArgs();
        List<Object> serializableArgs = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof ServletResponse || arg instanceof MultipartFile) {
                continue;
            } else if (arg instanceof ServletRequest) {

                String wholeStr = "";

                String url = request.getRequestURL().toString();
                logger.info("[SYNC_Request]=>{}{}}", url, wholeStr);
            } else {
                serializableArgs.add(arg);
            }
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        logger.info("[{}_Request]=>{}.{}:{}", request.getMethod(), method.getDeclaringClass().getName(), method.getName(), JSONObject.toJSONString(serializableArgs));
    }


    @AfterReturning(value = "@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)", returning = "rvt")
    public void after(JoinPoint joinPoint, Object rvt) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        logger.info("[Response]<={}.{}:{}", method.getDeclaringClass().getName(), method.getName(), JSONObject.toJSONString(rvt));
    }
}

package com.yjk.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

//AOP面向切面编程
@Component
@Aspect
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //配置切面
    @Pointcut("execution( * com.yjk.web.*.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //获取RequestLog的参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取请求地址
        String url = request.getRequestURL().toString();
        //获取ip地址
        String ip = request.getRemoteAddr();
        //获取类和方法
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        //获取参数
        Object args[] = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);

        logger.info("Request : {}",requestLog);
    }

    @After("log()")
    public void doAfter(){
        logger.info("-----------doAfter--------------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRetureing(Object result){
        logger.info("Result:{}",result);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class RequestLog{

        private String url;
        private String ip;
        private String classMethod;
        private Object[] agrs;

    }

}

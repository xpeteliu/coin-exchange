package io.github.xpeteliu.aspect;

import io.github.xpeteliu.constant.CommonConstant;
import io.github.xpeteliu.entity.SysUserLog;
import io.github.xpeteliu.model.WebLog;
import io.github.xpeteliu.service.SysUserLogService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

//@Component
@Aspect
@Slf4j
@Order(2)
public class WebLogAdminAspect {

    @Autowired
    SysUserLogService sysUserLogService;

    @Pointcut("execution(* io.github.xpeteliu.controller..*(..))")
    public void logPoint() {
    }

    @Around("logPoint()")
    public Object writeLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        WebLog webLog = new WebLog();

        long startTime = System.currentTimeMillis();
        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long endTime = System.currentTimeMillis();
        webLog.setTimeSpent((int) (endTime - startTime));

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new IllegalStateException("No request attributes bound to current thread");
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        webLog.setUri(request.getRequestURI());

        String strUrl = request.getRequestURL().toString();
        webLog.setUrl(strUrl);

        URL url = new URL(strUrl);
        int port = url.getPort();
        webLog.setBasePath((url.getProtocol() + "://" + url.getHost()) +
                (port == CommonConstant.HTTP_PORT || port == CommonConstant.HTTPS_PORT ? "" : ":" + port));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        webLog.setUsername(authentication == null ? "Anonymous" : authentication.getName());

        webLog.setIp(request.getRemoteAddr()); //TODO: get original IP address

        Method targetMethod = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        webLog.setMethod(targetMethod.getDeclaringClass().getName() + "." + targetMethod.getName());

        ApiOperation apiOperationAnnotation = targetMethod.getAnnotation(ApiOperation.class);
        webLog.setDescription(apiOperationAnnotation == null ? "No description" : apiOperationAnnotation.value());

        webLog.setParameter(getMethodParameter(targetMethod, proceedingJoinPoint.getArgs()));

        webLog.setResult(result);

        SysUserLog log = new SysUserLog();
        log.setCreated(new Timestamp(System.currentTimeMillis()));
        log.setDescription(webLog.getDescription());
        log.setDescription(webLog.getDescription());
        String username = webLog.getUsername();
        log.setUserId("Anonymous".equals(username) ? -1 : Long.parseLong(username));
        log.setMethod(webLog.getMethod());
        log.setIp(webLog.getIp());
        sysUserLogService.saveLog(log);

        return result;
    }

    private Map<String, Object> getMethodParameter(Method method, Object[] args) {
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

        HashMap<String, Object> parameter = new HashMap<>();
        if (parameterNames == null) {
            return parameter;
        }

        for (int i = 0; i < parameterNames.length; i++) {
            parameter.put(parameterNames[i], args[i]);
        }

        return parameter;
    }
}

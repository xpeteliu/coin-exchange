package io.github.xpeteliu.aspect;

import com.alibaba.fastjson.JSON;
import io.github.xpeteliu.constant.CommonConstant;
import io.github.xpeteliu.model.WebLog;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
@Order(1)
public class WebLogAspect {

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
            return result;
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

        log.info(JSON.toJSONString(webLog, true));

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
            if ("password".equals(parameterNames[i]) || "file".equals(parameterNames[i])) {
                parameter.put(parameterNames[i], "[UNSERIALIZABLE ARGUMENT]");
            } else {
                parameter.put(parameterNames[i], args[i]);
            }
        }

        return parameter;
    }
}

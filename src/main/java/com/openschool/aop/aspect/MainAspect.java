package com.openschool.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Aspect
@Component
public class MainAspect {

    private static final Logger logger = LoggerFactory.getLogger(MainAspect.class.getName());

    @Before("@annotation(LogArgs)")
    public void logInputParameters(JoinPoint joinPoint) {
        logger.info("The args of the method {} - {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing(pointcut = "@annotation(LogException)", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        if (ex.getClass() != ResponseStatusException.class) {
            logger.error("Method {} threw {}", joinPoint.getSignature().getName(), ex.getCause().toString());
        }
    }

    @AfterReturning(
            pointcut = "execution(* com.openschool.aop.service.TaskService.*(..))",
            returning = "returnValue")
    public void logReturnValue(JoinPoint joinPoint, Object returnValue) {
        if (returnValue != null) {
            logger.info("Method {} returned {}", joinPoint.getSignature().getName(), returnValue);
        }
    }

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        logger.info("Method {} took {} ms", joinPoint.getSignature().getName(), endTime - startTime);

        return result;
    }
}

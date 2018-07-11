package com.anthonyzero.seed.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.anthonyzero.seed.common.exception.BaseException;


/**
 * Redis切面处理类
 * @author pingjin create 2018年7月11日
 *
 */
@Aspect
@Component
public class RedisAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //是否开启redis缓存  true开启   false关闭
    @Value("${spring.redis.open}")
    private boolean open;

    /**
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.anthonyzero.seed.common.utils.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if(open){
            try{
                result = point.proceed();
            }catch (Exception e){
                logger.error("redis error", e);
                throw new BaseException("Redis服务异常");
            }
        }
        return result;
    }
}

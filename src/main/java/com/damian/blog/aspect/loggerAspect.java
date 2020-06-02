package com.damian.blog.aspect;


import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class loggerAspect {


//    拿到日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    定义一个切面 通过注解@Pointcut 表名这个切面
//    * com.damian.web.*.*(..)) 代表将web下定义的所有的类都拦截下来
    @Pointcut("execution(* com.damian.web.*.*(..))")
    public void log (){}

// 写一个测试类 在切面之前执行
        @Before("log()")
        public void doBefore (){
            logger.info("--doBefore--");
        }

        @After("log()")
        public  void doAfter(){
            logger.info("--doAfter--");

        }

        @AfterReturning(returning = "result",pointcut = "log()")
        public void doAfterReturn (Object result){
            logger.info("result : {}"+ result);
        }
}

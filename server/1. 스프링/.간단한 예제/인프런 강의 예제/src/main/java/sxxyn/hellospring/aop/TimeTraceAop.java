package sxxyn.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeTraceAop {
    /*
    AOP는 핵심 기능은 아니지만, 여러 군데에서 공통으로 사용하는 API이다.
     */

    @Around("execution(* sxxyn.hellospring..*(..))")
    public Object excute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();

        System.out.println("START: "+joinPoint.toString());

        try{
            return joinPoint.proceed();
        }finally {
            long finish=System.currentTimeMillis();
            long timeMs=finish-start;

            System.out.println("END "+joinPoint.toString()+" "+timeMs+"ms");
        }
    }

}

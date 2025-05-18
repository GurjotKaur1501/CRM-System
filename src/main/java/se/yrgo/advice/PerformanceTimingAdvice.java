package se.yrgo.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceTimingAdvice {

    @Around("execution(* se.yrgo.services..*.*(..)) || execution(* se.yrgo.dataaccess.*.*(..))")
    public Object measureMethodTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.nanoTime();
        Object result = pjp.proceed();
        long end = System.nanoTime();

        double durationMs = (end - start) / 1_000_000.0;
        System.out.printf("Time taken for the method %s from the class %s took %.4fms%n",
                pjp.getSignature().getName(),
                pjp.getTarget().getClass().getName(),
                durationMs);

        return result;
    }
}

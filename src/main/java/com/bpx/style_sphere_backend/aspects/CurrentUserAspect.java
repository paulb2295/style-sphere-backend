package com.bpx.style_sphere_backend.aspects;

import com.bpx.style_sphere_backend.models.entities.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

@Aspect
@Component
public class CurrentUserAspect {

    @Around("@annotation(CurrentUser)")
    public Object injectCurrentUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("No authenticated user found");
        }

        User currentUser = (User) authentication.getPrincipal();
        Object[] args = joinPoint.getArgs();
        Parameter[] parameters = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameters();

        for (int i = 0; i < args.length; i++) {
            if (parameters[i].getType().equals(User.class)) {
                args[i] = currentUser;
                break;
            }
        }

        return joinPoint.proceed(args);
    }
}

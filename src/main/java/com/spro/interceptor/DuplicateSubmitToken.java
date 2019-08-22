package com.spro.interceptor;

import java.lang.annotation.*;

/**
 * @description 防止表单重复提交注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DuplicateSubmitToken {
    boolean save() default true;
}

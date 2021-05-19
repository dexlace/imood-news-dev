package com.dexlace.model.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUrlValidate.class)  //指定要处理验证的类
public @interface CheckUrl {

    // value就是需要传值的
   // int[] value() default {};
    // 参数校验失败的时候返回的默认信息
    // 下面三个都是必须的

    // 验证失败的消息
    String message() default "Url不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
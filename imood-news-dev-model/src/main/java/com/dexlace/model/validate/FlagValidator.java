package com.dexlace.model.validate;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
// FlagValidatorClass.class 这个类就是验证是否通过
@Constraint(validatedBy = FlagValidatorClass.class)
public @interface FlagValidator {
    // value就是需要传值的，这里使用数组，即前端传来的值只要这个数组里存在就通过
    // 这是自定义的注解元素
    int[] value() default {};
    // 参数校验失败的时候返回的默认信息
    // 以下三个东东别管默认都要
    String message() default "flag is not found";
    // 分组使用
    Class<?>[] groups() default {};
    // 不知道是啥，反正都有，写上总没错
    Class<? extends Payload>[] payload() default {};
}

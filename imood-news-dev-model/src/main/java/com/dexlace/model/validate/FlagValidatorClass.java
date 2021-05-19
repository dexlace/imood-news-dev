package com.dexlace.model.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/11
 */
//第一个参数需要指定为你自定义的校验注解类
// 第二个指定为你要校验属性的类型,如果前端传的是List那这里的Integer就变成List<String>,就是需要接受前端数据的数据类型
//isValid方法中就是具体的校验逻辑
public class FlagValidatorClass implements ConstraintValidator<FlagValidator, Integer> {

    private FlagValidator constraint;

    // 一般来说如果用到了自定义注解里面的值，从这个初始化方法中给全局变量赋值，方便isValis()方法中使用
    @Override
    public void initialize(FlagValidator constraint) {
        this.constraint = constraint;
    }

    /**
     * 第一个参数value就是接收到的前端的值
     * 第二个参数目前没用到
     * 如果返回true就表示通过，false就表示校验失败，返回默认的信息
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        // 如果前端不传值返回false，返回错误异常
        if(value == null) {
            return false;
        }
        // 这个values就是你放在注解上的value数组，即 @FlagValidator(value = {0,1},message = "预约状态参数错误") 这里面的value值
        int[] values = constraint.value();
        // 遍历设定的值，当有一个值等于的话，就放行，否则拒绝放行
        for (int i : values) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }
}
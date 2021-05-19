package com.dexlace.model.validate;



import com.dexlace.common.utils.UrlUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
  * 
  * 自定义的注解验证方法
  * 实现ConstraintValidator<T,T>
  * 两个泛型类型为:
  * 第一个:你自定义的注解
  * 第二个:传递的值例如你定义在field字段上,那么这个类型就是你定义注解的那个字段类型
  * ConstraintValidator<MyValid, Object>
  * 
  * 在这边只要实现了ConstraintValidator<T,T>,那么你的这个方法就会被spring容器纳入管理
  * 因此你就可以很方便的在这个验证方法中注入spring管理的类去进行业务逻辑验证
  *
  */
public class CheckUrlValidate implements ConstraintValidator<CheckUrl, String> {

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        return UrlUtil.verifyUrl(url.trim());
    }
}

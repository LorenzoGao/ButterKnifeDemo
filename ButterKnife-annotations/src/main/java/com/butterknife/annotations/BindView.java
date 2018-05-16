package com.butterknife.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：Lorenzo Gao
 * Date: 2018/5/16
 * Time: 11:25
 * 邮箱：2508719070@qq.com
 * Description: 依赖注解
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS) //编译时注解
public @interface BindView {
    int value();
}

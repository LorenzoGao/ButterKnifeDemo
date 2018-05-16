package com.butterknife;

import android.app.Activity;

import java.lang.reflect.Constructor;

/**
 * 作者：Lorenzo Gao
 * Date: 2018/5/16
 * Time: 11:24
 * 邮箱：2508719070@qq.com
 * Description:
 */

public class ButterKnife {


    public static Unbinder bind(Activity activity) {
//  XXXActivity_ViewBinding viewBinding=new XXXActivity_ViewBinding(this);
        try {
            Class<? extends Unbinder> bindClassName = (Class<? extends Unbinder>) Class.forName(activity.getClass().getName() + "_ViewBinding");

            //构造函数
            Constructor<? extends Unbinder> bindConstructor = bindClassName.getDeclaredConstructor(activity.getClass());
            //     bindConstructor.setAccessible(true);
            Unbinder unbinder = bindConstructor.newInstance(activity);

            return unbinder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Unbinder.EMPTY;
    }
}

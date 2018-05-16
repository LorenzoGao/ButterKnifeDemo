package com.butterknife;

import android.app.Activity;
import android.view.View;

/**
 * 作者：Lorenzo Gao
 * Date: 2018/5/16
 * Time: 17:18
 * 邮箱：2508719070@qq.com
 * Description:
 */

public class Utils {


    public static <T extends View> T findViewByid(Activity activity, int viewId) {
        return activity.findViewById(viewId);
    }
}

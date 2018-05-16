package com.butterknife;

import android.support.annotation.UiThread;

/**
 * 作者：Lorenzo Gao
 * Date: 2018/5/16
 * Time: 16:36
 * 邮箱：2508719070@qq.com
 * Description:
 */

public interface Unbinder {

    @UiThread
    void unbind();



    Unbinder EMPTY = new Unbinder() {
        @Override
        public void unbind() {

        }
    };

}

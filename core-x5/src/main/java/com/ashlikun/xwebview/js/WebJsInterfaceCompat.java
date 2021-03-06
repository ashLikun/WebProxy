package com.ashlikun.xwebview.js;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;

import com.ashlikun.xwebview.XWeb;
import com.ashlikun.xwebview.XWebUtils;

import java.lang.ref.WeakReference;

/**
 * @author　　: 李坤
 * 创建时间: 2018/9/21 15:45
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：js接口兼容
 */

public class WebJsInterfaceCompat {
    private WeakReference<XWeb> mReference = null;
    private WeakReference<Activity> mActivityWeakReference = null;

    public WebJsInterfaceCompat(XWeb xWeb, Activity activity) {
        mReference = new WeakReference<XWeb>(xWeb);
        mActivityWeakReference = new WeakReference<Activity>(activity);
    }


    @JavascriptInterface
    public void uploadFile() {
        uploadFile("*/*");
    }

    @JavascriptInterface
    public void uploadFile(String acceptType) {
        if (mActivityWeakReference.get() != null && mReference.get() != null) {

            XWebUtils.showFileChooserCompat(mActivityWeakReference.get(),
                    mReference.get().getWebCreator().getWebView(),
                    null,
                    null,
                    mReference.get().getPermissionInterceptor(),
                    null,
                    acceptType,
                    new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {
                            if (mReference.get() != null) {
                                mReference.get().getJsAccessEntrace()
                                        .quickCallJs("uploadFileResult",
                                                msg.obj instanceof String ? (String) msg.obj : null);
                            }
                            return true;
                        }
                    }
            );


        }
    }

}

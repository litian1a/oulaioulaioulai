package com.biyi.hypnosis.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 提示工具类
 *
 * @author ljj
 */
public class ToastUtils {
    private final Handler mHandler;
    private String oldMsg;
    private Toast toast = null;
    private long oneTime = 0;
    private long twoTime = 0;
//    private boolean isMain;
//    private Timer timer;

    public ToastUtils() {
        mHandler = new Handler();
    }

    public static ToastUtils getInstance() {
        return ToastUtilsHolder.sInstance;
    }

    public void cancel() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 静态内部类
     */
    private static class ToastUtilsHolder {
        private static final ToastUtils sInstance = new ToastUtils();
    }

    /**
     * 在中间显示的
     * @param context
     * @param s
     */
    public Toast showToast(final Context context, final String s) {
        boolean isMain = Thread.currentThread() == Looper.getMainLooper().getThread();
        if (isMain) {
            show(s,context);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    show(s,context);
                }
            });
        }
        return toast;



    }
//    /**
//     * 无限在中间显示的toast   必须调用Cancle关闭
//     * @param context
//     * @param s
//     */
//    public ToastUtils showPerpetualToast(final Context context, final String s) {
//
//        if (Thread.currentThread() != Looper.getMainLooper().getThread())
//            isMain = false;
//        if (isMain) {
//            show(s,context);
//        } else {
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    show(s,context);
//                }
//            });
//        }
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showPerpetualToast(context,s);
//            }
//        },2000);
//
//
//        return this;
//
//
//    }

    private void show(String s, Context context) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), s, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 40);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 40);
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 40);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    /**
     * 在底部的
     * @param context
     * @param text
     */
    public void showBottomToast(Context context, String text) {
        //	ToastCompat.makeText(context, text, Toast.LENGTH_LONG).toast.show();
        if (!TextUtils.isEmpty(text)) {
            if (toast == null) {
                toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
            } else {
                toast.setText(text);
            }
            toast.show();
        }

    }
    public void showToast(Context context, int resId) {
        showToast(context, context.getApplicationContext().getString(resId));
    }
}

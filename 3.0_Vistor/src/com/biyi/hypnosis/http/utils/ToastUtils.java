
package com.biyi.hypnosis.http.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * show toast with single instance
 * @author hulk 20140722
 *
 */
public class ToastUtils {
    //use for refereed to last toast in order to cancel before next one
    private static Toast sToast = null;

    public static void show(Context context, int msgId) {
        show(context, context.getString(msgId), Toast.LENGTH_SHORT, 0);
    }
    
    public static void show(Context context, int msgId, int duration) {
        show(context, context.getString(msgId), duration, 0);
    }
    
    public static void show(Context context, String msg) {
        show(context, msg, Toast.LENGTH_SHORT, 0);
    }
    
    public static void show(Context context, String msg, int duration) {
        show(context, msg, duration, 0);
    }

    /**
     * show toast that is single instance
     * @param context
     * @param msg
     * @param duration
     * @param gravity   reference to Gravity.* no gravity: <= 0
     */
    public static void show(final Context context, final String msg, int duration, final int gravity) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //cancel The last one at first
                if (sToast != null) {
                    sToast.cancel();
                    sToast = null;
                }
                //create new toast object and it will be refereed by static sToast
                sToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                if(gravity > 0) {
                    sToast.setGravity(gravity, 0, 0);
                }
                sToast.show();
            }
        });
       
    }
}

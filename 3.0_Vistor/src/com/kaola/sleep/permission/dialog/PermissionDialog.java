package com.kaola.sleep.permission.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.kaola.sleep.R;
import com.kaola.sleep.permission.AndPermission;
import com.kaola.sleep.permission.Setting;

import java.util.List;

/**
 * Description：
 * Time：2019-08-02 17:59
 *
 * @author：ltc
 */
public class PermissionDialog extends AlertDialog {
    protected PermissionDialog(Context context) {
        super(context);
    }
    
    protected PermissionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    
    protected PermissionDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    
    public static void showDialog(final Activity activity , List<String> permissions ){
        String message = activity.getResources().getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissions));
        new Builder(activity)
                .setCancelable(false)
                .setTitle(R.string.permission_tips)
                .setMessage(message)
                .setPositiveButton(R.string.permission_setting, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission(activity);
                    }
                })
                .setNegativeButton(R.string.permission_cancle, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
    /**
     * Set permissions.
     */
    private static void setPermission(Activity activity) {
        AndPermission.with(activity)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
//                        Toast.makeText(activity R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }
}

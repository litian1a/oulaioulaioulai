/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sleep.kaola.permission.source;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * <p>Context Wrapper.</p>
 * Created by Yan Zhenjie on 2017/5/1.
 */
public class ContextSource extends Source {

    private Context mContext;

    public ContextSource(Context context) {
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void startActivity(Intent intent) {
        if (mContext instanceof Activity) {
            mContext.startActivity(intent);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            activity.startActivityForResult(intent, requestCode);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    @Override
    public boolean isShowRationalePermission(String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;

        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            return activity.shouldShowRequestPermissionRationale(permission);
        }

        PackageManager packageManager = mContext.getPackageManager();
        Class<?> pkManagerClass = packageManager.getClass();
        try {
            Method method = pkManagerClass.getMethod("shouldShowRequestPermissionRationale", String.class);
            if (!method.isAccessible()) method.setAccessible(true);
            return (boolean) method.invoke(packageManager, permission);
        } catch (Exception ignored) {
            return false;
        }
    }
}

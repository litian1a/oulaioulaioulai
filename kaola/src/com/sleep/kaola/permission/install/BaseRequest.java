/*
 * Copyright 2018 Yan Zhenjie
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
package com.sleep.kaola.permission.install;

import android.content.Context;

import com.sleep.kaola.permission.Action;
import com.sleep.kaola.permission.Rationale;
import com.sleep.kaola.permission.RequestExecutor;
import com.sleep.kaola.permission.source.Source;

import java.io.File;

/**
 * Created by YanZhenjie on 2018/6/1.
 */
abstract class BaseRequest implements InstallRequest {

    private Source mSource;

    private File mFile;
    private Rationale<File> mRationale = new Rationale<File>() {
        @Override
        public void showRationale(Context context, File data, RequestExecutor executor) {
            executor.execute();
        }
    };
    private Action<File> mGranted;
    private Action<File> mDenied;

    BaseRequest(Source source) {
        this.mSource = source;
    }

    @Override
    public final InstallRequest file(File file) {
        this.mFile = file;
        return this;
    }

    @Override
    public final InstallRequest rationale(Rationale<File> rationale) {
        this.mRationale = rationale;
        return this;
    }

    @Override
    public final InstallRequest onGranted(Action<File> granted) {
        this.mGranted = granted;
        return this;
    }

    @Override
    public final InstallRequest onDenied(Action<File> denied) {
        this.mDenied = denied;
        return this;
    }

    /**
     * Why permissions are required.
     */
    final void showRationale(RequestExecutor executor) {
        mRationale.showRationale(mSource.getContext(), null, executor);
    }

    /**
     * Start the installation.
     */
 

    /**
     * Callback acceptance status.
     */
    final void callbackSucceed() {
        if (mGranted != null) {
            mGranted.onAction(mFile);
        }
    }

    /**
     * Callback rejected state.
     */
    final void callbackFailed() {
        if (mDenied != null) {
            mDenied.onAction(mFile);
        }
    }
}
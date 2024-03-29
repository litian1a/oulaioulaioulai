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

import com.sleep.kaola.permission.Options;
import com.sleep.kaola.permission.source.Source;

/**
 * Created by YanZhenjie on 2018/4/28.
 */
public class NRequestFactory implements Options.InstallRequestFactory {
    @Override
    public InstallRequest create(Source source) {
        return new NRequest(source);
    }
}
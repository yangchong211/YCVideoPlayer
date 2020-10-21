/*
Copyright 2017 yangchong211（github.com/yangchong211）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.yc.video.old.listener;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/21
 *     desc  : 清晰度监听接口
 *     revise:
 * </pre>
 */
@Deprecated
public interface OnClarityChangedListener {

    /**
     * 切换清晰度后回调
     *
     * @param clarityIndex 切换到的清晰度的索引值
     */
    void onClarityChanged(int clarityIndex);

    /**
     * 清晰度没有切换，比如点击了空白位置，或者点击的是之前的清晰度
     */
    void onClarityNotChanged();
    
}

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
package org.yczbj.ycvideoplayerlib.inter.listener;


import android.graphics.SurfaceTexture;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/9
 *     desc  : VideoTextureView监听
 *     revise:
 * </pre>
 */
public interface OnTextureListener {

    /**
     * SurfaceTexture准备就绪
     * @param surface                   surface
     */
    void onSurfaceAvailable(SurfaceTexture surface);

    /**
     * SurfaceTexture缓冲大小变化
     * @param surface                   surface
     * @param width                     width
     * @param height                    height
     */
    void onSurfaceSizeChanged(SurfaceTexture surface, int width, int height);

    /**
     * SurfaceTexture即将被销毁
     * @param surface                   surface
     * @return                          销毁
     */
    boolean onSurfaceDestroyed(SurfaceTexture surface);


    /**
     * SurfaceTexture通过updateImage更新
     * @param surface                   surface
     */
    void onSurfaceUpdated(SurfaceTexture surface);

}

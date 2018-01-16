package org.yczbj.ycvideoplayer.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

public class ImageCropUtils {


    /**
     * 按宽/高缩放图片到指定大小并进行裁剪得到中间部分图片
     * @param bitmap 源bitmap
     * @param w 缩放后指定的宽度
     * @param h 缩放后指定的高度
     * @return 缩放后的中间部分图片 Bitmap
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.i("TAG", "zoomBitmap---" + "width:" + width + "---" + "height:" + height);
        float scaleWidht, scaleHeight, x, y;
        Bitmap newbmp;
        Matrix matrix = new Matrix();
        if (width > height) {
            scaleWidht = ((float) h / height);
            scaleHeight = ((float) h / height);
            // 获取bitmap源文件中x做表需要偏移的像数大小
            x = (width - w * height / h) / 2;
            y = 0;
        } else if (width < height) {
            scaleWidht = ((float) w / width);
            scaleHeight = ((float) w / width);
            x = 0;
            // 获取bitmap源文件中y做表需要偏移的像数大小
            y = (height - h * width / w) / 2;
        } else {
            scaleWidht = ((float) w / width);
            scaleHeight = ((float) w / width);
            x = 0;
            y = 0;
        }
        matrix.postScale(scaleWidht, scaleHeight);
        try {
            // createBitmap()方法中定义的参数x+width要小于或等于bitmap.getWidth()，y+height要小于或等于bitmap.getHeight()
            newbmp = Bitmap.createBitmap(bitmap, (int) x, (int) y, (int) (width - x), (int) (height - y), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return newbmp;
    }


    /**
     * 裁剪图片
     */
    public static Bitmap cutBitmap(Bitmap bm){
        Bitmap bitmap = null;
        if(bm!=null){
            //对图片的进行裁剪
            if((bm.getWidth()*2)/5+(bm.getWidth()*3)/5 <= bm.getWidth()){
                Matrix matrix = new Matrix();
                //缩放比例
                matrix.postScale(1, 1);
                bitmap = Bitmap.createBitmap(bm,(bm.getWidth()*2)/5,0,(bm.getWidth()*3)/5,bm.getHeight(),matrix,true);
                LogUtils.e("图片image1    "+bitmap.getWidth());
            }else {
                bitmap = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight());
                LogUtils.e("图片image2    "+bitmap.getWidth());
            }
        }
        return bitmap;
    }


}

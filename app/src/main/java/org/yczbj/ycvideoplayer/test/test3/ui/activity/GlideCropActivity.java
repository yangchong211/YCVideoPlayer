package org.yczbj.ycvideoplayer.test.test3.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.ConstantImage;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.util.ImageCropUtils;
import org.yczbj.ycvideoplayer.weight.HeightTransformation;

import butterknife.Bind;

/**
 * Created by yc on 2018/1/10.
 * 1. SetScaleType(ImageView.ScaleType.CENTER);
 * 按图片的原来size居中显示，当图片长/宽超过View的长/宽，则截取图片的居中部分显示
 * 2. SetScaleType(ImageView.ScaleType.CENTER_CROP);
 * 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)
 * 3. setScaleType(ImageView.ScaleType.CENTER_INSIDE);
 * 将图片的内容完整居中显示，通过按比例缩小或原来的size使得图片长/宽等于或小于View的长/宽
 * 4. setScaleType(ImageView.ScaleType.FIT_CENTER);
 * 把图片按比例扩大/缩小到View的宽度，居中显示
 * 5. FIT_START, FIT_END
 * 在图片缩放效果上与FIT_CENTER一样，只是显示的位置不同，FIT_START是置于顶部，FIT_CENTER居中，FIT_END置于底部。
 * 6. FIT_XY
 * 不按比例缩放图片，目标是把图片塞满整个View。
 */

public class GlideCropActivity extends BaseActivity implements View.OnClickListener {

    /**
     * http://blog.csdn.net/libra_louis/article/details/58604149
     * http://blog.csdn.net/xuwenneng/article/details/77097872
     * http://blog.csdn.net/u014038534/article/details/45197333
     * http://blog.csdn.net/zhou452840622/article/details/51602042
     */

    @Bind(R.id.btn_1)
    Button btn1;
    @Bind(R.id.btn_2)
    Button btn2;
    @Bind(R.id.btn_3)
    Button btn3;
    @Bind(R.id.btn_4)
    Button btn4;
    @Bind(R.id.btn_5)
    Button btn5;
    @Bind(R.id.btn_6)
    Button btn6;
    @Bind(R.id.btn_7)
    Button btn7;
    @Bind(R.id.btn_8)
    Button btn8;
    @Bind(R.id.btn_9)
    Button btn9;
    @Bind(R.id.btn_10)
    Button btn10;
    @Bind(R.id.btn_11)
    Button btn11;
    @Bind(R.id.btn_12)
    Button btn12;
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.btn_13)
    Button btn13;
    @Bind(R.id.btn_14)
    Button btn14;

    @Override
    public int getContentView() {
        return R.layout.activity_test_glide_crop;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                ivImage.setScaleType(ImageView.ScaleType.CENTER);
                ivImage.setImageResource(R.drawable.test);
                break;
            case R.id.btn_2:
                ivImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ivImage.setImageResource(R.drawable.test);
                break;
            case R.id.btn_3:
                ivImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                ivImage.setImageResource(R.drawable.test);
                break;
            case R.id.btn_4:
                ivImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ivImage.setImageResource(R.drawable.test);
                break;
            case R.id.btn_5:
                ivImage.setScaleType(ImageView.ScaleType.FIT_START);
                ivImage.setImageResource(R.drawable.test);
                break;
            case R.id.btn_6:
                ivImage.setScaleType(ImageView.ScaleType.FIT_END);
                ivImage.setImageResource(R.drawable.test);
                break;
            case R.id.btn_7:
                ivImage.setScaleType(ImageView.ScaleType.FIT_XY);
                ivImage.setImageResource(R.drawable.test);
                break;
            case R.id.btn_8:
                Glide.with(this)
                        .load(R.drawable.test)
                        .asBitmap()
                        .placeholder(R.drawable.image_default)
                        .transform(new HeightTransformation(this))
                        .into(ivImage);
                break;
            case R.id.btn_9:
                ivImage.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(this)
                        .load(R.drawable.test)
                        .asBitmap()
                        .placeholder(R.drawable.image_default)
                        .transform(new HeightTransformation(this))
                        .into(ivImage);
                break;
            case R.id.btn_10:
                ivImage.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(this)
                        .load(R.drawable.test)
                        .asBitmap()
                        .placeholder(R.drawable.image_default)
                        .transform(new HeightTransformation(this, SizeUtils.dp2px(174),
                                SizeUtils.dp2px(105), HeightTransformation.CropType.CENTER))
                        .into(ivImage);
                break;
            case R.id.btn_11:
                Glide.with(this)
                        .load(R.drawable.test)
                        .asBitmap()
                        .placeholder(R.drawable.image_default)
                        //.fitCenter()
                        //.centerCrop()
                        //.override(SizeUtils.dp2px(200), SizeUtils.dp2px(100))
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                if (resource != null) {
                                    //调用裁剪图片工具类进行裁剪
                                    Bitmap bitmap = ImageCropUtils.cutBitmap(resource);
                                    if (bitmap != null) {
                                        //设置Bitmap到图片上
                                        ivImage.setImageBitmap(bitmap);
                                    }
                                }
                            }
                        });
                break;
            case R.id.btn_12:
                Glide.with(this)
                        .load(ConstantImage.homePageConcentration[1])
                        //强制Glide返回一个Bitmap对象
                        .asBitmap()
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                Log.d("image", "onException " + e.toString());
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap bitmap, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                int width = bitmap.getWidth();
                                int height = bitmap.getHeight();
                                Log.d("image", "width2  " + width);
                                Log.d("image", "height3  " + height);
                                return false;
                            }
                        })
                        .into(ivImage);
                break;
            case R.id.btn_13:

                break;
            case R.id.btn_14:

                break;
            default:
                break;
        }
    }


    /**
     * 返回一个不可变的源位图的位图的子集,改变了可选的矩阵。新的位图可能与源相同的对象,或可能是一个副本。
     * 它初始化与原始位图的密度。如果源位图是不可变的,请求的子集是一样的源位图本身,然后返回源位图,没有新的位图创建。
     *
     * @param source 产生子位图的源位图
     * @param x      子位图第一个像素在源位图的X坐标
     * @param y      子位图第一个像素在源位图的y坐标
     * @param width  子位图每一行的像素个数
     * @param height 子位图的行数
     * @param m      对像素值进行变换的可选矩阵
     * @param filter 如果为true，源图要被过滤。该参数仅在matrix包含了超过一个翻转才有效
     * @return 一个描述了源图指定子集的位图。 Bitmap
     */
    public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height, Matrix m, boolean filter) {
        Bitmap bitmap;
        try {
            bitmap = Bitmap.createBitmap(source, x, y, width, height, m, filter);
        } catch (OutOfMemoryError localOutOfMemoryError) {
            gc();
            bitmap = Bitmap.createBitmap(source, x, y, width, height, m, filter);
        }
        return bitmap;
    }


    /**
     * 回收
     */
    private static void gc() {
        System.gc();
        // 表示java虚拟机会做一些努力运行已被丢弃对象（即没有被任何对象引用的对象）的 finalize
        // 方法，前提是这些被丢弃对象的finalize方法还没有被调用过
        System.runFinalization();
    }


}

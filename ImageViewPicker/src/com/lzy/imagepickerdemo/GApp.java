package com.lzy.imagepickerdemo;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.app.Application;
import android.widget.ImageView;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.loader.ImagePickerLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/4/13
 * 描    述：我的Github地址  https://github.com/jeasonlzy0216
 * 修订历史：
 * ================================================
 */
public class GApp extends Application {

    public static DisplayImageOptions imageLoaderOptions = new DisplayImageOptions.Builder()//
            .showImageOnLoading(R.mipmap.default_image)         //设置图片在下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.default_image)       //设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.default_image)            //设置图片加载/解码过程中错误时候显示的图片
            .cacheInMemory(true)                                //设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                                  //设置下载的图片是否缓存在SD卡中
            .build();                                           //构建完成

    public static ImageOptions xUtilsOptions = new ImageOptions.Builder()//
            .setIgnoreGif(false)                                //是否忽略GIF格式的图片
            .setImageScaleType(ImageView.ScaleType.FIT_CENTER)  //缩放模式
            .setLoadingDrawableId(R.mipmap.default_image)       //下载中显示的图片
            .setFailureDrawableId(R.mipmap.default_image)       //下载失败显示的图片
            .build();                                           //得到ImageOptions对象

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);

        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
//        ImageLoader.getInstance().init(config);     //UniversalImageLoader初始化
        x.Ext.init(this);                           //xUtils3初始化
    }
}
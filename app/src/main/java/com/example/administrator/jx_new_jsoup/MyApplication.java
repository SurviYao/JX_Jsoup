package com.example.administrator.jx_new_jsoup;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.administrator.jx_new_jsoup.utils.SharedUtil;
import com.example.administrator.jx_new_jsoup.utils.SqliteUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MyApplication extends Application {
    public static DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        SqliteUtil.initDB(getApplicationContext(),"sc.db");
        SharedUtil.init(getApplicationContext(), "file");
        //imageloader框架的配置信息
        ImageLoaderConfiguration configuration
                = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                //设置每一次下载图片的最大数量为100
                .diskCacheFileCount(100)
                //将下载的图片名字进行MD5文件加密
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                //从手机内存中划分出多大的内存用来存储图片
                .diskCacheSize(50 * 1024 * 1024)
                //设置图片下载方式(以栈的形式进行下载)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .threadPriority(3)
                .writeDebugLogs().build();

        ImageLoader.getInstance().init(configuration);


        options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.image_no)
                .showImageOnFail(R.mipmap.image_error)
                .showImageOnLoading(R.mipmap.image_loading)
                .displayer(new RoundedBitmapDisplayer(720))
                .build();
    }
}

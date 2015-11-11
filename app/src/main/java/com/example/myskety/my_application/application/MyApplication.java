package com.example.myskety.my_application.application;

import android.app.Application;

import java.io.File;

import com.example.myskety.my_application.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;


/**
 * MyApplication
 * 
 * @author minking
 */
public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_stub) // 设置图片下载期间显示的图
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存
				.cacheOnDisc(true)
				 // 设置下载的图片是否缓存在SD卡中
				.resetViewBeforeLoading(true)
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图
				.build(); // 创建配置过得DisplayImageOption对象

		File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageloader/Cache");

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.defaultDisplayImageOptions(options).threadPriority(Thread.NORM_PRIORITY - 2)// 线程池加载数
				.denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCache(new UnlimitedDiscCache(cacheDir))// 设置缓存地址

				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

}

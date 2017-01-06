package com.lzy.imagepickerdemo.imageloader;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/3/28
 * 描    述：我的Github地址  https://github.com/jeasonlzy0216
 * 修订历史：
 * ================================================
 */

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImagePickerLoader;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class UILImageLoader implements ImagePickerLoader {

	@Override
	public void displayImage(Activity activity, String path,
			ImageView imageView, int width, int height) {
		ImageSize size = new ImageSize(width, height);
		initImageLoader(activity);
		ImageLoader.getInstance()
		.displayImage(Uri.fromFile(new File(path)).toString(),
				imageView, size);
	}

	/** init ImageLoader **/
	private void initImageLoader(Activity activity) {
		// TODO Auto-generated method stub
		ImageLoaderConfiguration config = null;
		if (!Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			config = new ImageLoaderConfiguration.Builder(activity)
					.memoryCacheExtraOptions(720, 1200)
					.imageDownloader(new CustomImageDownloader(activity))
					.threadPoolSize(6)
					.threadPriority(Thread.NORM_PRIORITY - 1)
					.denyCacheImageMultipleSizesInMemory()
					.memoryCache(
							new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
					.diskCacheSize(50 * 1024 * 1024)
					// You can pass your own memory cache implementation
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					// You can pass your own disc cache implementation
					.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
					.build();
		} else {
			File cacheDir = new File(Environment
					.getExternalStorageDirectory() + "/IMP-Cloud/cache/");
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			config = new ImageLoaderConfiguration.Builder(activity)
					.memoryCacheExtraOptions(720, 1200)
					.imageDownloader(new CustomImageDownloader(activity))
					.threadPoolSize(6)
					.threadPriority(Thread.NORM_PRIORITY - 1)
					.denyCacheImageMultipleSizesInMemory()
					.memoryCache(
							new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
					.diskCacheSize(50 * 1024 * 1024)
					// You can pass your own memory cache implementation
					.tasksProcessingOrder(QueueProcessingType.LIFO)
					.diskCache(new UnlimitedDiskCache(cacheDir))
					// You can pass your own disc cache implementation
					.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
					.build();
		}
		// L.disableLogging(); // 关闭imageloader的疯狂的log
		ImageLoader.getInstance().init(
				config);
	}

	public class CustomImageDownloader extends BaseImageDownloader {// universal
		// image
		// loader获取图片时,若需要cookie，
		// 需在application中进行配置添加此类。
		public CustomImageDownloader(Context context) {
			super(context);
		}

		@Override
		protected HttpURLConnection createConnection(String url, Object extra)
				throws IOException {
			// Super...
			HttpURLConnection connection = super.createConnection(url, extra);
			// connection.setRequestProperty("Authorization", getToken());
			connection.setRequestProperty("Connection", "keep-Alive");
			connection.setRequestProperty("User-Agent", "jsgdMobile");
			return connection;
		}
	}

	@Override
	public void clearMemoryCache() {
	}
}

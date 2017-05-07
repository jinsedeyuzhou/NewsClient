package com.yuxuan.common.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class FileUtils {
	private static final String TAG = FileUtils.class.getSimpleName();



	public FileUtils() {
		super();
	}
	
	/* Checks if external storage is available for read and write */
	/**
	 * 需要存储在外存储器中才需要判断
	 * @return
	 */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}



	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * 创建外部公共文件，APP卸载后不会删除
	 * @param albumName
	 * @return 目录
	 */
	public File getAlbumStorageDir(String albumName) {
		// Get the directory for the user's public pictures directory.
		File file = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				albumName);
		if (!file.mkdirs()) {
			LogUtils.e(TAG, "Directory not created");
		}
		return file;
	}

	/**
	 * 创建外部文件，APP卸载后会删除
	 * @param context
	 * @param albumName
	 * @return 目录
	 */
	public File getAlbumStorageDir(Context context, String albumName) {
		// Get the directory for the app's private pictures directory.
		File file = new File(
				context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
				albumName);
		if (!file.mkdirs()) {
			LogUtils.e(TAG, "Directory not created");
		}
		return file;
	}

	/**
	 * 创建内部缓存文件
	 * @param context
	 * @param url
	 * @return
	 */
	public File getTempFile(Context context, String url) {
		File file = null;
		try {
			String fileName = Uri.parse(url).getLastPathSegment();
			file = File.createTempFile(fileName, null, context.getCacheDir());
		} catch (IOException e) {
			// Error while creating file
		}
		return file;
	}

	/**
	 * 创建内部私人文件
	 * @param context
	 * @param url
	 * @return
	 */
	public File getPrivateFile(Context context, String url) {
		File file = null;
		try {
			String fileName = Uri.parse(url).getLastPathSegment();
			file = File.createTempFile(fileName, null, context.getFilesDir());
		} catch (IOException e) {
			// Error while creating file
		}
		return file;
	}
}

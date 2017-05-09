package com.jerry.javalearning.base;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.litesuits.orm.LiteOrm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 自定义Application
 * <p>
 * Created by Jerry on 2017/5/2.
 */

public class BaseApplication extends Application
{
	public static final String TAG = BaseApplication.class.getName();
	private static final String DB_NAME = "JavaLearning.db";

	private static LiteOrm liteOrm;

	private static BaseApplication baseApplication;

	public static BaseApplication getInstance()
	{
		return baseApplication;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		baseApplication = this;

		try
		{
			initDB();
		}
		catch (IOException e)
		{

			Log.e(TAG, "Error:initDB()" + e.getMessage());
		}

		getLiteOrm();
	}

	/**
	 * 初始化数据库
	 *
	 * @throws IOException IO流异常
	 */
	private void initDB() throws IOException
	{
		// 获取数据库位置
		File file = getDatabasePath(DB_NAME);
		InputStream inputStream = null;
		OutputStream outputStream = null;

		Log.e(TAG, "initDB: file.getPath()" + file.getPath());

		//通过IO流的方式，将assets目录下的数据库文件，写入到SD卡中。
		if (!file.exists())
		{
			try
			{
				inputStream = getClass().getClassLoader().getResourceAsStream("assets/" + DB_NAME);
				outputStream = new FileOutputStream(file);

				byte[] buffer = new byte[1024];
				int len;

				while ((len = inputStream.read(buffer)) != -1)
				{
					outputStream.write(buffer, 0, len);
				}

				Log.e(TAG, "initDB:" + "Complete");
			}
			finally
			{
				if (outputStream != null)
				{
					outputStream.flush();
					outputStream.close();
				}
				if (inputStream != null)
				{
					inputStream.close();
				}
			}

		}
	}

	public static LiteOrm getLiteOrm()
	{
		if (liteOrm == null)
		{
			liteOrm = LiteOrm.newSingleInstance(baseApplication, DB_NAME);
			liteOrm.setDebugged(true);
		}
		return liteOrm;
	}
}

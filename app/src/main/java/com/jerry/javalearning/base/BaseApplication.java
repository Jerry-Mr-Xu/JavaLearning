package com.jerry.javalearning.base;

import android.app.Application;
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
		if (liteOrm == null)
		{
			liteOrm = LiteOrm.newSingleInstance(baseApplication, DB_NAME);
			liteOrm.setDebugged(true);
		}

		try
		{
			initDB();
		}
		catch (IOException e)
		{

			Log.e("Error:initDB()", e.getMessage());
			e.printStackTrace();
		}
	}

	private void initDB() throws IOException
	{
		// TODO 初始化数据库
		//		File dir = getDatabasePath("JavaLearning.db").getParentFile();
		//
		//		if (!dir.exists() || !dir.isDirectory())
		//		{
		//			dir.mkdir();
		//		}
		//
		//		File file = new File(dir, DB_NAME);
		File file = getDatabasePath("JavaLearning.db");
		InputStream inputStream = null;
		OutputStream outputStream = null;

		//通过IO流的方式，将assets目录下的数据库文件，写入到SD卡中。
		if (!file.exists())
		{
			try
			{
				file.createNewFile();

				inputStream = getClass().getClassLoader().getResourceAsStream("assets/" + DB_NAME);
				outputStream = new FileOutputStream(file);

				byte[] buffer = new byte[1024];
				int len;

				while ((len = inputStream.read(buffer)) != -1)
				{
					outputStream.write(buffer, 0, len);
				}

				Log.e("initDB:", "Complete");
			}
			catch (IOException e)
			{
				e.printStackTrace();
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

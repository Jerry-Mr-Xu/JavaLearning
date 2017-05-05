package com.jerry.javalearning.utils;

import android.widget.Toast;

import com.jerry.javalearning.base.BaseApplication;

/**
 * 吐司工具类
 * <p>
 * Created by Jerry on 2017/4/18.
 */

public class ToastUtil
{
	private static Toast toast;

	public static void showToast(String text, int duration)
	{
		if (toast != null)
		{
			toast.setText(text);
			toast.setDuration(duration);
		}
		else
		{
			toast = Toast.makeText(BaseApplication.getInstance(), text, duration);
		}
		toast.show();
	}
}

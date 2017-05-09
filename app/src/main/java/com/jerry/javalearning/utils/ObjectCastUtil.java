package com.jerry.javalearning.utils;

/**
 * 消除Object转其他类型时的Warning
 * <p>
 * Created by Jerry on 2017/5/9.
 */

public class ObjectCastUtil
{
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object obj)
	{
		return (T) obj;
	}
}

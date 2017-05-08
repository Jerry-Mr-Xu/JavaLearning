package com.jerry.javalearning.exam;

/**
 * 考试统计数据类
 * <p>
 * Created by Jerry on 2017/5/8.
 */

public class ExamCountModule
{
	public int totalNum;
	public int correctNum;
	public int errorNum;
	public int undoNum;

	@Override
	public String toString()
	{
		return "ExamCountModule{" + "totalNum=" + totalNum + ", correctNum=" + correctNum + ", errorNum=" + errorNum + ", undoNum=" + undoNum + '}';
	}
}

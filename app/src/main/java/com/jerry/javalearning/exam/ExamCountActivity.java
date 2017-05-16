package com.jerry.javalearning.exam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jerry.javalearning.R;
import com.jerry.javalearning.base.BaseActivity;
import com.jerry.javalearning.databinding.ActivityCountBinding;
import com.jerry.javalearning.utils.ToastUtil;

import java.text.DecimalFormat;

/**
 * 考试统计界面
 * <p>
 * Created by Jerry on 2017/5/8.
 */

public class ExamCountActivity extends BaseActivity
{
	private ActivityCountBinding binding;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_count);

		initActionBar();
		initData();
	}

	@Override
	protected int getContentViewId()
	{
		return R.layout.activity_count;
	}

	private void initData()
	{
		ExamCountModule count = new ExamCountModule();

		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getDatabasePath("JavaLearning.db"), null);
		Cursor cursor = db.rawQuery("select sum(exam_total_num),sum(exam_correct_num),sum(exam_error_num),sum(exam_undo_num) from exam_table", null);
		while (cursor.moveToNext())
		{
			count.totalNum = cursor.getInt(0);
			count.correctNum = cursor.getInt(1);
			count.errorNum = cursor.getInt(2);
			count.undoNum = cursor.getInt(3);
		}
		cursor.close();
		db.close();
		if (count.totalNum == 0)
		{
			showNoCount();
		}
		binding.setCount(count);
		binding.setDecimalFormat(new DecimalFormat("00.00"));
	}

	/**
	 * 显示还没有考试
	 */
	private void showNoCount()
	{
		ToastUtil.showToast("还没有考试哦", Toast.LENGTH_SHORT);
	}

	@Override
	protected boolean isHaveActionBarBack()
	{
		return true;
	}

	@Override
	protected String getActionBarTitle()
	{
		return "考试统计";
	}
}

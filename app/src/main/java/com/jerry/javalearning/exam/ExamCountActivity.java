package com.jerry.javalearning.exam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.javalearning.R;
import com.jerry.javalearning.databinding.ActivityCountBinding;

import java.text.DecimalFormat;

/**
 * 考试统计界面
 * <p>
 * Created by Jerry on 2017/5/8.
 */

public class ExamCountActivity extends AppCompatActivity
{
	private ActivityCountBinding binding;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_count);

		initView();
		initData();
	}

	private void initView()
	{
		ImageView ivActionBarBack = (ImageView) findViewById(R.id.iv_action_bar_back);
		TextView tvActionBarTitle = (TextView) findViewById(R.id.tv_action_bar_title);

		if (ivActionBarBack != null)
		{
			ivActionBarBack.setVisibility(isHaveActionBarBack() ? View.VISIBLE : View.GONE);
			ivActionBarBack.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					finish();
				}
			});
		}
		if (tvActionBarTitle != null)
		{
			tvActionBarTitle.setText(getActionBarTitle());
		}
	}

	private void initData()
	{
		ExamCountModule count = new ExamCountModule();
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getDatabasePath("JavaLearning.db"), null);
		Cursor cursor = db.rawQuery("select sum(test_total_num),sum(test_correct_num),sum(test_error_num),sum(test_undo_num) from test_table", null);
		while (cursor.moveToNext())
		{
			count.totalNum = cursor.getInt(0);
			count.correctNum = cursor.getInt(1);
			count.errorNum = cursor.getInt(2);
			count.undoNum = cursor.getInt(3);
		}
		cursor.close();
		db.close();
		binding.setCount(count);
		binding.setDecimalFormat(new DecimalFormat("00.00"));
	}

	protected boolean isHaveActionBarBack()
	{
		return true;
	}

	protected String getActionBarTitle()
	{
		return "考试统计";
	}
}

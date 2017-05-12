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
import com.jerry.javalearning.base.BaseActivity;
import com.jerry.javalearning.base.BaseApplication;
import com.jerry.javalearning.databinding.ActivityCountBinding;
import com.litesuits.orm.db.assit.QueryBuilder;

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

		count = BaseApplication.getLiteOrm().query(new QueryBuilder<ExamCountModule>(ExamCountModule.class)).get(0);

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

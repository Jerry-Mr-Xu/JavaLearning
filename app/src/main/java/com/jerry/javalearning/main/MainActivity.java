package com.jerry.javalearning.main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jerry.javalearning.R;
import com.jerry.javalearning.base.BaseActivity;
import com.jerry.javalearning.base.BaseApplication;
import com.jerry.javalearning.collect.CollectActivity;
import com.jerry.javalearning.error.ErrorActivity;
import com.jerry.javalearning.exam.ExamCountActivity;
import com.jerry.javalearning.exam.ExamRecordActivity;
import com.jerry.javalearning.learning.LearningActivity;
import com.jerry.javalearning.module.ExamModule;
import com.jerry.javalearning.module.PointModule;
import com.jerry.javalearning.module.QuestionModule;
import com.jerry.javalearning.point.ShowPointActivity;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener
{
	public static final String TAG = MainActivity.class.getName();

	private LinearLayout llExam;
	private TextView tvExamRecord;
	private TextView tvExamCount;
	private LinearLayout llCollection;
	private LinearLayout llError;
	private LinearLayout llStudyInOrder;
	private LinearLayout llStudyInRandom;
	private LinearLayout llStudyInPoint;
	private LinearLayout llTestInOrder;
	private LinearLayout llTestInRandom;
	private LinearLayout llTestInPoint;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initView();
		initListener();
	}

	@Override
	protected int getContentViewId()
	{
		return R.layout.activity_main;
	}

	@Override
	protected boolean isHaveActionBarBack()
	{
		return false;
	}

	@Override
	protected String getActionBarTitle()
	{
		return "在线考试系统";
	}

	private void initListener()
	{
		llExam.setOnClickListener(this);
		tvExamCount.setOnClickListener(this);
		tvExamRecord.setOnClickListener(this);
		llCollection.setOnClickListener(this);
		llError.setOnClickListener(this);
		llStudyInOrder.setOnClickListener(this);
		llStudyInRandom.setOnClickListener(this);
		llStudyInPoint.setOnClickListener(this);
		llTestInOrder.setOnClickListener(this);
		llTestInRandom.setOnClickListener(this);
		llTestInPoint.setOnClickListener(this);
	}

	private void initView()
	{
		llExam = (LinearLayout) findViewById(R.id.ll_exam);
		tvExamRecord = (TextView) findViewById(R.id.tv_exam_record);
		tvExamCount = (TextView) findViewById(R.id.tv_exam_count);
		llCollection = (LinearLayout) findViewById(R.id.ll_collection);
		llError = (LinearLayout) findViewById(R.id.ll_error);
		llStudyInOrder = (LinearLayout) findViewById(R.id.ll_study_in_order);
		llStudyInRandom = (LinearLayout) findViewById(R.id.ll_study_in_random);
		llStudyInPoint = (LinearLayout) findViewById(R.id.ll_study_in_point);
		llTestInOrder = (LinearLayout) findViewById(R.id.ll_test_in_order);
		llTestInRandom = (LinearLayout) findViewById(R.id.ll_test_in_random);
		llTestInPoint = (LinearLayout) findViewById(R.id.ll_test_in_point);
	}

	@Override
	public void onClick(View view)
	{
		Intent intent = new Intent();
		switch (view.getId())
		{
			case R.id.ll_exam:
				intent.setClass(MainActivity.this, LearningActivity.class);
				intent.putExtra("question_list", BaseApplication.getLiteOrm().query(new QueryBuilder<>(QuestionModule.class).appendOrderAscBy("random()").limit(0, 20)));
				intent.putExtra("is_study", false);
				intent.putExtra("is_exam", true);
				intent.putExtra("title", "模拟考试");
				startActivity(intent);
				break;

			case R.id.tv_exam_record:
				intent.setClass(MainActivity.this, ExamRecordActivity.class);
				intent.putExtra("item_list", BaseApplication.getLiteOrm().query(ExamModule.class));
				startActivity(intent);
				break;

			case R.id.tv_exam_count:
				intent.setClass(MainActivity.this, ExamCountActivity.class);
				startActivity(intent);
				break;

			case R.id.ll_collection:
				intent.setClass(MainActivity.this, CollectActivity.class);
				intent.putExtra("question_list", BaseApplication.getLiteOrm().query(new QueryBuilder<>(QuestionModule.class).where("is_collected = ?", true)));
				startActivity(intent);
				break;

			case R.id.ll_error:
				intent.setClass(MainActivity.this, ErrorActivity.class);
				intent.putExtra("question_list", BaseApplication.getLiteOrm().query(new QueryBuilder<>(QuestionModule.class).where("error_num > ?", 0)));
				startActivity(intent);
				break;

			case R.id.ll_study_in_order:
				intent.setClass(MainActivity.this, LearningActivity.class);
				intent.putExtra("question_list", BaseApplication.getLiteOrm().query(QuestionModule.class));
				intent.putExtra("is_study", true);
				intent.putExtra("title", "顺序学习");
				startActivity(intent);
				break;

			case R.id.ll_study_in_random:
				intent.setClass(MainActivity.this, LearningActivity.class);
				intent.putExtra("question_list", BaseApplication.getLiteOrm().query(new QueryBuilder<>(QuestionModule.class).appendOrderAscBy("random()")));
				intent.putExtra("is_study", true);
				intent.putExtra("title", "随机学习");
				startActivity(intent);
				break;

			case R.id.ll_study_in_point:
				intent.setClass(MainActivity.this, ShowPointActivity.class);
				intent.putExtra("item_list", BaseApplication.getLiteOrm().query(PointModule.class));
				intent.putExtra("is_study", true);
				intent.putExtra("title", "专项学习");
				startActivity(intent);
				break;

			case R.id.ll_test_in_order:
				intent.setClass(MainActivity.this, LearningActivity.class);
				intent.putExtra("question_list", BaseApplication.getLiteOrm().query(new QueryBuilder<>(QuestionModule.class)));
				intent.putExtra("is_study", false);
				intent.putExtra("title", "顺序练习");
				startActivity(intent);
				break;

			case R.id.ll_test_in_random:
				intent.setClass(MainActivity.this, LearningActivity.class);
				intent.putExtra("question_list", BaseApplication.getLiteOrm().query(new QueryBuilder<>(QuestionModule.class).appendOrderAscBy("random()").limit(0, 10)));
				intent.putExtra("is_study", false);
				intent.putExtra("title", "随机练习");
				startActivity(intent);
				break;

			case R.id.ll_test_in_point:
				intent.setClass(MainActivity.this, ShowPointActivity.class);
				intent.putExtra("item_list", BaseApplication.getLiteOrm().query(PointModule.class));
				intent.putExtra("is_study", false);
				intent.putExtra("title", "专项练习");
				startActivity(intent);
				break;
		}
	}
}

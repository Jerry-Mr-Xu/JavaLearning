package com.jerry.javalearning.learning;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jerry.javalearning.R;
import com.jerry.javalearning.base.BaseActivity;
import com.jerry.javalearning.base.BaseApplication;
import com.jerry.javalearning.module.ExamModule;
import com.jerry.javalearning.module.QuestionModule;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 学习界面包括考试，学习，练习
 * <p>
 * Created by Jerry on 2017/5/7.
 */

public class LearningActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener
{
	public static final String TAG = LearningActivity.class.getName();

	private ViewPager vpQuestion;
	private QuestionAdapter adapter;
	private List<QuestionModule> questionList;
	private List<Fragment> questionFragmentList = new ArrayList<>();

	private boolean isStudy;
	private boolean isExam;

	private TextView tvShowTime;
	private boolean stopTime;
	private Time currentTime = Time.valueOf("00:00:00");
	private TextView tvShowPage;

	private LinearLayout llCollectOption;
	private CheckBox cbCollectOption;
	private TextView tvFinishOption;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initView();
		initData();
		initListener();
	}

	private void initListener()
	{
		vpQuestion.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{

			}

			@Override
			public void onPageSelected(int position)
			{
				tvShowPage.setText((position + 1) + "/" + questionList.size());
				if (isStudy)
				{
					cbCollectOption.setChecked(questionList.get(position).isCollected);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{

			}
		});

		if (isStudy)
		{
			llCollectOption.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					collectQuestion();
				}
			});
		}
		else
		{
			tvFinishOption.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					showFinishPage();
				}
			});
		}
	}

	private void collectQuestion()
	{
		cbCollectOption.setChecked(!cbCollectOption.isChecked());
		questionList.get(vpQuestion.getCurrentItem()).isCollected = cbCollectOption.isChecked();
		BaseApplication.getLiteOrm().update(questionList.get(vpQuestion.getCurrentItem()));
		Log.d(TAG, "collectQuestion: liteOrm update(" + questionList.get(vpQuestion.getCurrentItem()).toString() + ")");
	}

	private void initData()
	{
		questionList = (List<QuestionModule>) getIntent().getSerializableExtra("question_list");
		if (questionList == null)
		{
			questionList = new ArrayList<>();
		}
		tvShowPage.setText("1/" + questionList.size());

		isStudy = getIntent().getBooleanExtra("is_study", false);
		if (isStudy)
		{
			showCollectOption();
			cbCollectOption.setChecked(questionList.get(0).isCollected);
		}
		else
		{
			showFinishOption();
		}

		isExam = getIntent().getBooleanExtra("is_exam", false);
		if (isExam)
		{
			currentTime = Time.valueOf("00:45:00");
		}
		else
		{
			currentTime = Time.valueOf("00:00:00");
		}
		tvShowTime.setText(currentTime.toString());

		for (int i = 0; i < questionList.size(); i++)
		{
			QuestionModule singleQuestion = questionList.get(i);
			QuestionFragment singleQuestionFragment = new QuestionFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("question", singleQuestion);
			bundle.putInt("position", i);
			singleQuestionFragment.setOnCheckedChangeListener(this);
			singleQuestionFragment.setArguments(bundle);
			questionFragmentList.add(singleQuestionFragment);
		}

		adapter = new QuestionAdapter(getSupportFragmentManager(), questionFragmentList);
		vpQuestion.setAdapter(adapter);
	}

	private void initView()
	{
		tvShowTime = (TextView) findViewById(R.id.tv_show_time);
		tvShowPage = (TextView) findViewById(R.id.tv_show_page);
		vpQuestion = (ViewPager) findViewById(R.id.vp_question);

		llCollectOption = (LinearLayout) findViewById(R.id.ll_collect);
		cbCollectOption = (CheckBox) findViewById(R.id.cb_collect);
		tvFinishOption = (TextView) findViewById(R.id.tv_finish);
	}

	@Override
	public void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		if (!timeHandler.hasMessages(0))
		{
			timeHandler.sendEmptyMessageDelayed(0, 1000);
		}
	}

	@Override
	protected int getContentViewId()
	{
		return R.layout.activity_learning;
	}

	@Override
	protected boolean isHaveActionBarBack()
	{
		return true;
	}

	@Override
	protected String getActionBarTitle()
	{
		return getIntent().getStringExtra("title");
	}

	private Handler timeHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (isExam)
			{
				if (currentTime.getTime() - 1000 > Time.valueOf("00:00:00").getTime())
				{
					currentTime.setTime(currentTime.getTime() - 1000);
				}
				else
				{
					showFinishPage();
				}
			}
			else
			{
				currentTime.setTime(currentTime.getTime() + 1000);
			}
			tvShowTime.setText(currentTime.toString());
			if (!stopTime)
			{
				timeHandler.sendEmptyMessageDelayed(0, 1000);
			}
		}
	};

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i)
	{
		int selectPosition = (int) radioGroup.getTag();
		if (isStudy)
		{
			((QuestionFragment) questionFragmentList.get(selectPosition)).showCorrectAnswer();
		}
		switch (i)
		{
			case R.id.rb_answer1:
				questionList.get(selectPosition).myAnswer = 0;
				if (questionList.get(selectPosition).correctAnswer == 0)
				{
					if (isStudy)
					{
						showNextPage();
					}
				}
				break;

			case R.id.rb_answer2:
				questionList.get(selectPosition).myAnswer = 1;
				if (questionList.get(selectPosition).correctAnswer == 1)
				{
					if (isStudy)
					{
						showNextPage();
					}
				}
				break;

			case R.id.rb_answer3:
				questionList.get(selectPosition).myAnswer = 2;
				if (questionList.get(selectPosition).correctAnswer == 2)
				{
					if (isStudy)
					{
						showNextPage();
					}
				}
				break;

			case R.id.rb_answer4:
				questionList.get(selectPosition).myAnswer = 3;
				if (questionList.get(selectPosition).correctAnswer == 3)
				{
					if (isStudy)
					{
						showNextPage();
					}
				}
				break;
		}
	}

	private void showNextPage()
	{
		if (vpQuestion.getCurrentItem() + 1 < questionList.size())
		{
			vpQuestion.setCurrentItem(vpQuestion.getCurrentItem() + 1, true);
		}
	}

	private void showFinishPage()
	{
		tvFinishOption.setEnabled(false);

		timeHandler.removeMessages(0);
		stopTime = true;

		int correctQuestion = 0, errorQuestion = 0, undoQuestion = 0;
		for (QuestionModule singleQuestion : questionList)
		{
			singleQuestion.isShowCorrect = true;
			if (singleQuestion.myAnswer == singleQuestion.correctAnswer)
			{
				correctQuestion++;
			}
			else if (singleQuestion.myAnswer == -1)
			{
				undoQuestion++;
			}
			else
			{
				singleQuestion.errorNum++;
				errorQuestion++;
			}
		}
		BaseApplication.getLiteOrm().update(questionList);
		Log.d(TAG, "showFinishPage: liteOrm update(" + questionList.toString() + ")");

		if (isExam)
		{
			ExamModule examModule = new ExamModule();
			examModule.totalTime = Time.valueOf("00:45:00").toString();
			examModule.myTime = calTimeDistance(currentTime, Time.valueOf(examModule.totalTime)).toString();
			examModule.totalNum = questionList.size();
			examModule.correctNum = correctQuestion;
			examModule.errorNum = errorQuestion;
			examModule.undoNum = undoQuestion;
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
			examModule.doDate = format.format(new Date());
			long insertLine = BaseApplication.getLiteOrm().insert(examModule);
			Log.d(TAG, "showFinishPage: liteOrm insert(" + examModule.toString() + ")" + "return " + insertLine);
		}

		Bundle bundle = new Bundle();
		bundle.putString("finish_title", isStudy ? "学习完成" : "练习完成");
		bundle.putInt("total_question", questionList.size());
		bundle.putInt("correct_question", correctQuestion);
		bundle.putInt("error_question", errorQuestion);
		bundle.putInt("undo_question", undoQuestion);

		FinishFragment finishFragment = new FinishFragment();
		finishFragment.setArguments(bundle);

		questionFragmentList.add(finishFragment);
		adapter.notifyDataSetChanged();
		vpQuestion.setCurrentItem(questionFragmentList.size() - 1);
	}

	private Time calTimeDistance(Time currentTime, Time totalTime)
	{
		long timeDistance = Math.abs(currentTime.getTime() - totalTime.getTime());
		return new Time(Time.valueOf("00:00:00").getTime() + timeDistance);
	}

	private void showFinishOption()
	{
		llCollectOption.setVisibility(View.GONE);
		tvFinishOption.setVisibility(View.VISIBLE);
	}

	private void showCollectOption()
	{
		llCollectOption.setVisibility(View.VISIBLE);
		tvFinishOption.setVisibility(View.GONE);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		timeHandler.removeMessages(0);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		timeHandler.removeMessages(0);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		timeHandler.removeMessages(0);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		if (!timeHandler.hasMessages(0))
		{
			timeHandler.sendEmptyMessageDelayed(0, 1000);
		}
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		if (!timeHandler.hasMessages(0))
		{
			timeHandler.sendEmptyMessageDelayed(0, 1000);
		}
	}
}

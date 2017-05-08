package com.jerry.javalearning.collect;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jerry.javalearning.R;
import com.jerry.javalearning.base.BaseActivity;
import com.jerry.javalearning.learning.QuestionAdapter;
import com.jerry.javalearning.learning.QuestionFragment;
import com.jerry.javalearning.module.QuestionModule;

import java.util.ArrayList;
import java.util.List;

import static com.jerry.javalearning.R.id.tv_my_page;

/**
 * 收藏界面
 * <p>
 * Created by Jerry on 2017/5/7.
 */

public class CollectActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener
{
	private ViewPager vpQuestion;
	private TextView tvPage;

	private QuestionAdapter adapter;
	private List<QuestionModule> questionList;
	private List<Fragment> questionFragmentList = new ArrayList<>();

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
				tvPage.setText((position + 1) + "/" + questionList.size());
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{

			}
		});
	}

	private void initData()
	{
		questionList = (List<QuestionModule>) getIntent().getSerializableExtra("question_list");
		if (questionList == null)
		{
			questionList = new ArrayList<>();
		}
		tvPage.setText(questionList.size() == 0 ? "0/" : "1/" + questionList.size());

		for (int i = 0; i < questionList.size(); i++)
		{
			QuestionFragment singleQuestionFragment = new QuestionFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("question", questionList.get(i));
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
		vpQuestion = (ViewPager) findViewById(R.id.vp_my_question);
		tvPage = (TextView) findViewById(tv_my_page);
	}

	@Override
	protected int getContentViewId()
	{
		return R.layout.activity_my_question;
	}

	@Override
	protected boolean isHaveActionBarBack()
	{
		return true;
	}

	@Override
	protected String getActionBarTitle()
	{
		return "我的收藏";
	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i)
	{
		int selectPosition = (int) radioGroup.getTag();
		((QuestionFragment) questionFragmentList.get(selectPosition)).showCorrectAnswer();
		switch (i)
		{
			case R.id.rb_answer1:
				if (questionList.get(selectPosition).correctAnswer == 0)
				{
					showNextPage();
				}
				break;

			case R.id.rb_answer2:
				if (questionList.get(selectPosition).correctAnswer == 1)
				{
					showNextPage();
				}
				break;

			case R.id.rb_answer3:
				if (questionList.get(selectPosition).correctAnswer == 2)
				{
					showNextPage();
				}
				break;

			case R.id.rb_answer4:
				if (questionList.get(selectPosition).correctAnswer == 3)
				{
					showNextPage();
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
}

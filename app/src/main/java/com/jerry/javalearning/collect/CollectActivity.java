package com.jerry.javalearning.collect;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.javalearning.R;
import com.jerry.javalearning.base.BaseActivity;
import com.jerry.javalearning.learning.QuestionAdapter;
import com.jerry.javalearning.learning.QuestionFragment;
import com.jerry.javalearning.module.QuestionModule;
import com.jerry.javalearning.utils.ObjectCastUtil;
import com.jerry.javalearning.utils.ToastUtil;

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
	private List<QuestionModule> questionList;
	private List<Fragment> questionFragmentList = new ArrayList<>();

	private TextView tvPage;

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
				showPageNum();
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{

			}
		});
	}

	private void initData()
	{
		// 获取问题列表
		questionList = ObjectCastUtil.cast(getIntent().getSerializableExtra("question_list"));
		if (questionList == null)
		{
			questionList = new ArrayList<>();
		}

		if (questionList.size() == 0)
		{
			hidePageNum();
			initNoQuestionFragment();
		}
		else
		{
			showPageNum();
			initQuestionFragment();
		}

		QuestionAdapter adapter = new QuestionAdapter(getSupportFragmentManager(), questionFragmentList);
		vpQuestion.setAdapter(adapter);
	}

	/**
	 * 生成没有问题时页面
	 */
	private void initNoQuestionFragment()
	{
		// TODO
		ToastUtil.showToast("没有收藏哦", Toast.LENGTH_SHORT);
	}

	/**
	 * 由问题数据集生成问题页面
	 */
	private void initQuestionFragment()
	{
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
		// 显示当前页的正确答案
		((QuestionFragment) questionFragmentList.get(vpQuestion.getCurrentItem())).showCorrectAnswer();

		// 检测当前选择是否是正确答案
		switch (i)
		{
			case R.id.rb_answer1:
				if (questionList.get(vpQuestion.getCurrentItem()).correctAnswer == 0)
				{
					showNextPage();
				}
				break;

			case R.id.rb_answer2:
				if (questionList.get(vpQuestion.getCurrentItem()).correctAnswer == 1)
				{
					showNextPage();
				}
				break;

			case R.id.rb_answer3:
				if (questionList.get(vpQuestion.getCurrentItem()).correctAnswer == 2)
				{
					showNextPage();
				}
				break;

			case R.id.rb_answer4:
				if (questionList.get(vpQuestion.getCurrentItem()).correctAnswer == 3)
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

	/**
	 * 显示页数
	 */
	private void showPageNum()
	{
		if (tvPage.getVisibility() == View.GONE)
		{
			tvPage.setVisibility(View.VISIBLE);
		}
		tvPage.setText((vpQuestion.getCurrentItem() + 1) + "/" + questionList.size());
	}

	private void hidePageNum()
	{
		if (tvPage.getVisibility() == View.VISIBLE)
		{
			tvPage.setVisibility(View.GONE);
		}
	}
}

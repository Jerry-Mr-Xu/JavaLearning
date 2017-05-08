package com.jerry.javalearning.learning;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 问题ViewPager适配器
 * <p>
 * Created by Jerry on 2017/5/7.
 */

public class QuestionAdapter extends FragmentStatePagerAdapter
{
	private List<Fragment> questionFragmentList;

	public QuestionAdapter(FragmentManager fm, List<Fragment> questionFragmentList)
	{
		super(fm);
		this.questionFragmentList = questionFragmentList;
	}

	@Override
	public Fragment getItem(int position)
	{
		return questionFragmentList.get(position);
	}

	@Override
	public int getCount()
	{
		return questionFragmentList.size();
	}

	public void setQuestionFragmentList(List<Fragment> questionFragmentList)
	{
		this.questionFragmentList = questionFragmentList;
	}
}

package com.jerry.javalearning.learning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jerry.javalearning.R;
import com.jerry.javalearning.module.QuestionModule;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题Fragment
 * <p>
 * Created by Jerry on 2017/5/7.
 */

public class QuestionFragment extends Fragment
{
	public static final String TAG = QuestionFragment.class.getName();

	private TextView tvQuestion;
	private RadioGroup rgAnswers;
	private List<RadioButton> rbAnswerList = new ArrayList<>();

	private QuestionModule questionModule;

	private int position;
	private RadioGroup.OnCheckedChangeListener listener;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View contentView = inflater.inflate(R.layout.fragment_question, container, false);
		initView(contentView);
		initData();

		return contentView;
	}

	private void initData()
	{
		if (questionModule == null)
		{
			Bundle bundle = getArguments();
			questionModule = (QuestionModule) bundle.getSerializable("question");
			position = bundle.getInt("position");
		}

		if (questionModule != null)
		{
			tvQuestion.setText(questionModule.question);
			rgAnswers.setTag(position);
			rbAnswerList.get(0).setText(questionModule.answer1);
			rbAnswerList.get(1).setText(questionModule.answer2);
			if (questionModule.answer3 == null || questionModule.answer3.isEmpty())
			{
				rbAnswerList.get(2).setVisibility(View.GONE);
			}
			else
			{
				rbAnswerList.get(2).setText(questionModule.answer3);
			}
			if (questionModule.answer4 == null || questionModule.answer4.isEmpty())
			{
				rbAnswerList.get(3).setVisibility(View.GONE);
			}
			else
			{
				rbAnswerList.get(3).setText(questionModule.answer4);
			}
		}
	}

	private void initView(View contentView)
	{
		tvQuestion = (TextView) contentView.findViewById(R.id.tv_question);
		rgAnswers = (RadioGroup) contentView.findViewById(R.id.rg_answers);
		rbAnswerList.clear();
		rbAnswerList.add((RadioButton) contentView.findViewById(R.id.rb_answer1));
		rbAnswerList.add((RadioButton) contentView.findViewById(R.id.rb_answer2));
		rbAnswerList.add((RadioButton) contentView.findViewById(R.id.rb_answer3));
		rbAnswerList.add((RadioButton) contentView.findViewById(R.id.rb_answer4));
	}

	@Override
	public void onResume()
	{
		super.onResume();
		rgAnswers.setOnCheckedChangeListener(listener);
		if (questionModule.isShowCorrect)
		{
			rbAnswerList.get(questionModule.correctAnswer).setTextColor(getResources().getColor(R.color.text_correct_green));
		}
	}

	public void showCorrectAnswer()
	{
		questionModule.isShowCorrect = true;
		rbAnswerList.get(questionModule.correctAnswer).setTextColor(getResources().getColor(R.color.text_correct_green));
	}

	public void setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener listener)
	{
		this.listener = listener;
	}
}

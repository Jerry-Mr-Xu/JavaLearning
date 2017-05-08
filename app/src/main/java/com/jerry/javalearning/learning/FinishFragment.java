package com.jerry.javalearning.learning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jerry.javalearning.R;

/**
 * 结束界面
 * <p>
 * Created by Jerry on 2017/5/7.
 */

public class FinishFragment extends Fragment
{
	private static final String TAG = FinishFragment.class.getName();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View contentView = inflater.inflate(R.layout.fragment_finish, container, false);
		initView(contentView);

		return contentView;
	}

	private void initView(View contentView)
	{
		Bundle bundle = getArguments();
		if (bundle != null)
		{
			((TextView) contentView.findViewById(R.id.tv_finish_title)).setText(bundle.getString("finish_title"));
			((TextView) contentView.findViewById(R.id.tv_total_question)).setText("总题数:" + bundle.getInt("total_question"));
			((TextView) contentView.findViewById(R.id.tv_correct_question)).setText("答对数:" + bundle.getInt("correct_question"));
			((TextView) contentView.findViewById(R.id.tv_error_question)).setText("答错数:" + bundle.getInt("error_question"));
			((TextView) contentView.findViewById(R.id.tv_undo_question)).setText("未答数:" + bundle.getInt("undo_question"));
		}
	}
}

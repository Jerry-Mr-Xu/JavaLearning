package com.jerry.javalearning.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.javalearning.R;

/**
 * Activity基类
 * <p>
 * Created by Jerry on 2017/5/7.
 */

public abstract class BaseActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(getContentViewId());

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

	protected abstract int getContentViewId();

	protected abstract boolean isHaveActionBarBack();

	protected abstract String getActionBarTitle();
}

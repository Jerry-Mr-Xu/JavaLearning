package com.jerry.javalearning.exam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jerry.javalearning.R;
import com.jerry.javalearning.base.BaseActivity;
import com.jerry.javalearning.base.ListShowAdapter;
import com.jerry.javalearning.module.BaseItemModule;
import com.jerry.javalearning.module.ExamModule;

import java.util.ArrayList;
import java.util.List;

/**
 * 考试记录
 * <p>
 * Created by Jerry on 2017/5/8.
 */

public class ExamRecordActivity extends BaseActivity
{
	public static final String TAG = ExamRecordActivity.class.getName();

	private RecyclerView rvList;
	private ListShowAdapter adapter;
	private List<BaseItemModule> baseItemList;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initView();
		initData();
	}

	private void initData()
	{
		baseItemList = new ArrayList<BaseItemModule>((ArrayList<ExamModule>) getIntent().getSerializableExtra("item_list"));
		adapter = new ListShowAdapter(baseItemList);
		rvList.setAdapter(adapter);
	}

	private void initView()
	{
		rvList = (RecyclerView) findViewById(R.id.rv_list);
		rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
	}

	@Override
	protected int getContentViewId()
	{
		return R.layout.activity_list_show;
	}

	@Override
	protected boolean isHaveActionBarBack()
	{
		return true;
	}

	@Override
	protected String getActionBarTitle()
	{
		return "考试记录";
	}
}

package com.jerry.javalearning.exam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jerry.javalearning.R;
import com.jerry.javalearning.base.BaseActivity;
import com.jerry.javalearning.base.BaseApplication;
import com.jerry.javalearning.base.ListShowAdapter;
import com.jerry.javalearning.module.BaseItemModule;
import com.jerry.javalearning.module.ExamModule;
import com.jerry.javalearning.utils.ObjectCastUtil;
import com.jerry.javalearning.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 考试记录
 * <p>
 * Created by Jerry on 2017/5/8.
 */

public class ExamRecordActivity extends BaseActivity implements ListShowAdapter.OnItemDeleteClickListener
{
	public static final String TAG = ExamRecordActivity.class.getName();

	private RecyclerView rvList;
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
		ArrayList<ExamModule> examList = ObjectCastUtil.cast(getIntent().getSerializableExtra("item_list"));
		baseItemList = new ArrayList<BaseItemModule>(examList);
		if (baseItemList.size() == 0)
		{
			showNoRecord();
		}
		ListShowAdapter adapter = new ListShowAdapter(baseItemList);
		adapter.setOnItemDeleteClickListener(ExamRecordActivity.this);
		rvList.setAdapter(adapter);
	}

	/**
	 * 没有记录
	 */
	private void showNoRecord()
	{
		// TODO
		ToastUtil.showToast("没有考试记录哦", Toast.LENGTH_SHORT);
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

	@Override
	public void onItemDeleteClick(ListShowAdapter adapter, int position)
	{
		BaseApplication.getLiteOrm().delete(baseItemList.get(position));
		baseItemList.remove(position);
		adapter.notifyDataSetChanged();

		if (baseItemList.size() == 0)
		{
			showNoRecord();
		}
	}
}

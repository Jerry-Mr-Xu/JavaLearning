package com.jerry.javalearning.point;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jerry.javalearning.R;
import com.jerry.javalearning.base.BaseActivity;
import com.jerry.javalearning.base.ListShowAdapter;
import com.jerry.javalearning.learning.LearningActivity;
import com.jerry.javalearning.module.BaseItemModule;
import com.jerry.javalearning.module.PointModule;
import com.jerry.javalearning.module.QuestionModule;
import com.jerry.javalearning.utils.ObjectCastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识点界面
 * <p>
 * Created by Jerry on 2017/5/8.
 */

public class ShowPointActivity extends BaseActivity implements ListShowAdapter.OnItemClickListener
{
	public static final String TAG = ShowPointActivity.class.getName();

	private RecyclerView rvList;
	private List<BaseItemModule> itemList;

	private boolean isStudy;
	private String title;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		initView();
		initData();
	}

	private void initData()
	{
		ArrayList<PointModule> pointList = ObjectCastUtil.cast(getIntent().getSerializableExtra("item_list"));
		itemList = new ArrayList<BaseItemModule>(pointList);
		isStudy = getIntent().getBooleanExtra("is_study", false);
		title = getIntent().getStringExtra("title");

		ListShowAdapter adapter = new ListShowAdapter(itemList);
		adapter.setOnItemClickListener(this);
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
		return "知识点";
	}

	@Override
	public void onItemClick(ListShowAdapter adapter, int position)
	{
		ArrayList<QuestionModule> questionList = new ArrayList<>();
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getDatabasePath("JavaLearning.db"), null);
		Cursor cursor = db.rawQuery("select q.id,q.type,q.question,q.answer1,q.answer2,q.answer3,q.answer4,q.correct_answer,q.is_collected,q.is_deleted,q.error_num from question_table q inner join point_and_question_table pq on q.id=pq.question_id inner join point_table p on p.id=pq.point_id where p.id=?", new String[]{((PointModule) itemList.get(position)).id + ""});
		while (cursor.moveToNext())
		{
			QuestionModule singleQuestion = new QuestionModule();
			singleQuestion.id = cursor.getInt(0);
			singleQuestion.type = cursor.getInt(1);
			singleQuestion.question = cursor.getString(2);
			singleQuestion.answer1 = cursor.getString(3);
			singleQuestion.answer2 = cursor.getString(4);
			singleQuestion.answer3 = cursor.getString(5);
			singleQuestion.answer4 = cursor.getString(6);
			singleQuestion.correctAnswer = cursor.getInt(7);
			singleQuestion.isCollected = cursor.getInt(8) == 1;
			singleQuestion.isDeleted = cursor.getInt(9) == 1;
			singleQuestion.errorNum = cursor.getInt(10);
			questionList.add(singleQuestion);
		}
		cursor.close();
		db.close();

		Intent intent = new Intent(ShowPointActivity.this, LearningActivity.class);
		intent.putExtra("question_list", questionList);
		intent.putExtra("is_study", isStudy);
		intent.putExtra("title", title);
		startActivity(intent);
	}
}

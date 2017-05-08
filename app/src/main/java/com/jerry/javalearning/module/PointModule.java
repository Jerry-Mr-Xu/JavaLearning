package com.jerry.javalearning.module;

import com.jerry.javalearning.R;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * 知识点表
 * <p>
 * Created by Jerry on 2017/5/5.
 */

@Table("point_table")
public class PointModule implements Serializable, BaseItemModule
{
	@PrimaryKey(AssignType.AUTO_INCREMENT)
	public int id;

	@Column("point_content")
	public String content;

	@Override
	public String toString()
	{
		return "PointModule{" + "id=" + id + ", content='" + content + '\'' + '}';
	}

	@Override
	public int getLayout()
	{
		return R.layout.item_point;
	}
}

package com.jerry.javalearning.module;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.sql.Time;

/**
 * 考试记录表
 * <p>
 * Created by Jerry on 2017/5/5.
 */

@Table("test_table")
public class ExamModule
{
	@PrimaryKey(AssignType.AUTO_INCREMENT)
	public int id;

	@Column("test_total_time")
	public Time totalTime;

	@Column("test_my_time")
	public Time myTime;

	@Column("test_total_num")
	public int totalNum;

	@Column("test_correct_num")
	public int correctNum;

	@Column("test_error_num")
	public int errorNum;

	@Column("test_undo_num")
	public int undoNum;

	@Override
	public String toString()
	{
		return "ExamModule{" + "id=" + id + ", totalTime=" + totalTime + ", myTime=" + myTime + ", totalNum=" + totalNum + ", correctNum=" + correctNum + ", errorNum=" + errorNum + ", undoNum=" + undoNum + '}';
	}
}

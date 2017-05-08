package com.jerry.javalearning.module;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * 题表
 * <p>
 * Created by Jerry on 2017/5/5.
 */

@Table("question_table")
public class QuestionModule implements Serializable
{
	@PrimaryKey(AssignType.AUTO_INCREMENT)
	public int id;

	public int type;

	@NotNull
	public String question;

	public String answer1;

	public String answer2;

	public String answer3;

	public String answer4;

	@Column("correct_answer")
	public int correctAnswer;

	@Ignore
	public int myAnswer = -1;

	@Column("is_collected")
	public boolean isCollected;

	@Column("is_deleted")
	public boolean isDeleted;

	@Column("error_num")
	public int errorNum;

	@Ignore
	public boolean isShowCorrect;

	@Override
	public String toString()
	{
		return "QuestionModule{" + "id=" + id + ", type=" + type + ", question='" + question + '\'' + ", answer1='" + answer1 + '\'' + ", answer2='" + answer2 + '\'' + ", answer3='" + answer3 + '\'' + ", answer4='" + answer4 + '\'' + ", correctAnswer=" + correctAnswer + ", myAnswer=" + myAnswer + ", isCollected=" + isCollected + ", isDeleted=" + isDeleted + ", errorNum=" + errorNum + ", isShowCorrect=" + isShowCorrect + '}';
	}
}

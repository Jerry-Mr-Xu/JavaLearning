package com.jerry.javalearning.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jerry.javalearning.R;

/**
 * 饼图
 * <p>
 * Created by Jerry on 2017/5/8.
 */

public class PieView extends View
{
	private RectF viewRect;
	private Paint paint;

	private float correctPercent;
	private float errorPercent;
	private float undoPercent;

	public PieView(Context context)
	{
		super(context);
	}

	public PieView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);

		viewRect = new RectF(0, 0, w, h);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		paint.setColor(getResources().getColor(R.color.colorAccent));
		canvas.drawArc(viewRect, -90, -correctPercent * 360, true, paint);
		//		canvas.drawArc(viewRect, 0, 120, true, paint);
		paint.setColor(getResources().getColor(R.color.text_error_red));
		canvas.drawArc(viewRect, -correctPercent * 360 - 90, -errorPercent * 360, true, paint);
		//		canvas.drawArc(viewRect, 120, 240, true, paint);
		paint.setColor(getResources().getColor(R.color.text_gray_blue));
		canvas.drawArc(viewRect, -(correctPercent + errorPercent) * 360 - 90, -undoPercent * 360, true, paint);
		//		canvas.drawArc(viewRect, 240, 120, true, paint);
	}


	public float getCorrectPercent()
	{
		return correctPercent;
	}

	public void setCorrectPercent(float correctPercent)
	{
		this.correctPercent = correctPercent;
	}

	public float getErrorPercent()
	{
		return errorPercent;
	}

	public void setErrorPercent(float errorPercent)
	{
		this.errorPercent = errorPercent;
	}

	public float getUndoPercent()
	{
		return undoPercent;
	}

	public void setUndoPercent(float undoPercent)
	{
		this.undoPercent = undoPercent;
	}
}

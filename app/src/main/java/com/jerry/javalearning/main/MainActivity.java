package com.jerry.javalearning.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jerry.javalearning.R;

public class MainActivity extends AppCompatActivity
{
	public static final String TAG = MainActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}

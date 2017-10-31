package com.sabbir.personal_s_diary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class SQLView extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sql_database);
		TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
		DatabaseClass info = new DatabaseClass(this);


		info.open();
		String data = info.getData();
		info.close();
		tv.setText(data);
	}
}

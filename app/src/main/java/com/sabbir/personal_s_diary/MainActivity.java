package com.sabbir.personal_s_diary;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener
{

	Button sqlUpdate, sqlView, sqlDelete, sqlModify, sqlGetInfo;
	EditText sqlName, sqlDescription, sqlRow;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		sqlName = (EditText) findViewById(R.id.etSQLName);
		sqlDescription = (EditText) findViewById(R.id.etSQLDescription);
		sqlRow = (EditText) findViewById(R.id.etSQLrowInfo);


		sqlView = (Button) findViewById(R.id.bSQLopenView);
		sqlUpdate = (Button) findViewById(R.id.bSQLUpdate);
		sqlDelete = (Button) findViewById(R.id.bSQLdelete);
		sqlModify = (Button) findViewById(R.id.bSQLmodify);
		sqlGetInfo = (Button) findViewById(R.id.bgetInfo);

		sqlView.setOnClickListener(this);
		sqlUpdate.setOnClickListener(this);
		sqlDelete.setOnClickListener(this);
		sqlModify.setOnClickListener(this);
		sqlGetInfo.setOnClickListener(this);
	}

	public void onClick(View arg0)
	{
		switch (arg0.getId())
		{
		case R.id.bSQLUpdate:
			Boolean didItWork = true;
			try
			{
				String name = sqlName.getText().toString();
				String description = sqlDescription.getText().toString();
				
				if(!(name.matches("") || description.matches("")))
				{
					DatabaseClass entry = new DatabaseClass(MainActivity.this);
					entry.open();
					entry.createEntry(name, description);
					entry.close();
					
					Dialog d2 = new Dialog(this);
					d2.setTitle("Item added!");
					TextView tv = new TextView(this);
					tv.setText("Success !");
					d2.setContentView(tv);
					d2.show();
				}else { 
				
					Dialog d2 = new Dialog(this);
					d2.setTitle("No item added! Input field is empty!!!");
					TextView tv = new TextView(this);
					tv.setText("Failed !!!!!!!!!!!!!!");
					d2.setContentView(tv);
					d2.show();
				}

			} catch (Exception e)
			{
				didItWork = false;
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Dang it!");
				TextView tv = new TextView(this);
				tv.setText(error);
				d.setContentView(tv);
				d.show();
			}/* finally
			{
				if (didItWork)
				{
					Dialog d = new Dialog(this);
					d.setTitle("Added New Item!");
					TextView tv = new TextView(this);
					tv.setText("SUCCESS");
					d.setContentView(tv);
					d.show();
				}
			}*/
			sqlName.setText("");
			sqlDescription.setText("");
			sqlRow.setText("");
			break;

		case R.id.bSQLopenView:
			sqlName.setText("");
			sqlDescription.setText("");
			sqlRow.setText("");
			Intent i = new Intent("com.sabbir.personal_s_diary.SQLVIEW");
			startActivity(i);
			break;

		case R.id.bgetInfo:
			Boolean didItWork2 = true;
			try
			{
				String s = sqlRow.getText().toString();
								
				if(!s.matches(""))
				{
					long l = Long.parseLong(s);
					DatabaseClass hon = new DatabaseClass(this);
					hon.open();
					String returnedName = hon.getName(l);
					String returnedHotness = hon.getDescription(l);
					hon.close();
		
					sqlName.setText(returnedName);
					sqlDescription.setText(returnedHotness);
				} else
				{
					Dialog d2 = new Dialog(this);
					d2.setTitle("Input field is empty!!!");

					TextView tv = new TextView(this);
					tv.setText("Failed !!!!!!!!!!!!!!");
					d2.setContentView(tv);
					d2.show();
				}
			} catch (Exception e)
			{
				didItWork2 = false;
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Dang it!");
				TextView tv = new TextView(this);
				tv.setText(error);
				d.setContentView(tv);
				d.show();
			}

			break;

		case R.id.bSQLmodify:
			String mName = sqlName.getText().toString();
			String mHotness = sqlDescription.getText().toString();
			String sRow = sqlRow.getText().toString();

			if(!(mName.matches("") || mHotness.matches("") || sRow.matches("")))
			{
				long lRow = Long.parseLong(sRow);
			
				DatabaseClass ex = new DatabaseClass(this);
				ex.open();
				ex.updateEntry(lRow, mName, mHotness);
				ex.close();
				
				Dialog d2 = new Dialog(this);
				d2.setTitle("Item was modified!");
				TextView tv = new TextView(this);
				tv.setText("Success !");
				d2.setContentView(tv);
				d2.show();
			} else { 
				
				Dialog d2 = new Dialog(this);
				d2.setTitle("Item wasn't modified! One or more input field is empty!!!");
				TextView tv = new TextView(this);
				tv.setText("Failed !!!!!!!!!!!!!!");
				d2.setContentView(tv);
				d2.show();
			}
			sqlName.setText("");
			sqlDescription.setText("");
			sqlRow.setText("");
			break;

		case R.id.bSQLdelete:
			String sRow1 = sqlRow.getText().toString();
			
			if(!(sRow1.matches("")))
			{
				long lRow1 = Long.parseLong(sRow1);
	
				DatabaseClass ex1 = new DatabaseClass(this);
				ex1.open();
				ex1.deleteEntry(lRow1);
				ex1.close();
				
				Dialog d2 = new Dialog(this);
				d2.setTitle("Item was DELETED !");
				TextView tv = new TextView(this);
				tv.setText("Success !");
				d2.setContentView(tv);
				d2.show();
			} else { 
				
				Dialog d2 = new Dialog(this);
				d2.setTitle("Item wasn't DELETED! Input field is empty!!!");
				TextView tv = new TextView(this);
				tv.setText("Failed !!!!!!!!!!!!!!");
				d2.setContentView(tv);
				d2.show();
			}
			sqlName.setText("");
			sqlDescription.setText("");
			sqlRow.setText("");
			break;
		}
	}

}

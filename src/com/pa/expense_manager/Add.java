package com.pa.expense_manager;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Add extends Activity {
	
	EditText date,price,event;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		date=(EditText)findViewById(R.id.dd);
		price=(EditText)findViewById(R.id.rs);
		event=(EditText)findViewById(R.id.data);
		db=openOrCreateDatabase("mydb",0,null);
	}
	public void save(View v)
	{
		String d=date.getText().toString().trim();
		String p=price.getText().toString().trim();
		String ev=event.getText().toString().trim();
		try
		{
			db.execSQL("insert into reminder values('"+d+"','"+ev+"','"+p+"');");
			Toast.makeText(Add.this,"Reminder saved.",Toast.LENGTH_LONG).show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void back(View v)
	{
		finish();
	}

}

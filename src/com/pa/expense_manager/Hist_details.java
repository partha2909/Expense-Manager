package com.pa.expense_manager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Hist_details extends Activity{
	ListView lv;
	int n=0;
	SQLiteDatabase db;
	String details[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hist_details);
		lv=(ListView)findViewById(R.id.lv);
		db=openOrCreateDatabase("mydb",0,null);
		Intent i=getIntent();
		Bundle b=i.getExtras();
		String date=b.getString("m");
		Cursor c=db.rawQuery("select event,price from expense where dd='"+date+"';",null);
		details=new String[c.getCount()];
		n=0;
		while(c.moveToNext())
		{
			details[n++]="Rs."+c.getInt(1)+" is spent on "+c.getString(0);
		}
		ArrayAdapter<String> ob=new ArrayAdapter<String>(Hist_details.this,android.R.layout.simple_list_item_1,details);
		lv.setAdapter(ob);
	}

}

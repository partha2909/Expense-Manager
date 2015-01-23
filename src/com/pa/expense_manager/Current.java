package com.pa.expense_manager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Current extends Activity{
	SQLiteDatabase db;
	int i=0,today_amount=0,n;
	String last_date,str;
	EditText p,e;
	TextView tym,amt;
	ArrayAdapter<String> spinnerAdapter;
	Spinner sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.current);
		p=(EditText)findViewById(R.id.price);
		e=(EditText)findViewById(R.id.event);
		tym=(TextView)findViewById(R.id.time);
		amt=(TextView)findViewById(R.id.amount);
		sp=(Spinner)findViewById(R.id.spin);
		db=openOrCreateDatabase("mydb",0,null);
		try
		{
			db.execSQL("create table expense(dd date,event varchar(20),price number(10)primary key);");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String date = df.format(cal.getTime());
		
		
		//saving the last date of the database so that we can get the expenses of present day(last entry)
		 Cursor c=db.rawQuery("SELECT dd FROM expense order by dd desc limit 1 ", null);

		if(c.moveToNext())
		{
			last_date=c.getString(0);
		}
		
		//making spinner to list the expenses of present day
		spinnerAdapter = new ArrayAdapter<String>(Current.this, android.R.layout.simple_spinner_item);
				spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerAdapter.clear();
				spinnerAdapter.add("Details");
				sp.setAdapter(spinnerAdapter);
				
				
			
				if(date.equals(last_date))
				{	
					//adding data to spinner for the present day
				  c=db.rawQuery("SELECT event,price FROM expense where dd='"+last_date+"'", null);
			//	spinnerAdapter.add("Details");
				while(c.moveToNext())
				{
					today_amount = today_amount +c.getInt(1);
					String tmp=""+c.getInt(1);
					str="Rs."+c.getString(1)+" spent on "+tmp;
					spinnerAdapter.add(str);
					spinnerAdapter.notifyDataSetChanged();
					
				}
				}
				//displaying current date and amount spent on that day when the app starts
				amt.setText(today_amount+"");
				tym.setText(date);
	}
	
	
	public void save(View v)
	{	
		String ta=amt.getText().toString().trim();
		String price=p.getText().toString().trim();
		String event=e.getText().toString().trim();
		String date=tym.getText().toString().trim();
		
		if(!price.equals(null))
		{
			 n=Integer.parseInt(price);
		}
		try
		{
			db.execSQL("insert into expense (dd,event,price) values('"+date+"','"+event+"',"+n+");");			
		}
		catch(Exception e)
		{
			Toast.makeText(this,"Enter the price.",Toast.LENGTH_LONG).show();
		}
	
		//Saving the last date of the table
		Cursor c=db.rawQuery("SELECT dd FROM expense order by dd desc limit 1 ", null);
		if(c.moveToNext())
		{
			last_date=c.getString(0);
		}
		int nta = Integer.parseInt(price)+Integer.parseInt(ta);
		amt.setText(nta + "");
		spinnerAdapter.clear();
		spinnerAdapter.add("Details");
		c=db.rawQuery("SELECT event,price FROM expense where dd='"+last_date+"'", null);
		while(c.moveToNext())
		{
			int x=c.getInt(1);
			str="Rs."+x+" spent on "+c.getString(0);
			spinnerAdapter.add(str);
			spinnerAdapter.notifyDataSetChanged();
		}
		sp.setAdapter(spinnerAdapter);
		p.setText("");
		e.setText("");
			
		
				
	}
	public void back(View v)
	{
		finish();
	}
}

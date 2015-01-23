package com.pa.expense_manager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class History extends Activity {
	ListView lv;
	TextView tv;
	String dd[],details[];
	int arr[],count=0;
	int sum=0,f=0,i=0;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		lv=(ListView)findViewById(R.id.lv);
		tv=(TextView)findViewById(R.id.tv);
		db=openOrCreateDatabase("mydb",0,null);
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String date = df.format(cal.getTime());
		Cursor c=db.rawQuery("select sum(price),dd from expense group by dd having dd<'"+date+"';",null);
		count=c.getCount();
		//Toast.makeText(History.this,""+count,Toast.LENGTH_LONG).show();
		i=0;
		f=0;
	if(count>0)
	{	
		tv.setText("DETAILS OF LAST SEVEN DAYS");
		if(count>7)
		{
			arr= new int[7];
			dd= new String[7];
			details= new String[7];
			while(c.moveToNext())
			{
				f++;
				if(f>(count-7))
				{
					arr[i]=c.getInt(0);
					dd[i]=c.getString(1);
					details[i]=dd[i]+"	Amount Spent=Rs"+arr[i] ;
					i++;
				}
			}
		}
		else
		{
			arr= new int[count];
			dd= new String[count];
			details= new String[count];
			//Toast.makeText(History.this,"yes",Toast.LENGTH_LONG).show();
			while(c.moveToNext())
			{
				arr[i]=c.getInt(0);
				dd[i]=c.getString(1);
				details[i]=dd[i]+"	Amount Spent=Rs"+arr[i] ;
				i++;
			}
		}
		ArrayAdapter<String> ob=new ArrayAdapter<String>(History.this,android.R.layout.simple_list_item_1,details);
		lv.setAdapter(ob);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
			{
				String date=dd[arg2];
				Intent i=new Intent(History.this,Hist_details.class);
				i.putExtra("m",date);
				startActivity(i);
			}
		});
	}
	else
	{
		tv.setText("NO DETAILS");
	}
		
	}

}

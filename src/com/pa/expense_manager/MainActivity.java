package com.pa.expense_manager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	int count=0;
	String arr,num;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db=openOrCreateDatabase("mydb",0,null);
		
		try
		{
			db.execSQL("create table reminder (dd date,event varchar(50),price varchar(10));");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String date = df.format(cal.getTime());
		Cursor c=db.rawQuery("select * from reminder where dd='"+date+"';",null);
		count=c.getCount();
		Toast.makeText(this,""+count,Toast.LENGTH_LONG).show();
		while(c.moveToNext())
		{
			arr=c.getString(1);
			num=c.getString(2);
			Notification n=new Notification(android.R.drawable.stat_sys_warning,"Expense Pending",System.currentTimeMillis());
			n.defaults=Notification.DEFAULT_SOUND;
			Intent i=new Intent(this,Reminder.class);
			PendingIntent pi=PendingIntent.getActivity(this,0,i,0);
			n.setLatestEventInfo(this,"Expense Pending",arr+" Rs."+num,pi);
			NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			nm.notify(count,n);
			count--;
		}
	}

	public void current(View v)
	{
		Intent i=new Intent(this,Current.class);
		startActivity(i);
	}
	public void history(View v)
	{
		Intent i=new Intent(this,History.class);
		startActivity(i);
	}
	public void reminder(View v)
	{
		Intent i=new Intent(this,Reminder.class);
		startActivity(i);
	}

}

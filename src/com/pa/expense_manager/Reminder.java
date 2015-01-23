package com.pa.expense_manager;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Reminder extends Activity {
	ListView lv;
	TextView tv;
	SQLiteDatabase db;
	int count=0,i=0,position;
	String details[],dd[],arr[],price[];
	ArrayAdapter<String> ob;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminder);
		lv=(ListView)findViewById(R.id.listView1);
		tv=(TextView)findViewById(R.id.tv);
		db=openOrCreateDatabase("mydb",0,null);
		Cursor c=db.rawQuery("select * from reminder;",null);
		count=c.getCount();
	//	Toast.makeText(Reminder.this,""+count,Toast.LENGTH_LONG).show();
		i=0;
		if(count>0)
		{
			tv.setText("     DETAILS");
			details= new String[count];
			arr= new String[count];
			dd= new String[count];
			price= new String[count];
			while(c.moveToNext())
			{
				dd[i]=c.getString(0);
				arr[i]=c.getString(1);
				price[i]=c.getString(2);
				details[i]=dd[i]+" "+arr[i]+"	Rs."+price[i] ;
				i++;
			}
		//	Toast.makeText(Reminder.this,details[0],Toast.LENGTH_LONG).show();
			ob=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,details);
			lv.setAdapter(ob);
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
			{
				position=arg2;
				AlertDialog.Builder notice=new AlertDialog.Builder(Reminder.this);
				notice.setTitle("Delete");
				notice.setMessage("This reminder will be deleted.");
				notice.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String date=dd[position];
					String ev=arr[position];
					try
					{
						db.execSQL("delete from reminder where dd='"+date+"' and event='"+ev+"';");
						Toast.makeText(Reminder.this,"Reminder deleted.",Toast.LENGTH_LONG).show();
					}	
					catch(Exception e)
					{
						e.printStackTrace();
					}
					Cursor c=db.rawQuery("select * from reminder;",null);
					count=c.getCount();
					i=0;
					if(count>0)
					{
						
						details= new String[count];
						arr= new String[count];
						dd= new String[count];
						price= new String[count];
						while(c.moveToNext())
						{
							dd[i]=c.getString(0);
							arr[i]=c.getString(1);
							price[i]=c.getString(2);
							details[i]=dd[i]+" "+arr[i]+" "+price[i]; ;
							i++;
						}
					}	
					else
					{
						tv.setText("NO REMINDER SET");
						details[i]="";
					}
					ob=new ArrayAdapter<String>(Reminder.this,android.R.layout.simple_list_item_1,details);
					lv.setAdapter(ob);
				}
				}); 
				notice.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
				Toast.makeText(Reminder.this,"Deletion cancelled.",Toast.LENGTH_LONG).show();
				}
				});
				notice.show();
				
			}
			});
		}
		else
		{
			tv.setText("NO REMINDER SET");
		}
	}
	public void back(View v)
	{
		finish();
	}
	public void add(View v)
	{
		Intent i=new Intent(Reminder.this,Add.class);
		startActivity(i);
		finish();
	}

}

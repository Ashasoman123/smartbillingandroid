package com.example.smartbillandroid;



import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("NewApi")
public class IPsetting extends Activity 
{

	EditText e1;
	Button b1;
	public static String m,url;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ipsetting);
		e1 = (EditText) findViewById(R.id.ed_ip);
		b1 = (Button) findViewById(R.id.bt_ip);
		
		b1.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				try 
				{	
					if(android.os.Build.VERSION.SDK_INT>9)
					{
						StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
						StrictMode.setThreadPolicy(policy);
					}			
				} 
				catch (Exception e) 
				{
					// TODO: handle exception
				}
				
				
				m=e1.getText().toString();
				if(m.equalsIgnoreCase(""))
				{
					e1.setError("Enter IP address");
					e1.setFocusable(true);					
				}		
				else
				{
				SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				Editor e=sh.edit();
				String url="http://"+m+"/WebService.asmx";
				e.putString("url",url);				
				e.putString("ip",m);
				e.commit();
				Intent b=new Intent(getApplicationContext(),Login.class);			
				startActivity(b);
				//Toast.makeText(getApplicationContext(), "url", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ipsetting, menu);
		return true;
	}

}

package com.example.smartbillandroid;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity 
{

	EditText ed1,ed2;
	Button b1, b3;
	
	String ss = "", ur = "";
	String method="login";
	String namespace="http://tempuri.org/";
	String soapAction=namespace+method;
	String url;
	String username;
	SharedPreferences sh;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		ed1=(EditText)findViewById(R.id.editText1);
		ed2=(EditText)findViewById(R.id.editText2);
		b1=(Button)findViewById(R.id.button1);
		b3=(Button)findViewById(R.id.button3);
		
		sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		b1.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
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
				
				
				
				// TODO Auto-generated method stub
				String un=ed1.getText().toString();
				String pn=ed2.getText().toString();

                if(un.equalsIgnoreCase(""))
				{
					ed1.setError("Enter username");
					ed1.setFocusable(true);
					
				}			
				else if(pn.equalsIgnoreCase(""))
				{
					ed2.setError("Enter Password");
					ed2.setFocusable(true);
				}
				else
				{
				try 
				{
					SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					url = sh.getString("url", "");
					SoapObject sop=new  SoapObject(namespace, method);	
					sop.addProperty("username", un);
					sop.addProperty("password", pn);
					SoapSerializationEnvelope snv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					snv.setOutputSoapObject(sop);
					snv.dotNet=true;
					HttpTransportSE hp=new HttpTransportSE(url);
					hp.call(soapAction, snv);
					
					String result=snv.getResponse().toString();
					//Toast.makeText(getApplicationContext(), result,Toast.LENGTH_LONG).show();
					if(result.equalsIgnoreCase("Customer"))
					{
						SharedPreferences sh1=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
						Editor e=sh1.edit();
						e.putString("username",un);
						username=sh1.getString("username","");
						e.commit();
						//Toast.makeText(getApplicationContext(),studuname,Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(),"Login Successfull...",Toast.LENGTH_LONG).show();
						Intent b=new Intent(getApplicationContext(),Home.class);		
						startActivity(b);
					}
					else
					{
						Toast.makeText(getApplicationContext(),"Login Unsuccessfull...",Toast.LENGTH_LONG).show();
					}
					
					//SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					//Editor e=sh.edit();
					//e.putString("logid",re[0] );
					//String logid=sh.getString("logid","");
					//e.commit();				
				} catch (Exception e) 
				{
					// TODO: handle exception
					Toast.makeText(getApplicationContext(),"Exception : "+ e,Toast.LENGTH_LONG).show();
				}
				}
			}
		});
		
		b3.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				Intent b=new Intent(getApplicationContext(),Registration.class);		
				startActivity(b);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void onBackPressed() 
	{
    	Toast.makeText(getApplicationContext(), "Successfully signout from account", Toast.LENGTH_SHORT).show();
		Intent intlogin =new Intent(getApplicationContext(), IPsetting.class);
		startActivity(intlogin);
		
	}
}

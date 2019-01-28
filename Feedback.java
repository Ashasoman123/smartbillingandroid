package com.example.smartbillandroid;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Feedback extends Activity 
{

	EditText e1;
	String feedback,desc;
	Button b1;
	String namespace="http://tempuri.org/";
	String method="Feedback";
	String url="",username;
	String soapAction=namespace+method;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		e1=(EditText)findViewById(R.id.editText1);
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				feedback=e1.getText().toString();
				
				try 
				{
					SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					url=sh.getString("url", "");
					username = sh.getString("username", "");
					
					url=sh.getString("url", "");
					
					SoapObject sop=new  SoapObject(namespace, method);
					sop.addProperty("uname",username);
					sop.addProperty("feedback", feedback);
					
					
					SoapSerializationEnvelope snv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					snv.setOutputSoapObject(sop);
					snv.dotNet=true;
					HttpTransportSE hp=new HttpTransportSE(url);
					hp.call(soapAction, snv);
					
					String result=snv.getResponse().toString();
								
					
					if(result.equalsIgnoreCase("ok"))
					{
						Toast.makeText(getApplicationContext(),"Feedback Send Successfully..",Toast.LENGTH_LONG).show();
						Intent a=new Intent(getApplicationContext(),Home.class);
						startActivity(a);
					}
					else if(result.equalsIgnoreCase("failed"))
					{
						Toast.makeText(getApplicationContext(),"Failled..",Toast.LENGTH_LONG).show();
					
					}
					
					
				} catch (Exception e)
				{
					Toast.makeText(getApplicationContext(),"ex:"+e.toString(),Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedback, menu);
		return true;
	}

}

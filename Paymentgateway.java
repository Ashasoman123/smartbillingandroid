package com.example.smartbillandroid;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Paymentgateway extends Activity 
{

	TextView t1; 
	Button b1;
	String namespace="http://tempuri.org/";
	String method="view_totalamt";
	String soapAction=namespace+method;
	String url="";
	String[] re;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paymentgateway);
		
		t1=(TextView)findViewById(R.id.textView6);
		b1=(Button)findViewById(R.id.button1);
		
		try 
		{
		SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url=sh.getString("url", "");
		
			SoapObject sop=new  SoapObject(namespace, method);
			
			SoapSerializationEnvelope snv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
			snv.setOutputSoapObject(sop);
			snv.dotNet=true;
			HttpTransportSE hp=new HttpTransportSE(url);
			hp.call(soapAction, snv);
			
			String result=snv.getResponse().toString();
			//Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
					
			
			if(!result.equalsIgnoreCase("failed"))
			{
				re=result.split("\\$");
				t1.setText(re[0]);
				
			}
			else if(result.equalsIgnoreCase("failed"))
			{
				Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
			
			}
			
			
		} catch (Exception e)
		{
			// TODO: handle exception
			Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
			
		}
		
		b1.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"Paid Successfully !!!",Toast.LENGTH_LONG).show();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.paymentgateway, menu);
		return true;
	}

}

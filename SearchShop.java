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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class SearchShop extends Activity implements OnItemSelectedListener
{

	Spinner ca;
	String method="";
	String namespace="http://tempuri.org/";
	String soapAction="";
	
	String url="",username;
	Button b1;
	String[] catid,cat;
	public static String ccatid,ccat;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_shop);
		
		ca=(Spinner)findViewById(R.id.spinner1);
		b1=(Button)findViewById(R.id.button1);
		ca.setOnItemSelectedListener(this);
		
		SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url = sh.getString("url", "");
		
		try 
		{
			method="ViewCategory";
			soapAction=namespace+method;
			
			SoapObject sop=new  SoapObject(namespace, method);
			
			
			SoapSerializationEnvelope snv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
			snv.setOutputSoapObject(sop);
			snv.dotNet=true;
			HttpTransportSE hp=new HttpTransportSE(url);
			hp.call(soapAction, snv);
			
			String result=snv.getResponse().toString();
			if(!result.equalsIgnoreCase("failed"))
			{
				String[] temp=result.split("\\#");
				if(temp.length>0)
				{
					initArray(temp.length);
					for(int z=0;z<temp.length;z++)
					{
						String temp1[]=temp[z].split("\\$");
						catid[z]=temp1[0];
						cat[z]=temp1[1];
					}
					ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,cat);
					ca.setAdapter(ar);
				}
			}
		
		} catch (Exception e) 
		{
			// TODO: handle exception
		}
		
		b1.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),ShopDetails.class));
			}
		});
	}

	private void initArray(int length) 
	{
		// TODO Auto-generated method stub
		catid=new String[length];
		cat=new String[length];	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_shop, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3)
	{
		// TODO Auto-generated method stub
		ccatid=catid[arg2];
		ccat=cat[arg2];
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0)
	{
		// TODO Auto-generated method stub
		
	}

}

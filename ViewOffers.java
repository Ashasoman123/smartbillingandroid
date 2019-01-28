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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewOffers extends Activity implements OnItemClickListener
{

	ListView l1;
	String namespace="http://tempuri.org/";
	String method="view_offerstore";
	String url="";
	String soapAction=namespace+method;

	ArrayAdapter<String> ar;
	
	String[] sid,sname;
	public static String csid,csname; 
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_offers);
		
		l1=(ListView)findViewById(R.id.listView1);
		l1.setOnItemClickListener(this);
		
		SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url=sh.getString("url", "");
		try 
		{
			
			SoapObject sop=new  SoapObject(namespace, method);	
			
//			Toast.makeText(getApplicationContext(), custuname,Toast.LENGTH_LONG).show();
			
			SoapSerializationEnvelope snv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
			snv.setOutputSoapObject(sop);
			snv.dotNet=true;
			HttpTransportSE hp=new HttpTransportSE(url);
			hp.call(soapAction, snv);
			
			String result=snv.getResponse().toString();
			//Toast.makeText(getApplicationContext(), result,Toast.LENGTH_LONG).show();
			if(!result.equalsIgnoreCase("failed"))
			{
				String[] temp=result.split("\\#");
				if(temp.length>0)
				{
					initArray(temp.length);
					for(int z=0;z<temp.length;z++)
					{
						String[] temp1=temp[z].split("\\$");
						sid[z]=temp1[0];
						sname[z]=temp1[1];
				    						    		
						
					}
					ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,sname);
					l1.setAdapter(ar);
					//l1.setAdapter(new Single_item(CustProductLists.this.itemid,iname));
				}
			}
			
							
		} catch (Exception e) 
		{
			// TODO: handle exception
		}
	}

	private void initArray(int length) 
	{
		// TODO Auto-generated method stub
		sid = new String[length];
		sname= new String[length];
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_offers, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		// TODO Auto-generated method stub
		
		csid= sid[arg2];	
		csname= sname[arg2];	
	    startActivity(new Intent(getApplicationContext(),ViewOfferDetails.class));
	}

}

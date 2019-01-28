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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ShopDetails extends Activity implements OnItemClickListener
{

	ListView lv;
	String namespace="http://tempuri.org/";
	String method="";
	String url="";
	String soapAction;
	String[] sname,floor,owner,ph,email,web;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_details);
		
		lv = (ListView) findViewById(R.id.listView1);
		lv.setOnItemClickListener(this);
		
		try 
		{
			
			method="view_shopdetails";
			soapAction=namespace+method;
			SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			url = sh.getString("url", "");
			
			SoapObject sop=new  SoapObject(namespace, method);	
			
			sop.addProperty("cat_id",SearchShop.ccatid);
			
			SoapSerializationEnvelope snv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
			snv.setOutputSoapObject(sop);
			snv.dotNet=true;
			HttpTransportSE hp=new HttpTransportSE(url);
			hp.call(soapAction, snv);
			
			String result=snv.getResponse().toString();
			//Toast.makeText(getApplicationContext(), result,Toast.LENGTH_LONG).show();
			
			if(!result.equalsIgnoreCase("failed"))
			{
				String[] tmp = result.split("\\#");
				if (tmp.length > 0) 
				{
					String[] det = new String[tmp.length];
					for (int i = 0; i < tmp.length; i++) 
					{
						String[] tmp1 = tmp[i].split("\\$");
						det[i] = "Shop Name : "+tmp1[0] + "\nFloor : "+tmp1[1] +"\nShop Owner : "+tmp1[2] + "\nPhone : "+tmp1[3] +"\nEmail : "+tmp1[4] +"\nWeb Address : "+tmp1[5] +"\n";
					}
					lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, det));
				}
			 }
			else
			{
				Toast.makeText(getApplicationContext(),"No Details",Toast.LENGTH_LONG).show();
				
			}
			
							
		} catch (Exception e) 
		{
			// TODO: handle exception

			Toast.makeText(getApplicationContext(),"Exception : "+ e,Toast.LENGTH_LONG).show();
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop_details, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}

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
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewMyCart extends Activity implements OnItemClickListener
{

	Button bt_pay;
	ListView lv;

	String ur = "", pid = "";
	String url,username;
	String[] pro_names, pro_ids,quantity,details;
	
	int flg = 0;
	String namespace="http://tempuri.org/";
	String method="";
	String soapAction;
	
	SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_my_cart);
		
sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		bt_pay = (Button) findViewById(R.id.bt_pay_cart);
		lv = (ListView) findViewById(R.id.lv_my_cart);
		lv.setOnItemClickListener(this);
		
        try 
		{
			method="cart_details";
			soapAction=namespace+method;
			
			SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			url = sh.getString("url", "");
			username = sh.getString("username", "");
			
			SoapObject sop=new  SoapObject(namespace, method);
			sop.addProperty("uname", username);
			//Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
			
			SoapSerializationEnvelope snv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
			snv.setOutputSoapObject(sop);
			snv.dotNet=true;
			HttpTransportSE hp=new HttpTransportSE(url);
			hp.call(soapAction, snv);
			
			String result=snv.getResponse().toString();
			//Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
			
			
			if(!result.equalsIgnoreCase("failed"))
			{
				String[] tmp = result.split("\\#");
				if (tmp.length > 0) 
				{
					String[] det = new String[tmp.length];
					for (int i = 0; i < tmp.length; i++) 
					{
						String[] tmp1 = tmp[i].split("\\$");
						det[i] = "Product Name : "+tmp1[0] + "\nAmount : "+tmp1[1] + "\nQuantity : " + tmp1[2] +  "\n";
					}
					lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, det));
				}
			}
		
		} catch (Exception e) 
		{
			// TODO: handle exception
			
			Toast.makeText(getApplicationContext(),"Exception : "+ e,Toast.LENGTH_LONG).show();
			
		}
        bt_pay.setOnClickListener(new View.OnClickListener()
        {
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				try 
				{
		        	method="payment";
		        	soapAction=namespace+method;
		        	
		        	
					SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					url = sh.getString("url", "");
					username = sh.getString("username", "");
					SoapObject sop=new  SoapObject(namespace, method);
					
					sop.addProperty("uname", username);
					//Toast.makeText(getApplicationContext(), custuname,Toast.LENGTH_LONG).show();
					//Toast.makeText(getApplicationContext(), query,Toast.LENGTH_LONG).show();
					
					SoapSerializationEnvelope snv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
					snv.setOutputSoapObject(sop);
					snv.dotNet=true;
					HttpTransportSE hp=new HttpTransportSE(url);
					hp.call(soapAction, snv);
					
					String result=snv.getResponse().toString();
					if(result.equalsIgnoreCase("ok"))
					{
						Toast.makeText(getApplicationContext(),"Successfull..!",Toast.LENGTH_LONG).show();
						Intent b=new Intent(getApplicationContext(),Paymentgateway.class);			
						startActivity(b);
					}
					
				} catch (Exception e) 
				{
					
					// TODO: handle exception
				}
		        
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_my_cart, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		// TODO Auto-generated method stub
		
	}

}

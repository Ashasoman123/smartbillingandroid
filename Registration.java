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
import android.widget.RadioButton;
import android.widget.Toast;

public class Registration extends Activity
{

	EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,ed9,ed10,ed11;
	Button b1,b2;
	RadioButton r1,r2;
	String url,username,gender;
	String method="registration";
	String namespace="http://tempuri.org/";
	String soapAction=namespace+method;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		ed1=(EditText)findViewById(R.id.editText1);
        ed2=(EditText)findViewById(R.id.editText4);
        ed3=(EditText)findViewById(R.id.editText5);
        ed4=(EditText)findViewById(R.id.editText6);
        ed5=(EditText)findViewById(R.id.editText7);
        ed6=(EditText)findViewById(R.id.editText8);
        
        ed7=(EditText)findViewById(R.id.editText9);
        ed8=(EditText)findViewById(R.id.editText10);
        ed9=(EditText)findViewById(R.id.editText11);
        ed10=(EditText)findViewById(R.id.editText12);
        ed11=(EditText)findViewById(R.id.editText13);
        r1=(RadioButton)findViewById(R.id.radioButton1);
		r2=(RadioButton)findViewById(R.id.radioButton2);
		
		
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        
      b1.setOnClickListener(new View.OnClickListener()
      {
		
		@Override
		public void onClick(View arg0)
		{
			// TODO Auto-generated method stub
			String fname=ed1.getText().toString();
			String lname=ed7.getText().toString();
			String hname=ed8.getText().toString();
			String place=ed9.getText().toString();
			String pin=ed10.getText().toString();
			String dob=ed11.getText().toString();
			if(r1.isChecked())
			{
				gender="Male";
			
			}
			else
			{
				gender="Female";
			}
			
			String email=ed2.getText().toString();
			String phone=ed3.getText().toString();
			String uname=ed4.getText().toString();
			String pword=ed5.getText().toString();
			String cpword=ed6.getText().toString();
		
			try 
			{
				if (pword.equals(cpword))
				{
				SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				url = sh.getString("url", "");
				username = sh.getString("username", "");
				SoapObject sop=new  SoapObject(namespace, method);
				sop.addProperty("fname", fname);
				sop.addProperty("lname", lname);
				sop.addProperty("hname", hname);
				sop.addProperty("place", place);
				sop.addProperty("pin", pin);
				sop.addProperty("dob", dob);
				sop.addProperty("gender", gender);
				
				sop.addProperty("email", email);
				sop.addProperty("phone", phone);
				sop.addProperty("uname", uname);
				sop.addProperty("pword", pword);
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
					Toast.makeText(getApplicationContext(),"Submitted Successfull...",Toast.LENGTH_LONG).show();
					//Intent b=new Intent(getApplicationContext(),CustEnq.class);			
					//startActivity(b);
				}
				}else
				{
					ed6.setError("Re-enter password");
				}
				
			} catch (Exception e) 
			{
				
				// TODO: handle exception
				Toast.makeText(getApplicationContext(),"Exception : "+ e,Toast.LENGTH_LONG).show();
				
			}
			
		}
	});
      
      b2.setOnClickListener(new View.OnClickListener()
      {
		
		@Override
		public void onClick(View arg0)
		{
			// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(), Registration.class));
		}
	});
      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

}

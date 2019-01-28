package com.example.smartbillandroid;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.string;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.CursorJoiner.Result;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetails extends Activity {

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	String ur = "";
	int flg = 0;
	SharedPreferences sh;

	TextView tv;
	Button bt;
	EditText qt;

	String url, username,pr_id;
	String namespace = "http://tempuri.org/";
	String method = "";
	String soapAction;
	string qty;
	String[] pro_id, pro_name, pro_decr, pro_price;
	public static String cpro_id, cpro_name, cpro_decr, cpro_price;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
//		pr_id=getIntent().getExtras().getString("qr_id");
		
		tv = (TextView) findViewById(R.id.tv_details);
		qt = (EditText) findViewById(R.id.editText1);
		bt = (Button) findViewById(R.id.bt_add_cart);
		
		SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		url = sh.getString("url", "");
		chooseProduct(getIntent().getStringExtra("qr_id")) ;


		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					method = "add_cart";
					soapAction = namespace + method;

					String qty = qt.getText().toString();

					SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					url = sh.getString("url", "");
					username = sh.getString("username", "");
					
					
					SoapObject sop = new SoapObject(namespace, method);
					sop.addProperty("product_id",pr_id);
					sop.addProperty("qty", qty);
					sop.addProperty("uname", username);
					
					//Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
					//Toast.makeText(getApplicationContext(),pr_id,Toast.LENGTH_LONG).show();
					//Toast.makeText(getApplicationContext(),qty,Toast.LENGTH_LONG).show();
					
					
					SoapSerializationEnvelope snv = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					snv.setOutputSoapObject(sop);
					snv.dotNet = true;
					HttpTransportSE hp = new HttpTransportSE(url);
					hp.call(soapAction, snv);
					
					String result = snv.getResponse().toString();
					
					//Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
					
					if (result.equalsIgnoreCase("ok")) {
						Toast.makeText(getApplicationContext(), "Added..!",
								Toast.LENGTH_LONG).show();
						// Intent b=new
						// Intent(getApplicationContext(),CustEnq.class);
						// startActivity(b);
					}

				} catch (Exception e) {

					// TODO: handle exception
					Toast.makeText(getApplicationContext(),"Exception : "+ e,Toast.LENGTH_LONG).show();
					
				}
			}
		});

	}

	

	

	public void chooseProduct(String qr_id) {

//		Intent in = new Intent(getApplicationContext(), Details.class);
//		in.putExtra("content", qr_id);
//		startActivity(in);
		
		try {
//			String namespace = "http://tempuri.org/";
			method = "product_details";
			soapAction = namespace + method;
			SharedPreferences sh = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			String url = sh.getString("url", "");
			
			//Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();

			SoapObject sop = new SoapObject(namespace, method);
			
			sop.addProperty("product_id", qr_id);
			
		
//			pr_id = qr_id;
			//pr_id=sh.getString("prid","");
			
			//Toast.makeText(getApplicationContext(), qr_id, Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplicationContext(), ScanQRCode.prid, Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplicationContext(), pr_id, Toast.LENGTH_LONG).show();
			
			
			SoapSerializationEnvelope snv = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			snv.setOutputSoapObject(sop);
			snv.dotNet = true;

			HttpTransportSE hp = new HttpTransportSE(url);
			hp.call(soapAction, snv);

			String result = snv.getResponse().toString();

			//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			if (!result.equalsIgnoreCase("failed")) {
				String[] temp = result.split("\\#");
				if (temp.length > 0) {
					initArray(temp.length);
					for (int z = 0; z < temp.length; z++) {
						String temp1[] = temp[z].split("\\$");
						pro_id[z] = temp1[0];
						pro_name[z] = temp1[1];
						pro_price[z] = temp1[2];

					}
					tv.setText(pro_id[0] + "\n" + pro_name[0] + "\n"
							+ pro_price[0]);
					pr_id=pro_id[0];
					//Toast.makeText(getApplicationContext(), pr_id, Toast.LENGTH_LONG).show();
					
				}
			}

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "12" + e.toString(),
					Toast.LENGTH_LONG).show();
			// TODO: handle exception
		}

	}

	private void initArray(int length) 
	{
		// TODO Auto-generated method stub
		pro_id= new String[length];
		pro_name =new String[length];
		pro_price = new String[length];

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_details, menu);
		return true;
	}

}

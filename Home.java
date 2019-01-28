package com.example.smartbillandroid;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends Activity {
	Button bt_choose, bt_scan, bt_view_cart, bt_budget, bt_logout,bt_feed,bt_offer,bt_search;

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	String ur = "", ss = "";
	public static String cart_id = "";

	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		sh = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

	//	bt_choose = (Button) findViewById(R.id.bt_choose);
		bt_scan = (Button) findViewById(R.id.bt_scan);
		bt_view_cart = (Button) findViewById(R.id.bt_view_cart);
	//	bt_budget = (Button) findViewById(R.id.bt_budget);
		bt_feed = (Button) findViewById(R.id.bt_feed);
		bt_offer = (Button) findViewById(R.id.bt_offer);
		bt_search = (Button) findViewById(R.id.bt_search);
		bt_logout = (Button) findViewById(R.id.bt_logout);

		bt_search.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),SearchShop.class));
			}
		});
		
		bt_offer.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),ViewOffers.class));
			}
		});
		bt_feed.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Feedback.class));
			}
		});
		bt_scan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
//				try {
//					Intent intent = new Intent(ACTION_SCAN);
//					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
//					startActivityForResult(intent, 0);
//				} catch (Exception e) {
//					Toast.makeText(getApplicationContext(), "Download cheyyado..Ayye", Toast.LENGTH_LONG).show();
//				}
				startActivity(new Intent(getApplicationContext(),
						ScanQRCode.class));
			}
		});

		bt_view_cart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						ViewMyCart.class));
			}
		});

		bt_logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), Login.class));

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				// String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

//				chooseProduct(contents);
			}
		}
	}

}

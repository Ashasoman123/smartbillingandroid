package com.example.smartbillandroid;

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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ScanQRCode extends Activity {
	public static String prid; 

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_qrcode);
		
		Button bt1=(Button)findViewById(R.id.button1);
		bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				chooseProduct("9");
				try {
					Intent intent = new Intent(ACTION_SCAN);
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					startActivityForResult(intent, 0);
				} catch (ActivityNotFoundException anfe) {
					showDialog(ScanQRCode.this, "No Scanner Found",
							"Download a scanner code activity?", "Yes", "No")
							.show();
				}
			}
		});
	}

	private static AlertDialog showDialog(final Activity act,
			CharSequence title, CharSequence message, CharSequence buttonYes,
			CharSequence buttonNo) {
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
		downloadDialog.setTitle(title);
		downloadDialog.setMessage(message);
		downloadDialog.setPositiveButton(buttonYes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
						Uri uri = Uri.parse("market://search?q=pname:"
								+ "com.google.zxing.client.android");
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						try {
							act.startActivity(intent);
						} catch (ActivityNotFoundException anfe) {

						}
					}
				});
		downloadDialog.setNegativeButton(buttonNo,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
					}
				});
		return downloadDialog.show();
	}
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				// String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

//				
Intent i=new Intent(getApplicationContext(), ProductDetails.class);
//prid=contents;

//SharedPreferences sh1=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//Editor e=sh1.edit();
//e.putString("prid",contents);
//prid=sh1.getString("prid","");
//Toast.makeText(getApplicationContext(), prid, Toast.LENGTH_LONG).show();


i.putExtra("qr_id", contents);
startActivity(i);
			}
		}
	}
}

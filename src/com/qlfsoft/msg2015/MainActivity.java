package com.qlfsoft.msg2015;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends Activity implements Runnable {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		new Thread(this).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			Thread.sleep(1500);
		}catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		Intent smsIntent = new Intent(MainActivity.this,sms.class);
		finish();
		startActivity(smsIntent);
	}

}

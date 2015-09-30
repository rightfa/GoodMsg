package com.qlfsoft.msg2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qlfsoft.msg2015.other.ActivityMeg;
import com.qlfsoft.msg2015.util.otherUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class sms extends Activity {

	private GridView smsgridView;

	private SimpleAdapter adapter;

	private int c_img[] = { R.drawable.c1, R.drawable.c2, R.drawable.c3,
			R.drawable.c4, R.drawable.c5, R.drawable.c6, R.drawable.c7,
			R.drawable.c8, R.drawable.c9,R.drawable.c10 };
	private String c_name[] = { "女朋友的", "普通朋友", "给同事", "热恋的人","给密友", "给领导", "喜气羊羊",
			"羊年大吉", "三羊开泰","羊羊得意" };

	List<Map<String, Object>> smsData = new ArrayList<Map<String, Object>>();

	// 数据的键值
	public static String mapKey[] = { "icon", "title" };
	// 数据对应的布局ID
	public static int layoutID[] = { R.id.sms_grid_img, R.id.sms_grid_info };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.test_2);
		ActivityMeg.getInstance().addActivity(this);
		dataInit();
		viewInit();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			System.out.println("--------------------------");
			otherUtil.ExitApp(getApplicationContext());
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void dataInit() {

		addData(c_name, c_img);
	}

	public void addData(String name[], int img[]) {
		smsData.clear();
		for (int i = 0; i < img.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(mapKey[0], img[i]);
			map.put(mapKey[1], name[i]);
			smsData.add(map);
		}
	}

	public void viewInit() {
		smsgridView = (GridView) this.findViewById(R.id.sms_grid1);
		adapter = new SimpleAdapter(this, smsData, R.layout.smsgriditem,
				mapKey, layoutID);
		smsgridView.setAdapter(adapter);
		smsgridView.setOnItemClickListener(onItemClickListener);
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			Log.i(Thread.currentThread().getStackTrace()[2].getMethodName(), String.valueOf(position));
			Intent intent = new Intent(sms.this, NormalActivity.class);
			Log.i(Thread.currentThread().getStackTrace()[2].getClassName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName(), "line98");
			intent.putExtra("position", position);
			intent.putExtra("title", c_name[position]);
			sms.this.startActivity(intent);
		}

	};
}

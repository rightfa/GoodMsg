package com.qlfsoft.msg2015;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;

import com.baidu.mobads.AdView;
import com.qlfsoft.msg2015.other.ActivityMeg;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;

/**
 * This example shows a expandable listview
 * with a more button per list item which expands the expandable area.
 *
 * In the expandable area there are two buttons A and B which can be click.
 *
 * The events for these buttons are handled here in this Activity.
 *
 * @author tjerk
 * @date 6/13/12 7:33 AM
 */
public class NormalActivity extends Activity {

	ActionSlideExpandableListView list = null;
	private int position;
	private SqlDatebase sqlDatebase;
	private TextView tv_title;
	private AdView myad;
	@Override
	public void onCreate(Bundle savedData) {

		super.onCreate(savedData);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// set the content view for this activity, check the content view xml file
		// to see how it refers to the ActionSlideExpandableListView view.
		this.setContentView(R.layout.single_expandable_list);
		// get a reference to the listview, needed in order
		// to call setItemActionListener on it
		ActivityMeg.getInstance().addActivity(this);
		tv_title = (TextView)this.findViewById(R.id.title);
		getsmsData();
		list = (ActionSlideExpandableListView)this.findViewById(R.id.list);
		list.setAdapter(buildDummyData());
		myad = (AdView)this.findViewById(R.id.adView);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());
		String str = formatter.format(curDate);
		if(str.equals("2014-12-31") || str.equals("2015-01-01"))
		{
			myad.setVisibility(View.GONE);
		}
		
		/*Button btnNext = (Button)this.findViewById(R.id.btnNext);
		btnNext.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				list.setAdapter(buildDummyData());
			}
		});
		*/
		// listen for events in the two buttons for every list item.
		// the 'position' var will tell which list item is clicked
		list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

			@Override
			public void onClick(View listView, View buttonview, int position) {

				/**
				 * Normally you would put a switch
				 * statement here, and depending on
				 * view.getId() you would perform a
				 * different action.
				 */
				TextView c = (TextView)listView.findViewById(R.id.text);
				String txt = c.getText().toString();
				if(buttonview.getId()==R.id.buttonA) {
					
					ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
					clipboard.setText(txt);
					Toast toast = Toast.makeText(NormalActivity.this, "短信复制成功！", Toast.LENGTH_SHORT);
					toast.show();
				} else {
					Uri smsToUri = Uri.parse("smsto:");
					Intent intent = new Intent(Intent.ACTION_SENDTO,smsToUri);
					intent.putExtra("sms_body", txt);
					startActivity(intent);
				}
			}

		// note that we also add 1 or more ids to the setItemActionListener
		// this is needed in order for the listview to discover the buttons
		}, R.id.buttonA, R.id.buttonB);
	}

	public void getsmsData() {
		Intent intent = getIntent();
		position = intent.getIntExtra("position", -1);
		Log.i(Thread.currentThread().getStackTrace()[2].getMethodName(), String.valueOf(position));
		String title = intent.getStringExtra("title");
		tv_title.setText(title);
	}
	
	/**
	 * Builds dummy data for the test.
	 * In a real app this would be an adapter
	 * for your data. For example a CursorAdapter
	 */
	public ListAdapter buildDummyData() {
		sqlDatebase = new SqlDatebase(this);
		List<String> values = new ArrayList<String>();
		values = getSMSWish(position);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this,
				R.layout.expandable_list_item,
				R.id.text,
				values
		);
		return adapter;
	}
	
	public List<String> getSMSWish(int position) {
		List<String> data = new ArrayList<String>();
		Cursor cursor = null;
		try {
			Log.i(Thread.currentThread().getStackTrace()[2].getMethodName(), String.valueOf(position));
			if(position == 0)
				cursor = sqlDatebase.sqLiteDatabase_sms.rawQuery("select Content from normal",null);
			else
				cursor = sqlDatebase.sqLiteDatabase_sms.rawQuery("select Content from msg2015 where msgclass=?", new String[]{String.valueOf(position + 1)});
			if (cursor != null) {
				// 游标下标归零
				cursor.moveToPosition(0);
				// 取游标中的值
				while (true) {
					// 判断是否在最后
					if (cursor.isAfterLast()) {
						break;
					}
					String str = cursor.getString(0);
					/** 添加到数据容器 */
					data.add(str);
					cursor.moveToNext();
				}
			}
		} finally {
			if (cursor != null) {

				try {

					cursor.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		return data;
	}
}

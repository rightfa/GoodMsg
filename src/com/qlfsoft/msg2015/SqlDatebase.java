package com.qlfsoft.msg2015;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/***
 * 
 * �����ⲿ���ݿ⵽����
 * 
 * 
 */
public class SqlDatebase {

	/*** �����Ķ��� */
	private Context context;

	/** ���ݿ�������� **/
	public SQLiteDatabase sqLiteDatabase_sms;

	public SqlDatebase(Context context) {
		this.context = context;
		// ��һ�ν�ȥ�����Ƚ������ݿ��ļ��������getWritableDatabase()�ͻὨ�����ݿ�,
		// ʹ��fromOutCopyDate()��ȡ��������,��Ϊ����ʹ�õ���ͬһ�����ݿ�
		sqLiteDatabase_sms = fromOutCopyDate();
	
	}


	/***
	 * ���ⲿ�������ݵ�����
	 * 
	 * @return
	 */
	public SQLiteDatabase fromOutCopyDate() {
		String DATEBASE_PATH = "/data/data/com.qlfsoft.msg2015/databases";
		String DATEBASE_FILENAME = "messages.db";

		/** ���ݵľ���·�� */
		String databasePath = DATEBASE_PATH + '/' + DATEBASE_FILENAME;
		
		File path = new File(DATEBASE_PATH);
		// ����һ���ļ���
		if (!path.exists()) {
			path.mkdir();
		}
		Log.i(Thread.currentThread().getStackTrace()[2].getMethodName(), "mkdir");
		// ����һ���ļ�
		File databaseFile = new File(databasePath);
		Log.i("filexist", String.valueOf(databaseFile.exists()));
		if (!databaseFile.exists()) {
			
			InputStream inputStream = context.getResources().openRawResource(
					R.raw.messages);
		
			try {
				databaseFile.createNewFile();
				Log.i(Thread.currentThread().getStackTrace()[2].getMethodName(), "fileinputStream");
				FileOutputStream fileOutputStream = new FileOutputStream(
						databasePath);
				Log.i(Thread.currentThread().getStackTrace()[2].getMethodName(), "fileoutputStream");
				byte data[] = new byte[2048];

				int index = 0;
				while ((index = inputStream.read(data)) != -1) {
					fileOutputStream.write(data, 0, index);
					System.out.println("-======-------------");
				}
				inputStream.close();
				fileOutputStream.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(
				databasePath, null);
		
		return sqLiteDatabase;
	}

	public SQLiteDatabase getSqLiteDatabase() {
		return sqLiteDatabase_sms;
	}

	public void setSqLiteDatabase(SQLiteDatabase sqLiteDatabase) {
		this.sqLiteDatabase_sms = sqLiteDatabase;
	}

	public void closeSQl() {
		sqLiteDatabase_sms.close();
	}

}

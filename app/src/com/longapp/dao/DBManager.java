package com.longapp.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import com.longapp.fitness.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private final int BUFF_SIZE = 8192;
	private final String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/Fitness";
	private final String DATABASE_FILENAME = "fitness.db";
	private Context context;

	public DBManager(Context context) {
		this.context = context;
	}

	public SQLiteDatabase openDatabase() {
		try {
			// ���food.db�ļ��ľ���·��
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File dir = new File(DATABASE_PATH);
			// ���/sdcard/FitnessĿ¼�д��ڣ��������Ŀ¼
			if (!dir.exists())
				dir.mkdir();
			// �����/sdcard/FitnessĿ¼�в�����food.db�ļ������resawĿ¼�и�������ļ���SD����Ŀ¼/sdcard/Fitness
			if (!(new File(databaseFilename)).exists()) {
				// ��÷�װfood.db�ļ���InputStream����
				InputStream is = context.getResources().openRawResource(R.raw.fitness);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[BUFF_SIZE];
				int count = 0;
				// ��ʼ����food.db�ļ�
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}

				fos.close();
				is.close();
			}
			// ��/sdcard/FitnessĿ¼�е�food.db�ļ�
			SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
			return database;
		} catch (Exception e) {
		}
		return null;
	}
}

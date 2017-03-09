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
			// 获得food.db文件的绝对路径
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File dir = new File(DATABASE_PATH);
			// 如果/sdcard/Fitness目录中存在，创建这个目录
			if (!dir.exists())
				dir.mkdir();
			// 如果在/sdcard/Fitness目录中不存在food.db文件，则从resaw目录中复制这个文件到SD卡的目录/sdcard/Fitness
			if (!(new File(databaseFilename)).exists()) {
				// 获得封装food.db文件的InputStream对象
				InputStream is = context.getResources().openRawResource(R.raw.fitness);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[BUFF_SIZE];
				int count = 0;
				// 开始复制food.db文件
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}

				fos.close();
				is.close();
			}
			// 打开/sdcard/Fitness目录中的food.db文件
			SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
			return database;
		} catch (Exception e) {
		}
		return null;
	}
}

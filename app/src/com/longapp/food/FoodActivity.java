package com.longapp.food;

import com.longapp.dao.DBManager;
import com.longapp.dialog.AddDietDialog;
import com.longapp.dialog.AddFoodDialog;
import com.longapp.fitness.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodActivity extends Activity implements TextWatcher {

	private SQLiteDatabase database;
	private AutoCompleteTextView actvFood;
	private Button searchBtn;
	private Button addfoodBtn;
	private Button adddietBtn;
	private Button deletefoodBtn;
	private TextView foodName;
	private ListView nutrientList;
	private AddFoodDialog addfoodDialog;
	private AddDietDialog adddietDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.food_activity);

		DBManager dbmanager = new DBManager(this);
		database = dbmanager.openDatabase();

		actvFood = (AutoCompleteTextView) findViewById(R.id.actvFood);
		actvFood.addTextChangedListener(this);

		searchBtn = (Button) findViewById(R.id.searchBtn);
		searchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchFood();
			}
		});

		addfoodBtn = (Button) findViewById(R.id.addfoodBtn);
		addfoodBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addFood();
			}
		});

		foodName = (TextView) findViewById(R.id.nutrientResult);

		adddietBtn = (Button) findViewById(R.id.adddietBtn);
		adddietBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addDiet();
			}
		});

		deletefoodBtn = (Button) findViewById(R.id.deletefoodBtn);
		deletefoodBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDeleteDialog();
			}
		});

		nutrientList = (ListView) findViewById(R.id.nutrientList);
	}

	private void searchFood() {
		String sql = "select name,type,nutrient from food where name=?";
		if (actvFood.getText().toString().isEmpty())
			return;
		Cursor cursor = database.rawQuery(sql, new String[] { actvFood.getText().toString() });
		String result = "未找到该食品";
		if (cursor.getCount() > 0) {
			// 必须使用moveToFirst方法将记录指针移动到第1条记录的位置
			cursor.moveToFirst();
			result = cursor.getString(cursor.getColumnIndex("name")) + "-";
			result = result + cursor.getString(cursor.getColumnIndex("type"));
			String nutrient = cursor.getString(cursor.getColumnIndex("nutrient"));
			NutrientAdapter nutrientAdapter = new NutrientAdapter();
			nutrientList.setAdapter(nutrientAdapter.getAdapter(this, nutrient));
			adddietBtn.setVisibility(View.VISIBLE);
			deletefoodBtn.setVisibility(View.VISIBLE);
		} else {
			nutrientList.setAdapter(null);
			adddietBtn.setVisibility(View.INVISIBLE);
			deletefoodBtn.setVisibility(View.INVISIBLE);
		}
		foodName.setText(result);
	}

	private void addFood() {
		addfoodDialog = new AddFoodDialog(this, database);
		addfoodDialog.show();
	}
	
	private void addDiet() {
		String name = foodName.getText().toString().split("-")[0];
		TextView heat_tv = (TextView)nutrientList.getChildAt(0).findViewById(R.id.nutrient_content);
		TextView protein_tv = (TextView)nutrientList.getChildAt(1).findViewById(R.id.nutrient_content);
		TextView fat_tv = (TextView)nutrientList.getChildAt(2).findViewById(R.id.nutrient_content);
		TextView carbohydrate_tv = (TextView)nutrientList.getChildAt(3).findViewById(R.id.nutrient_content);
		String nutrient = heat_tv.getText().toString() + ";" + protein_tv.getText().toString() + ";" + fat_tv.getText().toString() + ";" + carbohydrate_tv.getText().toString();
		adddietDialog = new AddDietDialog(this, database, name, nutrient);
		adddietDialog.show();
	}

	private void showDeleteDialog() {
		new AlertDialog.Builder(this).setTitle("提示").setMessage("确定删除该食物数据?")
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						deleteFood();
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
					}
				}).show();
	}

	private void deleteFood() {
		String sql = "delete from food where name=?";
		String name = foodName.getText().toString().split("-")[0];
		database.execSQL(sql, new String[] { name });
		adddietBtn.setVisibility(View.INVISIBLE);
		deletefoodBtn.setVisibility(View.INVISIBLE);
		
		Toast toast=Toast.makeText(this, "成功删除食品数据", Toast.LENGTH_SHORT); 
		//显示toast信息 
		toast.show();
	}

	// AutoCompleteTextView的重写函数3个
	@Override
	public void afterTextChanged(Editable s) {
		Cursor cursor = database.rawQuery("select name as _id from food where name like ?",
				new String[] { "%" + s.toString() + "%" });
		FoodNameAdapter myAdapter = new FoodNameAdapter(this, cursor, true);
		actvFood.setAdapter(myAdapter);
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
		if(actvFood.getAdapter() == null) {
			Cursor cursor = database.rawQuery("select name as _id from food where name like ?",
					new String[] { "%" + s.toString() + "%" });
			FoodNameAdapter myAdapter = new FoodNameAdapter(this, cursor, true);
			actvFood.setAdapter(myAdapter);
		}
	}

}

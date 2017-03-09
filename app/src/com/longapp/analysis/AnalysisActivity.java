package com.longapp.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.longapp.dao.DBManager;
import com.longapp.fitness.R;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AnalysisActivity extends Activity {
	private SQLiteDatabase database;
	private PieChartView pieChartView;
	private TextView text_left;
	private TextView text_right;
	private Spinner timeSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.analysis_activity);
		
		DBManager dbmanager = new DBManager(this);
		database = dbmanager.openDatabase();
		
		pieChartView = (PieChartView) findViewById(R.id.analysis_pie_chart);
		text_left = (TextView) findViewById(R.id.analysis_text_left);
		text_right = (TextView) findViewById(R.id.analysis_text_right);
		timeSpinner = (Spinner) findViewById(R.id.analysis_time);
		
		setData();
	}
	
	private void showChart(String time) {
		//蛋白质所供热量占总热量的10％～15％，脂肪所供热量占总热量的20％～25％，碳水化合物所供热量占总热量的55％～60％
		Cursor cursor = database.rawQuery("select sum(carbohydrate) as total_carbohydrate, sum(protein) as total_protein, sum(fat) as total_fat, sum(heat) as total_heat from diet where time=?", new String[] { time });
		cursor.moveToFirst();
		Double total_carbohydrate = cursor.getDouble(0);
		Double total_protein = cursor.getDouble(1);
		Double total_fat = cursor.getDouble(2);
		Double total_heat = cursor.getDouble(3);
		PieChartView.PieItemBean[] items = new PieChartView.PieItemBean[]{
		    new PieChartView.PieItemBean("碳水化合物", (float)(total_carbohydrate * 4)),
		    new PieChartView.PieItemBean("脂肪", (float)(total_fat * 9)),
		    new PieChartView.PieItemBean("蛋白质", (float)(total_protein * 4))
		};
		pieChartView.setPieItems(items);
		
		text_left.setText("总热量(大卡):\n碳水化合物(克):\n蛋白质(克):\n脂肪(克):");
		text_right.setText(String.format("%.0f", total_heat) +"\n" + String.format("%.2f", total_carbohydrate) +"\n" + String.format("%.2f", total_protein) +"\n" + String.format("%.2f", total_fat));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setData() {
        List time_list = new ArrayList();
        Cursor cursor = database.rawQuery("select distinct time from diet", null);
        while(cursor.moveToNext()){
                time_list.add(cursor.getString(0));
        }
        ArrayAdapter timeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time_list);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //样式
	 
        timeSpinner.setAdapter(timeAdapter);
        timeSpinner.setSelection(time_list.size() - 1, true);
        timeSpinner.setOnItemSelectedListener(itemSelectedListener);
	}
	
	private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner timeSp = (Spinner) parent;
                String time = (String) timeSp.getItemAtPosition(position);
                showChart(time);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
        }
	};

	@Override
	protected void onResume() {
		super.onResume();
		setData();
	}
}
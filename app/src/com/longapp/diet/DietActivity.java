package com.longapp.diet;

import java.util.Date;

import com.longapp.dao.DBManager;
import com.longapp.fitness.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DietActivity extends Activity{
	private ListView listview;
	private SQLiteDatabase database;
	private TextView diet_total;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.diet_activity);
		
		listview = (ListView)findViewById(R.id.diet_list);
		diet_total = (TextView)findViewById(R.id.diet_total);
		
		DBManager dbmanager = new DBManager(this);
		database = dbmanager.openDatabase();
		
		setData();
		itemOnLongClick();
	 }

	@SuppressWarnings("deprecation")
	private void setData() {
		Date date = new Date();
		String time = String.format("%tY%tm%td", date, date, date);
		Cursor cursor = database.rawQuery("select id as _id, comment, name, count, heat, protein, fat, carbohydrate from diet where time=?", new String[] { time });
	    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.diet_list_item, cursor,
	    		new String[] { "_id", "comment", "heat", "name", "count", "protein", "fat", "carbohydrate" }, 
	    		new int[] {R.id.diet_list_item_id, R.id.diet_list_item_comment, R.id.diet_list_item_heat, R.id.diet_list_item_name, R.id.diet_list_item_count, 
	    				R.id.diet_list_item_protein, R.id.diet_list_item_fat, R.id.diet_list_item_carbohydrate});
	    listview.setAdapter(adapter);
	    Cursor cursor_total = database.rawQuery("select sum(heat) as total from diet where time=?", new String[] { time });
	    cursor_total.moveToFirst();
	    String total = String.format("%.0f", cursor_total.getDouble(0));
	    diet_total.setText("今日总热量" + total + "大卡");
	 }
	
	private void itemOnLongClick() {
		this.listview.setOnItemLongClickListener(new OnItemLongClickListener() {
					        @Override
					        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
					        	
					                new AlertDialog.Builder(DietActivity.this)
					                        .setItems(R.array.menu, new DialogInterface.OnClickListener() {
						                                                public void onClick(DialogInterface dialog, int which) {
						                                                        String[] PK = getResources().getStringArray(R.array.menu);
						                                                        if(PK[which].equals("删除")) {
						                                                        	   //Log.i("test", "itemDelete:"+position);
						                                                               itemDelete(position);
						                                                        }
						                                                }
					                                        })
					                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
					                                                    public void onClick(DialogInterface dialog,int which) {
					                                                            // TODO Auto-generated method stub
					                                                    }
					                        }).show();
					                return true;
					       }
		});
	}
	
	private void itemDelete(int position) {
		//Log.i("test", "itemdelete...");
		try{
			//listview的getChildat只能获取当前页面显示的item,滚动条以下的item获取后使用（第二条语句）会nullpointerexecption
			//View view = this.listview.getChildAt(position);
			//TextView item_id = (TextView)view.findViewById(R.id.diet_list_item_id);
			//改成先获取adapter再取item即可解决问题
			TextView item_id = (TextView)listview.getAdapter().getView(position, null, null).findViewById(R.id.diet_list_item_id);

			int id = Integer.parseInt(item_id.getText().toString());
			database.execSQL("delete from diet where id=?", new Integer[]{id});
			setData();
		} catch(Exception e) {
			//Log.i("exception:", ""+e);
			Toast toast=Toast.makeText(this, "删除出错", Toast.LENGTH_SHORT); 
			//显示toast信息 
			toast.show();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setData();
	}
}

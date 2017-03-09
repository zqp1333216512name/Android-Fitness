package com.longapp.food;

import com.longapp.fitness.R;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FoodNameAdapter extends CursorAdapter {

	public FoodNameAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	private void setView(View view, Cursor cursor) {
		TextView foodItem = (TextView) view;
		foodItem.setText(cursor.getString(cursor.getColumnIndex("_id")));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		Log.i("test", "newView");
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.food_name_list_item, null);
		setView(view, cursor);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Log.i("test", "bindView");
		setView(view, cursor);
	}

	@Override
	public String convertToString(Cursor cursor) {
		Log.i("test", "toString");
		return cursor == null ? "" : cursor.getString(cursor.getColumnIndex("_id"));
	}
}

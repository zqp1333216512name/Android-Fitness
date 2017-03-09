package com.longapp.fitness;

import com.longapp.analysis.AnalysisActivity;
import com.longapp.diet.DietActivity;
import com.longapp.food.FoodActivity;
import com.longapp.tools.ToolsActivity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {
	private TabHost tabHost;// ����һ��TabHost����

	// ��Դ�ļ�
	@SuppressWarnings("rawtypes")
	private Class activitys[] = { DietActivity.class, AnalysisActivity.class, ToolsActivity.class, FoodActivity.class };// ��ת��Activity
	private String title[] = { "��ʳ", "����", "����", "ʳƷ��" };// ���ò˵��ı���
	private int image[] = { R.drawable.tab_icon1, R.drawable.tab_icon2, R.drawable.tab_icon3, R.drawable.tab_icon4 };// ���ò˵�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		initTabView();// ��ʼ��tab��ǩ
	}

	private void initTabView() {
		// ʵ����tabhost
		this.tabHost = (TabHost) findViewById(R.id.mytabhost);
		// ���ڼ̳���ActivityGroup��������Ҫ��setup���������˲��������̳�TabActivity���ʡ��
		tabHost.setup(this.getLocalActivityManager());

		// ������ǩ
		for (int i = 0; i < activitys.length; i++) {
			// ʵ����һ��view��Ϊtab��ǩ�Ĳ���
			View view = View.inflate(this, R.layout.main_tab_layout, null);

			// ����imageview
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			imageView.setImageDrawable(getResources().getDrawable(image[i]));
			// ����textview
			TextView textView = (TextView) view.findViewById(R.id.title);
			textView.setText(title[i]);
			// ������תactivity
			Intent intent = new Intent(this, activitys[i]);

			// ����view����������ת��activity
			TabSpec spec = tabHost.newTabSpec(title[i]).setIndicator(view).setContent(intent);

			// ��ӵ�ѡ�
			tabHost.addTab(spec);
		}

	}
}

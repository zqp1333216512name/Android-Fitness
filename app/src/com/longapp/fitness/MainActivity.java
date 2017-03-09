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
	private TabHost tabHost;// 声明一个TabHost对象

	// 资源文件
	@SuppressWarnings("rawtypes")
	private Class activitys[] = { DietActivity.class, AnalysisActivity.class, ToolsActivity.class, FoodActivity.class };// 跳转的Activity
	private String title[] = { "饮食", "分析", "工具", "食品库" };// 设置菜单的标题
	private int image[] = { R.drawable.tab_icon1, R.drawable.tab_icon2, R.drawable.tab_icon3, R.drawable.tab_icon4 };// 设置菜单

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		initTabView();// 初始化tab标签
	}

	private void initTabView() {
		// 实例化tabhost
		this.tabHost = (TabHost) findViewById(R.id.mytabhost);
		// 由于继承了ActivityGroup，所以需要在setup方法里加入此参数，若继承TabActivity则可省略
		tabHost.setup(this.getLocalActivityManager());

		// 创建标签
		for (int i = 0; i < activitys.length; i++) {
			// 实例化一个view作为tab标签的布局
			View view = View.inflate(this, R.layout.main_tab_layout, null);

			// 设置imageview
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			imageView.setImageDrawable(getResources().getDrawable(image[i]));
			// 设置textview
			TextView textView = (TextView) view.findViewById(R.id.title);
			textView.setText(title[i]);
			// 设置跳转activity
			Intent intent = new Intent(this, activitys[i]);

			// 载入view对象并设置跳转的activity
			TabSpec spec = tabHost.newTabSpec(title[i]).setIndicator(view).setContent(intent);

			// 添加到选项卡
			tabHost.addTab(spec);
		}

	}
}

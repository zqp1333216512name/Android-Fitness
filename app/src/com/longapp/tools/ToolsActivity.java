package com.longapp.tools;

import java.util.ArrayList;
import com.longapp.fitness.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ToolsActivity extends Activity {
	private ViewPager pager = null;
	private ArrayList<View> viewContainter = new ArrayList<View>();
	private EditText bmi_height;
	private EditText bmi_weight;
	private Button bmi_calculate;
	private TextView bmi_result;
	
	private Spinner bmr_age;
	private Spinner bmr_sex;
	private EditText bmr_weight;
	private Spinner bmr_action;
	private Button bmr_calculate;
	private TextView bmr_result;
	
	private EditText bfr_age;
	private Spinner bfr_sex;
	private EditText bfr_height;
	private EditText bfr_weight;
	private Button bfr_calculate;
	private TextView bfr_result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tools_activity);
		
		pager = (ViewPager) this.findViewById(R.id.tools_viewpager);
		View view1 = LayoutInflater.from(this).inflate(R.layout.tools_page1, null);
		View view2 = LayoutInflater.from(this).inflate(R.layout.tools_page2, null);
		View view3 = LayoutInflater.from(this).inflate(R.layout.tools_page3, null);

		//页面1
		bmi_height = (EditText) view1.findViewById(R.id.tools_bmi_height);
		bmi_weight = (EditText) view1.findViewById(R.id.tools_bmi_weight);
		bmi_calculate = (Button) view1.findViewById(R.id.tools_bmi_calculate);
		bmi_result = (TextView) view1.findViewById(R.id.tools_bmi_result);
		bmi_calculate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bmi_calculate();
			}
		});
		
		//页面2
		bmr_age = (Spinner) view2.findViewById(R.id.tools_bmr_age);
		bmr_sex = (Spinner) view2.findViewById(R.id.tools_bmr_sex);
		bmr_weight = (EditText) view2.findViewById(R.id.tools_bmr_weight);
		bmr_action = (Spinner) view2.findViewById(R.id.tools_bmr_action);
		bmr_calculate = (Button) view2.findViewById(R.id.tools_bmr_calculate);
		bmr_result = (TextView) view2.findViewById(R.id.tools_bmr_result);
		bmr_calculate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bmr_calculate();
			}
		});
		
		
		//页面3
		bfr_age = (EditText)view3.findViewById(R.id.tools_bfr_age);
		bfr_sex = (Spinner)view3.findViewById(R.id.tools_bfr_sex);
		bfr_height = (EditText)view3.findViewById(R.id.tools_bfr_height);
		bfr_weight = (EditText)view3.findViewById(R.id.tools_bfr_weight);
		bfr_calculate = (Button)view3.findViewById(R.id.tools_bfr_calculate);
		bfr_result = (TextView)view3.findViewById(R.id.tools_bfr_result);
		bfr_calculate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bfr_calculate();
			}
		});
		
		//viewpager开始添加view
		viewContainter.add(view1);
		viewContainter.add(view2);
		viewContainter.add(view3);
		
		pager.setAdapter(new PagerAdapter() {
			//viewpager中的组件数量
			@Override
			public int getCount() {
				return viewContainter.size();
			}
			//滑动切换的时候销毁当前的组件
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				((ViewPager) container).removeView(viewContainter.get(position));
			}
			//每次滑动的时候生成的组件
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				((ViewPager) container).addView(viewContainter.get(position));
				return viewContainter.get(position);
			}
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			@Override
			public int getItemPosition(Object object) {
				return super.getItemPosition(object);
			}
		});
	}
	//bmr基础代谢率
	//年龄				男					女
	//0-2			60.9*m(kg)-54		61.0m-51
	//3-9			22.7m+495			22.5m+499
	//10-17			17.5m+651			12.2m+746
	//18-29			15.3m+679			14.7m+496
	//30-60			11.6m+879			8.7m+825
	//>60			13.5*m+487			10.5*m+596
	
	//体质指数(BMI)=体重(kg)/身高(m)的平方;"Tips:BMI<18.5,24,28"
	
	//脂肪率 = (1.20*bmi) + (0.23*age) - (10.8*gender) - 5.4
	//男gender=1、女gender=0
	
	private void bmr_calculate() {
		if(bmr_weight.getText().toString().equals(""))
			return;
		
		double weight = Double.parseDouble(bmr_weight.getText().toString());
		double result = 0;
		if(bmr_sex.getSelectedItemPosition() == 0 ){//男
			if(bmr_age.getSelectedItemPosition() == 0) {
				result = 60.9 * weight - 54;
			} else if(bmr_age.getSelectedItemPosition() == 1){
				result = 22.7 * weight +495;
			} else if(bmr_age.getSelectedItemPosition() == 2){
				result = 17.5 * weight +651;
			} else if(bmr_age.getSelectedItemPosition() == 3){
				result = 15.3 * weight +679;
			} else if(bmr_age.getSelectedItemPosition() == 4){
				result = 11.6 * weight +879;
			} else if(bmr_age.getSelectedItemPosition() == 5){
				result = 13.5 * weight +487;
			}
		} else if(bmr_sex.getSelectedItemPosition() == 0){//女
			if(bmr_age.getSelectedItemPosition() == 0) {
				result = 61.0* weight-51;
			} else if(bmr_age.getSelectedItemPosition() == 1){
				result = 22.5 * weight +499;
			} else if(bmr_age.getSelectedItemPosition() == 2){
				result = 12.2 * weight +746;
			} else if(bmr_age.getSelectedItemPosition() == 3){
				result = 14.7 * weight +496;
			} else if(bmr_age.getSelectedItemPosition() == 4){
				result = 8.7 * weight +825;
			} else if(bmr_age.getSelectedItemPosition() == 5){
				result = 10.5 * weight +596;
			}
		}
		bmr_result.setText("你的基础代谢率为" + String.format("%.0f", result) + "大卡\n建议每天需要热量" + String.format("%.0f", result * (1.2 + bmr_action.getSelectedItemPosition()*0.2)) +"大卡");
	}
	
	private void bmi_calculate() {
		if(bmi_weight.getText().toString().equals("") || bmi_height.getText().toString().equals(""))
			return;
		double weight = Double.parseDouble(bmi_weight.getText().toString());
		double height = Double.parseDouble(bmi_height.getText().toString());
		if(weight == 0 || height == 0)
			return;
		double result = weight /((height / 100.0) * (height / 100.0));
		String tip = "正常";
		if(result > 28)
			tip = "肥胖";
		else if(result > 24)
			tip = "超重";
		else if(result < 18.5)
			tip = "偏瘦";
		bmi_result.setText("你的BMI指数为:" + String.format("%.1f", result) + "\n" + tip + "(21-22为最佳)");
	}
	
	private void bfr_calculate() {
		if(bfr_weight.getText().toString().equals("") || bfr_height.getText().toString().equals("") || bfr_age.getText().toString().equals(""))
			return;
		double age = Double.parseDouble(bfr_age.getText().toString());
		double weight = Double.parseDouble(bfr_weight.getText().toString());
		double height = Double.parseDouble(bfr_height.getText().toString());
		if(weight == 0 || height == 0 || age < 18)
			return;
		double result = 0;
		String tip = "正常";
		double bmi = weight /((height / 100.0) * (height / 100.0));
		if(bfr_sex.getSelectedItemPosition() == 0) {//男
			result = 1.20*bmi + 0.23*age - 10.8 - 5.4;
			if(age < 40) {
				if(result < 10) {
					tip = "偏瘦";
				} else if(result < 22) {
					tip = "正常";
				} else if(result < 27) {
					tip = "超重";
				} else {
					tip = "肥胖";
				}
			} else if(age < 60) {
				if(result < 11) {
					tip = "偏瘦";
				} else if(result < 23) {
					tip = "正常";
				} else if(result < 28) {
					tip = "超重";
				} else {
					tip = "肥胖";
				}
			} else {
				if(result < 13) {
					tip = "偏瘦";
				} else if(result < 25) {
					tip = "正常";
				} else if(result < 30) {
					tip = "超重";
				} else {
					tip = "肥胖";
				}
			}
			
		} else if(bfr_sex.getSelectedItemPosition() == 1) {//女
			result = 1.20*bmi + 0.23*age - 5.4;
			if(age < 40) {
				if(result < 20) {
					tip = "偏瘦";
				} else if(result < 35) {
					tip = "正常";
				} else if(result < 40) {
					tip = "超重";
				} else {
					tip = "肥胖";
				}
			} else if(age < 60) {
				if(result < 21) {
					tip = "偏瘦";
				} else if(result < 36) {
					tip = "正常";
				} else if(result < 41) {
					tip = "超重";
				} else {
					tip = "肥胖";
				}
			} else {
				if(result < 22) {
					tip = "偏瘦";
				} else if(result < 37) {
					tip = "正常";
				} else if(result < 42) {
					tip = "超重";
				} else {
					tip = "肥胖";
				}
			}
		}
		bfr_result.setText("你的体脂率为:" + String.format("%.1f\n", result) + tip);
	}
}

package com.joyi.xungeng.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.domain.LineNode;

import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【巡更打卡】 Activity
 */
public class XunGengDaKaActivity extends BaseActivity {
	private TableLayout tableLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xun_geng_da_ka);
		tableLayout = (TableLayout) findViewById(R.id.patrol_record_table);

		List<LineNode> lineNodes = (List<LineNode>) getIntent().getSerializableExtra("lineNodes");
		Log.e(TAG, lineNodes == null ? "null": lineNodes.toArray().toString());
		for (LineNode node : lineNodes) {
			TableRow tableRow = new TableRow(this);
			tableRow.setBackgroundColor(Color.WHITE);
			tableRow.setPadding(1, 1, 1, 1);

			TextView dian = new TextView(this);
			dian.setText(node.getNodeName());
			dian.setTextColor(Color.WHITE);
			dian.setBackgroundColor(Color.BLACK);
			dian.setTextSize(20);
			TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
			layoutParams.setMargins(0, 0, 2, 0);
			dian.setLayoutParams(layoutParams);

			TextView status = new TextView(this);
			status.setText(node.getStatus());
			status.setTextColor(Color.WHITE);
			status.setBackgroundColor(Color.BLACK);
			status.setTextSize(20);
			layoutParams = new TableRow.LayoutParams();
			dian.setLayoutParams(layoutParams);

			TextView time = new TextView(this);
			time.setText(node.getTime());
			time.setTextColor(Color.WHITE);
			time.setBackgroundColor(Color.BLACK);
			time.setTextSize(20);
			layoutParams.setMargins(0, 0, 2, 0);
			time.setLayoutParams(layoutParams);

			tableRow.addView(dian);
			tableRow.addView(status);
			tableRow.addView(time);
		}
	}
}


package com.joyi.xungeng.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.dao.PatrolViewDao;
import com.joyi.xungeng.domain.PatrolView;
import com.joyi.xungeng.util.DateUtil;

import java.util.List;

/**
 * Created by zhangyong on 2014/10/17.
 * 【巡查打卡】Activity
 */
public class XunchaDaKaActivity extends BaseActivity {
	private PatrolViewDao patrolViewDao = new PatrolViewDao();
	private TableLayout tableLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xun_cha_da_ka);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText(SystemVariables.USER_NAME);

		tableLayout = (TableLayout) findViewById(R.id.patrol_view_record_table);
		List<PatrolView> patrolViewList = patrolViewDao.getAll();

		if (patrolViewList != null) {
			for (PatrolView pview : patrolViewList) {
				TableRow tableRow = new TableRow(this);
				tableRow.setBackgroundColor(Color.WHITE);
				tableRow.setPadding(1, 1, 1, 1);

				TextView dian = new TextView(this);
				dian.setGravity(Gravity.CENTER);
				dian.setText(pview.getNodeName());
				dian.setTextColor(Color.WHITE);
				dian.setBackgroundColor(Color.BLACK);
				dian.setTextSize(20);
				TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
				layoutParams.setMargins(0, 0, 2, 0);
				dian.setLayoutParams(layoutParams);

				TextView status = new TextView(this);
				status.setGravity(Gravity.CENTER);
				status.setText(pview.getStatus());
				status.setTextColor(Color.WHITE);
				status.setBackgroundColor(Color.BLACK);
				status.setTextSize(20);
				status.setLayoutParams(layoutParams);

				TextView time = new TextView(this);
				time.setGravity(Gravity.CENTER);
				time.setText(DateUtil.getHumanReadStr(pview.getPatrolTime()));
				time.setTextColor(Color.WHITE);
				time.setBackgroundColor(Color.BLACK);
				time.setTextSize(20);
				time.setLayoutParams(layoutParams);

				tableRow.addView(dian);
				tableRow.addView(status);
				tableRow.addView(time);
				tableLayout.addView(tableRow);
			}
		}
	}
}

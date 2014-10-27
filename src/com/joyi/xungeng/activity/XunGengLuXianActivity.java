package com.joyi.xungeng.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.joyi.xungeng.BaseActivity;
import com.joyi.xungeng.R;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.PatrolLine;

import java.io.Serializable;

/**
 * Created by zhangyong on 2014/10/17.
 * 【巡更路线】Activity
 */
public class XunGengLuXianActivity extends BaseActivity implements AdapterView.OnItemClickListener {
	private ListView listView;
	private NfcAdapter nfcAdapter;
	private PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xun_geng_lu_xian);
		TextView textView = (TextView) findViewById(R.id.username_edittext);
		textView.setText("巡更路线");

		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		freshList();
	}


	public void freshList() {
		listView = (ListView) findViewById(R.id.line_list_view);
		listView.setOnItemClickListener(this);
		ArrayAdapter<PatrolLine> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, SystemVariables.PATROL_LINES);
		listView.setAdapter(arrayAdapter);
	}
	@Override
	protected void onResume() {
		super.onResume();
		freshList();
	}

	/**
	 * 点击路线
	 * @param adapterView
	 * @param view
	 * @param i
	 * @param l
	 */
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

		PatrolLine patrolLine = (PatrolLine) adapterView.getAdapter().getItem(i);
		Intent intent = new Intent(this, XunGengDaKaActivity.class);
		Bundle bundle = new Bundle();
        bundle.putSerializable("patrolLine", patrolLine);
        bundle.putSerializable("lineNodes", (Serializable) patrolLine.getLineNodes());
		intent.putExtras(bundle);
		startActivity(intent);
	}


	@Override
	protected void onPostResume() {
		super.onPostResume();
		if (nfcAdapter != null) {
			nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (nfcAdapter != null) {
			nfcAdapter.disableForegroundDispatch(this);
		}
	}
}

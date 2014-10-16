package com.joyi.xungeng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by zhangyong on 2014/10/16.
 * Spinner适配器
 */
public class SpinnerAdapter extends BaseAdapter {
	private List data;
	private Context context;

	public SpinnerAdapter(List data, Context context) {
		this.data = data;
		this.context = context;
	}
	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int i) {
		return null;
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		return null;
	}
}

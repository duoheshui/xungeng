package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.db.LineNodeDBHelper;
import com.joyi.xungeng.domain.LineNode;

import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【线路节点】 dao
 */
public class LineNodeDao {
	private LineNodeDBHelper lineNodeDBHelper;

	public LineNodeDao(Context context) {
		this.lineNodeDBHelper = new LineNodeDBHelper(context);
	}

	/**
	 * 添加节点
	 * @param lineNode
	 */
	public void add(LineNode lineNode) {
		SQLiteDatabase writableDatabase = lineNodeDBHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("id", lineNode.getId());
		contentValues.put("lineid", lineNode.getLineid());
		contentValues.put("nodeName", lineNode.getNodename());
		contentValues.put("nfcCode", lineNode.getNfcCode());
		writableDatabase.insert("line_node", null, contentValues);
	}

	public List<LineNode> getListByLineId(String lineid) {
		List<LineNode> lineNodes = null;



		return lineNodes;
	}
}

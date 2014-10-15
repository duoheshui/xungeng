package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.db.LineNodeDBHelper;
import com.joyi.xungeng.domain.LineNode;

import java.util.ArrayList;
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

	/**
	 * 根据巡更路线获取该路线下的所有节点
	 * @param lineid
	 * @return
	 */
	public List<LineNode> getListByLineId(String lineid) {
		List<LineNode> lineNodes = new ArrayList<LineNode>();

		SQLiteDatabase readableDatabase = lineNodeDBHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query("line_node", null, "lineid = ?", new String[]{lineid}, null, null, null);
		if (cursor != null) {
			LineNode lineNode = null;
			while (cursor.moveToNext()) {
				lineNode = new LineNode();
				lineNode.setId(cursor.getString(cursor.getColumnIndex("id")));
				lineNode.setLineid(cursor.getString(cursor.getColumnIndex("lineid")));
				lineNode.setNodename(cursor.getString(cursor.getColumnIndex("nodeName")));
				lineNode.setNfcCode(cursor.getString(cursor.getColumnIndex("nfcCode")));
				lineNodes.add(lineNode);
			}
		}
		return lineNodes;
	}

	public void deleteAll() {
		SQLiteDatabase sqLiteDatabase = lineNodeDBHelper.getWritableDatabase();
		sqLiteDatabase.delete("line_node", null, null);
	}
}

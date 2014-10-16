package com.joyi.xungeng.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.LineNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2014/10/15.
 * 【线路节点】 dao
 */
public class LineNodeDao {

	/**
	 * 添加节点
	 * @param lineNode
	 */
	public long add(LineNode lineNode) {
		SQLiteDatabase writableDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("id", lineNode.getId());
		contentValues.put("lineid", lineNode.getLineId());
		contentValues.put("nodeName", lineNode.getNodeName());
		contentValues.put("nfcCode", lineNode.getNfcCode());
        return writableDatabase.insert("line_node", null, contentValues);
    }

	/**
	 * 根据巡更路线获取该路线下的所有节点
	 * @param lineid
	 * @return
	 */
	public List<LineNode> getListByLineId(String lineid) {
		List<LineNode> lineNodes = new ArrayList<LineNode>();

		SQLiteDatabase readableDatabase = SystemVariables.sqLiteOpenHelper.getReadableDatabase();
		Cursor cursor = readableDatabase.query("line_node", null, "lineid = ?", new String[]{lineid}, null, null, null);
		if (cursor != null) {
			LineNode lineNode = null;
			while (cursor.moveToNext()) {
				lineNode = new LineNode();
				lineNode.setId(cursor.getString(cursor.getColumnIndex("id")));
				lineNode.setLineId(cursor.getString(cursor.getColumnIndex("lineid")));
				lineNode.setNodeName(cursor.getString(cursor.getColumnIndex("nodeName")));
				lineNode.setNfcCode(cursor.getString(cursor.getColumnIndex("nfcCode")));
				lineNodes.add(lineNode);
			}
		}
		return lineNodes;
	}

	public void deleteAll() {
		SQLiteDatabase sqLiteDatabase = SystemVariables.sqLiteOpenHelper.getWritableDatabase();
		sqLiteDatabase.delete("line_node", null, null);
	}
}

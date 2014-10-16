package com.joyi.xungeng.test;

import com.joyi.xungeng.SystemVariables;
import com.joyi.xungeng.domain.KeyValuePair;
import com.joyi.xungeng.domain.Station;

/**
 * Created by zhangyong on 2014/10/16.
 */
public class Test {
	public static void test() {
		Station pair1 = new Station("111", "岗位1");
		Station pair2 = new Station("222", "岗位2");
		Station pair3 = new Station("333", "岗位3");

		KeyValuePair pair4 = new KeyValuePair("1111", "班次1");
		KeyValuePair pair5 = new KeyValuePair("2222", "班次2");
		KeyValuePair pair6 = new KeyValuePair("3333", "班次3");

		KeyValuePair pair7 = new KeyValuePair("11111", "路线7");
		KeyValuePair pair8 = new KeyValuePair("22222", "路线8");
		KeyValuePair pair9 = new KeyValuePair("33333", "1路线9");

		KeyValuePair pair10 = new KeyValuePair("11111", "路线10");
		KeyValuePair pair11 = new KeyValuePair("22222", "路线11");
		KeyValuePair pair12 = new KeyValuePair("33333", "路线12");

		KeyValuePair pair13 = new KeyValuePair("11111", "路线13");
		KeyValuePair pair14 = new KeyValuePair("22222", "路线14");
		KeyValuePair pair15 = new KeyValuePair("33333", "路线15");

		pair1.getLines().add(pair7);
		pair1.getLines().add(pair8);
		pair1.getLines().add(pair9);

		pair2.getLines().add(pair10);
		pair2.getLines().add(pair11);
		pair2.getLines().add(pair12);

		pair3.getLines().add(pair13);
		pair3.getLines().add(pair14);
		pair3.getLines().add(pair15);

		SystemVariables.STATION_LIST.add(pair1);
		SystemVariables.STATION_LIST.add(pair2);
		SystemVariables.STATION_LIST.add(pair3);

		SystemVariables.SHIFT_LIST.add(pair4);
		SystemVariables.SHIFT_LIST.add(pair5);
		SystemVariables.SHIFT_LIST.add(pair6);
	}
}

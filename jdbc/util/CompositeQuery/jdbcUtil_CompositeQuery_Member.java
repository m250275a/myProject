/*
 *  1. 萬用複合查詢-可由客戶端隨意增減任何想查詢的欄位
 *  2. 為了避免影響效能:
 *        所以動態產生萬用SQL的部份,本範例無意採用MetaData的方式,也只針對個別的Table自行視需要而個別製作之
 * */


package jdbc.util.CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_Member {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("memno".equals(columnName) || "memshit".equals(columnName) || "memwow".equals(columnName) || "memballage".equals(columnName)
				||"memreb".equals(columnName)||"memscore".equals(columnName)||"memblock".equals(columnName)||"memast".equals(columnName)||"memsteal".equals(columnName)) // 用於其他
			aCondition = columnName + "=" + value;
		else if ("memname".equals(columnName) || "memadd".equals(columnName)|| "mempassword".equals(columnName)|| "memvarname".equals(columnName)|| "memphone".equals(columnName)
				|| "mememail".equals(columnName)|| "memsex".equals(columnName)|| "memcheck".equals(columnName)) // 用於varchar
			aCondition = columnName + " like '%" + value + "%'";
		else if ("memage".equals(columnName))                          // 用於Oracle的date
			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);
				System.out.println("送出查詢資料的欄位 = " + aCondition);
				System.out.println("送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("memno", new String[] {  });
		map.put("memname", new String[] {  });
		map.put("memadd", new String[] {  });
		map.put("memage", new String[] {  });
		map.put("mempassword", new String[] {  });
		map.put("memvarname", new String[] {  });
		map.put("memphone", new String[] {  });
		map.put("mememail", new String[] {  });
		map.put("memsex", new String[] {  });
		map.put("memcheck", new String[] {  });
		map.put("memshit", new String[] {  });
		map.put("memwow", new String[] {  });
		map.put("memballage", new String[] {  });
		map.put("memreb", new String[] {  });
		map.put("memscore", new String[] {  });
		map.put("memblock", new String[] {  });
		map.put("memast", new String[] {  });
		map.put("memsteal", new String[] {  });
		map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key

		String finalSQL = "select * from Member "
				          + jdbcUtil_CompositeQuery_Member.get_WhereCondition(map)
				          + "order by Memno";
		System.out.println("●●finalSQL = " + finalSQL);

	}
}

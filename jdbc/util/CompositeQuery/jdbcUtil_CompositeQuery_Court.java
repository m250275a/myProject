

package jdbc.util.CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_Court {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

String aCondition = null;

		
		if ("courtname".equals(columnName)|| "courtloc".equals(columnName)) 
			aCondition = columnName + " like '%" + value + "%'";

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
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("courtname", new String[] {  });
		map.put("courtloc", new String[] {  });

		String finalSQL = "select * from Court "
				          + jdbcUtil_CompositeQuery_Court.get_WhereCondition(map)
				          + "order by courtno";

	}
}

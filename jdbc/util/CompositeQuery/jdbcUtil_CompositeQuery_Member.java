/*
 *  1. �U�νƦX�d��-�i�ѫȤ���H�N�W�����Q�d�ߪ����
 *  2. ���F�קK�v�T�į�:
 *        �ҥH�ʺA���͸U��SQL������,���d�ҵL�N�ĥ�MetaData���覡,�]�u�w��ӧO��Table�ۦ���ݭn�ӭӧO�s�@��
 * */


package jdbc.util.CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_Member {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("memno".equals(columnName) || "memshit".equals(columnName) || "memwow".equals(columnName) || "memballage".equals(columnName)
				||"memreb".equals(columnName)||"memscore".equals(columnName)||"memblock".equals(columnName)||"memast".equals(columnName)||"memsteal".equals(columnName)) // �Ω��L
			aCondition = columnName + "=" + value;
		else if ("memname".equals(columnName) || "memadd".equals(columnName)|| "mempassword".equals(columnName)|| "memvarname".equals(columnName)|| "memphone".equals(columnName)
				|| "mememail".equals(columnName)|| "memsex".equals(columnName)|| "memcheck".equals(columnName)) // �Ω�varchar
			aCondition = columnName + " like '%" + value + "%'";
		else if ("memage".equals(columnName))                          // �Ω�Oracle��date
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
				System.out.println("�e�X�d�߸�ƪ���� = " + aCondition);
				System.out.println("�e�X�d�߸�ƪ�����count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// �t�X req.getParameterMap()��k �^�� java.util.Map<java.lang.String,java.lang.String[]> ������
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
		map.put("action", new String[] { "getXXX" }); // �`�NMap�̭��|�t��action��key

		String finalSQL = "select * from Member "
				          + jdbcUtil_CompositeQuery_Member.get_WhereCondition(map)
				          + "order by Memno";
		System.out.println("����finalSQL = " + finalSQL);

	}
}

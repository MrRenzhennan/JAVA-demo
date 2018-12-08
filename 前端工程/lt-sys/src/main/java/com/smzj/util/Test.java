package com.smzj.util;
import java.util.Calendar;
import java.util.Date;


/**
 *
 */

/**
 * Title: Test
 * Description:
 * @author 赵鹏飞
 * @date 2017-9-20下午03:14:15
 */
public class Test {
	private final static String AES_PWD ="aybmfw";
	public static void main(String[] args) {
		/*String str ="%7B%22access_token%22%3A%22V66ny9-TTHYAAAAAAAAts6QDVyI51RTB%22%2C%22refresh_token%22%3A%22aqbiAtK8QeAAAAAAAAAts5kO6kcxxxpV%22%2C%22scope%22%3A%22openid%22%2C%22expire_in%22%3A-5438730%2C%22openid%22%3A11699%7D&preUserInfo=%7B%22userId%22%3A11699%2C%22isAdmin%22%3A0%2C%22authLevel%22%3A0%2C%22realName%22%3Anull%2C%22account%22%3A%22w1457997060%22%2C%22email%22%3Anull%2C%22mobile%22%3A%2218211657381%22%2C%22zipCode%22%3Anull%2C%22caKey%22%3Anull%2C%22gender%22%3Anull%2C%22nation%22%3Anull%2C%22birth%22%3Anull%2C%22workTel%22%3Anull%2C%22workFax%22%3Anull%2C%22homeTel%22%3Anull%2C%22idCode%22%3Anull%2C%22addresses%22%3A%5B%5D%2C%22identities%22%3A%5B%5D%2C%22status%22%3A1%2C%22orderBy%22%3A%22%22%2C%22imgAddr%22%3Anull%2C%22displayName%22%3A%22w1457997060%22%2C%22lastLoginAt%22%3A1505890321000%7D&category=13&preUserId=11699";
		System.out.println(str);*/

		/*	long l1 =1509417492073l;
			long l2 =new Date().getTime();
			long t  =(long) (l2-l1)/(1000*60);
			System.out.println(t);

			int a = 8;
			System.out.println(a==0?"大于或等于0":"小于0");*/
		String content="GFTWc2kJiNWkVk1oCfJbeVjiHS9MVdCOzXOm9kkl7lqkm/c+p0h5eecnZmDZm09JM2kBV7OrOLw0bNDFhKs1n73IlcLkXBW3BWEs1epvnaMBW9FbfFdngwM8r0AZ0hr3xqYZbBaN5TiFhjQB5DVhPy3QUmQ8bcfCja+ckFOcl/KMAsQ2B4rYiDCWcp8SDyW76tOFH8KKJK3ak0OD+o7Pe9N308WLUm9JJ0AeqssU14pTCQbTBWmy8Sm0bMlFxwiwMZXnV+emMHD/fZhHmzYMLz3hIMqaN7L7hNTehW2qhiDmqvyZvJnAGGxzF847Z6CDIl+EozzYcHM0yLqjebVI+sfeD/7Ibb86OVu8Jz7xnBeerDWTBDrQCdyxyFjAe4wnXz5S4ijm2Fw9sQOt99w+Ew==";
//		String result =AES.decrypt(content, AES_PWD);
		String a =Base64.encode("11111111111111111111111");
	System.out.println(com.smzj.util.Base64.encode("1212"));
//		System.out.println(result);
//		String tempUserID =result.substring(256);
//		System.out.println(tempUserID);
	}

	public static Long dateDiff(Date sDate, Date eDate, int diffType) {
	    java.util.Calendar calst = java.util.Calendar.getInstance();
	    java.util.Calendar caled = java.util.Calendar.getInstance();

	    calst.setTime(sDate);
	    caled.setTime(eDate);

	    long diffMill = caled.getTime().getTime() - calst.getTime().getTime();
	    long rtn = 0;
	    switch (diffType) {
	    case Calendar.MILLISECOND:
	        rtn = diffMill;
	        break;
	    case Calendar.SECOND:
	        rtn = diffMill / 1000;
	        break;
	    case Calendar.MINUTE:
	        rtn = diffMill / 1000 / 60;
	        break;
	    case Calendar.HOUR:
	        rtn = diffMill / 1000 / 3600;
	        break;
	    case Calendar.DATE:
	        rtn = diffMill / 1000 / 60 / 60 / 24;
	        break;
	    case Calendar.MONTH:
	        rtn = diffMill / 1000 / 60 / 60 / 24 / 12;
	        break;
	    case Calendar.YEAR:
	        rtn = diffMill / 1000 / 60 / 60 / 24 / 365;
	        break;
	    }
	    return rtn;
	}
}

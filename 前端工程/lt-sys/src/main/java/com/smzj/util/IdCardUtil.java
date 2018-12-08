/**
 *
 */
package com.smzj.util;

import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: Test
 * Description:
 * @author 赵鹏飞
 * @date 2017-9-20下午03:50:10
 */
public class IdCardUtil {

	public static void main(String[] args) {
		String idCard="41061119840229703";
//		String idCard="[{\"id\":928,\"idType\":1,\"idCode\":\"410611198402297034\"},{\"id\":928,\"idType\":1,\"idCode\":\"410611198402297033\"}]";
		System.out.println(new IdCardUtil().getIdCard(idCard));;
	}

	public static String getIdCard(String idCard){
		String TempIdCard ="";
		if(StringUtil.isNull(idCard)|| "[]".equals(idCard)){
			return TempIdCard;
		}
		if(!idCard.startsWith("[") && (idCard.length()==18 ||idCard.length()==15) ){
			return idCard;
		}else if(!"[]".equals(idCard) && idCard.startsWith("[") && idCard.endsWith("]")){
			JSONArray  dataArrJson =JSONArray.fromObject(idCard);
			for( Object dataJsonObject:dataArrJson){
				JSONObject  dataJsonInfo =JSONObject.fromObject(dataJsonObject);
				int idTypeStr =dataJsonInfo.getInt("idType");
				if(idTypeStr ==1){
					TempIdCard =dataJsonInfo.getString("idCode");
					break;
				}
			}
		}

		return TempIdCard ;
	}
	
	
	/**
	 * 是否是身份证号
	 * @param str 要验证的身份证号
	 * @return	true or false
	 */
	public static boolean isIDCard(String str){
		String regxStr="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
		Pattern pattern = Pattern.compile(regxStr);
		return pattern.matcher(str).matches();
	}
}

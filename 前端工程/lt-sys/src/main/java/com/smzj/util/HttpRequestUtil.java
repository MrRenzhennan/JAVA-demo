package com.smzj.util;


import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

public class HttpRequestUtil {
	private  static final Logger logger=Logger.getLogger(HttpRequestUtil.class);
	/**
	 * HttpClient 模拟POST请求
	  * 方法说明
	  * @Discription:扩展说明
	  * @param url
	  * @param params
	  * @return String 1-发送成功
	 */
	public static String postRequest(String url, Map<String, String> params) {
		//接收处理结果
		String result = null;
		if(params.isEmpty()){
			return "2";
		}
		//构造HttpClient的实例
		HttpClient httpClient = new HttpClient();

		//创建POST方法的实例
		PostMethod postMethod = new PostMethod(url);

		//设置请求头信息
		postMethod.setRequestHeader("Connection", "close");

		//添加参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			postMethod.addParameter(entry.getKey(), entry.getValue());
		}

		//使用系统提供的默认的恢复策略,设置请求重试处理，用的是默认的重试处理：请求三次
		httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);


		try {
			//执行Http Post请求
			httpClient.executeMethod(postMethod);

			//返回处理结果
			result = postMethod.getResponseBodyAsString();
			result ="1";
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			logger.error("发送短信验证码失败,发生致命的异常，可能是协议不对或者返回的内容有问题",e);
			result="发送短信验证码失败!";
		} catch (IOException e) {
			// 发生网络异常
			logger.error("发送短信验证码失败,发生网络异常",e);
			result="发送短信验证码失败!";
		} finally {
			//释放链接
			postMethod.releaseConnection();
			//关闭HttpClient实例
			if (httpClient != null) {
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				httpClient = null;
			}
		}
		return result;
	}

	/**
	 *  HttpClient 模拟GET请求
	  * 方法说明
	  * @Discription:扩展说明
	  * @param url
	  * @param params
	  * @return String
	  * @Author: feizi
	  * @Date: 2015年4月17日 下午7:15:28
	  * @ModifyUser：feizi
	  * @ModifyDate: 2015年4月17日 下午7:15:28
	 */
	public static String getRequest(String url,String MSGTITLE) {
		//构造HttpClient实例
		HttpClient client = new HttpClient();


		//创建GET方法的实例
		GetMethod method = new GetMethod(url);

		//接收返回结果
		String result = null;
		try {
			//执行HTTP GET方法请求
			client.executeMethod(method);

			//返回处理结果
			result = method.getResponseBodyAsString();
			if("True".equals(result)){
				result ="1";
			}else{
				//
				result ="2";
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			logger.error("发送短信验证码失败,发生致命的异常，可能是协议不对或者返回的内容有问题",e);
			result="发送"+MSGTITLE+"短信失败!";
		} catch (IOException e) {
			// 发生网络异常
			logger.error("发送短信验证码失败,发生网络异常",e);
			result="发送"+MSGTITLE+"短信失败!";
		} finally {
			//释放链接
			method.releaseConnection();

			//关闭HttpClient实例
			if (client != null) {
				((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
				client = null;
			}
		}
		return result;
	}
}

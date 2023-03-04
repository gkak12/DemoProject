package com.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public class RestUtil {
	
	public static String getHttpRestApi(String apiUrl, Map<String, Object> params) throws MalformedURLException, IOException, Exception {
		if(StringUtils.isEmpty(apiUrl)) {
			throw new Exception("WebUtil getHttpRestApi: apiUrl is empty or null.");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(apiUrl).append("?");
		
		Iterator<String> paramItr = params.keySet().iterator();
		
		while(paramItr.hasNext()) {	// parameter 추가
			String paramKey = paramItr.next();
			String paramValue = params.get(paramKey).toString();
			
			sb.append(paramKey).append("=").append(paramValue).append("&");
		}
		
		String urlAddr = sb.deleteCharAt(sb.length()-1).toString();
		
		URL url = new URL(urlAddr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("GET");	//method 설정
		
		Map<String, Object> headers = new HashMap<String, Object>();	//header 추가
		
		headers.put("charset", "UTF-8");
		headers.put("Content-Type", "application/json; charset=UTF-8");
		headers.put("Accept", "application/json");
		
		Iterator<String> headerItr = headers.keySet().iterator();
		
		while(headerItr.hasNext()) {
			String haaderKey = headerItr.next();
			String headerValue = headers.get(haaderKey).toString();
			
			conn.addRequestProperty(haaderKey, headerValue);
		}
		
		conn.connect();	//rest api 접속
		int statusCode = conn.getResponseCode();
		
		if(statusCode == 403){
			throw new Exception("403 error.");
		} else if(statusCode == 404) {
			throw new Exception("404 error.");
		} else if(statusCode == 405) {
			throw new Exception("405 error.");
		} else if(statusCode == 500) {
			throw new Exception("500 error.");
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuffer resSb = new StringBuffer();
		String tmpRes = null;
		
		while((tmpRes = br.readLine()) != null) {
			resSb.append(tmpRes+"\n");
		}
		
		conn.disconnect();
		br.close();
		
		return resSb.toString();
	}
	
	public static String getHttpsRestApi(String apiUrl, Map<String, Object> params) throws MalformedURLException, IOException, Exception {
		if(StringUtils.isEmpty(apiUrl)) {
			throw new Exception("WebUtil getHttpsRestApi: apiUrl is empty or null.");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(apiUrl).append("?");
		
		Iterator<String> paramItr = params.keySet().iterator();
		
		while(paramItr.hasNext()) {	// parameter 추가
			String paramKey = paramItr.next();
			String paramValue = params.get(paramKey).toString();
			
			sb.append(paramKey).append("=").append(paramValue).append("&");
		}
		
		String urlAddr = sb.deleteCharAt(sb.length()-1).toString();
		
		URL url = new URL(urlAddr);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		
		conn.setRequestMethod("GET");	//method 설정
		
		Map<String, Object> headers = new HashMap<String, Object>();	//header 추가
		
		headers.put("charset", "UTF-8");
		headers.put("Content-Type", "application/json; charset=UTF-8");
		headers.put("Accept", "application/json");
		
		Iterator<String> headerItr = headers.keySet().iterator();
		
		while(headerItr.hasNext()) {
			String haaderKey = headerItr.next();
			String headerValue = headers.get(haaderKey).toString();
			
			conn.addRequestProperty(haaderKey, headerValue);
		}
		
		conn.connect();	//rest api 접속
		int statusCode = conn.getResponseCode();
		
		if(statusCode == 403){
			throw new Exception("403 error.");
		} else if(statusCode == 404) {
			throw new Exception("404 error.");
		} else if(statusCode == 405) {
			throw new Exception("405 error.");
		} else if(statusCode == 500) {
			throw new Exception("500 error.");
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuffer resSb = new StringBuffer();
		String tmpRes = null;
		
		while((tmpRes = br.readLine()) != null) {
			resSb.append(tmpRes+"\n");
		}
		
		conn.disconnect();
		br.close();
		
		return resSb.toString();
	}
	
	public static String postHttpRestApi(String apiUrl, Map<String, Object> params) throws MalformedURLException, IOException, Exception {
		if(StringUtils.isEmpty(apiUrl)) {
			throw new Exception("WebUtil postHttpRestApi: apiUrl is empty or null.");
		}
		
		URL url = new URL(apiUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String paramStr = null;
		
		conn.setRequestMethod("POST");	//method 설정
		
		Map<String, Object> headers = new HashMap<String, Object>();	//header 추가
		
		headers.put("charset", "UTF-8");
		headers.put("Content-Type", "application/json; utf-8");
		headers.put("Accept", "application/json");
		
		Iterator<String> headerItr = headers.keySet().iterator();
		
		while(headerItr.hasNext()) {
			String haaderKey = headerItr.next();
			String headerValue = headers.get(haaderKey).toString();
			
			conn.addRequestProperty(haaderKey, headerValue);
		}
		
		conn.setDoOutput(true);
		
		JSONObject jsonObject = new JSONObject(params);	//parameter body 작성
		paramStr = jsonObject.toString();
		
		OutputStream os = conn.getOutputStream();
		os.write(paramStr.getBytes("UTF-8"));
		
		conn.connect();
		
		int statusCode = conn.getResponseCode();
		
		if(statusCode == 403){
			throw new Exception("403 error.");
		} else if(statusCode == 404) {
			throw new Exception("404 error.");
		} else if(statusCode == 405) {
			throw new Exception("405 error.");
		} else if(statusCode == 500) {
			throw new Exception("500 error.");
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuffer sb = new StringBuffer();
		String tmpRes = null;
		
		while((tmpRes = br.readLine()) != null) {
			sb.append(tmpRes+"\n");
		}
		
		conn.disconnect();
		os.close();
		br.close();
		
		return sb.toString();
	}
	
	public static String postHttpsRestApi(String apiUrl, Map<String, Object> params) throws MalformedURLException, IOException, Exception {
		if(StringUtils.isEmpty(apiUrl)) {
			throw new Exception("WebUtil postHttpsRestApi: apiUrl is empty or null.");
		}
		
		URL url = new URL(apiUrl);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		String paramStr = null;
		
		conn.setRequestMethod("POST");	//method 설정
		
		Map<String, Object> headers = new HashMap<String, Object>();	//header 추가
		
		headers.put("charset", "UTF-8");
		headers.put("Content-Type", "application/json; utf-8");
		headers.put("Accept", "application/json");
		
		
		Iterator<String> headerItr = headers.keySet().iterator();
		
		while(headerItr.hasNext()) {
			String haaderKey = headerItr.next();
			String headerValue = headers.get(haaderKey).toString();
			
			conn.addRequestProperty(haaderKey, headerValue);
		}
		
		conn.setDoOutput(true);
		
		JSONObject jsonObject = new JSONObject(params);	//parameter body 작성
		paramStr = jsonObject.toString();
		
		OutputStream os = conn.getOutputStream();
		os.write(paramStr.getBytes("UTF-8"));
		
		conn.connect();
		
		int statusCode = conn.getResponseCode();
		
		if(statusCode == 403){
			throw new Exception("403 error.");
		} else if(statusCode == 404) {
			throw new Exception("404 error.");
		} else if(statusCode == 405) {
			throw new Exception("405 error.");
		} else if(statusCode == 500) {
			throw new Exception("500 error.");
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuffer sb = new StringBuffer();
		String tmpRes = null;
		
		while((tmpRes = br.readLine()) != null) {
			sb.append(tmpRes+"\n");
		}
		
		conn.disconnect();
		os.close();
		br.close();
		
		return sb.toString();
	}
}

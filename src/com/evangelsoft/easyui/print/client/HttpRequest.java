package com.evangelsoft.easyui.print.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpRequest {


	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		HttpURLConnection conn = null;
		try {
			URL realUrl = new URL("http://10.0.1.107:8080/okdeer-jxc-ecard/ecard/sign/signOut");
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			// conn.addRequestProperty("test", param);
			// conn.setRequestProperty("test", param);
			// System.out.println("---------------------------------");
			// URL url = new URL(fileUrl);
			// HttpURLConnection httpConn = (HttpURLConnection)
			// url.openConnection();

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("contentType", "utf8");
			conn.addRequestProperty("Content-type", "application/json");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			// out = new PrintWriter(conn.getOutputStream());
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf8"));
			// 发送请求参数

			out.print(param);
			// flush输出流的缓冲
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			// String cookieValue=conn.getHeaderField("Set-Cookie");
		} catch (java.net.ConnectException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException ex) {
			}
		}
		return result;
	}

	/**
	 * @Description: 发送信息
	 * @param url
	 * @param vo
	 * @param signInfo
	 * @param clz
	 * @return
	 * @throws RuntimeException   
	 * @return RespJsonExt<T>  
	 * @throws
	 * @author yangyq02
	 * @date 2017年6月2日
	 */


	public static void main(String[] args) {
		for(int i=0;i<1000;i++){
			sendPost("http://10.20.106.56:8080/okdeerjxc/print/printLabel", "{\"branchCode\":\"20006\",\"branchId\":\"8a98680e5669d46a01566e0f9f8701f8\",\"ip\":\"172.18.1.116\",\"port\":10001,\"posId\":\"540270000001\",\"posNo\":\"01\",\"sign\":\"6FF9DD82B2FAE603ADC121E5B768E7AD\",\"unitId\":\"54000027\",\"unlockingKey\":\"E7AC092E790C2F0B8878E892DB0678A7\",\"val\":0,\"totalSvNum\":0,\"totalSvAmt\":0,\"totalSaleDep\":0}");
		}
		System.out.println("执行完毕");
	}
}
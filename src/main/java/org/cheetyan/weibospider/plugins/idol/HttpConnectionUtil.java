/**
 * 
 */
package org.cheetyan.weibospider.plugins.idol;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author cheetyan I love this httpconnector!! because we don't need to inclue apache httpclient lib.
 *
 */
public class HttpConnectionUtil {
	// return UTF-8 string
	public static String get(String url) throws IOException {
		HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
		httpConnection.setRequestMethod("GET");
		httpConnection.setDoInput(true);
		if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			String charset = getCharset(httpConnection.getContentType());
			charset = charset == null ? "UTF-8" : charset;
			StringBuilder response = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), Charset.forName(charset)))) {
				for (String line; (line = reader.readLine()) != null;) {
					if (charset.equals("UTF-8") || charset.equals("utf-8")) {
						response.append(line);
					} else {
						response.append(new String(line.getBytes(charset), Charset.forName("UTF-8")));
					}
				}
			}
			httpConnection.disconnect();
			return response.toString();
		} else {
			return null;
		}
	}

	public static String get(String host, String baseUrl, Map<String, String> pairs) throws IOException {
		return get("http", host, 80, baseUrl, pairs);
	}

	public static String get(String http, String host, int port, String baseUrl, Map<String, String> pairs) throws IOException {
		String absURL = http + "://" + host + ":" + port + "/" + baseUrl;
		int i = 0;
		for (Entry<String, String> entry : pairs.entrySet()) {
			if (i == 0) {
				absURL += "?" + entry.getKey() + "=" + entry.getValue();
				i++;
			} else {
				absURL += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
			}
		}
		return get(absURL);
	}

	public static String post(String host, String baseUrl, Map<String, String> pairs) throws IOException {
		String absURL = "http" + "://" + host + ":" + 80 + "/" + baseUrl;
		return post(absURL, pairs);
	}

	public static String post(String http, String host, int port, String baseUrl, Map<String, String> pairs) throws IOException {
		String absURL = http + "://" + host + ":" + port + "/" + baseUrl;
		return post(absURL, pairs);
	}

	// return UTF-8 string
	// String urlParameters ="fName=" + URLEncoder.encode("???", "UTF-8") +"&lName=" + URLEncoder.encode("???", "UTF-8")
	public static String post(String url, Map<String, String> pairs) throws IOException {
		String urlParameters = mapToString(pairs);
		return postEntity(url, urlParameters);
	}

	public static byte[] getBytes(String url) throws IOException {
		HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
		httpConnection.setRequestMethod("GET");
		httpConnection.setDoInput(true);
		if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			BufferedInputStream bi = new BufferedInputStream(httpConnection.getInputStream());
			int b;
			while ((b = bi.read()) != -1) {
				bos.write(b);
			}
			return bos.toByteArray();
		} else {
			return null;
		}
	}

	public static String postEntity(String url, String entity) throws IOException {
		HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
		httpConnection.setRequestMethod("POST");
		httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpConnection.setRequestProperty("Content-Length", "" + Integer.toString(entity.getBytes("UTF-8").length));
		// httpConnection.setRequestProperty("Content-Language", "en-US");
		httpConnection.setUseCaches(false);
		httpConnection.setDoInput(true);
		httpConnection.setDoOutput(true);
		// Send request
		DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
		wr.write(entity.getBytes("UTF-8"));
		wr.flush();
		wr.close();
		//
		if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			String charset = getCharset(httpConnection.getContentType());
			charset = charset == null ? "UTF-8" : charset;
			StringBuilder response = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), Charset.forName(charset)))) {
				for (String line; (line = reader.readLine()) != null;) {
					if (charset.equals("UTF-8") || charset.equals("utf-8")) {
						response.append(line);
					} else {
						response.append(new String(line.getBytes(charset), Charset.forName("UTF-8")));
					}
				}
			}
			httpConnection.disconnect();
			return response.toString();
		} else {
			return null;
		}
	}

	private static String getCharset(String contentType) {
		if (contentType == null)
			return null;
		String charset = null;
		for (String param : contentType.replace(" ", "").split(";")) {
			if (param.startsWith("charset=")) {
				charset = param.split("=", 2)[1];
				break;
			}
		}
		return charset;
	}

	// ----------------------------------------------------------------------------------------------
	private static String mapToString(Map<String, String> pairs) throws UnsupportedEncodingException {
		String pairstring = "";
		int i = 0;
		for (Entry<String, String> entry : pairs.entrySet()) {
			if (i == 0) {
				pairstring += entry.getKey() + "=" + entry.getValue();
				i++;
			} else {
				pairstring += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
			}
		}
		return pairstring;
	}
}

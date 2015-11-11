package com.example.myskety.my_application.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import android.os.AsyncTask;

public class NetConnection {

	public NetConnection(final SuccessCallback successCallback, final FailCallback failCallback, final String url,
			final HttpMethod method, final String... kvs) {

		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				System.out.println("URL:" + url);
				StringBuffer paramStr = new StringBuffer();
				for (int i = 0; i < kvs.length; i += 2) {
					paramStr.append(kvs[i]).append("=").append(kvs[i + 1]);
					if (i + 2 < kvs.length) {
						paramStr.append("&");
					}
				}
				System.out.println("Data:" + paramStr.toString());
				try {
					URL httpUrl;
					switch (method) {
					case POST:
						httpUrl = new URL(url);
						HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
						conn.setRequestMethod("POST");
						OutputStream out = conn.getOutputStream();
						out.write(paramStr.toString().getBytes());
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						StringBuffer sb = new StringBuffer();
						String str;
						while ((str = br.readLine()) != null) {
							sb.append(str);
						}
						System.out.println("Result:" + sb.toString());
						String result = sb.toString();
						
						return URLDecoder.decode(result, "utf-8");
					default:
						break;
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);

				if (result != null) {
					if (successCallback != null) {
						successCallback.onSuccess(result);
					}
				} else {
					if (failCallback != null) {
						failCallback.onFail("网络连接失败！");
					}
				}

			}
		}.execute();
	}

	public static interface SuccessCallback {
		void onSuccess(String result);
	}

	public static interface FailCallback {
		void onFail(String result);
	}
}

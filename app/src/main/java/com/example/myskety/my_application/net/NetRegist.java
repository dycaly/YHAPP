package com.example.myskety.my_application.net;

import com.example.myskety.my_application.MainConfig;

import org.json.JSONException;
import org.json.JSONObject;


public class NetRegist {

	public NetRegist(final SuccessCallback successCallback, final FailCallback failCallback, String username,
			String password) {
		new NetConnection(new NetConnection.SuccessCallback() {

			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					switch (json.getInt("status")) {
					case 0:
						if (successCallback != null) {
							successCallback.onSuccess(json.getString("token"));
						}
						break;
					case 1:
						if (failCallback != null) {
							failCallback.onFail(json.getString("reason"));
						}
						break;
					default:
						failCallback.onFail("Login Faild:" + json.getInt("status"));
						break;
					}

				} catch (JSONException e) {
					e.printStackTrace();
					if (failCallback != null) {
						failCallback.onFail("Login Faild:返回JSON数据有误");
					}
				}

			}
		}, new NetConnection.FailCallback() {

			@Override
			public void onFail(String result) {
				if (failCallback != null) {
					failCallback.onFail(result);
				}
			}
		}, MainConfig.REGIST_URL, HttpMethod.POST, "username", username, "password", password);
	}
	
	public static interface SuccessCallback{
		public void onSuccess(String token);
	}
	
	public static interface FailCallback{
		public void onFail(String reason);
	}
}

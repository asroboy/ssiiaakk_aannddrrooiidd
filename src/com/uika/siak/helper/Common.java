package com.uika.siak.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class Common {
	private static String TAG = "Common";
	private static Context context_;

	public static void init(Context context) {
		if (context_ == null)
			context_ = context;
	}

	public static int getIntPreference(String key, int defaultValue) {
		SharedPreferences sharedPreferences = context_.getSharedPreferences(
				ConstantValues.PREFERENCES_NAME, Activity.MODE_PRIVATE);
		Log.d(TAG, "getIntPreference key : " + key + " data: "
				+ sharedPreferences.getInt(key, defaultValue));
		return sharedPreferences.getInt(key, defaultValue);

	}

	public static void saveIntPreferences(String key, int data) {
		SharedPreferences sharedPreferences = context_.getSharedPreferences(
				ConstantValues.PREFERENCES_NAME, Activity.MODE_PRIVATE);
		sharedPreferences.edit().putInt(key, data).commit();
		Log.d(TAG, "saveIntPreferences key : " + key + " data: " + data);
	}

	public static void saveStringPreferences(String key, String data) {
		SharedPreferences sharedPreferences = context_.getSharedPreferences(
				ConstantValues.PREFERENCES_NAME, Activity.MODE_PRIVATE);
		sharedPreferences.edit().putString(key, data).commit();
		Log.d(TAG, "saveIntPreferences key : " + key + " data: " + data);
	}

	public static String getStringPreference(String key, String defaultValue) {
		SharedPreferences sharedPreferences = context_.getSharedPreferences(
				ConstantValues.PREFERENCES_NAME, Activity.MODE_PRIVATE);
		Log.d(TAG, "getIntPreference key : " + key + " data: "
				+ sharedPreferences.getString(key, defaultValue));
		return sharedPreferences.getString(key, defaultValue);

	}

	public static String httpRequest(String url) {
		String resp = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		StringBuilder sb_ = new StringBuilder();
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {

				InputStream instream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream));
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb_.append(line + "\n");
				}
				instream.close();
			}
			resp = sb_.toString();
			Log.v(TAG, "Response : " + resp);
			// System.out.println("Response " + resp);
		} catch (Exception e) {
		}

		// showToast("response "+resp);
		return resp;
	}

	public static String login(String username, String password) {
		String url = "";
		try {
			url = ConstantValues.URI_LOGIN + "?username="
					+ URLEncoder.encode(username, "UTF-8") + "&password="
					+ URLEncoder.encode(password, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpRequest(url);
	}

	public static String changePassword(String username,
			String currentPassword, String password, String confirmPassword) {
		String url = "";
		try {
			url = ConstantValues.URI_CHANGE_PASSWORD + "?username="
					+ URLEncoder.encode(username, "UTF-8")
					+ "&currentpassword="
					+ URLEncoder.encode(currentPassword, "UTF-8")
					+ "&password=" + URLEncoder.encode(password, "UTF-8")
					+ "&cpassword="
					+ URLEncoder.encode(confirmPassword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpRequest(url);
	}

	public static String kirimSaran(String username, String email,
			String judul, String isi) {
		String url = "";
		try {
			url = ConstantValues.URI_KIRIM_SARAN + "?student_code="
					+ URLEncoder.encode(username, "UTF-8") + "&email_address="
					+ URLEncoder.encode(email, "UTF-8") + "&judul="
					+ URLEncoder.encode(judul, "UTF-8") + "&isi="
					+ URLEncoder.encode(isi, "UTF-8");

			Log.d(TAG, "url : " + url);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpRequest(url);
	}

	public static String getProfileInfo(String username) {

		// http://localhost/siak_uika/app/api/student.php?code=11215310615

		String url = "";
		try {
			url = ConstantValues.URI_GET_PROFIL + "?code="
					+ URLEncoder.encode(username, "UTF-8");

			Log.d(TAG, "url : " + url);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpRequest(url);
	}

	public static String getJadwal(String username) {

		// http://localhost/siak_uika/app/api/jadwal_kuliah.php?IDNumber=11215310615

		String url = "";
		try {
			url = ConstantValues.URI_GET_JADWAL_KULIAH + "?IDNumber="
					+ URLEncoder.encode(username, "UTF-8");

			Log.d(TAG, "url : " + url);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpRequest(url);
	}

	public static void showToast(String msg) {
		if (context_ != null) {
			Toast.makeText(context_, msg, Toast.LENGTH_SHORT).show();
		}
	}

	public static ArrayList<ArrayList<String>> getJSONArray(
			String stringResult, ArrayList<String> keys) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		String newJson = stringResult;
		try {
			JSONArray jsonArr = new JSONArray(newJson);
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject obj = jsonArr.getJSONObject(i);
				ArrayList<String> o = new ArrayList<String>();
				for (String key : keys) {
					Log.d(TAG, obj.getString(key));
					o.add(obj.getString(key));
				}
				result.add(o);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;

	}

	public static ArrayList<String> parseJson(String stringResult,
			ArrayList<String> keys) {
		ArrayList<String> result = new ArrayList<String>();
		String newJson = stringResult;
		try {
			JSONObject jsonObj = new JSONObject(newJson);
			// JSONObject jsonArr = jsonObj.getJSONObject(KEY_DATA_PRIBADI);
			for (String key : keys) {
				Log.d(TAG, jsonObj.getString(key));
				result.add(jsonObj.getString(key));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;

	}
}

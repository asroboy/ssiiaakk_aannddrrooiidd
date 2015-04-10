package com.uika.siak.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.uika.siak.R;
import com.uika.siak.helper.AlertDialogManager;
import com.uika.siak.helper.Common;
import com.uika.siak.helper.SessionManager;

public class LoginFragment extends Fragment implements OnClickListener {
	protected static final String TAG = "LOGIN FRAGMENT";
	// MainActivity activity;
	EditText username, password;
	SessionManager session;
	// Alert Dialog Manager
	AlertDialogManager alert;
	String key = "FRAGMENT_POSTION";
	private final String KEY_LOGIN_SUCCESS = "login_success";
	private final String KEY_USERNAME = "UserName";
	// private final String KEY_USER_GROUP = "UserGroup";
	private final String KEY_REAL_NAME = "RealName";
	private final String KEY_BRANCH_ID = "BranchID";
	// private final String KEY_DIVISION = "Division";
	// private final String KEY_TITLE = "Title";
	private final String KEY_EMAIL_ADDRESS = "EmailAddress";
	private final String KEY_ID_NUMBER = "IDNumber";

	// private final String KEY_IS_LOCKED = "IsLocked";

	public LoginFragment() {

	}

	Button loginButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_login, container,
				false);
		getActivity().getActionBar().setTitle(
				getResources().getString(R.string.app_name));
		Common.init(getActivity());
		alert = new AlertDialogManager();
		loginButton = (Button) rootView.findViewById(R.id.login_button);
		username = (EditText) rootView.findViewById(R.id.editTextUsername);
		password = (EditText) rootView.findViewById(R.id.editTextPassword);
		session = new SessionManager(getActivity().getApplicationContext());
		username.setText("11215310615");
		password.setText(session.getUserDetails().get(
				SessionManager.KEY_PASSWORD));
		loginButton.setOnClickListener(this);
		// if (activity != null)

		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			hideSoftKeyboard();
			login();
			break;
		default:
			break;
		}

	}

	private void hideSoftKeyboard() {
		View view = getView();
		if (view != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
					.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager
					.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	@SuppressLint("NewApi")
	void login() {
		final String usernameString = username.getText().toString();
		final String passwordString = password.getText().toString();

		final ProgressDialog progressDialog = new ProgressDialog(getActivity());
		progressDialog.setTitle(getResources().getString(R.string.loading));
		progressDialog.setMessage(getResources().getString(
				R.string.harap_tunggu));
		// Check if username, password is filled
		if (usernameString.trim().length() > 0
				&& passwordString.trim().length() > 0) {

			new AsyncTask<String, String, String>() {
				protected void onPreExecute() {

					progressDialog.show();
				};

				@Override
				protected String doInBackground(String... params) {
					// TODO Auto-generated method stub
					return Common.login(usernameString, passwordString);
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					Log.d(TAG, "login result " + result);

					ArrayList<String> keys = new ArrayList<>();
					keys.add(KEY_BRANCH_ID);
					keys.add(KEY_ID_NUMBER);
					keys.add(KEY_EMAIL_ADDRESS);
					keys.add(KEY_REAL_NAME);
					keys.add(KEY_USERNAME);
					ArrayList<String> results = parseJson(result, keys);
					progressDialog.dismiss();
					if (result.contains("login_success")) {
						session.createLoginSession(usernameString,
								results.get(2).toString(), passwordString,
								results.get(3).toString());
						FragmentTransaction ft = getFragmentManager()
								.beginTransaction();
						ft.replace(R.id.container, new MenuFragment());
						ft.commit();

					} else {
						// username / password doesn't match
						alert.showAlertDialog(
								getActivity(),
								getResources().getString(R.string.login_gagal),
								getResources().getString(
										R.string.username_or_password_is_wrong),
								false);
					}

					
				}
			}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		} else {
			// user didn't entered username or password
			// Show alert asking him to enter the details
			alert.showAlertDialog(
					getActivity(),
					getResources().getString(R.string.login_gagal),
					getResources().getString(
							R.string.please_enter_username_or_password), false);
		}
	}

	private ArrayList<String> parseJson(String stringResult,
			ArrayList<String> keys) {
		ArrayList<String> result = new ArrayList<String>();
		String newJson = "{" + stringResult + "}";
		try {
			JSONObject jsonObj = new JSONObject(newJson);
			JSONArray jsonArr = jsonObj.getJSONArray(KEY_LOGIN_SUCCESS);
			for (String key : keys) {
				Log.d(TAG, jsonArr.getJSONObject(0).getString(key));
				result.add(jsonArr.getJSONObject(0).getString(key));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;

	}
}

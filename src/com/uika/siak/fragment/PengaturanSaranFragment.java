package com.uika.siak.fragment;

import java.util.HashMap;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uika.siak.R;
import com.uika.siak.helper.AlertDialogManager;
import com.uika.siak.helper.Common;
import com.uika.siak.helper.SessionManager;

public class PengaturanSaranFragment extends Fragment implements
		OnClickListener {

	private TextView tvUserInfo;
	private EditText etJudulSaran, etIsiSaran;
	private Button btKirim;
	private SessionManager session;

	public PengaturanSaranFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_kirim_saran,
				container, false);
		getActivity().getActionBar().setTitle(
				getResources().getString(R.string.kirim_saran));
		getActivity().getActionBar().setHomeButtonEnabled(true);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		session = new SessionManager(getActivity().getApplicationContext());
		setupView(rootView);
		return rootView;
	}

	void setupView(View rootView) {
		tvUserInfo = (TextView) rootView.findViewById(R.id.tv_profil_code);
		etJudulSaran = (EditText) rootView.findViewById(R.id.et_judul_saran);
		etIsiSaran = (EditText) rootView.findViewById(R.id.et_isi_saran);
		btKirim = (Button) rootView.findViewById(R.id.bt_kirim_saran);
		btKirim.setOnClickListener(this);
		tvUserInfo.setText(session.getUserDetails()
				.get(SessionManager.KEY_NAME)
				+ " - "
				+ session.getUserDetails().get(SessionManager.KEY_REAL_NAME));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_kirim_saran:
			String username = session.getUserDetails().get(
					SessionManager.KEY_NAME);
			String email = session.getUserDetails().get(
					SessionManager.KEY_EMAIL);
			String judul = etJudulSaran.getText().toString();
			String isi = etIsiSaran.getText().toString();

			kirimSaran(username, (email.equals("") ? username : email), judul,
					isi);
			break;

		default:
			break;
		}

	}

	public String getPasswordLama() {
		HashMap<String, String> user = session.getUserDetails();
		return user.get(SessionManager.KEY_PASSWORD);
	}

	public String getUsername() {
		HashMap<String, String> user = session.getUserDetails();
		return user.get(SessionManager.KEY_NAME);
	}

	public void kirimSaran(String username, String email, String judul,
			String isi) {
		final String params[] = { username, email, judul, isi };

		final ProgressDialog progressDialog = new ProgressDialog(getActivity());
		progressDialog.setTitle(getResources().getString(R.string.loading));
		progressDialog.setMessage(getResources().getString(
				R.string.harap_tunggu));

		new AsyncTask<String, String, String>() {
			protected void onPreExecute() {
				progressDialog.show();
			};

			@Override
			protected String doInBackground(String... params) {
				return Common.kirimSaran(params[0], params[1], params[2],
						params[3]);
			}

			protected void onPostExecute(String result) {
				progressDialog.hide();
				etJudulSaran.setText("");
				etIsiSaran.setText("");
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(etIsiSaran.getWindowToken(), 0);
				AlertDialogManager alertManager = new AlertDialogManager();
				alertManager.showAlertDialog(getActivity(), "Saran", result);
			};
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
	}

}

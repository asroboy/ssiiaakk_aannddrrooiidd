package com.uika.siak.fragment;

import java.util.HashMap;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uika.siak.R;
import com.uika.siak.helper.Common;
import com.uika.siak.helper.SessionManager;

public class PengaturanPasswordFragment extends Fragment implements
		OnClickListener {

	EditText ETPasswordLama, ETPasswordBaru, ETUlangiPassword;
	Button buttonSimpan;
	SessionManager session;

	public PengaturanPasswordFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.ubah_sandi, container, false);
		getActivity().getActionBar().setTitle(
				getResources().getString(R.string.Pengaturan_Password));
		getActivity().getActionBar().setHomeButtonEnabled(true);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		session = new SessionManager(getActivity().getApplicationContext());
		setupView(rootView);

		return rootView;
	}

	void setupView(View rootView) {
		ETPasswordLama = (EditText) rootView
				.findViewById(R.id.editTextPasswordLama);
		ETPasswordBaru = (EditText) rootView
				.findViewById(R.id.editTextPasswordBaru);
		ETUlangiPassword = (EditText) rootView
				.findViewById(R.id.editTextUlangiSandiBaru);
		buttonSimpan = (Button) rootView.findViewById(R.id.buttonSimpan);
		buttonSimpan.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonSimpan:
			String passwordBaru = ETPasswordBaru.getText().toString();
			String confirmPasswordBaru = ETUlangiPassword.getText().toString();
			if (isPasswordLamaSama()) {
				savePassword(passwordBaru, confirmPasswordBaru);
			} else {
				Toast.makeText(
						getActivity(),
						getResources()
								.getString(
										R.string.Password_lama_yang_Anda_masukkan_salah),
						Toast.LENGTH_SHORT).show();
			}
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

	public Boolean isPasswordLamaSama() {
		String passwordLamaKeyedIn = ETPasswordLama.getText().toString();
		String passwordLama = getPasswordLama();
		return (passwordLamaKeyedIn.equals(passwordLama));
	}

	public Boolean isPasswordBaruSama(String passwordBaru,
			String passwordBaruConfirm) {
		return (passwordBaru.equals(passwordBaruConfirm));
	}

	public void savePassword(String passwordBaru, String confirmPasswordBaru) {
		if (passwordBaru.getBytes().length < 4) {
			Toast.makeText(
					getActivity(),
					getResources().getString(
							R.string.Password_minimal_4_karakter),
					Toast.LENGTH_SHORT).show();
		} else {
			if (isPasswordBaruSama(passwordBaru, confirmPasswordBaru)) {
				savePasswrodAtBackground(getUsername(), getPasswordLama(),
						passwordBaru, confirmPasswordBaru);
			} else {
				Toast.makeText(
						getActivity(),
						getResources().getString(
								R.string.Password_baru_Anda_tidak_cocok),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void savePasswrodAtBackground(String username,
			String currentPassword, String password, String confirmPassword) {

		final String params[] = { username, currentPassword, password,
				confirmPassword };

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
				// TODO Auto-generated method stub
				return Common.changePassword(params[0], params[1], params[2],
						params[3]);
			}

			protected void onPostExecute(String result) {
				Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT)
						.show();
				if (result.trim().equals(
						"Your password was changed successfully")) {
					session.createLoginSession(getUsername(), getUsername(),
							params[2], "");
				}
				progressDialog.hide();
			};
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);

	}
}

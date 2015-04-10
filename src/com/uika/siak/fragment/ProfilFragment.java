package com.uika.siak.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uika.siak.R;
import com.uika.siak.helper.Common;
import com.uika.siak.helper.SessionManager;

public class ProfilFragment extends Fragment {

	TextView tvCode, tvDepartemenCode, tvGender, tvBirthPlace, tvAgama,
			tvAddress, tvCity, tvPhone, tvMobilePhone, tvEmail;

	SessionManager session;

	private final String KEY_CODE = "code";
	private final String KEY_DEPARTEMENT_CODE = "department_code";
	private final String KEY_SEX = "sex";
	private final String KEY_BIRTH_DATE = "birthdate";
	private final String KEY_RELIGION = "religion";
	private final String KEY_REG_DATE = "regdate";
	private final String KEY_DEPARTEMENT_NAME = "departement_name";
	private final String KEY_EMAIL = "email";
	private final String KEY_NAME = "name";
	private final String KEY_KELAS = "class";
	private final String KEY_BIRTHPLACE = "birthplace";
	private final String KEY_ADDRESS = "address";
	private final String KEY_PHONE = "phone";
	private final String KEY_MOBILE_PHONE = "mobile_phone";
	private final String KEY_PHOTO = "photo";
	private final String KEY_REGISTERED_NUMBER = "registered_number";
	private final String KEY_THESIS_TITLE = "thesis_title";
	private final String KEY_CITY = "city";

	String TAG = "ProfilFragment";

	public ProfilFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_profil, container,
				false);
		getActivity().getActionBar().setTitle(
				getResources().getString(R.string.profil));
		getActivity().getActionBar().setHomeButtonEnabled(true);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		session = new SessionManager(getActivity());
		setupViews(rootView);

		return rootView;
	}

	private void setupViews(View root) {
		tvCode = (TextView) root.findViewById(R.id.tv_profil_code);
		tvDepartemenCode = (TextView) root
				.findViewById(R.id.tv_profil_departement_code);
		tvGender = (TextView) root.findViewById(R.id.tv_profil_gender);
		tvBirthPlace = (TextView) root.findViewById(R.id.tv_profil_birth_place);
		tvAgama = (TextView) root.findViewById(R.id.tv_profil_agama);
		tvAddress = (TextView) root.findViewById(R.id.tv_profil_address);
		tvCity = (TextView) root.findViewById(R.id.tv_profil_city);
		tvPhone = (TextView) root.findViewById(R.id.tv_profil_phone);
		tvMobilePhone = (TextView) root
				.findViewById(R.id.tv_profil_mobile_phone);
		tvEmail = (TextView) root.findViewById(R.id.tv_profil_email);
		getProfile();
	}

	private void getProfile() {
		final ProgressDialog progressDialog = new ProgressDialog(getActivity());
		progressDialog.setTitle(getResources().getString(R.string.loading));
		progressDialog.setMessage(getResources().getString(
				R.string.harap_tunggu));

		new AsyncTask<String, String, String>() {

			@Override
			protected void onPreExecute() {
				progressDialog.show();
				super.onPreExecute();
			}

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return Common.getProfileInfo(session.getUserDetails().get(
						SessionManager.KEY_NAME));
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);

				ArrayList<String> keys = new ArrayList<>();
				keys.add(KEY_CODE);
				keys.add(KEY_DEPARTEMENT_CODE);
				keys.add(KEY_SEX);
				keys.add(KEY_BIRTHPLACE);
				keys.add(KEY_RELIGION);
				keys.add(KEY_ADDRESS);
				keys.add(KEY_CITY);
				keys.add(KEY_PHONE);
				keys.add(KEY_MOBILE_PHONE);
				keys.add(KEY_EMAIL);
				keys.add(KEY_BIRTH_DATE);
				keys.add(KEY_EMAIL);
				keys.add(KEY_REGISTERED_NUMBER);
				keys.add(KEY_THESIS_TITLE);
				keys.add(KEY_REG_DATE);
				keys.add(KEY_PHOTO);
				keys.add(KEY_KELAS);
				keys.add(KEY_DEPARTEMENT_NAME);
				keys.add(KEY_NAME);

				ArrayList<String> results = Common.parseJson(result, keys);

				if (results.size() > 0) {
					try {
						tvCode.setText(results.get(0));
						tvDepartemenCode.setText(results.get(1));
						tvGender.setText(results.get(2));
						tvBirthPlace.setText(results.get(3));
						tvAgama.setText(results.get(4));
						tvAddress.setText(results.get(5));
						tvCity.setText(results.get(6));
						tvPhone.setText(results.get(7));
						tvMobilePhone.setText(results.get(8));
						tvEmail.setText(results.get(9));
					} catch (Exception e) {
					}
				}
				progressDialog.dismiss();
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

}

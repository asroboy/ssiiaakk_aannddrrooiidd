package com.uika.siak.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.uika.siak.R;
import com.uika.siak.helper.Common;
import com.uika.siak.helper.SessionManager;
import com.uika.siak.object.Jadwal;

public class JadwalFragment extends Fragment implements OnItemSelectedListener,
		OnClickListener {
	private ListView listView;
	private SessionManager session;
	private final String key_smt = "smt";
	private final String key_code = "code";
	private final String key_course = "course";
	private final String key_lecturer = "lecturer";
	private final String key_class = "class";
	private final String key_on_day = "on_day";
	private final String key_from = "from";
	private final String key_until = "until";
	private final String key_room = "room";

	private Spinner spinnerDay;
	ArrayList<String> categories;
	ArrayList<Jadwal> jadwalMatakuliahs;
	JadwalListAdapter adapter;
	Button btCari;
	EditText etMatakuliah;
	String category;
	FragmentManager fm;

	public JadwalFragment(FragmentManager fm) {
		this.fm = fm;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_jadwal, container,
				false);
		getActivity().getActionBar().setTitle(
				getResources().getString(R.string.jadwal_perkuliahan));
		getActivity().getActionBar().setHomeButtonEnabled(true);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		session = new SessionManager(getActivity());
		listView = (ListView) rootView.findViewById(R.id.list);
		btCari = (Button) rootView.findViewById(R.id.bt_cari);
		btCari.setOnClickListener(this);
		etMatakuliah = (EditText) rootView.findViewById(R.id.et_matakuliah);
		getDataJadwal(session.getUserDetails().get(SessionManager.KEY_NAME));

		spinnerDay = (Spinner) rootView.findViewById(R.id.spinner_day);
		// Creating adapter for spinner
		categories = new ArrayList<String>();
		categories.add("Semua");
		categories.add("Senin");
		categories.add("Selasa");
		categories.add("Rabu");
		categories.add("Kamis");
		categories.add("Jumat");
		categories.add("Sabtu");
		categories.add("Minggu");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, categories);

		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinnerDay.setAdapter(dataAdapter);
		spinnerDay.setOnItemSelectedListener(this);
		return rootView;
	}

	private void getDataJadwal(String username) {
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
				return Common.getJadwal(params[0]);
			}

			protected void onPostExecute(String result) {
				// tv_profil_code.setText(result);
				ArrayList<String> keys = new ArrayList<>();
				keys.add(key_smt);
				keys.add(key_code);
				keys.add(key_course);
				keys.add(key_class);
				keys.add(key_lecturer);
				keys.add(key_on_day);
				keys.add(key_from);
				keys.add(key_until);
				keys.add(key_room);
				ArrayList<ArrayList<String>> results = Common.getJSONArray(
						result, keys);
				jadwalMatakuliahs = (ArrayList<Jadwal>) setupJadwal(results);
				// adapter = new JadwalListAdapter(getActivity()
				// .getApplicationContext(), jadwalMatakuliahs,
				// getFragmentManager());
				// listView.setAdapter(adapter);
				cariMataKuliah(etMatakuliah.getText().toString());
				progressDialog.dismiss();

			};
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, username);

	}

	private List<Jadwal> setupJadwal(ArrayList<ArrayList<String>> results) {
		List<Jadwal> jadwals = new ArrayList<Jadwal>();
		for (ArrayList<String> r : results) {
			try {
				Jadwal jadwal = new Jadwal();
				jadwal.setSmt(r.get(0));
				jadwal.setCode(r.get(1));
				jadwal.setCourse(r.get(2));
				jadwal.setKelas(r.get(3));
				jadwal.setLecturer(r.get(4));
				jadwal.setOn_day(r.get(5));
				jadwal.setFrom(r.get(6));
				jadwal.setUntil(r.get(7));
				jadwal.setRoom(r.get(8));

				jadwals.add(jadwal);
			} catch (Exception e) {
			}
		}

		return jadwals;

	}

	@SuppressLint("DefaultLocale")
	private void cariMataKuliah(String nameKey) {

		ArrayList<Jadwal> newjadwals = new ArrayList<Jadwal>();
		if (jadwalMatakuliahs != null) {

			for (Jadwal jadwal : jadwalMatakuliahs) {
				if (!category.equalsIgnoreCase("semua")) {
					if (jadwal.getCourse().toLowerCase()
							.contains(nameKey.toLowerCase())
							&& jadwal.getOn_day().equals(category)) {
						newjadwals.add(jadwal);
					}
				} else {
					if (jadwal.getCourse().toLowerCase()
							.contains(nameKey.toLowerCase())) {

						newjadwals.add(jadwal);
					}
				}

			}
			adapter = new JadwalListAdapter(getActivity()
					.getApplicationContext(), newjadwals, fm);

			listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		category = categories.get(position);
		cariMataKuliah(etMatakuliah.getText().toString());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_cari:
			String nameKey = etMatakuliah.getText().toString();
			cariMataKuliah(nameKey);
			break;

		default:
			break;
		}
	}

}

package com.uika.siak.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uika.siak.R;
import com.uika.siak.object.Jadwal;

public class DetilJadwalFragment extends Fragment implements
		OnItemSelectedListener, OnClickListener {
	Jadwal jadwal;
	TextView tvNamaMatkul;
	TextView tvLecture;
	TextView tvDay;
	TextView tvFrom;
	TextView tvUntil;
	TextView tvKelas;
	Spinner spinnerJam;
	CheckBox checkBoxIngatkanAku;

	TextView tvRoom;
	TextView tvTidakAdaTugas;
	Button btTambahTugas;

	public DetilJadwalFragment(Jadwal jadwal) {
		this.jadwal = jadwal;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_detil_jadwal,
				container, false);

		getActivity().getActionBar().setTitle(jadwal.getCourse());
		tvNamaMatkul = (TextView) rootView
				.findViewById(R.id.tv_detail_nama_matkul);
		tvLecture = (TextView) rootView.findViewById(R.id.tv_detail_lecture);
		tvDay = (TextView) rootView.findViewById(R.id.tv_detail_day);
		tvFrom = (TextView) rootView.findViewById(R.id.tv_detail_from);
		tvUntil = (TextView) rootView.findViewById(R.id.tv_detail_until);
		tvRoom = (TextView) rootView.findViewById(R.id.tv_detail_ruang);
		tvKelas = (TextView) rootView.findViewById(R.id.tv_detil_kelas);
		btTambahTugas = (Button) rootView.findViewById(R.id.bt_tambah_tugas);
		btTambahTugas.setOnClickListener(this);
		checkBoxIngatkanAku = (CheckBox) rootView
				.findViewById(R.id.check_box_ingatkan_aku);

		spinnerJam = (Spinner) rootView.findViewById(R.id.spinner_jam);
		ArrayList<String> jam = new ArrayList<String>();
		jam.add("1");
		jam.add("2");
		jam.add("3");
		jam.add("4");
		jam.add("5");
		jam.add("6");
		jam.add("7");
		jam.add("8");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, jam);
		// Drop down layout style - list view with radio button
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinnerJam.setAdapter(dataAdapter);
		spinnerJam.setOnItemSelectedListener(this);
		if (checkBoxIngatkanAku.isChecked())
			spinnerJam.setEnabled(false);

		tvNamaMatkul.setText(jadwal.getCourse());
		tvLecture.setText(jadwal.getLecturer());
		tvDay.setText(jadwal.getOn_day());
		tvFrom.setText(jadwal.getFrom());
		tvUntil.setText(jadwal.getUntil());
		tvRoom.setText(jadwal.getRoom());
		tvKelas.setText(jadwal.getKelas());

		return rootView;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_tambah_tugas:
			Toast.makeText(getActivity(), "Tambah Tugas Clicked",
					Toast.LENGTH_SHORT).show();

			break;

		default:
			break;
		}

	}
}

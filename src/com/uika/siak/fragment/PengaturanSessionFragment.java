package com.uika.siak.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.uika.siak.R;
import com.uika.siak.helper.Common;
import com.uika.siak.helper.ConstantValues;

public class PengaturanSessionFragment extends Fragment implements
		OnItemSelectedListener, OnClickListener {
	Spinner spnr;

	public PengaturanSessionFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pengaturan_session,
				container, false);
		getActivity().getActionBar().setTitle(
				getResources().getString(R.string.Pengaturan_Session));
		getActivity().getActionBar().setHomeButtonEnabled(true);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		setupView(rootView);
		return rootView;
	}

	void setupView(View rootView) {
		spnr = (Spinner) rootView.findViewById(R.id.spinner_day);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.simple_spinner_item,
				ConstantValues.session_timeout_options);
		adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		spnr.setAdapter(adapter);
		Common.init(getActivity());
		int itemPosition = Common.getIntPreference(
				ConstantValues.PREFERENCES_KEY_SESSION, 0);
		spnr.setSelection(itemPosition);
		spnr.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		int pos = spnr.getSelectedItemPosition();
		Common.init(getActivity());
		Common.saveIntPreferences(ConstantValues.PREFERENCES_KEY_SESSION, pos);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}

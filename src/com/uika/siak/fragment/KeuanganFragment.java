package com.uika.siak.fragment;

import com.uika.siak.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KeuanganFragment extends Fragment {
	public KeuanganFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_keuangan,
				container, false);
		getActivity().getActionBar().setTitle(
				getResources().getString(R.string.keuangan));
		getActivity().getActionBar().setHomeButtonEnabled(true);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		return rootView;
	}

}

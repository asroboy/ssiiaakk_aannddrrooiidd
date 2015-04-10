package com.uika.siak.fragment;

import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.uika.siak.R;

public class PengaturanFragment extends ListFragment {
	public PengaturanFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pengaturan,
				container, false);
		getActivity().getActionBar().setTitle(
				getResources().getString(R.string.pengaturan));
		getActivity().getActionBar().setHomeButtonEnabled(true);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		ArrayList<String> values = new ArrayList<String>();
		values.add(getResources().getString(R.string.session));
		values.add(getResources().getString(R.string.ubah_password));
		values.add(getResources().getString(R.string.saran));
		ArrayList<String> desc = new ArrayList<String>();
		desc.add(getResources().getString(
				R.string.Menentukan_waktu_session_habis));
		desc.add(getResources().getString(R.string.mengubah_password));
		desc.add(getResources().getString(R.string.kirim_saran_ke_pengembang));
		PengaturanListAdapter adapter1 = new PengaturanListAdapter(
				getActivity(), R.layout.pengaturan_list_item, values, desc);
		ListView lv = (ListView) rootView.findViewById(android.R.id.list);
		lv.setAdapter(adapter1);

		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		FragmentTransaction ft;
		switch (position) {
		case 0:
			ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.container, new PengaturanSessionFragment());
			ft.addToBackStack("PengaturanSessionFragment");
			ft.commit();
			break;
		case 1:
			ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.container, new PengaturanPasswordFragment());
			ft.addToBackStack("PengaturanPasswordFragment");
			ft.commit();
			break;
		case 2:
			ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.container, new PengaturanSaranFragment());
			ft.addToBackStack("PengaturanSaranFragment");
			ft.commit();
			break;
		default:
			break;
		}
		super.onListItemClick(l, v, position, id);
	}
}

package com.uika.siak.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.uika.siak.ImageAdapter;
import com.uika.siak.R;
import com.uika.siak.SiakMenu;
import com.uika.siak.helper.SessionManager;
import com.uika.siak.helper.Util;

@SuppressLint("NewApi")
public class MenuFragment extends Fragment {
	ImageAdapter adapter;
	GridView gridView;

	String key = "FRAGMENT_POSTION";

	public MenuFragment() {

	}

	@Override
	public void onStart() {
		SessionManager session = new SessionManager(getActivity());
		getActivity().getActionBar().setTitle(
				getResources().getString(R.string.app_name)
						+ " - "
						+ session.getUserDetails().get(
								SessionManager.KEY_REAL_NAME));
		getActivity().getActionBar().setHomeButtonEnabled(false);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
		super.onStart();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		gridView = (GridView) rootView.findViewById(R.id.gridView1);
		ArrayList<SiakMenu> menus = new ArrayList<SiakMenu>();
		for (int i = 0; i < Util.menuName.length; i++) {
			SiakMenu menu = new SiakMenu();
			menu.setId(i + 1);
			menu.setIcon(getResources().getDrawable(Util.icons[i]));
			menu.setName(Util.menuName[i]);
			menus.add(menu);
		}

		adapter = new ImageAdapter(getFragmentManager(), getActivity()
				.getApplicationContext(), menus);
		adapter.notifyDataSetChanged();
		((GridView) gridView).setAdapter(adapter);
		return rootView;
	}

}

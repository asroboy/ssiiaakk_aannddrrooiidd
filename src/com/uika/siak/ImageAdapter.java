package com.uika.siak;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uika.siak.fragment.FRSFragment;
import com.uika.siak.fragment.InfoFragment;
import com.uika.siak.fragment.JadwalFragment;
import com.uika.siak.fragment.KeuanganFragment;
import com.uika.siak.fragment.LoginFragment;
import com.uika.siak.fragment.NilaiFragment;
import com.uika.siak.fragment.PengaturanFragment;
import com.uika.siak.fragment.ProfilFragment;
import com.uika.siak.helper.SessionManager;
import com.uika.siak.helper.Util;

public class ImageAdapter extends ArrayAdapter<SiakMenu> {

	private Context context;
	List<SiakMenu> menus;
	FragmentManager fm;
	SessionManager session;

	public ImageAdapter(FragmentManager fm, Context context,
			ArrayList<SiakMenu> menus) {
		super(context, 0, menus);
		this.context = context;
		this.menus = menus;
		this.fm = fm;
	}

	@SuppressLint("InflateParams")
	public View getView(final int position, View convertView, ViewGroup parent) {

		final SiakMenu menu = menus.get(position);
		// final PackageManager pm = getContext().getPackageManager();
		// Drawable icon = info.icon;
		session = new SessionManager(context);
		View gridView;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// gridView = new View(getContext());
			gridView = inflater.inflate(R.layout.grid_item_menu, null);

		} else {
			gridView = (View) convertView;
			convertView.forceLayout();
		}

		gridView.setTag(position);
		TextView textView = (TextView) gridView
				.findViewById(R.id.tv_profil_code);
		ImageView imageView = (ImageView) gridView
				.findViewById(R.id.imageView1);
		textView.setText(menu.getName());
		// String mobile = mobileValues[position];
		imageView.setImageDrawable(menu.getIcon());
		gridView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (menus.get(position).getName().equals(Util.menuKeluar)) {
					session.logoutUser();
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.container, new LoginFragment());
					ft.commit();
				}

				if (menus.get(position).getName().equals(Util.menuPengaturan)) {
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.container, new PengaturanFragment());
					ft.addToBackStack("PngaturanFragment");
					ft.commit();
				}

				if (menus.get(position).getName().equals(Util.menuFRS)) {
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.container, new FRSFragment());
					ft.addToBackStack("FRSFragment");
					ft.commit();
				}
				if (menus.get(position).getName().equals(Util.menuInfo)) {
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.container, new InfoFragment());
					ft.addToBackStack("InfoFragment");
					ft.commit();
				}

				if (menus.get(position).getName().equals(Util.menujadwal)) {
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.container, new JadwalFragment(fm));
					ft.addToBackStack("JadwalFragment");
					ft.commit();
				}

				if (menus.get(position).getName().equals(Util.menuKeuangan)) {
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.container, new KeuanganFragment());
					ft.addToBackStack("KeuanganFragment");
					ft.commit();
				}

				if (menus.get(position).getName().equals(Util.menuProfil)) {
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.container, new ProfilFragment());
					ft.addToBackStack("ProfilFragment");
					ft.commit();
				}

				if (menus.get(position).getName().equals(Util.menuNilai)) {
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.container, new NilaiFragment());
					ft.addToBackStack("NilaiFragment");
					ft.commit();
				}
			}
		});

		return gridView;

	}
}

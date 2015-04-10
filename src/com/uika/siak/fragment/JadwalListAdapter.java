package com.uika.siak.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.provider.DocumentsContract.Root;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uika.siak.R;
import com.uika.siak.object.Jadwal;

public class JadwalListAdapter extends ArrayAdapter<Jadwal> {
	private final Context context;
	private final ArrayList<Jadwal> values;
	FragmentManager fm;
	Jadwal mJadwal;

	public JadwalListAdapter(Context context, ArrayList<Jadwal> values,
			FragmentManager fm) {
		super(context, R.layout.item_jadwal, values);
		this.context = context;
		this.values = values;
		this.fm = fm;
	}

	@SuppressLint({ "ViewHolder", "ResourceAsColor" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.item_jadwal, parent, false);

		LinearLayout lin = (LinearLayout) rowView.findViewById(R.id.lin);

		lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.lin:
					mJadwal = values.get(position);
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.container, new DetilJadwalFragment(mJadwal));
					ft.addToBackStack("DetilJadwalFragment");
					ft.commit();
					break;

				default:
					break;
				}

			}
		});
		TextView tvMataKuliah = (TextView) rowView
				.findViewById(R.id.tv_mata_kuliah);
		TextView tvLecture = (TextView) rowView.findViewById(R.id.tv_lecture);
		TextView tvHari = (TextView) rowView.findViewById(R.id.tv_hari);
		TextView tvTimeFrom = (TextView) rowView
				.findViewById(R.id.tv_time_from);
		TextView tvTimeTo = (TextView) rowView.findViewById(R.id.tv_time_to);
		TextView tvRoom = (TextView) rowView.findViewById(R.id.tv_room);
		TextView tv_kelas = (TextView) rowView.findViewById(R.id.tv_Dosen);
		tvMataKuliah.setText(values.get(position).getCourse());
		tvLecture.setText(values.get(position).getLecturer());
		tvHari.setText(values.get(position).getOn_day());
		tvTimeFrom.setText(values.get(position).getFrom());
		tvTimeTo.setText(values.get(position).getUntil());
		tvRoom.setText(values.get(position).getRoom());
		tv_kelas.setText(values.get(position).getKelas());

		return rowView;
	}

}

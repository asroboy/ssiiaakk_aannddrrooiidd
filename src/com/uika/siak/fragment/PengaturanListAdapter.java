package com.uika.siak.fragment;

import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.uika.siak.R;

public class PengaturanListAdapter extends ArrayAdapter<String> {

	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
	Context context;
	List<String> values;
	List<String> desc;

	public PengaturanListAdapter(Context context, int layout,
			List<String> values, List<String> desc) {
		super(context, layout, values);
		this.context = context;
		this.values = values;
		this.desc = desc;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.pengaturan_list_item, parent,
				false);
		TextView textView2 = (TextView) rowView.findViewById(R.id.tv_hari);
		TextView textView3 = (TextView) rowView.findViewById(R.id.tv_profil_departement_code);
		textView2.setText(values.get(position));
		textView3.setText(desc.get(position));
		return rowView;
	}

}

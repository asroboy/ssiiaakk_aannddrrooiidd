package com.uika.siak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.uika.siak.fragment.LoginFragment;
import com.uika.siak.fragment.MenuFragment;
import com.uika.siak.helper.SIAKService;
import com.uika.siak.helper.SessionManager;
import com.uika.siak.helper.Util;

public class MainActivity extends Activity implements OnClickListener {

	// int position = 0;
	// private String key = "FRAGMENT_POSTION";
	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		session = new SessionManager(getApplicationContext());
		if (savedInstanceState == null) {
			if (session.isLoggedIn()) {
				getFragmentManager().beginTransaction()
						.add(R.id.container, new MenuFragment()).commit();
			} else {
				getFragmentManager().beginTransaction()
						.add(R.id.container, new LoginFragment()).commit();
			}

		}

		Intent service = new Intent(getApplicationContext(), SIAKService.class);
		startService(service);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}

		if (id == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.homeAsUp:

			break;

		default:
			break;
		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		broadcastIntent(false);
		super.onStart();
	}

	@Override
	protected void onStop() {
		broadcastIntent(true);
		super.onStop();
	}

	public void broadcastIntent(boolean value) {
		Intent intent = new Intent();
		intent.putExtra(Util.KEY_SESSION, value);
		intent.setAction("com.uika.SIAK");
		sendBroadcast(intent);
	}
}

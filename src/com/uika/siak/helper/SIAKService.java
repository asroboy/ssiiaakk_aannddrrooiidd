package com.uika.siak.helper;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;

public class SIAKService extends Service {
	private final IBinder mBinder = new LocalBinder();
	SessionManager session;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("SERVICE ON BIND");
		return mBinder;
	}

	public class LocalBinder extends Binder {
		SIAKService getService() {
			return SIAKService.this;
		}
	}

	@Override
	public void onCreate() {
		System.out.println("SERVICE ONCREATE");
		session = new SessionManager(getApplicationContext());
		t.start();

		super.onCreate();
	}

	int countSessionLimitTime() {
		Common.init(getApplicationContext());
		int limitCode = Common.getIntPreference(
				ConstantValues.PREFERENCES_KEY_SESSION, 0);
		int limit = 0;
		switch (limitCode) {
		case 0:
			limit = 10;
			break;
		case 1:
			limit = 15;
			break;
		case 2:
			limit = 30;
			break;
		case 3:
			limit = 60;
			break;
		case 4:
			limit = 100;
			break;
		default:
			break;
		}
		return limit;
	}

	Thread t = new Thread(new Runnable() {
		int second = 0;

		@Override
		public void run() {

			Looper.prepare();
			while (true) {
				try {
					Thread.sleep(1000);
					if (Util.isMinimized) {
						second = second + 1;
						System.out.println("INSIDE THREAD " + second);
						if (second == countSessionLimitTime()
								&& (countSessionLimitTime() != 100)) {
							session.logoutUser();
							Util.isMinimized = false;
						}
					} else {
						second = 0;
						Util.isMinimized = false;
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	});

}

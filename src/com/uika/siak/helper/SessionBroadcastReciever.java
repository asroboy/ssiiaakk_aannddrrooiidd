package com.uika.siak.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SessionBroadcastReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("SessionBroadcastReciever recieve ");
		Bundle extras = intent.getExtras();
		if (extras != null) {
			boolean b = extras.getBoolean(Util.KEY_SESSION);
			Util.isMinimized = b;
			System.out.println("SessionBroadcastReciever recieve "
					+ Util.isMinimized);
		}

	}

}

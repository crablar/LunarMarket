package com.jeffmeyerson.moonstocks.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Provides the time all the time.
 * 
 * @author jeffreymeyerson
 *
 */

public class TimeService extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate(){
		Log.d("TimeService", "Hello TimeService");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.d("TimeService", "Hello OnStart");
		return super.onStartCommand(intent, flags, startId);
	}

}

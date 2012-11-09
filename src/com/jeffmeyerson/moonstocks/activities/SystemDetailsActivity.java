package com.jeffmeyerson.moonstocks.activities;

import com.jeffmeyerson.moonstocks.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SystemDetailsActivity extends Activity {

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_details);
	}
	
    @Override
    public void onBackPressed() {
    	Intent returnIntent = new Intent();
    	setResult(RESULT_OK,returnIntent);     
    	finish();
    }

    public void quitToMarket(View view) {
    	onBackPressed();
    }
	
}

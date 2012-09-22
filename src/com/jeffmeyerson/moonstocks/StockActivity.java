package com.jeffmeyerson.moonstocks;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class StockActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_stock, menu);
        return true;
    }
    
    public void quitToMarket(View view) {
    	
    }
}

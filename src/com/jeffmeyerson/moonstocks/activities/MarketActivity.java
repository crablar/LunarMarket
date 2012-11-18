package com.jeffmeyerson.moonstocks.activities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.Utility;
import com.jeffmeyerson.moonstocks.pojos.Company;
import com.jeffmeyerson.moonstocks.pojos.Player;
import com.jeffmeyerson.moonstocks.pojos.Stock;
import com.jeffmeyerson.moonstocks.services.TimeService;

public class MarketActivity extends MoonActivity {

    private Context context = this;

    // used for persistence?
    private final String fileName = "mainactivity";
    private int size;
    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        //  Begin TimeService
        Intent timeIntent = new Intent(this, TimeService.class);
        startService(timeIntent);
        
        // TODO: move this stuff out to MoonActivity
        mPrefs = getSharedPreferences("moonstocks_prefs", MODE_PRIVATE);
        size = mPrefs.getInt("fileSize", 0);

        // Load company data from XML.
        List<Company> companies = getCompanies();

        // Iterate through the companies on the market and add a row to the
        // table for each one.
        TableLayout marketTable = (TableLayout) findViewById(R.id.market_table);
        for (final Company c : companies) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            // Add a button to take the player to the StockActivity for that
            // company.
            Button companyButton = new Button(this);
            companyButton.setText(c.getTicker());
            companyButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(context, StockActivity.class);
                    intent.putExtra("EXTRA_TICKER_ID", c.getTicker());
                    intent.putExtra("player", Utility.serialize(player));
                    startActivityForResult(intent, 1);
                }
            });
            row.addView(companyButton);

            TextView stockPrice = new TextView(this);
            stockPrice.setText("$" + c.getPrice());
            stockPrice.setGravity(Gravity.CENTER);
            stockPrice.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            row.addView(stockPrice);

            TextView sharesOwned = new TextView(this);
            sharesOwned.setText(String.valueOf(player.getSharesOwned(c
                    .getTicker())));
            sharesOwned.setGravity(Gravity.CENTER);
            sharesOwned.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            row.addView(sharesOwned);

            marketTable.addView(row, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putByteArray("player", Utility.serialize(player));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        player = (Player) Utility.deserialize(savedInstanceState.getByteArray("player"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        update();
    }

    @Override
    protected void onStop() {
        super.onStop();
        update();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                updateTable(data);
                update();
            } else if (resultCode == RESULT_CANCELED) {
                // Write your code on no result return
            }
        }  // onActivityResult
    }

    // TODO: what exactly does this function do and when is it getting called?
    private void update() {
        Log.d("Running", "update()");

        SharedPreferences.Editor ed = mPrefs.edit();

        try {
            Log.d("fileError", "writing file");
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(Utility.serialize(player));
            size = Utility.serialize(player).length;
            Log.d("fileError", "buffer size in write: " + size);
            ed.putInt("fileSize", size);
            ed.commit();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.d("fileError", "writing file not found");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d("fileError", "writing IO exception");
            e.printStackTrace();
        }

    }

    private void updateTable(Intent data) {
        TableLayout marketTable = (TableLayout) findViewById(R.id.market_table);

        player = (Player) Utility.deserialize(data.getByteArrayExtra("player"));

        InputStream inputStream = null;

        for (int i = 1; i <= getCompanies().size(); i++) {
            TableRow row = (TableRow) marketTable.getChildAt(i); // gets the row
            String company = (String) ((Button) row.getChildAt(0)).getText();

            // TODO: Make programmatic
            // Put the raw text file into an InputStream
            if (company.equals("EVIL")) {
                inputStream = this.getResources().openRawResource(R.raw.evil_vals);
            } else if (company.equals("BDST")) {
                inputStream = this.getResources().openRawResource(R.raw.bdst_vals);
            } else if (company.equals("WMC")) {
                inputStream = this.getResources().openRawResource(R.raw.wmc_vals);
            }

            Stock stock = new Stock(inputStream);
            ((TextView) row.getChildAt(1)).setText("$"
                    + String.valueOf(stock.getPrice(MoonActivity.globalTime)));
            ((TextView) row.getChildAt(2)).setText(String.valueOf(player
                    .getSharesOwned(company)));
        }

    }


}
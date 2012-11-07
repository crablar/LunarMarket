package com.jeffmeyerson.moonstocks.activities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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
import com.jeffmeyerson.moonstocks.pojos.Company;
import com.jeffmeyerson.moonstocks.pojos.Player;

public class MainActivity extends Activity {

    private Context context = this;

    // The amount of money the player has upon beginning a new game
    private final int STARTING_MONEY = 5000;

    // Plays launchpad music
    private MediaPlayer mp;

    // The object representing the person playing the game
    private Player player;

    // Navigation buttons
    // TODO: switch to using ActionBar for navigation between activities
    private Button newsStandButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Load company data from XML.
        List<Company> companies = getCompanies();

        // Start playing music
        mp = MediaPlayer.create(this, R.raw.main_menu);
        mp.setLooping(true);

        // Initialize news button
        newsStandButton = (Button) findViewById(R.id.newsStandButton);

        // Check for a persisted player
        Bundle extras = this.getIntent().getExtras();
        if (extras != null && extras.containsKey("EXTRA_PLAYER")) {
            extras.get("EXTRA_PLAYER");
        } else {
        	Log.d("onCreate", "creating new player");
            player = new Player();
            player.setBalance(STARTING_MONEY);
            player.setName("Jeff");
            //this.getIntent().putExtra("player", serializeObject(player));
        }

        newsStandButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsStandActivity.class);
                startActivity(intent);
            }
        });

        // Iterate through the companies on the market and add a row to the table for each one.
        TableLayout marketTable = (TableLayout) findViewById(R.id.market_table);
        for (final Company c : companies) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            // Add a button to take the player to the StockActivity for that company.
            Button companyButton = new Button(this);
            companyButton.setText(c.getTicker());
            companyButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(context, StockActivity.class);
                    intent.putExtra("EXTRA_TICKER_ID", c.getTicker());
                    intent.putExtra("player", serializeObject(player));
                    startActivity(intent);
                }
            });
            row.addView(companyButton);

            TextView stockPrice = new TextView(this);
            stockPrice.setText("$" + c.getPrice());
            stockPrice.setGravity(Gravity.CENTER);
            stockPrice.setTextAppearance(this, android.R.style.TextAppearance_Large);
            row.addView(stockPrice);

            TextView sharesOwned = new TextView(this);
            sharesOwned.setText(String.valueOf(player.getSharesOwned(c.getTicker())));
            sharesOwned.setGravity(Gravity.CENTER);
            sharesOwned.setTextAppearance(this, android.R.style.TextAppearance_Large);
            row.addView(sharesOwned);

            marketTable.addView(row, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }

    }

    /**
     * Create a list of CompanyModels as defined by the companies.xml file.
     * 
     * @return
     */
    private List<Company> getCompanies() {
        List<Company> result = new LinkedList<Company>();
        String[] companyStrings = getResources().getStringArray(R.array.companies);

        for (String companyString : companyStrings) {
            String[] companyArr = companyString.split(" ");
            String ticker = companyArr[0];
            String name = companyArr[1];
            Company company = new Company(ticker, name);
            result.add(company);
        }
        return result;
    }
    
    public static byte[] serializeObject(Object o) { 
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
     
        try { 
          ObjectOutput out = new ObjectOutputStream(bos); 
          out.writeObject(o); 
          out.close(); 
     
          // Get the bytes of the serialized object 
          byte[] buf = bos.toByteArray(); 
     
          return buf; 
        } catch(IOException ioe) { 
          Log.e("serializeObject", "error", ioe); 
     
          return null; 
        } 
      }
    
    public static Object deserializeObject(byte[] b) { 
        try { 
          ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b)); 
          Object object = in.readObject(); 
          in.close(); 
     
          return object; 
        } catch(ClassNotFoundException cnfe) { 
          Log.e("deserializeObject", "class not found error", cnfe); 
     
          return null; 
        } catch(IOException ioe) { 
          Log.e("deserializeObject", "io error", ioe); 
     
          return null; 
        } 
      }
//    
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//    	super.onSaveInstanceState(outState);
//    
//    	outState.putByteArray("companies", serializeObject(getCompanies()));
//    	outState.putString("player", player.getName());
//    	
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.release();
    }
}
package com.jeffmeyerson.moonstocks.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeffmeyerson.moonstocks.R;
import com.jeffmeyerson.moonstocks.Utility;
import com.jeffmeyerson.moonstocks.pojos.Player;
import com.jeffmeyerson.moonstocks.pojos.Player.AvatarType;
import com.jeffmeyerson.moonstocks.pojos.Protocol;
import com.jeffmeyerson.moonstocks.views.TickerView;

public class SystemDetailsActivity extends MoonActivity {
	Context context = this;

	ImageView img;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_details);

		assignAnimationAndBegin();

		// Set up the scrolling stock ticker at the top.
		TickerView tickerView = (TickerView) findViewById(R.id.stock_scroller);

		tickerView.scroll();

		play(R.raw.evil);

		// Get the data from the Intent
		// TODO: Move this to MoonActivity
		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			if (extras.containsKey("player")) {
				player = (Player) Utility.deserialize(extras
						.getByteArray("player"));
			} else {
				Log.d("SystemDetailsActivity.onCreate",
						"intent extras lacked player field");
			}
		}

		if (player == null) {
			player = new Player();
		}

		// Fill TextViews with information
		TextView playerNameView = (TextView) findViewById(R.id.playerText);
		playerNameView.setText("TradeBot Class xF4D2: " + player.getName());
		TextView balanceView = (TextView) findViewById(R.id.balanceText);
		balanceView.setText("Balance: $" + +player.getBalance());
		TextView netProfitsView = (TextView) findViewById(R.id.netProfitText);
		double netProfits = player.getBalance() - MarketActivity.STARTING_MONEY;
		netProfitsView.setText("Net profits: $" + netProfits);
		TextView protocolView = (TextView) findViewById(R.id.protocolText);
		protocolView.setText(Protocol.getProtocolVerbose());

	}

	private void assignAnimationAndBegin() {
		// Load the ImageView that will host the animation and
		// set its background to our AnimationDrawable XML resource.
		img = (ImageView) findViewById(R.id.avatarView);
		AvatarType avatarDescription = player.avatarType;

		if (avatarDescription.equals(AvatarType.NO_EYES)) {
			img.setBackgroundResource(R.drawable.avatar_no_eyes_animation);
		}
		if (avatarDescription.equals(AvatarType.ONE_EYE)) {
			img.setBackgroundResource(R.drawable.avatar_one_eye_animation);

		}
		if (avatarDescription.equals(AvatarType.NORMAL)) {
			img.setBackgroundResource(R.drawable.avatar_basic_animation);
		}

		if (avatarDescription.equals(AvatarType.GOLD_TEETH)) {
			img.setBackgroundResource(R.drawable.avatar_gold_teeth_animation);
		}

		if (avatarDescription.equals(AvatarType.SHADES)) {
			img.setBackgroundResource(R.drawable.avatar_shades_and_nose_animation);
		}
		// Get the background, which has been compiled to an AnimationDrawable
		// object.
		AnimationDrawable frameAnimation = (AnimationDrawable) img
				.getBackground();
		// Start the animation (looped playback by default).
		frameAnimation.start();
	}

	/*
	 * Pawn something
	 */
	public void pawn(View view) {
		int cash = player.pawn();
		if (cash == 0) {
			Toast.makeText(context, "You don't own anything worth pawning.",
					Toast.LENGTH_SHORT).show();
			return;
		}
		player.setBalance(player.getBalance() + cash);
		if (player.updateLevel()) {
			player.demoteAvatar();
			Toast.makeText(context, "You advanced a level!", Toast.LENGTH_SHORT)
					.show();
		}
		assignAnimationAndBegin();
		TextView balanceView = (TextView) findViewById(R.id.balanceText);
		balanceView.setText("Balance: $" + +player.getBalance());
	}

	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	public void quitToMarket(View view) {
		onBackPressed();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		player = (Player) Utility.deserialize(savedInstanceState
				.getByteArray("player"));

	}
}
package com.jeffmeyerson.moonstocks.pojos;

import java.util.List;

// This is sort of an experiment right now. It doesn't actually do anything yet. 
public class Luna {

    private List<Company> companies;
    private Player player;
    private int time;

    private int currentSong;

    /**
     * Called every tick by MoonActivity. Updates stock prices.
     */
    public void update() {
        time++;
        for (Company c : companies) {
            // TODO: need to get price changes from a StockFlux or something like that.
            //c.setPrice(c.getPrice() + delta);
        }
    }
}

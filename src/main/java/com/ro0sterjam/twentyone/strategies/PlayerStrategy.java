package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.actors.Watcher;
import com.ro0sterjam.twentyone.events.GlobalEvent;
import com.ro0sterjam.twentyone.events.PlayerEvent;
import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.DealerHand;
import com.ro0sterjam.twentyone.table.PlayerHand;

/**
 * Created by kenwang on 2017-08-09.
 */
public abstract class PlayerStrategy implements Watcher {

    public abstract Action nextAction(DealerHand dealerHand, PlayerHand hand, double cash);

    public abstract double nextBet(double minBet, double cash);

    public abstract boolean isPlaying(double minBet, double cash);

    @Override
    public void onGlobalEvent(GlobalEvent event) {
        // No-op
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        // No-op
    }
}

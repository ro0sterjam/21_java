package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.DealerHand;
import com.ro0sterjam.twentyone.table.PlayerHand;

/**
 * Created by kenwang on 2017-08-09.
 */
public class SimplePlayerStrategy extends PlayerStrategy {

    public static final double DEFAULT_BET = 25;

    @Override
    public Action nextAction(DealerHand dealerHand, PlayerHand hand, double cash) {
        // Always stay on hard 17 or soft 19
        if (!hand.getHigherValue().isPresent() && hand.getValue() >= 17 || hand.getBestHand() >= 19) {
            return Action.STAY;
        }

//        switch (dealersUpCard.getValue()) {
//            case ACE:
//
//        }
        return Action.HIT;
    }

    @Override
    public double nextBet(double minBet, double cash) {
        return Math.min(cash, minBet);
    }

    @Override
    public boolean isPlaying(double minBet, double cash) {
        return cash >= minBet && cash < 400;
    }

}

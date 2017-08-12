package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Card;
import com.ro0sterjam.twentyone.table.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kenwang on 2017-08-12.
 */
public class RandomPlayerStrategy extends PlayerStrategy {

    private static final Random rand = new Random();

    @Override
    public boolean takeInsurance(Hand hand, double bet, double cash) {
        return rand.nextBoolean() && cash / 2 >= bet;
    }

    @Override
    public Action nextAction(Card upcard, Hand hand, double bet, double cash) {
        List<Action> availableOptions = new ArrayList<>();
        availableOptions.add(Action.HIT);
        availableOptions.add(Action.STAND);
        if (hand.isStartingHand() && cash >= bet) {
            availableOptions.add(Action.DOUBLE);
            if (hand.isPair()) {
                availableOptions.add(Action.SPLIT);
            }
        }
        return availableOptions.get(rand.nextInt(availableOptions.size()));
    }

    @Override
    public double nextBet(double minBet, double cash) {
        return (int)(cash - minBet) > 0 ? rand.nextInt((int) (cash - minBet)) + minBet : minBet;
    }

    @Override
    public boolean isPlaying(double minBet, double cash) {
        return cash >= minBet && cash < 1000;
    }
}

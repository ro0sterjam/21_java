package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Hand;

/**
 * Created by kenwang on 2017-08-09.
 */
public class SimplePlayerStrategy implements PlayerStrategy {

    public static final PlayerStrategy INSTANCE = new SimplePlayerStrategy();
    public static final double DEFAULT_BET = 25;

    private SimplePlayerStrategy() {

    }

    @Override
    public Action nextAction(Hand hand, double cash) {
        return SimpleDealerStrategy.INSTANCE.nextAction(hand);
    }

    @Override
    public double bet(double cash) {
        return Math.min(cash, DEFAULT_BET);
    }

}

package com.ro0sterjam.twentyone.rules;

import lombok.Getter;

/**
 * Created by kenwang on 2017-08-11.
 */
public class SimpleGameRules implements GameRules {

    @Getter private final double minBet = 25;
    @Getter private final int numDecks = 7;
    @Getter private final DealerRules dealerRules = new SimpleDealerRules();
    @Getter private final double blackjackPayout = 1.5;
    @Getter private final double insurancePayout = 2;

}

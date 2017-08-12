package com.ro0sterjam.twentyone.rules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by kenwang on 2017-08-11.
 */
@AllArgsConstructor
@NoArgsConstructor
public class SimpleGameRules implements GameRules {

    private static final double DEFAULT_MIN_BET = 25;
    private static final int DEFAULT_NUM_DECKS = 7;
    private static final DealerRules DEFAULT_DEALER_RULES = new SimpleDealerRules();

    @Getter private double minBet = DEFAULT_MIN_BET;
    @Getter private int numDecks = DEFAULT_NUM_DECKS;
    @Getter private DealerRules dealerRules = DEFAULT_DEALER_RULES;

}

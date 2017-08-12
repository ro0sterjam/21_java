package com.ro0sterjam.twentyone.rules;

/**
 * Created by kenwang on 2017-08-11.
 */
public interface GameRules {

    double getMinBet();

    int getNumDecks();

    DealerRules getDealerRules();

    double getBlackjackPayout();

    double getInsurancePayout();

}

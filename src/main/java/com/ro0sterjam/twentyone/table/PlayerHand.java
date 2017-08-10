package com.ro0sterjam.twentyone.table;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by kenwang on 2017-08-09.
 */
@ToString(callSuper = true)
public class PlayerHand extends Hand {

    @Getter private double bet;

    public PlayerHand(double bet) {
        super();
        this.bet = bet;
    }
}

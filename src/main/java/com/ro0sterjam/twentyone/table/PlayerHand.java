package com.ro0sterjam.twentyone.table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by kenwang on 2017-08-09.
 */
@ToString(callSuper = true)
public class PlayerHand extends Hand {

    @Getter @Setter private double bet;

    public PlayerHand(double bet) {
        super();
        this.bet = bet;
    }

    @Override
    public int compareTo(Hand other) {
        return isBusted()? -1 : super.compareTo(other);
    }
}

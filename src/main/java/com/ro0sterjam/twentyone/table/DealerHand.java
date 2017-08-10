package com.ro0sterjam.twentyone.table;

/**
 * Created by kenwang on 2017-08-09.
 */
public class DealerHand extends Hand {

    public Card getUpCard() {
        return getCards().get(1);
    }

    @Override
    public String toString() {
        return String.valueOf(getCards());
    }
}

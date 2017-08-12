package com.ro0sterjam.twentyone.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * Created by kenwang on 2017-08-03.
 */
@ToString
@RequiredArgsConstructor
public class Card {

    @Delegate @Getter private final Rank rank;
    @Getter private final Suite suite;

    @Override
    public String toString() {
        return this.rank.toString();
    }
}

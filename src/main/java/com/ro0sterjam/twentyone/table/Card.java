package com.ro0sterjam.twentyone.table;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by kenwang on 2017-08-03.
 */
@RequiredArgsConstructor
@AllArgsConstructor
public class Card {

    private final CardValue value;
    private boolean revealed;

    public int getValue() {
        return this.value.getValue();
    }

    public Optional<Integer> getHigherValue() {
        return this.value.getHigherValue();
    }

    public void reveal() {
        this.revealed = true;
    }

    @Override
    public String toString() {
        return this.revealed ? value.toString() : "?";
    }

    public static List<Card> fullDeck() {
        return Arrays.stream(CardValue.values())
                .map(cardValue -> Collections.nCopies(4, cardValue))
                .flatMap(Collection::stream)
                .map(Card::new)
                .collect(Collectors.toList());
    }
}
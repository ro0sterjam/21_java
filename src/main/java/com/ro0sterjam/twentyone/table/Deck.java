package com.ro0sterjam.twentyone.table;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by kenwang on 2017-08-11.
 */
public class Deck {

    private final List<Card> cards;

    public Deck(int decks) {
        this.cards = fullDecks(decks);
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public Card deal() {
        return this.cards.remove(this.cards.size() - 1);
    }

    public int size() {
        return this.cards.size();
    }

    public static Deck empty() {
        return new Deck(0);
    }

    private static List<Card> fullDeck() {
        return Arrays.stream(Rank.values())
                .map(rank -> Arrays.stream(Suite.values())
                        .map(suite -> new Card(rank, suite))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static List<Card> fullDecks(int decks) {
        return IntStream.range(0, decks)
                .mapToObj(i -> fullDeck())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}

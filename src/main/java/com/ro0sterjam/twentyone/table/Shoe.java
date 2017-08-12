package com.ro0sterjam.twentyone.table;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class Shoe {

	private Deck deck = Deck.empty();

	public void load(Deck deck) {
		this.deck = deck;
	}

	public Card deal() {
		return this.deck.deal();
	}

	public boolean isDone() {
		return this.deck.size() <= 80;
	}
}
package com.ro0sterjam.twentyone.table;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ToString
public class Hand implements Comparable<Hand> {

	@Getter private final List<Card> cards;
	@Getter private int value;
	@Getter private Optional<Integer> higherValue;

	public Hand() {
		this.cards = new ArrayList<>();
		this.value = 0;
		this.higherValue = Optional.empty();
	}

	public void add(@NonNull Card card) {
		this.cards.add(card);
		this.value += card.getValue().getValue();
		if (this.higherValue.isPresent()) {
			this.higherValue = Optional.of(this.higherValue.get() + card.getValue().getValue());
		} else if (card.getValue().getHigherValue().isPresent()) {
			this.higherValue = Optional.of(this.value - card.getValue().getValue() + card.getValue().getHigherValue().get());
		}
	}

	public int getBestHand() {
		return this.higherValue.isPresent()? (this.getHigherValue().get() <= 21? this.higherValue.get() : this.value) : this.value;
	}

	public boolean isBusted() {
		return this.value > 21;
	}

	public int size() {
		return this.cards.size();
	}

	@Override
	public int compareTo(Hand other) {
		return isBusted()? (other.isBusted()? 0 : -1) : (other.isBusted()? 1 : (getBestHand() > other.getBestHand()? 1 : (getBestHand() < other.getBestHand()? -1 : 0)));
	}
}
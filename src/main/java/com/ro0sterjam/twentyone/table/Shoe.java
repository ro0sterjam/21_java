package com.ro0sterjam.twentyone.table;

import com.ro0sterjam.twentyone.actors.Actor;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ToString
public class Shoe {

	private final List<Card> cards;

	public Shoe() {
		this(7);
	}

	public Shoe(int decks) {
		this.cards = IntStream.range(0, decks)
				.mapToObj(i -> Card.fullDeck())
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	public void shuffle() {
		Collections.shuffle(this.cards);
	}

	public void deal(Actor actor) {
		actor.take(this.cards.remove(this.cards.size() - 1));
	}

	public boolean isDone() {
		return this.cards.size() <= 80;
	}
}
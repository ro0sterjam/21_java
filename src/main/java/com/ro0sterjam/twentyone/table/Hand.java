package com.ro0sterjam.twentyone.table;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Hand implements Comparable<Hand> {

	@Getter private final ImmutableList<Card> cards;
	@Getter(lazy = true) private final int total = this.cards.stream().mapToInt(Card::getValue).sum();
	@Getter(lazy = true) private final boolean soft = this.cards.stream().anyMatch(card -> card.getRank() == Rank.ACE) && getTotal() <= 11;
	@Getter(lazy = true) private final Optional<Integer> softTotal = isSoft()? Optional.of(getTotal() + 10) : Optional.empty();
	@Getter(lazy = true) private final int bestTotal = isBusted()? -1 : getSoftTotal().orElse(getTotal());
	@Getter(lazy = true) private final boolean busted = getTotal() > 21;
	@Getter(lazy = true) private final boolean startingHand = getCards().size() == 2;
	@Getter(lazy = true) private final boolean pair = isStartingHand() && getCards().get(0).getValue() == getCards().get(1).getValue();

	public static Hand empty() {
		return new Hand(ImmutableList.of());
	}

	public Hand add(@Nonnull Card card) {
		return new Hand(ImmutableList.<Card> builder().addAll(this.cards).add(card).build());
	}

	@Override
	public int compareTo(@Nonnull Hand other) {
		return Integer.compare(getBestTotal(), other.getBestTotal());
	}

	@Override
	public String toString() {
		return cards.toString();
	}
}
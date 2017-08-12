package com.ro0sterjam.twentyone.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Rank {

	ACE(1, "A"),
	TWO(2, "2"),
	THREE(3, "3"),
	FOUR(4, "4"),
	FIVE(5, "5"),
	SIX(6, "6"),
	SEVEN(7, "7"),
	EIGHT(8, "8"),
	NINE(9, "9"),
	TEN(10, "X"),
	JACK(10, "J"),
	QUEEN(10, "Q"),
	KING(10, "K");

	@Getter private final int value;
	@Getter private final String symbol;

	@Override
	public String toString() {
		return this.symbol;
	}
}
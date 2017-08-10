package com.ro0sterjam.twentyone.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public enum CardValue {

	TWO(2, Optional.empty(), "2"),
	THREE(3, Optional.empty(), "3"),
	FOUR(4, Optional.empty(), "4"),
	FIVE(5, Optional.empty(), "5"),
	SIX(6, Optional.empty(), "6"),
	SEVEN(7, Optional.empty(), "7"),
	EIGHT(8, Optional.empty(), "8"),
	NINE(9, Optional.empty(), "9"),
	TEN(10, Optional.empty(), "X"),
	JACK(10, Optional.empty(), "J"),
	QUEEN(10, Optional.empty(), "Q"),
	KING(10, Optional.empty(), "K"),
	ACE(1, Optional.of(11), "A");

	@Getter private final int value;
	@Getter private final Optional<Integer> higherValue;
	private final String symbol;

	@Override
	public String toString() {
		return this.symbol;
	}
}
package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Hand;

public class SimpleDealerStrategy implements DealerStrategy {

	public static final DealerStrategy INSTANCE = new SimpleDealerStrategy();

	private SimpleDealerStrategy() {

	}

	public Action nextAction(Hand hand) {
		if (hand.getValue() >= 17 || hand.getHigherValue().isPresent() && hand.getHigherValue().get() > 17 && hand.getHigherValue().get() <= 21) {
			return Action.STAY;
		} else {
			return Action.HIT;
		}
	}

}
package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Hand;

public class SimpleDealerStrategy implements DealerStrategy {

	public Action nextAction(Hand hand) {
		if (hand.getValue() >= 17 || hand.getHigherValue().isPresent() && hand.getHigherValue().get() > 17 && hand.getHigherValue().get() <= 21) {
			return Action.STAND;
		} else {
			return Action.HIT;
		}
	}

}
package com.ro0sterjam.twentyone.rules;

import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Hand;

public class SimpleDealerRules implements DealerRules {

	@Override
	public Action nextAction(Hand hand) {
		return hand.getBestTotal() >= 18 || hand.getTotal() == 17? Action.STAND : Action.HIT;
	}

}
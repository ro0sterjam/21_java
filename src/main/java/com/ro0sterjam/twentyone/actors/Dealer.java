package com.ro0sterjam.twentyone.actors;

import com.ro0sterjam.twentyone.rules.DealerRules;
import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Card;
import com.ro0sterjam.twentyone.table.Hand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Dealer implements Actor, HasHand {

	@Getter private Hand hand = Hand.empty();
	private final DealerRules rules;

	public void clearHand() {
		this.hand = Hand.empty();
	}

	public Action nextAction() {
		return this.rules.nextAction(this.hand);
	}

	public Card getUpcard() {
		return this.hand.getCards().get(1);
	}

	public Card getHoleCard() {
		return this.hand.getCards().get(0);
	}

	@Override
	public boolean take(Card card) {
		this.hand = hand.add(card);
		return !this.hand.isBusted();
	}
}
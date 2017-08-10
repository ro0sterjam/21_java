package com.ro0sterjam.twentyone.actors;

import com.ro0sterjam.twentyone.strategies.DealerStrategy;
import com.ro0sterjam.twentyone.strategies.SimpleDealerStrategy;
import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.DealerHand;
import lombok.Getter;
import lombok.ToString;

@ToString(exclude = "strategy")
public class Dealer implements Actor {

	@Getter private DealerHand hand;
	private DealerStrategy strategy;

	public Dealer() {
		this(new SimpleDealerStrategy());
	}

	public Dealer(DealerStrategy strategy) {
		this.hand = new DealerHand();
		this.strategy = strategy;
	}

	public void revealDownCard() {
		this.hand.getCards().get(0).reveal();
	}

	public void clearHand() {
		this.hand = new DealerHand();
	}

	public Action nextAction() {
		return this.strategy.nextAction(this.hand);
	}

	public boolean isBusted() {
		return this.hand.isBusted();
	}
}
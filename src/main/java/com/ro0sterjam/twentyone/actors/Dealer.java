package com.ro0sterjam.twentyone.actors;

import com.ro0sterjam.twentyone.strategies.DealerStrategy;
import com.ro0sterjam.twentyone.strategies.SimpleDealerStrategy;
import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Card;
import com.ro0sterjam.twentyone.table.Hand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true, exclude = "strategy")
public class Dealer extends Actor {

	@Getter @Setter private Hand hand;

	private static final DealerStrategy DEFAULT_STRATEGY = SimpleDealerStrategy.INSTANCE;

	private DealerStrategy strategy;

	public Dealer() {
		this(DEFAULT_STRATEGY);
	}

	public Dealer(DealerStrategy strategy) {
		super();
		this.strategy = strategy;
	}

	@Override
	public Action nextAction() {
		return strategy.nextAction(this.hand);
	}

	public void revealDownCard() {
		this.hand.getCards().get(0).reveal();
	}

	@Override
	public void take(Card card) {
		if (this.hand == null) {
			this.hand = new Hand();
		}
		if (this.hand.size() > 0) {
			card.reveal();
		}
		super.take(card);
	}

	@Override
	public void clearHand() {
		this.hand = null;
	}
}
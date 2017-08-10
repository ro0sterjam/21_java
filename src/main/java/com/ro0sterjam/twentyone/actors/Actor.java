package com.ro0sterjam.twentyone.actors;

import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Card;
import com.ro0sterjam.twentyone.table.Hand;
import lombok.ToString;

@ToString()
public abstract class Actor {

	public abstract Hand getHand();

	public abstract Action nextAction();

	public boolean isBusted() {
		return getHand().isBusted();
	}

	public void take(Card card) {
		getHand().add(card);
	}

	public abstract void clearHand();
}
package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.events.CardRevealedEvent;
import com.ro0sterjam.twentyone.events.GlobalEvent;
import com.ro0sterjam.twentyone.events.LoadShoeEvent;
import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Card;
import com.ro0sterjam.twentyone.table.Hand;

/**
 * Created by kenwang on 2017-08-12.
 */
public class SimpleCardCountingStrategy extends PlayerStrategy {

    private PlayerStrategy simplePlayerStrategy = new SimplePlayerStrategy();
    private int count;

    @Override
    public boolean takeInsurance(Hand hand, double bet, double cash) {
        return this.simplePlayerStrategy.takeInsurance(hand, bet, cash);
    }

    @Override
    public Action nextAction(Card upcard, Hand hand, double bet, double cash) {
        return this.simplePlayerStrategy.nextAction(upcard, hand, bet, cash);
    }

    @Override
    public double nextBet(double minBet, double cash) {
        return Math.min(cash, this.count > 10? this.count > 15 ? minBet * 3 : minBet * 2 : minBet);
    }

    @Override
    public boolean isPlaying(double minBet, double cash) {
        return cash >= minBet && cash < 1000;
    }

    @Override
    public void onGlobalEvent(GlobalEvent event) {
        if (event instanceof CardRevealedEvent) {
            Card card = ((CardRevealedEvent) event).getCard();
            if (card.getRank().getValue() == 10 || card.getRank().getValue() == 1) {
                this.count--;
            } else if (card.getRank().getValue() >= 2 && card.getRank().getValue() <= 6){
                this.count++;
            }
        } else if (event instanceof LoadShoeEvent) {
            this.count = 0;
        }
    }
}

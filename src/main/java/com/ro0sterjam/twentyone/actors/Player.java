package com.ro0sterjam.twentyone.actors;

import com.ro0sterjam.twentyone.events.GlobalEvent;
import com.ro0sterjam.twentyone.events.PlayerEvent;
import com.ro0sterjam.twentyone.exceptions.NotEnoughCashException;
import com.ro0sterjam.twentyone.strategies.PlayerStrategy;
import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Card;
import com.ro0sterjam.twentyone.table.Hand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Created by kenwang on 2017-08-03.
 */
@ToString(exclude = "strategy")
@RequiredArgsConstructor
public class Player implements Actor, Watcher {

    private static final double DEFAULT_STARTER_CASH = 500;

    private final PlayerStrategy strategy;
    @Getter private double cash = DEFAULT_STARTER_CASH;
    @Getter private double maxCash = this.cash;

    public Action nextAction(Card upcard, Hand hand, double bet) {
        return this.strategy.nextAction(upcard, hand, bet, this.cash);
    }

    public boolean takeInsurance(Hand hand, double bet) {
        return this.strategy.takeInsurance(hand, bet, this.cash);
    }

    public double nextBet(double minBet) {
        return this.strategy.nextBet(minBet, this.cash);
    }

    public double getCash(double cash) {
        if (this.cash < cash) {
            throw new NotEnoughCashException();
        }
        this.cash -= cash;
        return cash;
    }

    public boolean isPlaying(double minBet) {
        return this.strategy.isPlaying(minBet, this.cash);
    }

    public void addCash(double cash) {
        this.cash += cash;
        if (this.maxCash < this.cash) {
            this.maxCash = this.cash;
        }
    }

    @Override
    public void onGlobalEvent(GlobalEvent event) {
        this.strategy.onGlobalEvent(event);
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        this.strategy.onPlayerEvent(event);
    }

}

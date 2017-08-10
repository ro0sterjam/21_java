package com.ro0sterjam.twentyone.actors;

import com.ro0sterjam.twentyone.events.GlobalEvent;
import com.ro0sterjam.twentyone.events.PlayerEvent;
import com.ro0sterjam.twentyone.exceptions.NotEnoughCashException;
import com.ro0sterjam.twentyone.strategies.PlayerStrategy;
import com.ro0sterjam.twentyone.strategies.SimplePlayerStrategy;
import com.ro0sterjam.twentyone.table.PlayerHand;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenwang on 2017-08-03.
 */
@ToString(exclude = "strategy")
public class Player implements Actor, Watcher {

    private static final double DEFAULT_STARTER_CASH = 200;

    @Getter private final List<PlayerHand> hands;
    @Getter private PlayerStrategy strategy;
    @Getter private double cash;

    public Player() {
        this(new SimplePlayerStrategy());
    }

    public Player(PlayerStrategy strategy) {
        this(strategy, DEFAULT_STARTER_CASH);
    }

    public Player(PlayerStrategy strategy, double starterCash) {
        this.hands = new ArrayList<>();
        this.strategy = strategy;
        this.cash = starterCash;
    }

    public double bet(double bet) {
        if (this.cash < bet) {
            throw new NotEnoughCashException();
        }
        this.cash -= bet;
        return bet;
    }

    public boolean isPlaying(double minBet) {
        return this.strategy.isPlaying(minBet, this.cash);
    }

    public void addCash(double cash) {
        this.cash += cash;
    }

    @Override
    public void onGlobalEvent(GlobalEvent event) {
        this.strategy.onGlobalEvent(event);
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        this.strategy.onPlayerEvent(event);
    }

    public void clearHands() {
        this.hands.clear();
    }

}

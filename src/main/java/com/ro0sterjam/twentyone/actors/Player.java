package com.ro0sterjam.twentyone.actors;

import com.ro0sterjam.twentyone.strategies.PlayerStrategy;
import com.ro0sterjam.twentyone.strategies.SimplePlayerStrategy;
import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Card;
import com.ro0sterjam.twentyone.table.PlayerHand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by kenwang on 2017-08-03.
 */
@ToString(callSuper = true, exclude = "strategy")
public class Player extends Actor {

    @Getter @Setter private PlayerHand hand;

    private static final PlayerStrategy DEFAULT_STRATEGY = SimplePlayerStrategy.INSTANCE;
    private static final double DEFAULT_STARTER_CASH = 200;

    private PlayerStrategy strategy;
    private double cash;

    public Player() {
        this(DEFAULT_STRATEGY);
    }

    public Player(PlayerStrategy strategy) {
        this(strategy, DEFAULT_STARTER_CASH);
    }

    public Player(PlayerStrategy strategy, double starterCash) {
        super();
        this.strategy = strategy;
        this.cash = starterCash;
    }

    @Override
    public Action nextAction() {
        return strategy.nextAction(this.hand, this.cash);
    }

    public double bet() {
        double bet = strategy.bet(this.cash);
        this.cash -= bet;
        return bet;
    }

    public boolean isPlaying() {
        return this.cash > 0 && this.cash < 400;
    }

    public void addCash(double cash) {
        this.cash += cash;
    }

    @Override
    public void take(Card card) {
        card.reveal();
        super.take(card);
    }

    @Override
    public void clearHand() {
        this.hand = null;
    }

    public boolean hasHand() {
        return this.hand != null;
    }
}

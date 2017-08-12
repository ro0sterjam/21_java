package com.ro0sterjam.twentyone.table;

import com.ro0sterjam.twentyone.actors.HasHand;
import com.ro0sterjam.twentyone.actors.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by kenwang on 2017-08-09.
 */
@RequiredArgsConstructor
public final class Seat implements HasHand {

    @Getter private final Player player;
    @Getter private final double bet;
    @Getter private Hand hand = Hand.empty();

    public Action nextAction(Card upcard) {
        return this.player.nextAction(upcard, this.hand, this.bet);
    }

    public boolean takeInsurance() {
        return this.player.takeInsurance(this.hand, this.bet);
    }

    public boolean isBusted() {
        return this.hand.isBusted();
    }

    @Override
    public boolean take(Card card) {
        this.hand = hand.add(card);
        return !this.hand.isBusted();
    }
}

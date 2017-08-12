package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Card;
import com.ro0sterjam.twentyone.table.Hand;

/**
 * Created by kenwang on 2017-08-09.
 */
public class SimplePlayerStrategy extends PlayerStrategy {

    @Override
    public Action nextAction(Card upcard, Hand hand, double bet, double cash) {
        int upCardValue = upcard.getValue();
        if (hand.isPair()) {
            switch (hand.getTotal()) {
                case 2:
                case 16:
                    if (cash >= bet) return Action.SPLIT;
                    break;
                case 18:
                    return ((upCardValue >= 2 && upCardValue <= 6 || upCardValue == 8 || upCardValue == 9) && cash >= bet) ? Action.SPLIT : Action.STAND;
                case 14:
                    return (upCardValue >= 2 && upCardValue <= 7 && cash >= bet) ? Action.SPLIT : Action.HIT;
                case 12:
                    return (upCardValue >= 2 && upCardValue <= 6 && cash >= bet) ? Action.SPLIT : Action.HIT;
                case 8:
                    return ((upCardValue == 5 || upCardValue == 6) && cash >= bet) ? Action.SPLIT : Action.HIT;
                case 6:
                case 4:
                    return (upCardValue >= 2 && upCardValue <= 7 && cash >= bet) ? Action.SPLIT : Action.HIT;
            }
        }

        if (hand.isStartingHand() && hand.isSoft()) {
            switch (hand.getBestTotal()) {
                case 18:
                    return (upCardValue == 2 || upCardValue == 7 || upCardValue == 8)? Action.STAND : (upCardValue >= 3 && upCardValue <= 6 && cash >= bet)? Action.DOUBLE : Action.HIT;
                case 17:
                    return (upCardValue >= 3 && upCardValue <= 6 && cash >= bet)? Action.DOUBLE : Action.HIT;
                case 16:
                case 15:
                    return (upCardValue >= 4 && upCardValue <= 6 && cash >= bet)? Action.DOUBLE : Action.HIT;
                case 14:
                case 13:
                    return ((upCardValue == 5 || upCardValue == 6) && cash >= bet)? Action.DOUBLE : Action.HIT;
            }
        }

        if (hand.getBestTotal() >= 17) {
            return Action.STAND;
        } else if (hand.getBestTotal() <= 8) {
            return Action.HIT;
        }

        switch (hand.getBestTotal()) {
            case 16:
            case 15:
            case 14:
            case 13:
                return (upCardValue >= 2 && upCardValue <= 6)? Action.STAND : Action.HIT;
            case 12:
                return (upCardValue >= 4 && upCardValue <= 6)? Action.STAND : Action.HIT;
            case 11:
                return (upCardValue >= 2 && upCardValue <= 10 && hand.isStartingHand() && cash >= bet)? Action.DOUBLE : Action.HIT;
            case 10:
                return (upCardValue >= 2 && upCardValue <= 9 && hand.isStartingHand() && cash >= bet)? Action.DOUBLE : Action.HIT;
            case 9:
                return (upCardValue >= 3 && upCardValue <= 6 && hand.isStartingHand() && cash >= bet)? Action.DOUBLE : Action.HIT;
            default:
                throw new RuntimeException("Unexpected scenario; Dealer: " + upcard + "; Player: " + hand);
        }
    }

    @Override
    public double nextBet(double minBet, double cash) {
        return minBet;
    }

    @Override
    public boolean isPlaying(double minBet, double cash) {
        return cash >= minBet;
    }

}

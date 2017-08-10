package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.DealerHand;
import com.ro0sterjam.twentyone.table.PlayerHand;

/**
 * Created by kenwang on 2017-08-09.
 */
public class SimplePlayerStrategy extends PlayerStrategy {

    @Override
    public Action nextAction(DealerHand dealerHand, PlayerHand hand, double cash) {
        int upCard = dealerHand.getUpCard().getValue().getValue();

        if (hand.isPair()) {
            switch (hand.getValue()) {
                case 2:
                case 16:
                    if (cash >= hand.getBet()) return Action.SPLIT;
                    break;
                case 18:
                    return ((upCard >= 2 && upCard <= 6 || upCard == 8 || upCard == 9) && cash >= hand.getBet()) ? Action.SPLIT : Action.STAND;
                case 14:
                    return (upCard >= 2 && upCard <= 7 && cash >= hand.getBet()) ? Action.SPLIT : Action.HIT;
                case 12:
                    return (upCard >= 2 && upCard <= 6 && cash >= hand.getBet()) ? Action.SPLIT : Action.HIT;
                case 8:
                    return ((upCard == 5 || upCard == 6) && cash >= hand.getBet()) ? Action.SPLIT : Action.HIT;
                case 6:
                case 4:
                    return (upCard >= 2 && upCard <= 7 && cash >= hand.getBet()) ? Action.SPLIT : Action.HIT;
            }
        }

        if (hand.getHigherValue().isPresent() && hand.size() == 2) {
            switch (hand.getBestHand()) {
                case 18:
                    return (upCard == 2 || upCard == 7 || upCard == 8)? Action.STAND : (upCard >= 3 && upCard <= 6 && cash >= hand.getBet())? Action.DOUBLE : Action.HIT;
                case 17:
                    return (upCard >= 3 && upCard <= 6 && cash >= hand.getBet())? Action.DOUBLE : Action.HIT;
                case 16:
                case 15:
                    return (upCard >= 4 && upCard <= 6 && cash >= hand.getBet())? Action.DOUBLE : Action.HIT;
                case 14:
                case 13:
                    return ((upCard == 5 || upCard == 6) && cash >= hand.getBet())? Action.DOUBLE : Action.HIT;
            }
        }

        if (hand.getBestHand() >= 17) {
            return Action.STAND;
        } else if (hand.getBestHand() <= 8) {
            return Action.HIT;
        }

        switch (hand.getBestHand()) {
            case 16:
            case 15:
            case 14:
            case 13:
                return (upCard >= 2 && upCard <= 6)? Action.STAND : Action.HIT;
            case 12:
                return (upCard >= 4 && upCard <= 6)? Action.STAND : Action.HIT;
            case 11:
                return (upCard >= 2 && upCard <= 10 && hand.size() == 2 && cash >= hand.getBet())? Action.DOUBLE : Action.HIT;
            case 10:
                return (upCard >= 2 && upCard <= 9 && hand.size() == 2 && cash >= hand.getBet())? Action.DOUBLE : Action.HIT;
            case 9:
                return (upCard >= 3 && upCard <= 6 && hand.size() == 2 && cash >= hand.getBet())? Action.DOUBLE : Action.HIT;
            default:
                throw new RuntimeException("Unexpected scenario; Dealer: " + dealerHand + "; Player: " + hand);
        }
    }

    @Override
    public double nextBet(double minBet, double cash) {
        return Math.min(cash, minBet);
    }

    @Override
    public boolean isPlaying(double minBet, double cash) {
        return cash >= minBet;
    }

}

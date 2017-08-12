package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.events.BustedEvent;
import com.ro0sterjam.twentyone.events.PlayerEvent;
import com.ro0sterjam.twentyone.events.RoundResultsEvent;
import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Card;
import com.ro0sterjam.twentyone.table.Hand;

import java.util.Scanner;

/**
 * Created by kenwang on 2017-08-10.
 */
public class ManualPlayerStrategy extends PlayerStrategy {

    @Override
    public Action nextAction(Card upcard, Hand hand, double bet, double cash) {
        System.out.println("Dealer: " + upcard);
        System.out.println("You: " + hand);
        System.out.println("Next Action? (H/S/SP/D):");
        Scanner s = new Scanner(System.in);
        while (true) {
            switch (s.next()) {
                case "H":
                case "h":
                    return Action.HIT;
                case "S":
                case "s":
                    return Action.STAND;
                case "SP":
                case "sp":
                    if (!hand.isStartingHand() || !hand.isPair()) {
                        System.out.println("Cannot split");
                        continue;
                    } else if (bet > cash) {
                        System.out.println("Not enough cash");
                        continue;
                    }
                    return Action.SPLIT;
                case "D":
                case "d":
                    if (!hand.isStartingHand()) {
                        System.out.println("Cannot double");
                        continue;
                    } else if (bet > cash) {
                        System.out.println("Not enough cash");
                        continue;
                    }
                    return Action.DOUBLE;
                default:
                    System.out.println("Invalid Input");
            }
        }
    }

    @Override
    public double nextBet(double minBet, double cash) {
        System.out.println("Next Bet? (Max: " + cash + "):");
        Scanner s = new Scanner(System.in);
        while (true) {
            if (!s.hasNextDouble()) {
                System.out.println("Invalid Input");
                s.next();
                continue;
            }
            double bet = s.nextDouble();
            if (bet > cash) {
                System.out.println("Not enough cash");
                continue;
            } else if (bet < minBet) {
                System.out.println("Min Bet is: " + minBet);
                continue;
            }
            return bet;
        }
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        if (event instanceof RoundResultsEvent) {
            RoundResultsEvent roundResultsEvent = (RoundResultsEvent) event;
            System.out.println("Dealer: " + roundResultsEvent.getDealerHand());
            System.out.println("You: " + roundResultsEvent.getHand());
            System.out.println("Results: " + roundResultsEvent.getResult());
        } else if (event instanceof BustedEvent) {
            BustedEvent bustedEvent = (BustedEvent) event;
            System.out.println("You: " + bustedEvent.getHand());
            System.out.println("Results: BUSTED");
        }
    }

    @Override
    public boolean takeInsurance(Hand hand, double bet, double cash) {
        System.out.println("Dealer: A");
        System.out.println("You: " + hand);
        System.out.println("Take Insurance? (Y/N):");
        Scanner s = new Scanner(System.in);
        while (true) {
            switch (s.next()) {
                case "Y":
                case "y":
                    return true;
                case "N":
                case "n":
                    return false;
                default:
                    System.out.println("Invalid Input");
            }
        }
    }

    @Override
    public boolean isPlaying(double minBet, double cash) {
        return cash >= minBet;
    }
}

package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.events.PlayerEvent;
import com.ro0sterjam.twentyone.events.RoundResultsEvent;
import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.DealerHand;
import com.ro0sterjam.twentyone.table.PlayerHand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by kenwang on 2017-08-10.
 */
public class ManualPlayerStrategy extends PlayerStrategy {

    @Override
    public Action nextAction(DealerHand dealerHand, PlayerHand hand, double cash) {
        System.out.println("Dealer: " + dealerHand);
        System.out.println("You: " + hand);
        System.out.println("Next Action? (H/S/SP/D):");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                switch (br.readLine()) {
                    case "H":
                    case "h":
                        return Action.HIT;
                    case "S":
                    case "s":
                        return Action.STAY;
                    case "SP":
                    case "sp":
                        if (hand.size() != 2 || hand.getCards().get(0).getValue().getValue() != hand.getCards().get(1).getValue().getValue()) {
                            System.out.println("Cannot split");
                            continue;
                        } else if (hand.getBet() > cash) {
                            System.out.println("Not enough cash");
                            continue;
                        }
                        return Action.SPLIT;
                    case "D":
                    case "d":
                        if (hand.size() != 2) {
                            System.out.println("Cannot double");
                            continue;
                        } else if (hand.getBet() > cash) {
                            System.out.println("Not enough cash");
                            continue;
                        }
                        return Action.DOUBLE;
                    default:
                        System.out.println("Invalid Input");
                }
            } catch (IOException e) {
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
            } else if (bet <= minBet) {
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
        }
    }

    @Override
    public boolean isPlaying(double minBet, double cash) {
        return cash >= minBet;
    }
}

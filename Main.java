public class Main {

	public static double getOdds(int total, int current, Deck deck) {
		if (total < 17 || total > 26) {
			throw new IllegalArgumentException("Total " + total + " must be greater than 16 and less than 27");
		} else if (total == current) {
			return 1.0;
		} else if (current >= 17) {
			return 0.0;
		}
		double odds = 0.0;
		for (Card card : Card.values()) {
			if (deck.contains(card)) {
				if (card.hasAltValue() && card.getAltValue() + current >= 17 && card.getAltValue() + current <= 21) {
					odds += deck.getOdds(card) * getOdds(total, current + card.getAltValue(), new Deck(deck).remove(card));
				} else {
					odds += deck.getOdds(card) * getOdds(total, current + card.getValue(), new Deck(deck).remove(card));
				}
			}
		}
		return odds;
	}

	public static double getOddsOfBusting(int current, Deck deck) {
		double odds = 0.0;
		for (int total = 22; total <= 26; total++) {
			odds += getOdds(total, current, deck);
		}
		return odds;
	}

	public static double getOddsOfBeating(int value, int current, Deck deck) {
		if (value < 17) {
			return 1.0 - getOddsOfBusting(current, deck);
		}
		double odds = 0.0;
		for (int total = value + 1; total <= 21; total++) {
			odds += getOdds(total, current, deck);
		}
		return odds;
	}
	
	public static void main(String[] args) {
		Deck deck = new Deck();
		for (Card card1 : Card.values()) {
			for (Card card2 : Card.values()) {
				for (Card dealer : Card.values()) {
					double oddsOfWinning = 1.0 - getOddsOfBeating(card1.getValue() + card2.getValue(), dealer.getValue(), deck);
					double oddsOfWinningAfterHit = 0.0;
					for (Card next : Card.values()) {
						if (next.getValue() + card1.getValue() + card2.getValue() <= 21) {
							oddsOfWinningAfterHit += deck.getOdds(next) * (1.0 - getOddsOfBeating(next.getValue() + card1.getValue() + card2.getValue(), dealer.getValue(), new Deck(deck).remove(next)));
						}
					}
					String action = "Stay";
					if (oddsOfWinningAfterHit > oddsOfWinning) {
						action = "Hit";
					}
					System.out.println("Player: " + card1 + ", " + card2 + " Dealer: " + dealer + " Action: " + action);
				}
			}
		}
	}

}
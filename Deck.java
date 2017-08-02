import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Deck {

	private static final Random rand = new Random();

	private List<Card> cards;

	public Deck() {
		this(1);
	}

	public Deck(int numOfDecks) {
		this.cards = new ArrayList<>();
		for (Card card : Card.values()) {
			for (int i = 0; i < 4 * numOfDecks; i++) {
				cards.add(card);
			}
		}
	}
	public Deck(List<Card> cards) {
		this.cards = cards;
	}

	public void shuffle() {
		shuffle(10);
	}

	public void shuffle(int times) {
		for (int i = 0; i < times; i++) {
			shuffleOnce();
		}
	}

	public void shuffleOnce() {
		List<Card> shuffledCards = new ArrayList<>();
		int cardsInDeck = this.cards.size();
		synchronized (this.cards) {
			for (int i = cardsInDeck; i > 0; i--) {
				shuffledCards.add(this.cards.remove(rand.nextInt(i)));
			}
			this.cards = shuffledCards;
		}
	}

	public Card deal() {
		return this.cards.remove(this.cards.size() - 1);
	}

	public int numCards() {
		return this.cards.size();
	}

	public boolean hasCards() {
		return this.cards.size() > 0;
	}

}
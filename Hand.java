import java.util.List;
import java.util.ArrayList;

public class Hand {

	private List<Card> cards;
	private int value;
	private Integer higherValue;

	public Hand() {
		this.cards = new ArrayList<Card>();
		this.value = 0;
		this.higherValue = null;
	}

	public void add(Card card) {
		this.cards.add(card);
		this.value += card.getValue();
		if (hasHigherValue()) {
			this.higherValue += card.getValue();
		} else if (card.hasHigherValue()) {
			this.higherValue = this.value - card.getValue() + card.getHigherValue();
		}
	}

	public int getNumCards() {
		return this.cards.size();
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public int getValue() {
		return value;
	}

	public boolean hasHigherValue() {
		return this.higherValue != null;
	}

	public Integer getHigherValue() {
		return higherValue;
	}

	public boolean isBusted() {
		return this.value > 21;
	}

}
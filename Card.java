import java.util.Set;
import java.util.HashSet;

public enum Card {

	TWO(2, null, "2"),
	THREE(3, null, "3"),
	FOUR(4, null, "4"),
	FIVE(5, null, "5"),
	SIX(6, null, "6"),
	SEVEN(7, null, "7"),
	EIGHT(8, null, "8"),
	NINE(9, null, "9"),
	TEN(10, null, "X"),
	JACK(10, null, "J"),
	QUEEN(10, null, "Q"),
	KING(10, null, "K"),
	ACE(1, 11, "A");

	public static final Card[] FACE_CARDS = new Card[]{ TEN, JACK, QUEEN, KING };
	public static final Card[] LOW_CARDS = new Card[]{ TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE };

	private int value;
	private Integer higherValue;
	private boolean hasHigherValue;
	private String symbol;

	Card(int value, Integer higherValue, String symbol) {
		this.value = value;
		this.higherValue = higherValue;
		this.symbol = symbol;
	}

	public int getValue() {
		return this.value;
	}

	public Integer getHigherValue() {
		return this.higherValue;
	}

	public boolean hasHigherValue() {
		return this.higherValue != null;
	}

	public String getSymbol() {
		return this.symbol;
	}

	@Override
	public String toString() {
		return this.symbol;
	}

}
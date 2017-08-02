public class Player {

	protected Hand hand;
	protected Strategy strategy;

	public Player() {
		this(new DealerStrategy());
	}

	public Player(Strategy strategy) {
		this.hand = new Hand();
		this.strategy = strategy;
	}

	public Action nextAction() {
		return this.strategy.nextAction(this.hand);
	}

	public boolean isBusted() {
		return this.hand.isBusted();
	}

	public void take(Card card) {
		this.hand.add(card);
	}

	public String getHandAsString() {
		String handAsString = "";
		for (Card card : this.hand.getCards()) {
			handAsString += card.getSymbol();
		}
		return handAsString;
	}

}
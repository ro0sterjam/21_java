public class Dealer extends Player {

	private boolean revealed;

	public Dealer() {
		super(new DealerStrategy());
		this.revealed = false;
	}

	public void revealHand() {
		this.revealed = true;
	}

	@Override
	public String getHandAsString() {
		if (!revealed) {
			return "?" + this.hand.getCards().get(1);
		} else {
			return super.getHandAsString();
		}
	}
}
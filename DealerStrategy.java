public class DealerStrategy implements Strategy {

	@Override
	public Action nextAction(Hand hand) {
		if (hand.getValue() >= 17 || hand.hasHigherValue() && hand.getHigherValue() > 17) {
			return Action.STAY;
		} else {
			return Action.HIT;
		}
	}

}
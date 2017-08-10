import com.google.common.collect.ImmutableList;
import com.ro0sterjam.twentyone.actors.Dealer;
import com.ro0sterjam.twentyone.actors.Player;
import com.ro0sterjam.twentyone.events.*;
import com.ro0sterjam.twentyone.exceptions.IllegalActionException;
import com.ro0sterjam.twentyone.strategies.SimplePlayerStrategy;
import com.ro0sterjam.twentyone.table.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Game {

	private Shoe shoe;
	private Dealer dealer;
	private List<Player> players;
	private int numOfDecks;
	private double minBet;

	public Game() {
		this(1);
	}

	public Game(int numPlayers) {
		this.shoe = new Shoe(0);
		this.dealer = new Dealer();
		this.players = IntStream.range(0, numPlayers).mapToObj(i -> new Player(new SimplePlayerStrategy())).collect(Collectors.toList());
		this.numOfDecks = 5;
		this.minBet = 25;
	}

	public void loadShoe() {
		this.shoe = new Shoe(numOfDecks);
		sendGlobalEvent(new LoadShoeEvent(numOfDecks));
	}

	public void shuffleDeck() {
		this.shoe.shuffle();
	}

	public void placeBets () {
		getBettingPlayers().forEach(player -> player.getHands().add(new PlayerHand(player.bet(player.getStrategy().nextBet(this.minBet, player.getCash())))));
	}

	public void deal(Hand hand) {
		Card card = this.shoe.deal();
		if (hand instanceof PlayerHand || hand instanceof DealerHand && hand.size() >= 1) {
			card.reveal();
		}
		hand.add(card);
		sendGlobalEvent(new DealEvent(card));
	}

	public void dealTable() {
		sendGlobalEvent(new NewRoundEvent());
		if (this.shoe.isDone()) {
			loadShoe();
			shuffleDeck();
		}
		IntStream.range(0, 2).forEach(i -> {
			getBettingPlayers().map(Player::getHands).flatMap(Collection::stream).forEach(this::deal);
			deal(this.dealer.getHand());
		});
	}

	public void performPlayerActions() {
		this.players.forEach(player -> ImmutableList.copyOf(player.getHands()).forEach(hand -> performPlayerHandActions(player, hand)));
	}

	public void performDealerActions() {
		this.dealer.revealDownCard();
		while (this.dealer.nextAction() == Action.HIT && !this.dealer.isBusted()) {
			deal(this.dealer.getHand());
		}
	}

	public void processRoundResults() {
		this.players.forEach(player -> player.getHands().forEach(hand -> {
            switch (hand.compareTo(this.dealer.getHand())) {
                case 1:
                    player.addCash(hand.getBet() * 2);
					sendPlayerEvent(player, new RoundResultsEvent(this.dealer.getHand(), hand, RoundResultsEvent.Result.WIN));
                    break;
                case 0:
                    player.addCash(hand.getBet());
					sendPlayerEvent(player, new RoundResultsEvent(this.dealer.getHand(), hand, RoundResultsEvent.Result.PUSH));
					break;
				case -1:
					sendPlayerEvent(player, new RoundResultsEvent(this.dealer.getHand(), hand, RoundResultsEvent.Result.LOSE));
					break;
            }
		}));
	}

	public void clearTable() {
		this.players.forEach(Player::clearHands);
		this.dealer.clearHand();
	}

	public boolean hasPlayers() {
		return getBettingPlayers().findAny().isPresent();
	}

	private Stream<Player> getBettingPlayers() {
		return this.players.stream().filter(player -> player.isPlaying(this.minBet));
	}

	private void sendGlobalEvent(GlobalEvent event) {
		this.players.forEach(player -> player.onGlobalEvent(event));
	}

	private void sendPlayerEvent(Player player, PlayerEvent event) {
		player.onPlayerEvent(event);
	}

	private void performPlayerHandActions(Player player, PlayerHand hand) {
		if (hand.isBusted()) {
			return;
		}
		switch (player.getStrategy().nextAction(this.dealer.getHand(), hand, player.getCash())) {
			case HIT:
				deal(hand);
				performPlayerHandActions(player, hand);
				break;
			case DOUBLE:
				if (hand.size() != 2) {
					throw new IllegalActionException();
				}
				hand.setBet(hand.getBet() + player.bet(hand.getBet()));
				deal(hand);
				break;
			case SPLIT:
				if (hand.size() != 2 || hand.getCards().get(0).getValue().getValue() != hand.getCards().get(1).getValue().getValue()) {
					throw new IllegalActionException();
				}
				int handPosition = player.getHands().indexOf(hand);
				player.getHands().remove(handPosition);

				PlayerHand firstHand = new PlayerHand(hand.getBet());
				firstHand.add(hand.getCards().get(0));
				player.getHands().add(handPosition, firstHand);

				PlayerHand secondHand = new PlayerHand(player.bet(hand.getBet()));
				secondHand.add(hand.getCards().get(1));
				player.getHands().add(handPosition + 1, secondHand);

				deal(firstHand);
				performPlayerHandActions(player, firstHand);

				deal(secondHand);
				performPlayerHandActions(player, secondHand);
				break;
		}
	}

	public void printResults() {
		this.players.forEach(System.out::println);
	}

	public static void main(String args[]) {
		Game game = new Game(10);
		game.loadShoe();
		game.shuffleDeck();
		while (game.hasPlayers()) {
			game.placeBets();
			game.dealTable();
			game.performPlayerActions();
			game.performDealerActions();
			game.processRoundResults();
			game.clearTable();
		}
		game.printResults();
	}

}
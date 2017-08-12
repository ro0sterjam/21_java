import com.ro0sterjam.twentyone.actors.Dealer;
import com.ro0sterjam.twentyone.actors.HasHand;
import com.ro0sterjam.twentyone.actors.Player;
import com.ro0sterjam.twentyone.events.*;
import com.ro0sterjam.twentyone.exceptions.IllegalActionException;
import com.ro0sterjam.twentyone.rules.GameRules;
import com.ro0sterjam.twentyone.rules.SimpleGameRules;
import com.ro0sterjam.twentyone.strategies.ManualPlayerStrategy;
import com.ro0sterjam.twentyone.table.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Game {

	private final GameRules rules;
	private final List<Player> players;
	private final Dealer dealer;
	private final Shoe shoe;
	private List<Seat> seats;
	private List<Seat> finishedSeats;

	public Game(GameRules rules, List<Player> players) {
		this.rules = rules;
		this.players = players;
		this.dealer = new Dealer(rules.getDealerRules());
		this.shoe = new Shoe();
	}

	public void loadShoe() {
		Deck deck = new Deck(this.rules.getNumDecks());
		deck.shuffle();
		this.shoe.load(deck);
		sendGlobalEvent(new LoadShoeEvent(this.rules.getNumDecks()));
	}

	public void placeBets () {
		this.seats = getBettingPlayers().map(player -> new Seat(player, player.getCash(player.nextBet(this.rules.getMinBet())))).collect(Collectors.toList());
	}

	public void dealTable() {
		sendGlobalEvent(new NewRoundEvent());
		if (this.shoe.isDone()) loadShoe();
		IntStream.range(0, 2).forEach(i -> {
			this.seats.forEach(this::deal);
			deal(this.dealer);
		});
	}

	public boolean checkForDealerBlackjack() {
		if (this.dealer.getUpcard().getRank() == Rank.ACE) {
			List<Seat> insuranceTakers = new ArrayList<>();
			this.seats.forEach(seat -> {
				if (seat.takeInsurance()) {
					seat.getPlayer().getCash(seat.getBet() / 2);
					insuranceTakers.add(seat);
				}
			});
			if (this.dealer.getHand().isBlackjack()) {
				insuranceTakers.forEach(seat -> seat.getPlayer().addCash((seat.getBet() / 2) * (1 + this.rules.getInsurancePayout())));
				this.seats.stream().filter(seat -> seat.getHand().isBlackjack()).forEach(seat -> seat.getPlayer().addCash(seat.getBet()));
				return true;
			}
			return false;
		} else if (this.dealer.getHand().isBlackjack()) {
			this.seats.stream().filter(seat -> seat.getHand().isBlackjack()).forEach(seat -> seat.getPlayer().addCash(seat.getBet()));
			return true;
		}
		return false;
	}

	public void performPlayerActions() {
		this.finishedSeats = new ArrayList<>();
		this.seats.forEach(this::performPlayerAction);
	}

	public void performPlayerAction(Seat seat) {
		if (seat.getHand().isBlackjack()) {
			seat.getPlayer().addCash(seat.getBet() * (1 + this.rules.getBlackjackPayout()));
			return;
		}

		switch (seat.nextAction(this.dealer.getUpcard())) {
			case HIT:
				deal(seat);
				if (seat.isBusted()) {
					sendPlayerEvent(seat.getPlayer(), new BustedEvent(seat.getHand()));
				} else if (seat.getHand().getBestTotal() == 21) {
					this.finishedSeats.add(seat);
				} else {
					performPlayerAction(seat);
				}
				break;
			case DOUBLE:
				if (!seat.getHand().isStartingHand()) {
					throw new IllegalActionException();
				}
				Seat newSeat = new Seat(seat.getPlayer(), seat.getBet() + seat.getPlayer().getCash(seat.getBet()));
				newSeat.take(seat.getHand().getCards().get(0));
				newSeat.take(seat.getHand().getCards().get(1));
				deal(newSeat);
				if (newSeat.isBusted()) {
					sendPlayerEvent(newSeat.getPlayer(), new BustedEvent(newSeat.getHand()));
				} else {
					this.finishedSeats.add(newSeat);
				}
				break;
			case SPLIT:
				if (!seat.getHand().isPair()) {
					throw new IllegalActionException();
				}
				Seat firstSeat = new Seat(seat.getPlayer(), seat.getBet());
				firstSeat.take(seat.getHand().getCards().get(0));

				Seat secondSeat = new Seat(seat.getPlayer(), seat.getPlayer().getCash(seat.getBet()));
				secondSeat.take(seat.getHand().getCards().get(1));

				deal(firstSeat);
				if (firstSeat.getHand().getBestTotal() == 21 || seat.getHand().getTotal() == 2) {
					this.finishedSeats.add(seat);
				} else {
					performPlayerAction(firstSeat);
				}

				deal(secondSeat);
				if (secondSeat.getHand().getBestTotal() == 21 || seat.getHand().getTotal() == 2) {
					this.finishedSeats.add(seat);
				} else {
					performPlayerAction(secondSeat);
				}
				break;
			case STAND:
				this.finishedSeats.add(seat);
				break;
		}
	}

	public void performDealerActions() {
		while (this.dealer.nextAction() == Action.HIT && !this.dealer.getHand().isBusted()) {
			deal(this.dealer);
		}
	}

	public void processRoundResults() {
		this.finishedSeats.forEach(seat -> {
			int compared = seat.getHand().compareTo(this.dealer.getHand());
            if (compared > 0) {
				seat.getPlayer().addCash(seat.getBet() * 2);
				sendPlayerEvent(seat.getPlayer(), new RoundResultsEvent(this.dealer.getHand(), seat.getHand(), RoundResultsEvent.Result.WIN));
			} else if (compared < 0) {
				sendPlayerEvent(seat.getPlayer(), new RoundResultsEvent(this.dealer.getHand(), seat.getHand(), RoundResultsEvent.Result.LOSE));
			} else {
				seat.getPlayer().addCash(seat.getBet());
				sendPlayerEvent(seat.getPlayer(), new RoundResultsEvent(this.dealer.getHand(), seat.getHand(), RoundResultsEvent.Result.PUSH));
			}
		});
	}

	public void clearTable() {
		this.seats = null;
		this.dealer.clearHand();
	}

	public boolean hasPlayers() {
		return getBettingPlayers().findAny().isPresent();
	}

	private Stream<Player> getBettingPlayers() {
		return this.players.stream().filter(player -> player.isPlaying(this.rules.getMinBet()));
	}

	private void sendGlobalEvent(GlobalEvent event) {
		this.players.forEach(player -> player.onGlobalEvent(event));
	}

	private void sendPlayerEvent(Player player, PlayerEvent event) {
		player.onPlayerEvent(event);
	}

	private boolean deal(HasHand seat) {
		Card card = this.shoe.deal();
		sendGlobalEvent(new DealEvent(card));
		return seat.take(card);
	}

	public void printResults() {
		this.players.forEach(System.out::println);
	}

	public static void main(String args[]) {
		GameRules rules = new SimpleGameRules();
		List<Player> players = IntStream.range(0, 2).mapToObj(i -> new Player(new ManualPlayerStrategy())).collect(Collectors.toList());
		Game game = new Game(rules, players);
		game.loadShoe();
		while (game.hasPlayers()) {
			game.placeBets();
			game.dealTable();
			if (game.checkForDealerBlackjack()) {
				game.clearTable();
				continue;
			}
			game.performPlayerActions();
			game.performDealerActions();
			game.processRoundResults();
			game.clearTable();
		}
		game.printResults();
	}

}
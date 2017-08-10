import com.ro0sterjam.twentyone.actors.Dealer;
import com.ro0sterjam.twentyone.actors.Player;
import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.PlayerHand;
import com.ro0sterjam.twentyone.table.Shoe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Game {

	private Shoe shoe;
	private Dealer dealer;
	private List<Player> players;

	public Game() {
		this(1);
	}

	public Game(int numPlayers) {
		this.dealer = new Dealer();
		this.players = new ArrayList<>();
		for (int i = 0; i < numPlayers; i++) {
			this.players.add(new Player());
		}
	}

	public void loadShoe() {
		this.shoe = new Shoe(5);
	}

	public void shuffleDeck() {
		this.shoe.shuffle();
	}

	private boolean shouldReloadShoe() {
		return this.shoe.isDone();
	}

	public void placeBets () {
		this.players.stream().filter(Player::isPlaying).forEach(player -> player.setHand(new PlayerHand(player.bet())));
	}

	public void dealTable() {
		IntStream.range(0, 2).forEach(i -> {
			this.players.stream().filter(Player::hasHand).forEach(this.shoe::deal);
			this.shoe.deal(this.dealer);
		});
	}

	public void performPlayerActions() {
		this.players.stream().filter(Player::hasHand).forEach(player -> {
			while (player.nextAction() == Action.HIT && !player.isBusted()) {
				this.shoe.deal(player);
			}
		});
	}

	public void performDealerActions() {
		this.dealer.revealDownCard();
		while (this.dealer.nextAction() == Action.HIT && !this.dealer.isBusted()) {
			this.shoe.deal(this.dealer);
		}
	}

	public void payout() {
		this.players.stream().filter(Player::hasHand).filter(player -> !player.isBusted()).forEach(player -> {
            switch (player.getHand().compareTo(this.dealer.getHand())) {
                case 1:
                    player.addCash(player.getHand().getBet() * 2);
                    break;
                case 0:
                    player.addCash(player.getHand().getBet());
            }
		});
	}

	public void clearTable() {
		this.players.stream().filter(Player::hasHand).forEach(Player::clearHand);
		this.dealer.clearHand();
	}

	public boolean hasPlayers() {
		return this.players.stream().anyMatch(Player::isPlaying);
	}

	public void printTable() {
		System.out.println("----------------");
		System.out.println(String.format("Dealer: %s", this.dealer));
		this.players.forEach(player -> {
			System.out.println(String.format("Player: %s", player));
		});
		System.out.println("----------------");
	}

	public static void main(String args[]) {
		Game game = new Game(10);
		game.loadShoe();
		game.shuffleDeck();
		while (game.hasPlayers()) {
			game.placeBets();
			if (game.shouldReloadShoe()) {
				game.loadShoe();
				game.shuffleDeck();
			}
			game.dealTable();
			game.performPlayerActions();
			game.performDealerActions();
			game.payout();
			game.printTable();
			game.clearTable();
		}
	}

}
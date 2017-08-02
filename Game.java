import java.util.List;
import java.util.ArrayList;

public class Game {

	private Deck deck;
	private Dealer dealer;
	private List<Player> players;

	public Game() {
		this(1);
	}

	public Game(int numPlayers) {
		this.deck = new Deck();
		this.dealer = new Dealer();
		this.players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++) {
			this.players.add(new Player());
		}
	}

	public void shuffleDeck() {
		this.deck.shuffle();
	}

	public void dealTable() {
		for (int i = 0; i < 2; i++) {
			for (Player player : this.players) {
				player.take(this.deck.deal());
			}
			this.dealer.take(deck.deal());
		}
	}

	public void dealCard(Player player) {
		player.take(this.deck.deal());
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public void printTable() {
		System.out.println("----------------");
		System.out.println("Dealer's Hand: " + this.dealer.getHandAsString());
		for (int i = 0; i < this.players.size(); i++) {
			Player player = this.players.get(i);
			System.out.println("Player " + (i + 1) + "'s Hand: " + player.getHandAsString() + "; isBusted: " + player.isBusted());
		}
		System.out.println("----------------");
	}

	public static void main(String args[]) {
		Game game = new Game(10);
		game.shuffleDeck();
		game.dealTable();
		game.printTable();
		for (Player player : game.getPlayers()) {
			while (player.nextAction() == Action.HIT && !player.isBusted()) {
				game.dealCard(player);
				game.printTable();
			}
		}
		game.getDealer().revealHand();
		game.printTable();
		while (game.getDealer().nextAction() == Action.HIT && !game.getDealer().isBusted()) {
			game.dealCard(game.getDealer());
			game.printTable();
		}
	}

}
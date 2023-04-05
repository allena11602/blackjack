// Allen Abraham
// First version of Blackjack
// Game is played with one deck that is reshuffled every round
// Dealer stands on soft 17

import java.util.*;

public class Blackjack {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        while(true) {
        	Deck deck = new Deck(); // initialize a new deck
        	deck.shuffle();
        	Player player = new Player();
        	Dealer dealer = new Dealer();
        
        	System.out.println("Welcome to Blackjack!");

        	// Deal two cards to the player
        	Card card1 = deck.dealCard();
        	Card card2 = deck.dealCard();
        	player.addCard(card1);
        	player.addCard(card2);
        	System.out.println("You were dealt a " + card1.getName() + " and a " + card2.getName());
        	System.out.println("Your score is " + player.getScore());

        	// Deal two cards to the dealer
        	Card dealerCard1 = deck.dealCard();
        	Card dealerCard2 = deck.dealCard();
        	dealer.addCard(dealerCard1);
        	dealer.addCard(dealerCard2);
        	System.out.println("The dealer's face-up card is a " + dealerCard1.getName());

        	// Player's turn
        	while (true) {
        		System.out.print("Hit or stand? ");
        		String choice = input.nextLine().toLowerCase();
        		if (choice.equals("hit")) {
        			Card card = deck.dealCard(); // pull card from deck
        			player.addCard(card); // player takes card
        			System.out.println("You were dealt a " + card.getName());
        			System.out.println("Your score is " + player.getScore());
        			if (player.getScore() > 21) {
        				System.out.println("Bust! You lose.");
        				break;
        			}
        		} else if (choice.equals("stand")) {
                break;
        		} else {
                System.out.println("Invalid choice.");
        		}
        	}
        	if (player.getScore() > 21) {
        		System.out.print("Play again? (y/n) ");
            	String choice = input.nextLine().toLowerCase();
            	if (!choice.equals("y")) {
            		System.out.println("Thank you for playing, until next time!");
            		break;
            	}
				continue;
			}

        	// Dealer's turn
        	System.out.println("The dealer's face-down card is a " + dealerCard2.getName());
        	while (dealer.getScore() < 17) { // keep drawing till score is 17 or over
        		Card card = deck.dealCard();
        		dealer.addCard(card);
        		System.out.println("The dealer was dealt a " + card.getName());
        		System.out.println("The dealer's score is " + dealer.getScore());
        		if (dealer.getScore() > 21) {
        			System.out.println("Dealer busts! You win.");
        			break;
        		}
        	}
        	if (dealer.getScore() > 21) {
        		System.out.print("Play again? (y/n) ");
            	String choice = input.nextLine().toLowerCase();
            	if (!choice.equals("y")) {
            		System.out.println("Thank you for playing, until next time!");
            		break;
            	}
    			continue;
    		}

        // Determine the winner
        	int playerScore = player.getScore();
        	int dealerScore = dealer.getScore();
        	if (playerScore > dealerScore) {
        		System.out.println("You win!");
        	} else if (dealerScore > playerScore) {
        		System.out.println("You lose.");
        	} else {
        		System.out.println("It's a push.");
        	}
        
        	System.out.print("Play again? (y/n) ");
        	String choice = input.nextLine().toLowerCase();
        	if (!choice.equals("y")) {
        		System.out.println("Thank you for playing, until next time!");
        		break;
        	}
        }
        
        input.close();
    }
}

class Deck {
    private List<Card> cards;
    private Random random;

    public Deck() {
        cards = new ArrayList<Card>();
        random = new Random();
        for (int suit = 0; suit < 4; suit++) {
            for (int rank = 1; rank <= 13; rank++) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        for (int i = 0; i < cards.size(); i++) {
            int j = random.nextInt(cards.size());
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    public Card dealCard() {
        return cards.remove(0);
    }
}

class Card {
    private int rank;
    private int suit;

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }
    public int getRank() {
        return rank;
    }
    public String getName() {
        String rankName;
        switch (rank) {
            case 1:
                rankName = "Ace";
                break;
            case 11:
                rankName = "Jack";
                break;
            case 12:
                rankName = "Queen";
                break;
            case 13:
                rankName = "King";
                break;
            default:
                rankName = Integer.toString(rank);
                break;
        }
        String suitName;
        switch (suit) {
            case 0:
                suitName = "Spades";
                break;
            case 1:
                suitName = "Hearts";
                break;
            case 2:
                suitName = "Diamonds";
                break;
            case 3:
                suitName = "Clubs";
                break;
            default:
                suitName = "";
                break;
        }
        return rankName + " of " + suitName;
    }
}

class Player {
	private List<Card> hand;
	public Player() {
	    hand = new ArrayList<Card>();
	}

	public void addCard(Card card) {
	    hand.add(card);
	}

	public int getScore() {
	    int score = 0;
	    int aces = 0;
	    for (Card card : hand) {
	        int rank = card.getRank();
	        if (rank == 1) {  // Ace
	            aces++;
	            score += 11;
	        } else if (rank >= 10) {  // Face card or 10
	            score += 10;
	        } else {  // Number card
	            score += rank;
	        }
	    }
	    while (score > 21 && aces > 0) {  // Handle aces
	        score -= 10;
	        aces--;
	    }
	    return score;
	}
}

class Dealer {
	private List<Card> hand;
	public Dealer() {
	    hand = new ArrayList<Card>();
	}

	public void addCard(Card card) {
	    hand.add(card);
	}

	public int getScore() {
	    int score = 0;
	    int aces = 0;
	    for (Card card : hand) {
	        int rank = card.getRank();
	        if (rank == 1) {  // Ace
	            aces++;
	            score += 11;
	        } else if (rank >= 10) {  // Face card or 10
	            score += 10;
	        } else {  // Number card
	            score += rank;
	        }
	    }
	    while (score > 21 && aces > 0) {  // Handle aces
	        score -= 10;
	        aces--;
	    }
	    return score;
	}
}
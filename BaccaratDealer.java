import java.util.*;

public class BaccaratDealer {
	
	ArrayList<Card> deck = new ArrayList<Card>();
	
	int[] numbers = {1,2,3,4,5,6,7,8,9,10,11,12,13};
	String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
	
	//*******************************************************************
	//* We generate the deck by going through both arrays and assigning	*
	//* each index to the deck list										*
	//*******************************************************************
	public void generateDeck() {
		
		deck = new ArrayList<Card>();
		
		for(String suite : suits) {
			for(int num : numbers) {
				Card card = new Card(suite, num);
				deck.add(card);
			}
		}
	}
	
	//***************************************************************
	//* We deal the hand to whoever calls this function, should be	*
	//* player who gets the first draw								*
	//***************************************************************
	
	public ArrayList<Card> dealHand(){
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(deck.get(0));
		deck.remove(0);
		hand.add(deck.get(0));
		deck.remove(0);
		
		return hand;
	}
	
	//***********************************************
	//* We draw one card and remove from the deck	*
	//***********************************************
	public Card drawOne() {
		
		Card card = deck.get(0);
		
		deck.remove(0);
		
		return card;
	}
	
	//*******************************
	//* Shuffles the deck randomly	*
	//*******************************
	
	public void shuffleDeck() {
		
		generateDeck();
		Collections.shuffle(deck);
		
	}
	
	//***********************************
	//* Returns the size of the deck	*
	//***********************************
	public int deckSize() {
		
		return deck.size();
	}
}

import java.util.*;
public class BaccaratGameLogic {
	
	
	//***********************************************************************
	//* This 2D array is referenced from the PDF when the banker is allowed	*
	//* to draw. The 1's indicate that the banker is allowed to draw		*
	//* and the 0's indicate the banker is not allowed to draw a card.		*
	//***********************************************************************
	
	int[][] dealerDrawArr = new int[][]{
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
		{1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
		{1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}	
		
	};
	
	
	
	//*******************************************************************
	//* We determine who wins by who has an 8 or 9 or who is closest to	*
	//* 9 at the end of the game. Otherwise if both have the same score	*
	//* then the game ends in a draw.									*
	//*******************************************************************
	public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		int player = handTotal(hand1);
		int dealer = handTotal(hand2);
		
		if(player == dealer) {
			return "Tie!";
		}
		else if((player == 8 || player == 9)) {
			
			if(dealer == 9) {
				return "House Wins!";
			}
			else {
				return "Player Wins!";
			}
		}
		else if( (dealer == 8 || dealer == 9)) {
			return "House Wins!";
		}
		else if(player > dealer) {
			return "Player Wins!";
		}
		else {
			return "House Wins!";
		}
	}
	
	
	public int handTotal(ArrayList<Card> hand) {
		int total = 0;
		
		for(Card c : hand) {
			
			if(c.getValue() < 10) {
				total += c.getValue();
			}
			if (total >= 10) {
				total = total - 10;
			}
		}
		return total;
	}
	
	//*******************************************************************
	//* If bankers hand is 7 or more then banker is not allowed to draw	*
	//* another card. If bankers hand is 2 or less then another can be	*
	//* drawn. Otherwise using the PDF, we can determine if the banker	*
	//* is allowed to draw a card. The chart is implemented using a 2D	*
	//* array.															*
	//*******************************************************************
	
	public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		if(handTotal(hand) >= 7) {
			return false;
		}
		else if(handTotal(hand) <= 2) {
			return true;
		}
		else if(playerCard == null) {
			if(dealerDrawArr[handTotal(hand)][0] == 1) return true;
			
			else return false;
		}
		else if(dealerDrawArr[handTotal(hand)][playerCard.getValue()+1] == 1) {
			return true;
		}
		else return false;
	}
	
	//*******************************************************************
	//* If players hand total is less than or equal to 5 then player	*
	//* is allowed to draw another card and true is returned.			*
	//*******************************************************************
	
	public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		
		if(handTotal(hand) <= 5) {
			return true;
		}
		
		else return false;
	}
}

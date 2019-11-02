import java.io.FileInputStream;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class BaccaratGame extends Application {

	BorderPane borderPane = new BorderPane();
	VBox paneCenter;
	MenuBar menu;
	private Button startBttn;
	private TextField text;
	private Text betAmount = new Text();
	private Text currWins = new Text();
	PauseTransition pause = new PauseTransition(Duration.seconds(2));
	private VBox bankerC = new VBox();
	private VBox playerC = new VBox();
	private Text gameResults = new Text();
	private Text betOn = new Text();
	private Button newRound = new Button();
	private String placedBet;
	private Text validBet = new Text();
	Text dispPlayerHand = new Text();
	Text dispBankerHand = new Text();
	Text betCorrect = new Text();

	Scene scene;
	
	ArrayList<Card> playerHand = new ArrayList<Card>();
	ArrayList<Card> bankerHand = new ArrayList<Card>();
	BaccaratDealer theDealer = new BaccaratDealer();
	BaccaratGameLogic gameLogic = new BaccaratGameLogic();
	double currentBet = 0.0;
	double totalWinnings = 0.0;
	
	
	
	public String whoWins() {
		
		String winner = gameLogic.whoWon(playerHand, bankerHand);
		
		if(winner == "Player Wins!") {
			winner = "Player";
		}
		else if(winner == "House Wins!") {
			winner = "Banker";
		}
		else {
			winner = "Tie";
		}
		
		return winner;
	}
	
	//***********************************************************************
	//* This method will determine if the user won or lost their bet and 	*
	//* return the amount won or lost based on the value in currentBet.		*
	//***********************************************************************
	public double evaluateWinning() {
		
		if(placedBet == whoWins()) {
			totalWinnings = totalWinnings + currentBet;
			betCorrect.setText("Congrats you won!!!");
		}
		else if(whoWins() == "Tie" && placedBet != "Tie") {
			betCorrect.setText("Tie! No money lost!");
			return totalWinnings;
		}
		else {
			totalWinnings = totalWinnings - currentBet;
			betCorrect.setText("Sorry you lost!");
		}
				
		return totalWinnings;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);		

	}
	
	//*******************************
	//* Store cards into a vbox		*
	//*******************************

	public VBox displayCards(VBox hand, ArrayList<Card> cards) {
		
		if(cards.size() == 3) {
			hand = new VBox(10, cards.get(0).getDisplay(), cards.get(1).getDisplay(),
					cards.get(2).getDisplay());
		}
		else {
			hand = new VBox(10, cards.get(0).getDisplay(), cards.get(1).getDisplay());
		}
		
		return hand;
	}
	
	
	public void initGame() {
		//***********************
		//* Initialize the game	*
		//***********************
		theDealer = new BaccaratDealer();
		theDealer.shuffleDeck();
		
		playerHand = new ArrayList<Card>();
		bankerHand = new ArrayList<Card>();
		
		playerHand = theDealer.dealHand();
		bankerHand = theDealer.dealHand();
				
		if(gameLogic.evaluatePlayerDraw(playerHand)) {
			playerHand.add(theDealer.drawOne());
		}
		if(playerHand.size() == 3) {
			if(gameLogic.evaluateBankerDraw(bankerHand, playerHand.get(2))) {
				bankerHand.add(theDealer.drawOne());
			}
		}
		else if(gameLogic.evaluateBankerDraw(bankerHand, null)) {
			bankerHand.add(theDealer.drawOne());
		}
		
		String winner = gameLogic.whoWon(playerHand, bankerHand);
		
		gameResults.setText("Player total: " + gameLogic.handTotal(playerHand) 
		+ "\nBank total: " + gameLogic.handTotal(bankerHand) + "\n" + winner);
	}
	
	
	
	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Let's Play Baccarat!!!");
		
		//***************************
		//* Initialize the game		*
		//***************************
		initGame();
		gameResults.setVisible(false);
		betCorrect.setVisible(false);
		
		dispPlayerHand.setText("\t\t  Player");
		dispBankerHand.setText("\t\tBanker");
		
		//***********************************************
		//* Made the options menu containing the		*
		//* quit and restart options in the drop down	*
		//***********************************************
		Button playerBttn = new Button("Player");
		Button bankerBttn = new Button("Banker");
		Button drawBttn = new Button("Draw");
		
		newRound = new Button("New Round");
		newRound.setDisable(true);
		newRound.setVisible(false);
		
		betOn.setVisible(false);
		
		text = new TextField();
		text.setDisable(true);

		startBttn = new Button("Start");
		
		
		menu = new MenuBar();
		Menu mOne = new Menu("Options");
		
		MenuItem quitOption = new MenuItem("Quit");
		MenuItem resetOption = new MenuItem("Fresh Start");
		
		//***************************
		//* Start button to start 	*
		//* the game 				*
		//***************************
		startBttn.setOnAction(e ->{
			startBttn.setDisable(true);
			startBttn.setVisible(false);
			
			playerBttn.setDisable(false);
			bankerBttn.setDisable(false);
			drawBttn.setDisable(false);
			text.setDisable(false);
		});
		
		
		//***********************
		//* Quitting the game	*
		//***********************
		quitOption.setOnAction(e -> { Platform.exit(); });
		
		
		currWins.setVisible(false);
		
		VBox info = new VBox(10, betAmount, currWins);
		
		HBox playerBetChoices = new HBox(50, info, text, playerBttn, bankerBttn, drawBttn);
				
		//***************************************************************
		mOne.getItems().addAll(quitOption, resetOption);
		menu.getMenus().addAll(mOne);
		
		text.setMaxSize(100, 100);
		
		paneCenter = new VBox(30, menu, startBttn, validBet, newRound, betOn, betCorrect, gameResults);
		
		
		borderPane.setPadding(new Insets(10, 20, 40, 10));
		
		//***********************************
		//* Displaying the players cards	* 
		//***********************************
		
		playerC = new VBox(5,displayCards(playerC, playerHand), dispPlayerHand);
		playerC.setVisible(false);
		
		borderPane.setLeft(playerC);
				
		//***********************************
		//* Displaying the bankers cards	*
		//***********************************
		
		bankerC = new VBox(5, displayCards(bankerC, bankerHand), dispBankerHand);
		bankerC.setVisible(false);
		
		
		borderPane.setRight(bankerC);
		borderPane.setCenter(paneCenter);
		borderPane.setBottom(playerBetChoices);
		playerBetChoices.setAlignment(Pos.CENTER);
		
		
		//***************************************************************
		//* The Horizontal box that has which player you want to bid on	*
		//***************************************************************
		
		playerBttn.setDisable(true);
		bankerBttn.setDisable(true);
		drawBttn.setDisable(true);
		
		
		//***********************************************
		//* User sets bet when he clicks on a button	*
		//***********************************************
		
		pause.setOnFinished(event -> {
			playerC.setVisible(true);
			bankerC.setVisible(true);
			gameResults.setVisible(true);
			newRound.setVisible(true);
			newRound.setDisable(false);
			currWins.setVisible(true);
			betCorrect.setVisible(true);
			
		});
		
		//***********************************************
		//* If the user wants to reset or start			*
		//* a new round, the game is pretty much reset	*
		//* except for the total amount					*
		//***********************************************
		EventHandler<ActionEvent> extraRounds = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				startBttn.setDisable(false);
				startBttn.setVisible(true);
				playerBttn.setDisable(true);
				bankerBttn.setDisable(true);
				drawBttn.setDisable(true);
				newRound.setVisible(false);
				betOn.setVisible(false);
				currWins.setVisible(false);
				
				validBet.setText("");
				betCorrect.setVisible(false);
				
				initGame();
								
				playerC = new VBox(10,displayCards(playerC, playerHand), dispPlayerHand);
				playerC.setVisible(false);
				borderPane.setLeft(playerC);
						
				
				bankerC = new VBox(10, displayCards(bankerC, bankerHand), dispBankerHand);
				bankerC.setVisible(false);
				borderPane.setRight(bankerC);
				
				betAmount.setVisible(false);
				gameResults.setVisible(false);
				
				playerC.setVisible(false);
				bankerC.setVisible(false);
				
				currentBet = 0.0;
				
				text.clear();
				text.setDisable(true);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		};
		
		newRound.setOnAction(extraRounds);
		
		
		//***********************************************
		//* This event handler sets the game conditions	*
		//* whenever a button is pressed				*
		//***********************************************
		
		EventHandler<ActionEvent> buttonPress = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				text.getText();
				
				try
				{
					currentBet += Double.parseDouble(text.getText());
					betAmount.setText("Bet is " + currentBet);
					text.clear();
					betAmount.setVisible(true);
					playerBttn.setDisable(true);
					bankerBttn.setDisable(true);
					drawBttn.setDisable(true);
					text.setDisable(true);
					pause.play();
					
					totalWinnings = evaluateWinning();
					currWins.setText("Winnings : " + totalWinnings);
					betOn.setText("You bet on: " + placedBet);
					betOn.setVisible(true);
					validBet.setText("");
				}
				catch(NumberFormatException e)
				{
				  currentBet += 0;
				  text.clear();
				  validBet.setText("Invalid bet, enter valid number");
				}
								
			}
		};
		
		//*******************************************
		//* When the player button is pressed		*
		//* the it is set on who the user placed 	*
		//* their bet on							*
		//*******************************************
		
		playerBttn.addEventHandler(ActionEvent.ACTION, (e) -> {placedBet = "Player";});
		playerBttn.addEventHandler(ActionEvent.ACTION, buttonPress);

		bankerBttn.addEventHandler(ActionEvent.ACTION, (e) -> {placedBet = "Banker";});
		bankerBttn.addEventHandler(ActionEvent.ACTION, buttonPress);
		
		drawBttn.addEventHandler(ActionEvent.ACTION, (e) -> {placedBet = "Tie";});
		drawBttn.addEventHandler(ActionEvent.ACTION, buttonPress);
		
		
		//*******************
		//* Reset the game	*
		//*******************
		resetOption.addEventHandler(ActionEvent.ACTION, extraRounds);
		resetOption.addEventHandler(ActionEvent.ACTION, (e) -> {totalWinnings = 0.0;});

		
		//colors
		borderPane.setStyle("-fx-background-color: green;");
		menu.setStyle("-fx-background-color: BLANCHEDALMOND;");
		paneCenter.setStyle("-fx-background-color: green;");
		validBet.setStyle("-fx-background-color: RED;");
		
		//set scene
		scene = new Scene(borderPane,650, 650);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}

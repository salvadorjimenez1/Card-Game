import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Card {
	private String suite;
	private int value;
	private StackPane cardDisp;
	
	Card(String suite, int value){
		this.suite = suite;
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	public void setVal(int val) {
		this.value = val;
	}
	
	public String getSuite() {
		return suite;
	}
	
	public StackPane getDisplay() {
		
		cardDisp = new StackPane();
		Label name = new Label();
		Text val = new Text();
		
		if(getValue() == 1) {
			val.setText("Ace");
		}
		else if(getValue() == 10) {
			val.setText("10");
		}
		else if(getValue() == 11) {
			val.setText("King");
		}
		else if(getValue() == 12) {
			val.setText("Queen");
		}
		else if(getValue() == 13) {
			val.setText("Jack");
		}
		else {
			val.setText(Integer.toString(getValue()));
		}
		
		StackPane.setAlignment(cardDisp, Pos.BASELINE_CENTER);
		StackPane.setMargin(val, new Insets(80,95,30,70)); 
		StackPane.setAlignment(val, Pos.TOP_LEFT);
		
		if(getSuite() == "Spades") {
			name.setText("Spades");
			name.setTextFill(Color.BLACK);			
			cardDisp.getChildren().addAll(new Rectangle(100,125,Color.WHITE), name, val);

		}
		else if(getSuite() == "Clubs"){
			name.setText("Clubs");
			name.setTextFill(Color.BLACK);
			cardDisp.getChildren().addAll(new Rectangle(100,125,Color.WHITE), name, val);
		}
		else if(getSuite() == "Hearts") {
			name.setText("Hearts");
			name.setTextFill(Color.RED);
			cardDisp.getChildren().addAll(new Rectangle(100,125,Color.WHITE), name, val);
		}
		else {
			name.setText("Diamonds");
			name.setTextFill(Color.RED);
			cardDisp.getChildren().addAll(new Rectangle(100,125,Color.WHITE), name, val);
		}
		
		return cardDisp;
	}

}

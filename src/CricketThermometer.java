/*
 * Kurt Kangas
 * CIS 2212 Java Software Development I
 * JavaFX Cricket Thermometer - Gives temperature based on cricket chirps
 */

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class CricketThermometer extends Application {
	private double Temperature = 1.00;
	@SuppressWarnings("unused")
	private double CalculationNumber = 0;

	protected TextField txt1 = new TextField();
	protected TextField txt2 = new TextField();
	protected TextField label = new TextField("0.00");
	@Override
	public void start(Stage stage) {	
		try {		
			stage.setTitle("Cricket Thermometer");

			FlowPane root = new FlowPane();
			root.setPadding(new Insets(21,22,23,24));
			root.setVgap(20);
			root.setHgap(20);
			double x = 450;
			double y = 475;

			root.getChildren().add(Introduction(x, y));
			root.getChildren().add(CricketTypeSelection(x, y));
			root.getChildren().add(InputInfo(x, y));
			root.getChildren().add(TemperatureDisplay());
			root.getChildren().add(Exit());

			// create a Scene
			Scene scene = new Scene(root, x, y);

			// set the scene
			stage.setScene(scene);

			stage.show();

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public Label Introduction(double x, double y) {
		Label label = new Label("\tThis is a Cricket Thermometer; \nCounting the number of chirps in a certain amount of time will calculate the air temperature.");
		label.setPrefWidth(x);

		label.setWrapText(true);
		return label;
	}

	public BorderPane CricketTypeSelection(double x, double y) {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(10, 10, 10, 10));

		VBox vbox = new VBox(20);
		Label label = new Label("Cricket Type");
		label.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 20));

		RadioButton rb1 = new RadioButton("Common field cricket");
		RadioButton rb2 = new RadioButton("Snowy Tree cricket");
		RadioButton rb3 = new RadioButton("Common Katydid");
		vbox.setSpacing(10);
		vbox.getChildren().addAll(label, rb1, rb2, rb3);
		pane.setLeft(vbox);

		ToggleGroup group = new ToggleGroup();
		rb1.setToggleGroup(group);
		rb2.setToggleGroup(group);
		rb3.setToggleGroup(group);

		rb1.setOnAction(e -> {
			if (rb1.isSelected()) {
				this.Temperature = 1.00;
				Image image = new Image("image/CommonFieldCricket.jpg");
				ImageView imageview = new ImageView(image);
				imageview.setFitWidth(x/4);
				imageview.setFitHeight(y/4);
				pane.setRight(imageview);
			}
		});
		rb2.setOnAction(e -> {
			if (rb2.isSelected()) {
				this.Temperature = 2.00;
				Image image = new Image("image/SnowyTreeCricket.jpg");
				ImageView imageview = new ImageView(image);
				imageview.setFitWidth(x/4);
				imageview.setFitHeight(y/4);
				pane.setRight(imageview);
				//pane.setAlignment(imageview, null)
			}
		});
		rb3.setOnAction(e -> {
			if (rb3.isSelected()) {
				this.Temperature = 3.00;
				Image image = new Image("image/CommonKatydidCricket.jpg");
				ImageView imageview = new ImageView(image);
				imageview.setFitWidth(x/4);
				imageview.setFitHeight(y/4);
				pane.setRight(imageview);
			}
		});
		return pane;
	}

	public BorderPane TemperatureDisplay() {
		BorderPane pane = new BorderPane();

		VBox vbox = new VBox();
		Label lblTemperature = new Label("Temperature");
		lblTemperature.setTextFill(Color.BLACK);
		lblTemperature.setFont(new Font("Arial", 20));
		vbox.getChildren().add(lblTemperature);
		vbox.getChildren().add(this.label);
		this.label.setMinWidth(150); // added
		lblTemperature.setMinWidth(200); // added

		pane.setBottom(vbox);
		return pane;
	}

	public BorderPane Exit() {
		BorderPane pane = new BorderPane();
		VBox vbox = new VBox();
		vbox.setSpacing(10);

		Button btExit = new Button("Exit");
		btExit.setOnAction(e -> {
			System.exit(0);
		});
		btExit.setMinWidth(50);
		vbox.setAlignment(Pos.BOTTOM_RIGHT);
		vbox.getChildren().add(btExit);
		BorderPane.setAlignment(vbox, Pos.BOTTOM_RIGHT);
		pane.getChildren().add(vbox);
		vbox.setLayoutX(200);
		vbox.setLayoutY(30);
		return pane;
	}

	public GridPane InputInfo(double x, double y) {
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(11, 12, 13, 14));
		pane.setHgap(20);
		pane.setVgap(20);

		pane.add(new Label("Number of chirps: "), 0, 0);
		pane.add(this.txt1, 1, 0);
		this.txt1.setStyle("-fx-background-color: limegreen");
		pane.add(new Label("Number of seconds: "), 0, 1);
		pane.add(this.txt2, 1, 1);
		this.txt2.setStyle("-fx-background-color: lightblue");
		Button btCalculate = new Button("Calculate");
		pane.add(btCalculate, 1, 3);
		GridPane.setHalignment(btCalculate, HPos.LEFT);
		this.label.setStyle("-fx-background-color: yellow");
		btCalculate.setOnAction(e -> {
			double NumberOfChirps = Double.parseDouble(this.txt1.getText());
			double NumberOfSeconds = Double.parseDouble(this.txt2.getText());
			double d = CalculateTemperature(NumberOfChirps, NumberOfSeconds);
			this.CalculationNumber = d;
			this.label.setText(Double.toString(d));	
		});
		return pane;
	}

	public double CalculateTemperature(double NumberOfChirps, double NumberOfSeconds) {
		double d = 0.0;
		double N = ((NumberOfChirps / NumberOfSeconds) * 60);
		if (this.Temperature == 1.00) {
			d = 50 + ((N - 40) / 4);
		}
		else if (this.Temperature == 2.00) {
			d = 50 + ((N - 92) / 4.7);
		}
		else {
			d = 60 + ((N - 19) / 3);
		}
		return d;
	}

	public static void main(String[] args) {	
		launch(args);
	}
}
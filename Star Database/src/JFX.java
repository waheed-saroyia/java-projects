import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * <Waheed Saroyia>
 * CPS142 – Fall 2022
 * Date: <10/5/22>
 * Instructor: Adam Divelbiss
 * Assignment: Program03
 * Purpose: <implement a simple graphical user interface for a Star database and will allow 
 * the user to display Star information for all stars in the database and for specific constellations.>
 */
public class JFX extends Application {

	// --------------------------
	// Application instance fields.
	// --------------------------
	// UI Related Fields
	private Stage stage;
	//	private Scene scene; or use stage.getScene()


	// Data Fields
	private ArrayList<Star> stars;
	private ArrayList<Star> filteredStars;

	private ListView<String> listView;
	private Label selectedStarInfo;
	private Label comboBoxBrightestLabel;
	private Label comboBoxFarthestLabel;
	private Label comboBoxTotalLabel;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage){

		// save a reference to the star
		this.stage = primaryStage;

		// load the stars
		this.stars = loadData();

		// MenuBar
		MenuBar menuBar = new MenuBar();



		// Add the File menu and the Mode menu
		menuBar.getMenus().add(buildFileMenu());
		menuBar.getMenus().add(buildModeMenu());

		// Add a new BorderPane
		BorderPane rootPane = new BorderPane();
		rootPane.setTop(menuBar);
		rootPane.setCenter(getMainScene());

		// create and display the scene
		Scene scene = new Scene(rootPane, 900,450); // set the width and height

		// Using a css file in our JavaFX application
		scene.getStylesheets().add("modeLight.css");


		// Set the scene in the stage
		primaryStage.setScene(scene);

		// Set the title
		primaryStage.setTitle("Star Database");

		// Show the stage
		primaryStage.show();
	}


	/**
	 * Builds and returns the file Menu
	 * @return fileMenu
	 */
	private Menu buildFileMenu() {

		Menu fileMenu = new Menu("File");

		MenuItem exitItem = new MenuItem("Exit");

		// Add an event handler to our exitItem
		exitItem.setOnAction(event->{
			stage.close(); // Will quit the JavaFX app.
		});

		// Add the exit Item to the menu
		fileMenu.getItems().add(exitItem);		

		return fileMenu;
	}


	/**
	 * Builds and returns the mode Menu
	 * @return
	 */
	private Menu buildModeMenu() {

		Menu modeMenu = new Menu("Mode");

		RadioMenuItem modeLight = new RadioMenuItem("Light");
		RadioMenuItem modeDark = new RadioMenuItem("Dark");

		modeLight.setSelected(true); // Initialize to the startup mode


		ToggleGroup toggleGroup = new ToggleGroup();
		modeLight.setToggleGroup(toggleGroup);
		modeDark.setToggleGroup(toggleGroup);

		// Add the event handlers
		modeLight.setOnAction(event->{
			stage.getScene().getStylesheets().clear();
			stage.getScene().getStylesheets().add("modeLight.css");
		});

		modeDark.setOnAction(event->{
			stage.getScene().getStylesheets().clear();
			stage.getScene().getStylesheets().add("modeDark.css");
		});

		modeMenu.getItems().add(modeLight);
		modeMenu.getItems().add(modeDark);

		return modeMenu;
	}


	/**
	 * Builds the main scene
	 * @return root
	 */
	private Pane getMainScene() {

		
		


		// Setup the Right Control Panel
		//		Label rightLabel = new Label("Right label");
		VBox rightColumn = getComboBoxControls();

		// Setup the Root container
		//		HBox root = new HBox(20, leftContainer, middleColumn, rightColumn, getListViewControls());
		HBox root = new HBox(20, rightColumn, getListViewControls(),getLables());
		root.setAlignment(Pos.TOP_LEFT);
		root.setPadding(new Insets(20,20,20,20)); // Insets values: top, right, bottom, left
		root.setPrefSize(900, 450);		

		return root;
	}

	


	

		

	/**
	 * Demonstrate ComboBox controls
	 * @return container
	 */
	private VBox getComboBoxControls() {
		// Setup the Middle Control Panel
		Label titleLabel = new Label("Select a Constallation");
		titleLabel.getStyleClass().add("label-title");  // don't use .css

		// Add the ComboBox controls here
		ComboBox<String> comboBox = new ComboBox<>();
		// prevent editing
		comboBox.setEditable(false);
		// Add some items
		comboBox.getItems().add("All Stars");
		comboBox.getItems().add("AQL");
		comboBox.getItems().add("AND");
		comboBox.getItems().add("CEP");
		comboBox.setValue("All Stars");
		
		
		Label sortLabel = new Label("Sort By:");
		RadioButton sortMagnitudeButton = new RadioButton("Magnitude");
		RadioButton sortDistanceButton = new RadioButton("Distance");
		
		ToggleGroup toggleGroup = new ToggleGroup();
		sortMagnitudeButton.setToggleGroup(toggleGroup);
		sortDistanceButton.setToggleGroup(toggleGroup);
		sortMagnitudeButton.setSelected(true);

		

		Label comboBoxStatLabel = new Label("Statistics:");
		comboBoxTotalLabel = new Label(String.format("Total: "));
		comboBoxBrightestLabel = new Label(String.format("Brightest: "));
		comboBoxFarthestLabel = new Label(String.format("Farthest: "));

		VBox container = new VBox(20,titleLabel,comboBox,comboBoxStatLabel,sortLabel,sortMagnitudeButton,sortDistanceButton,comboBoxTotalLabel,comboBoxBrightestLabel,comboBoxFarthestLabel);
		container.setAlignment(Pos.TOP_LEFT);
		container.setPadding(new Insets(20,20,20,20)); // Insets values: top, right, bottom, left

		StarSelector brighter = (a,b) -> a.getVisualMagnitude() < b.getVisualMagnitude() ? a : b;
		
		// select the farther star
		// TODO: replace null with a Lambda expression or anonymous inner class to return the farther star
		StarSelector farther = (a,b) -> a.getDistance() > b.getDistance() ? a : b;
		
		// Reduce a star array with the given selector
		// TODO: replace null with a Lambda expression or anonymous inner class to reduce an array of Stars using a StarSelector
		StarReducer reducer = new StarReducer(){

			@Override
			public Star reduce(ArrayList<Star> array,StarSelector selector) {
				Star selected = array.get(0);
				// use enhanced for loop to iterate entire array
				for(Star current : array) {
					// compare current with selected and get selected
					selected = selector.select(selected, current);
				}
				
				return selected;
			}
			
		};
		
		// Filter the stars given an array and a predicate
		// TODO: replace null with a Lambda expression or anonymous inner class to filter an array of Stars using a StarPredicate
		
		
		
		// Helper functions for the ComboBox control
		StarFilter filter = (array, predicate) -> {
			ArrayList<Star> filtered = new ArrayList<>();
			for (Star current : array) {
				if (predicate.isOK(current)) {
					filtered.add(current);
				}
			}
			return filtered;
		};
		
		comboBoxTotalLabel.setText(String.format("Total: %d", stars.size()));
		comboBoxBrightestLabel.setText(String.format("Brightest: %.2f", reducer.reduce(stars,brighter).getVisualMagnitude()));
		comboBoxFarthestLabel.setText(String.format("Farthest: %.1f", reducer.reduce(stars,farther).getDistance()));


		// Handle the combo box action
		comboBox.setOnAction((event)->{
			// Get the current comboBox selection
			String currentSelection = comboBox.getValue();
			// Do something with the selection
			//comboBoxResultLabel.setText("Current selection: \n" + currentSelection);
			// Variable to hold the first index of star in the constellation
			



			
			


			// Update the filteredStars and then update the Listview
			this.filteredStars = filter.filter(
					this.stars, 
					(star)->{
						return currentSelection.equalsIgnoreCase("All Stars") || currentSelection.equalsIgnoreCase(star.getConstellationName());
					}
					);
			
			
			comboBoxTotalLabel.setText(String.format("Total: %d", filteredStars.size()));
			comboBoxBrightestLabel.setText(String.format("Brightest: %.2f", reducer.reduce(filteredStars,brighter).getVisualMagnitude()));
			comboBoxFarthestLabel.setText(String.format("Farthest: %.1f", reducer.reduce(filteredStars,farther).getDistance()));
			
			selectedStarInfo.setText("");
			listView.getSelectionModel().clearSelection();
			listView.getItems().clear();
			for (Star star : this.filteredStars) {
				String itemDisplay = String.format("%s in %s, %.1f, %.2f(ly)",star.getName(),star.getConstellationName().toUpperCase(),star.getVisualMagnitude(),star.getDistance());
				listView.getItems().add(itemDisplay);
			}



		});

		return container;
	}


	/**
	 * Demonstrate ListView controls
	 * @return container
	 */
	private VBox getListViewControls() {

		Label titleLabel = new Label("Select a Star");
		titleLabel.getStyleClass().add("label-title");  // don't use .css

		// TODO: Put ListView controls here
		ListView<String> listView = new ListView<>();
		listView.setPrefWidth(325);
		this.listView = listView; // capture this listview as an instance field.
		// Add manually
		//		listView.getItems().add("List Item 1");
		//		listView.getItems().add("List Item 2");
		//		listView.getItems().add("List Item 3");

		// Add multiple manually.
		//listView.getItems().addAll("Item 1", "Item 2", "Item 3");

		// Add multiple items from an ArrayList<Stars>
		for (Star star : this.stars) {
			String itemDisplay = String.format("%s in %s, %.1f, %.2f(ly)",star.getName(),star.getConstellationName().toUpperCase(),star.getVisualMagnitude(),star.getDistance());
			listView.getItems().add(itemDisplay);
		}


		// create some user output labels
		/*Label selectedStarName = new Label("Star: ");
		Label selectedStarDistance= new Label("distance: ");
		VBox outputGroup = new VBox(10, selectedStarName, selectedStarDistance);
		outputGroup.setAlignment(Pos.TOP_RIGHT);
		outputGroup.setPadding(new Insets(20,20,20,20)); // Insets values: top, right, bottom, left
		 */


		VBox container = new VBox(20,titleLabel, listView);
		container.setAlignment(Pos.TOP_LEFT);
		container.setPadding(new Insets(20,20,20,20)); // Insets values: top, right, bottom, left



		return container;
	}

	/**
	 * Creates labels for the Selected star
	 * @return container
	 */
	private VBox getLables() {

		// create some user output labels
		Label selectedStarLabel = new Label("Selected Star: ");
		selectedStarLabel.getStyleClass().add("label-title");
		selectedStarInfo= new Label();
		VBox outputGroup = new VBox(10, selectedStarLabel, selectedStarInfo);
		outputGroup.setAlignment(Pos.TOP_LEFT);
		outputGroup.setPadding(new Insets(0,0,0,0)); // Insets values: top, right, bottom, left

		VBox container = new VBox(20,outputGroup);
		container.setAlignment(Pos.TOP_RIGHT);
		container.setPadding(new Insets(20,20,20,20));

		// TODO Put ListView Event Handlers here
		listView.getSelectionModel().selectedItemProperty().addListener(event->{

			// Get the index of the selected item
			int selectedIndex = listView.getSelectionModel().getSelectedIndex();


			// List the star information 
			if(filteredStars==null)
			{
				Star star = stars.get(selectedIndex);
				selectedStarLabel.setText("Selected Star:");
				selectedStarInfo.setText(star.toString());
			}
			else
			{
				Star star = filteredStars.get(selectedIndex);
				selectedStarLabel.setText("Selected Star:");
				selectedStarInfo.setText(star.toString());
			}


			// update the output controls.
			//selectedStarLabel.setText("Selected Star:");
			//selectedStarInfo.setText(star.toString());
			//selectedStarDistance.setText(String.format("Distance: %.1f", star.getDistance()));



		});

		return container;

	}


	/**
	 * Load Star data from a file.
	 * @return
	 * @throws FileNotFoundException
	 */

	public static ArrayList<Star> loadData() /*throws FileNotFoundException*/ // 
	{

		// 2022.10.20.ADivelbiss - UPDATE TO CATCH FILENOTFOUND EXCEPTION.
		ArrayList<Star> data = new ArrayList<>();
		Scanner scanner = null;
		try {
			File file = new File("mag_5_stars.csv");
			scanner = new Scanner(file);
			// skip the first line  since it contains the column headers
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				Star shape = new Star(scanner.nextLine());
				data.add(shape);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("ERROR: " + ex.getLocalizedMessage());			
		} finally {
			if (scanner!=null) scanner.close();
		}

		return data;
	}

}

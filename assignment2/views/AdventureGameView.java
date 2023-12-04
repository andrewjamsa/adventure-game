package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.PlayerHealth.PlayerHealthBar;
import ColorWay.ColorWay;
import ColorWay.ColorWayFactory;
import com.sun.scenario.Settings;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.AccessibleRole;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class AdventureGameView.
 * <p>
 * This is the Class that will visualize your model.
 * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 * <p>
 * ZOOM LINK: https://drive.google.com/file/d/1ZDXW6s9ML44KhoF5pefP0Oqqx87CxsXB/view?usp=share_link
 * PASSWORD: (no password) if link requires password, kindly contact the host of the link.
 */
public class AdventureGameView {

    int fontSize; // font size for text

    AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton, settingsButton; //buttons
    Label timerLabel;
    Boolean helpToggle = false; //is help on display?

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input

    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing

    private ArrayList<Node> grid11 = new ArrayList<>();
    private VBox helpVBox = null;

    private ColorWay gameColorWay;
    private String gameFont;
    private String colorWayName;
    private ColorWayFactory gameColorWayFactory;
    private boolean settingsToggle = false;

    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            int hh;
            int mm;
            int ss = 0;
            if (time >= 3600) {
                hh = time / 3600;
                mm = (time % 3600) / 60;
                ss = (time % 3600 % 60);
            } else {
                hh = 0;
                if (time >= 60) {
                    mm = (time % 3600) / 60;
                    ss = (time % 60);
                } else {
                    mm = 0;
                    ss = time;
                }
            }
            time -= 1;
            int finalSs = ss;
            Platform.runLater(() -> timerLabel.setText(String.format("%d:%d:%d", hh, mm, finalSs)));
            Platform.runLater(() -> {
                if (time < 0) {
                    timer.cancel();
                    updateScene("YOU LOST AGAINST THE TIME");
                    PauseTransition pause = new PauseTransition(Duration.seconds(10));
                    pause.setOnFinished(event -> {
                        Platform.exit();
                    });
                    pause.play();
                }
                ;
            });
        }
    };
    private int time = 0;

    private PlayerHealthBar healthBar;

    public AdventureGameView(AdventureGame model, Stage stage) {
        this(model, stage, 16); // default fontsize as 16
    }

    public AdventureGameView(AdventureGame model, Stage stage, int fontSize) {
        this.model = model;
        this.stage = stage;
        this.fontSize = fontSize;

        stage.requestFocus();
        SettingsView settingsView = new SettingsView(this);
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {

        // setting up the stage
        this.stage.setTitle("Group 31's Adventure Game");

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        this.gameColorWayFactory = new ColorWayFactory();
        this.gameColorWay = gameColorWayFactory.getColorWay(colorWayName);

        System.out.println(colorWayName);
        System.out.println(this.gameColorWay);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                this.gameColorWay.getBoardColor(),
                new CornerRadii(0),
                new Insets(0)
        )));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow(Priority.SOMETIMES); //let some columns grow to take any extra space
        column1.setHgrow(Priority.SOMETIMES);

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints(550);
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow(Priority.SOMETIMES);
        row3.setVgrow(Priority.SOMETIMES);

        gridPane.getColumnConstraints().addAll(column1, column2, column1);
        gridPane.getRowConstraints().addAll(row1, row2, row1);

        // Buttons
        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 100, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 100, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        customizeButton(helpButton, 200, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();

        timerLabel = new Label("Normal Mode");
        timerLabel.setId("Timer");
        timerLabel.setAlignment(Pos.CENTER);
        timerLabel.setPrefSize(100, 50);
        timerLabel.setFont(Font.font("Arial", FontWeight.BOLD, this.getFontSize() - 2));
        timerLabel.setStyle("-fx-background-color: white; -fx-text-fill: #17871b;");

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(saveButton, helpButton, loadButton, this.timerLabel);
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);

        inputTextField = new TextField();

        inputTextField.setFont(new Font(this.gameFont, this.getFontSize()));

        inputTextField.setFocusTraversable(true);
        inputTextField.setStyle(" -fx-text-fill: #" + gameColorWay.getTextBoxColor().toString().substring(2) + ";");
        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent(); //attach an event to this input field

        //labels for inventory and room items
        Label objLabel = new Label("Objects in Room");
        objLabel.setAlignment(Pos.CENTER);


        objLabel.setStyle("-fx-text-fill:#" + gameColorWay.getTextColor().toString().substring(2) + " ;");
        objLabel.setFont(new Font(this.gameFont, this.getFontSize()));

        Label invLabel = new Label("Your Inventory");
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: #" + gameColorWay.getTextColor().toString().substring(2) + ";");
        invLabel.setFont(new Font(this.gameFont, this.getFontSize()));

        
        //add all the widgets to the GridPane
        gridPane.add(objLabel, 0, 0, 1, 1);  // Add label
        gridPane.add(topButtons, 1, 0, 1, 1);  // Add buttons
        gridPane.add(invLabel, 2, 0, 1, 1);  // Add label

        Label commandLabel = new Label("What would you like to do?");

        commandLabel.setStyle("-fx-text-fill:#" + this.getColorWay().getTextColor().toString().substring(2) + ";");
        commandLabel.setFont(new Font(this.gameFont, this.getFontSize()));

        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms


        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #" + gameColorWay.getBoardColor().toString().substring(2) + ";");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add(textEntry, 0, 2, 3, 1);

        // add the health bar
        healthBar = new PlayerHealthBar(model.getPlayer(), () -> {

            if (!healthBar.isAlive()) {
                updateScene("PLAYER DIED! GAME OVER.");
            }
            return null;
        });

        // add the health bar to the gridpane
        gridPane.add(healthBar.getBar(), 1, 3, 1, 1);

        // Render everything
        var scene = new Scene(gridPane, 1000, 800);
        scene.setFill(gameColorWay.getBoardColor());
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();

    }

    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name        ARIA name
     * @param shortString ARIA accessible text
     * @param longString  ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w           width
     * @param h           height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);

        inputButton.setFont(new Font(this.gameFont, this.getFontSize()));
        inputButton.setStyle("-fx-background-color: #" + gameColorWay.getButtonColor().toString().substring(2) + "; -fx-text-fill: #" + gameColorWay.getButtonTextColor().toString().substring(2) + ";");
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute
     * <p>
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped
     * string to submitEvent for processing.
     * <p>
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        // button.add

        inputTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                submitEvent(inputTextField.getText().stripTrailing().stripLeading());
                inputTextField.setText("");
            } else if (event.getCode() == KeyCode.TAB) {
                if (gridPane.getChildren().indexOf(inputTextField) == gridPane.getChildren().size() - 1) {
                    gridPane.getChildren().get(0).requestFocus();
                } else {
                    gridPane.getChildren().get(gridPane.getChildren().indexOf(inputTextField) + 1).requestFocus();
                }
            }
        });
    }

    private void showTimer() {
        this.timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent(String text) {

        if (!this.model.getPlayer().isAlive()) {
            updateScene("PLAYER DIED! GAME OVER.");
            return;
        }

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }
        String[] checkTimer = text.split(" ");
        if (checkTimer[0].equalsIgnoreCase("TIMER") || checkTimer[0].equalsIgnoreCase("T")) {
            this.time = 0;
            if (checkTimer.length == 1) {
                updateScene("Please enter the timer in the format of TIMER hh/mm/ss");
                return;
            }
            if (checkTimer.length == 2) {
                String[] timeInString = checkTimer[1].split(":");
                for (int j = 0; j < timeInString.length; j++) {
                    try {
                        Integer.parseInt(timeInString[j]);
                    } catch (NumberFormatException e) {
                        updateScene("Please enter the timer in the format of TIMER hh/mm/ss");
                    }
                }
                if (timeInString.length == 3) {
                    this.time += Integer.parseInt(timeInString[2]) + Integer.parseInt(timeInString[1]) * 60
                            + Integer.parseInt(timeInString[0]) * 3600;
                } else if (timeInString.length == 2) {
                    this.time += Integer.parseInt(timeInString[1]) + Integer.parseInt(timeInString[0]) * 60;
                } else if (timeInString.length == 1) {
                    this.time += Integer.parseInt((timeInString[0]));
                }
                showTimer();
                return;
            }

        }
        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            updateScene(model.player.getCurrentRoom().getRoomDescription());
            updateItems();
            articulateRoomDescription();
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> {
                submitEvent("FORCED");
            });
            pause.play();
        }
    }


    /**
     * showCommands
     * __________________________
     * <p>
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the
     * current room.
     */
    private void showCommands() {
        roomDescLabel.setStyle("-fx-text-fill: #" + gameColorWay.getTextColor().toString().substring(2) + ";");
        roomDescLabel.setText("The possible moves are: " + model.player.getCurrentRoom().getCommands());
    }

    /**
     * updateScene
     * __________________________
     * <p>
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     *
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {

        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        roomDescLabel.setPrefWidth(500);
        roomDescLabel.setPrefHeight(500);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        roomDescLabel.setStyle("-fx-text-fill: #" + gameColorWay.getTextColor().toString().substring(2) + ";");
        VBox roomPane = new VBox(roomImageView, roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle("-fx-background-color: #" + gameColorWay.getBoardColor().toString().substring(2) + ";");

        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
    }

    /**
     * formatText
     * __________________________
     * <p>
     * Format text for display.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty())
                roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");

        roomDescLabel.setFont(new Font(this.gameFont, this.getFontSize()));
    }

    /**
     * getRoomImage
     * __________________________
     * <p>
     * Get the image for the current room and place
     * it in the roomImageView
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
     * <p>
     * This method is partially completed, but you are asked to finish it off.
     * <p>
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     * <p>
     * Images of each object are in the assets
     * folders of the given adventure game.
     */
    public void updateItems() {
        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        //please use setAccessibleText to add "alt" descriptions to your images!
        //the path to the image of any is as follows:
        //this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";
        objectsInInventory.getChildren().clear();
        objectsInRoom.getChildren().clear();

        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background: #" + gameColorWay.getBoardColor().toString().substring(2) + "; -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO, 0, 1);
        for (AdventureObject element : model.player.getCurrentRoom().objectsInRoom) {
            Button button = null;
            try {
                Image image = new Image(this.model.getDirectoryName() + "/objectImages/" + element.getName() + ".jpg");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                button = new Button(element.getName(), imageView);
            } catch (Exception ignored) {
            }
            if (button == null) {
                button = new Button(element.getName());
            }
            Button finalButton = button;
            button.setOnMouseClicked(event -> {
                if (objectsInInventory.getChildren().contains(finalButton)) {
                    objectsInInventory.getChildren().remove(finalButton);
                    objectsInRoom.getChildren().add(finalButton);
                    submitEvent("DROP " + finalButton.getText());
                } else {
                    objectsInInventory.getChildren().add(finalButton);
                    objectsInRoom.getChildren().remove(finalButton);
                    submitEvent("TAKE " + finalButton.getText());
                }
            });
            makeButtonAccessible(button, element.getName(), element.getDescription(), element.getDescription());
            objectsInRoom.getChildren().add(button);
            /* the 2 lines below was to only check what would scrollpane looks like if there are more than 1 object.
            Button book = new Button("book", new ImageView(new Image(this.model.getDirectoryName() + "/objectImages/" + "book" + ".jpg")));
            objectsInRoom.getChildren().add(book);
             */
        }
        // --------------------------------------------------------------------------
        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background: #" + gameColorWay.getBoardColor().toString().substring(2) + "; -fx-background-color:transparent;");
        gridPane.add(scI, 2, 1);

        for (AdventureObject element : model.player.inventory) {
            Button button = null;
            try {
                Image image = new Image(this.model.getDirectoryName() + "/objectImages/" + element.getName() + ".jpg");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                button = new Button(element.getName(), imageView);
            } catch (Exception ignored) {
            }
            if (button == null) {
                button = new Button(element.getName());
            }
            Button finalButton = button;
            button.setOnMouseClicked(event -> {
                if (objectsInInventory.getChildren().contains(finalButton)) {
                    objectsInInventory.getChildren().remove(finalButton);
                    objectsInRoom.getChildren().add(finalButton);
                    submitEvent("DROP " + finalButton.getText());
                } else {
                    objectsInInventory.getChildren().add(finalButton);
                    objectsInRoom.getChildren().remove(finalButton);
                    submitEvent("TAKE " + finalButton.getText());
                }
            });
            makeButtonAccessible(button, element.getName(), element.getDescription(), element.getDescription());
            objectsInInventory.getChildren().add(button);
        }
    }

    /**
     * This method updates the helpVBox.
     */

    private void updateHelpVBox() {
        if (helpVBox == null) {
            TextArea helpTextArea = new TextArea();
            helpTextArea.setEditable(false);
            helpTextArea.setPrefHeight(1000);
            helpTextArea.setStyle("-fx-text-fill: #" + gameColorWay.getTextBoxColor().toString().substring(2) + ";");
            helpTextArea.setFont(new Font(this.gameFont, this.getFontSize()));
            helpTextArea.setText(model.getInstructions());
            helpVBox = new VBox();
            helpVBox.getChildren().add(helpTextArea);
        }
    }

    /*
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        updateHelpVBox();
        if (!helpToggle) {
            for (javafx.scene.Node element : new ArrayList<>(gridPane.getChildren())) {
                if (GridPane.getRowIndex(element) == 1 && GridPane.getColumnIndex(element) == 1) {
                    if (!grid11.contains(element)) {
                        grid11.add(element);
                    }
                }
            }
            gridPane.add(helpVBox, 1, 1);
            helpToggle = true;
            gridPane.getChildren().removeAll(grid11);

        } else {
            gridPane.getChildren().remove(helpVBox);
            for (Node element : grid11) {
                gridPane.add(element, 1, 1);
            }
            helpToggle = false;
        }
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }


    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited())
            musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3";
        else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3";
        musicFile = musicFile.replace(" ", "-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }

    /**
     * This method returns the font size
     *
     * @return fontSize
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * This method sets the font size
     *
     * @param fontSize the font size to set
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * This method gets the games colorway.
     *
     * @return the games colorway
     */
    public ColorWay getColorWay() {
        return gameColorWay;
    }

    /**
     * This method sets the games colorway.
     */
    public void setColorWay(ColorWay colorWay) {
        this.gameColorWay = colorWay;
    }

    /**
     * This method gets the games font.
     *
     * @return the games font
     */
    public String getGameFont() {
        return gameFont;
    }

    /**
     * This method sets the games font.
     */
    public void setGameFont(String gameFont) {
        this.gameFont = gameFont;
    }

    /**
     * This method gets the games colorway name.
     *
     * @return the games colorway name
     */
    public String getColorWayName() {
        return colorWayName;
    }

    /**
     * This method sets the games colorway name.
     */
    public void setColorWayName(String colorWayName) {
        this.colorWayName = colorWayName;
    }

    /**
     * This method sets the settings toggle.
     */
    public void setSettingsToggle(boolean settingsToggle) {
        this.settingsToggle = settingsToggle;
    }

}

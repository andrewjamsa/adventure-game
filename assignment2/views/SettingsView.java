package views;

import ColorWay.ColorWay;
import ColorWay.ColorWayFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.*;


/**
 * Class SettingsView.
 * <p>
 * This class is responsible for displaying the settings view.
 */
public class SettingsView {
    private AdventureGameView adventureGameView;
    private Button closeWindowButton;
    private Button changeColorButton;
    private ComboBox<Object> colorSelect; //the list of color options
    private ListView<String> colorList;
    private ListView<String> fontList;
    private ListView<String> fontSizeList;
    private ListView<String> difficultyList;
    private Button changeFontButton;
    private Button changeFontSizeButton;
    private Button changeDifficultyButton;
    private ColorWayFactory settingsColorWayFactory;

    /**
     * Constructor
     */
    public SettingsView(AdventureGameView adventureGameView) {
        this.adventureGameView = adventureGameView;

        System.out.println("Pick the settings you would like to change.");
        System.out.println("Then click the start game button to start the game.");

        //set default colorway and font
        this.adventureGameView.setColorWayName("dark");
        this.adventureGameView.setGameFont("Arial");

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(adventureGameView.stage);

        // --- Colorway ---
        changeColorButton = new Button("Change Color");
        changeColorButton.setId("Change Color"); // DO NOT MODIFY ID
        AdventureGameView.makeButtonAccessible(changeColorButton, "select color", "This is the button to select a colorway", "Use this button to indicate a colorway you would like to load.");

        // --- Font Type ---
        changeFontButton = new Button("Change Font");
        changeFontButton.setId("Change Font"); // DO NOT MODIFY ID
        AdventureGameView.makeButtonAccessible(changeFontButton, "select font", "This is the button to select a font", "Use this button to indicate a font you would like to load.");

        // --- Font Size ---
        changeFontSizeButton = new Button("Change Font Size");
        changeFontSizeButton.setId("Change Font Size"); // DO NOT MODIFY ID
        AdventureGameView.makeButtonAccessible(changeFontSizeButton, "select font size", "This is the button to select a font size", "Use this button to indicate the size of font you would like to load.");

        // --- Difficulty ---
        changeDifficultyButton = new Button("Change Difficulty");
        changeDifficultyButton.setId("Change Difficulty"); // DO NOT MODIFY ID
        AdventureGameView.makeButtonAccessible(changeDifficultyButton, "select difficulty", "This is the button to select a difficulty", "Use this button to indicate the difficulty you would like to load.");

        colorList = new ListView<>(); //to hold all the file names
        colorList.setId("ColorList");  // DO NOT MODIFY ID
        colorList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        colorList.getItems().add("Dark ColorWay");
        colorList.getItems().add("Light ColorWay");
        colorList.getItems().add("High Contrast ColorWay");
        colorList.setPrefHeight(100);

        fontList = new ListView<>(); //to hold all the file names
        fontList.setId("FontList");  // DO NOT MODIFY ID
        fontList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fontList.getItems().add("Arial");
        fontList.getItems().add("Times New Roman");
        fontList.getItems().add("Comic Sans");
        fontList.setPrefHeight(100);

        fontSizeList = new ListView<>(); //to hold all the file names
        fontSizeList.setId("FontSizeList");  // DO NOT MODIFY ID
        fontSizeList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fontSizeList.getItems().add("Small");
        fontSizeList.getItems().add("Medium");
        fontSizeList.getItems().add("Large");
        fontSizeList.setPrefHeight(100);

        difficultyList = new ListView<>(); //to hold all the file names
        difficultyList.setId("DifficultyList");  // DO NOT MODIFY ID
        difficultyList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        difficultyList.getItems().add("Easy");
        difficultyList.getItems().add("Medium");
        difficultyList.getItems().add("Hard");
        difficultyList.setPrefHeight(100);

        GridPane gridPane = new GridPane();

        changeColorButton.setOnAction(e -> {
            try {
                selectColor(colorList);
            } catch (Exception ex) {
                System.out.println("Please select a color first.");
            }
        });

        changeFontButton.setOnAction(e -> {
            try {
                selectFont(fontList);
            } catch (Exception ex) {
                System.out.println("Please select a font first.");
            }
        });


        closeWindowButton = new Button("Start Game");

        changeFontSizeButton.setOnAction(e -> {
            try {
                selectFontSize(fontSizeList);
            } catch (Exception ex) {
                System.out.println("Please select a font size first.");
            }
        });

        changeDifficultyButton.setOnAction(e -> {
            try {
                selectDifficulty(difficultyList);
            } catch (Exception ex) {
                System.out.println("Please select a difficulty first.");
            }
        });

        closeWindowButton.setId("closeWindowButton"); // DO NOT MODIFY ID
        closeWindowButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        closeWindowButton.setPrefSize(200, 50);
        closeWindowButton.setFont(new

                Font(16));
        closeWindowButton.setOnAction(e ->

        {
            dialog.close();
            this.adventureGameView.intiUI();
        });
        AdventureGameView.makeButtonAccessible(closeWindowButton, "close window", "This is a button to close the load game window", "Use this button to close the load game window.");

        // --- Colorway ---
        Label changeColorLabel = new Label("Change Color:");
        changeColorLabel.setId("ChangeColorLabel");
        changeColorLabel.setStyle("-fx-text-fill: white;");
        gridPane.add(changeColorLabel, 0, 0);
        gridPane.add(colorList, 1, 0);
        gridPane.add(changeColorButton, 2, 0);

        // --- Font Type ---
        Label changeFontLabel = new Label("Change Font:");
        changeFontLabel.setId("ChangeFontLabel");
        changeFontLabel.setStyle("-fx-text-fill: white;");
        gridPane.add(changeFontLabel, 0, 1);
        gridPane.add(fontList, 1, 1);
        gridPane.add(changeFontButton, 2, 1);

        // --- Font Size ---
        Label changeFontSizeLabel = new Label("Change Font Size:");
        changeFontSizeLabel.setId("ChangeFontSizeLabel");
        changeFontSizeLabel.setStyle("-fx-text-fill: white;");
        gridPane.add(changeFontSizeLabel, 0, 2);
        gridPane.add(fontSizeList, 1, 2);
        gridPane.add(changeFontSizeButton, 2, 2);

        // --- Difficulty ---
        Label changeDifficultyLabel = new Label("Change Difficulty:");
        changeDifficultyLabel.setId("ChangeDifficultyLabel");
        changeDifficultyLabel.setStyle("-fx-text-fill: white;");
        gridPane.add(changeDifficultyLabel, 0, 3);
        gridPane.add(difficultyList, 1, 3);
        gridPane.add(changeDifficultyButton, 2, 3);

        // --- Close Window ---
        gridPane.add(closeWindowButton, 1, 4);
        gridPane.setStyle("-fx-background-color: #000000;");
        gridPane.setHgap(25);
        gridPane.setVgap(50);
        Scene dialogScene = new Scene(gridPane, 500, 500);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    /**
     * This method gets the colorSet combo box.
     *
     * @return the colorSelect combo box
     */
    public ComboBox<Object> getColorSelect() {
        return colorSelect;
    }

    /**
     * This method gets the color selected by the user and sets it in the game.
     *
     * @param colorList: List to of colors to be added.
     */
    private void selectColor(ListView<String> colorList) {
        String color = colorList.getSelectionModel().getSelectedItems().get(0);
        if (color != null) {
            switch (color) {
                case "Dark ColorWay":
                    System.out.println("Selected Dark ColorWay");
                    this.adventureGameView.setColorWayName("Dark");
                    break;
                case "Light ColorWay":
                    System.out.println("Selected Light ColorWay");
                    this.adventureGameView.setColorWayName("Light");
                    break;
                case "High Contrast ColorWay":
                    System.out.println("Selected High Contrast ColorWay");
                    this.adventureGameView.setColorWayName("HighContrast");
                    break;
            }
        }
    }

    /**
     * This method gets the font selected by the user and sets it in the game.
     *
     * @param fontList: List to of fonts to be added.
     */
    private void selectFont(ListView<String> fontList) {
        String font = fontList.getSelectionModel().getSelectedItems().get(0);
        if (font != null) {
            switch (font) {
                case "Arial":
                    System.out.println("Selected Arial");
                    this.adventureGameView.setGameFont("Arial");
                    break;
                case "Times New Roman":
                    System.out.println("Selected Times New Roman");
                    this.adventureGameView.setGameFont("Times New Roman");
                    break;
                case "Comic Sans":
                    System.out.println("Selected Comic Sans");
                    this.adventureGameView.setGameFont("Comic Sans MS");
                    break;
            }
        }
    }

    /**
     * This method gets the font size selected by the user and sets it in the game.
     *
     * @param fontSizeList List to of font sizes to be added.
     */
    private void selectFontSize(ListView<String> fontSizeList) {
        String fontSize = fontSizeList.getSelectionModel().getSelectedItems().get(0);
        if (fontSize != null) {
            switch (fontSize) {
                case "Small":
                    System.out.println("Small");
                    this.adventureGameView.setFontSize(12);
                    break;
                case "Medium":
                    System.out.println("Medium");
                    this.adventureGameView.setFontSize(16);
                    break;
                case "Large":
                    System.out.println("Large");
                    this.adventureGameView.setFontSize(20);
                    break;
            }
        }
    }

    /**
     * This method gets the difficulty selected by the user and sets it in the game.
     *
     * @param difficultyList List to of difficulties to be added.
     */
    private void selectDifficulty(ListView<String> difficultyList) {
        String difficulty = difficultyList.getSelectionModel().getSelectedItems().get(0);
        if (difficulty != null) {
            switch (difficulty) {
                case "Easy":
                    System.out.println("Easy");
                    this.adventureGameView.model.player.setHealth(100);
                    break;
                case "Medium":
                    System.out.println("Medium");
                    this.adventureGameView.model.player.setHealth(70);
                    break;
                case "Hard":
                    System.out.println("Hard");
                    this.adventureGameView.model.player.setHealth(50);
                    break;
            }
        }
    }
}

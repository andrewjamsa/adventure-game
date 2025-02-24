package views;

import AdventureModel.AdventureGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class SaveView.
 * <p>
 * Saves Serialized adventure games.
 */
public class SaveView {

    static String saveFileSuccess = "Saved Adventure Game!!";
    static String saveFileExistsError = "Error: File already exists";
    static String saveFileNotSerError = "Error: File must end with .ser";
    private Label saveFileErrorLabel = new Label("");
    private Label saveGameLabel = new Label(String.format("Enter name of file to save"));
    private TextField saveFileNameTextField = new TextField("");
    private Button saveGameButton = new Button("Save Game");
    private Button closeWindowButton = new Button("Close Window");

    private AdventureGameView adventureGameView;

    /**
     * Constructor
     */
    public SaveView(AdventureGameView adventureGameView) {
        this.adventureGameView = adventureGameView;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(adventureGameView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #" + this.adventureGameView.getColorWay().getBoardColor().toString().substring(2) + ";");
        saveGameLabel.setId("SaveGame"); // DO NOT MODIFY ID
        saveFileErrorLabel.setId("SaveFileErrorLabel");
        saveFileNameTextField.setId("SaveFileNameTextField");

        saveGameLabel.setStyle("-fx-text-fill: #" + this.adventureGameView.getColorWay().getTextColor().toString().substring(2) + ";");
        saveGameLabel.setFont(new Font(this.adventureGameView.getGameFont(), this.adventureGameView.getFontSize()));
        saveFileErrorLabel.setStyle("-fx-text-fill: #" + this.adventureGameView.getColorWay().getTextColor().toString().substring(2) + ";");
        saveFileErrorLabel.setFont(new Font(this.adventureGameView.getFontSize()));
        saveFileNameTextField.setStyle("-fx-text-fill: #" + this.adventureGameView.getColorWay().getTextBoxColor().toString().substring(2) + ";");
        saveFileNameTextField.setFont(new Font(this.adventureGameView.getGameFont(), this.adventureGameView.getFontSize()));

        String gameName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + ".ser";
        saveFileNameTextField.setText(gameName);

        saveGameButton = new Button("Save board");
        saveGameButton.setId("SaveBoardButton"); // DO NOT MODIFY ID
        saveGameButton.setStyle("-fx-background-color: #" + this.adventureGameView.getColorWay().getButtonColor().toString().substring(2) + "; -fx-text-fill:#" + this.adventureGameView.getColorWay().getButtonTextColor().toString().substring(2) + ";");
        saveGameButton.setPrefSize(200, 50);
        saveGameButton.setFont(new Font(this.adventureGameView.getGameFont(), this.adventureGameView.getFontSize()));
        AdventureGameView.makeButtonAccessible(saveGameButton, "save game", "This is a button to save the game", "Use this button to save the current game.");
        saveGameButton.setOnAction(e -> saveGame());

        closeWindowButton = new Button("Close Window");
        closeWindowButton.setId("closeWindowButton"); // DO NOT MODIFY ID
        closeWindowButton.setStyle("-fx-background-color: #" + this.adventureGameView.getColorWay().getButtonColor().toString().substring(2) + "; -fx-text-fill:#" + this.adventureGameView.getColorWay().getButtonTextColor().toString().substring(2) + ";");
        closeWindowButton.setPrefSize(200, 50);

        closeWindowButton.setFont(new Font(this.adventureGameView.getGameFont(), this.adventureGameView.getFontSize()));

        closeWindowButton.setOnAction(e -> dialog.close());
        AdventureGameView.makeButtonAccessible(closeWindowButton, "close window", "This is a button to close the save game window", "Use this button to close the save game window.");

        VBox saveGameBox = new VBox(10, saveGameLabel, saveFileNameTextField, saveGameButton, saveFileErrorLabel, closeWindowButton);
        saveGameBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(saveGameBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    /**
     * Saves the Game
     * Save the game to a serialized (binary) file.
     * Get the name of the file from saveFileNameTextField.
     * Files will be saved to the Games/Saved directory.
     * If the file already exists, set the saveFileErrorLabel to the text in saveFileExistsError
     * If the file doesn't end in .ser, set the saveFileErrorLabel to the text in saveFileNotSerError
     * Otherwise, load the file and set the saveFileErrorLabel to the text in saveFileSuccess
     */
    private void saveGame() {
        Serializable content = adventureGameView.model;             // initialize content that's in the ser file
        String fileName = "../assignment2/Games/Saved" + File.separator + saveFileNameTextField.getText();   // initialize variable for the file name that's saved to Games/Saved
        File files = new File("../assignment2/Games/Saved", saveFileNameTextField.getText());
        if (!saveFileNameTextField.getText().substring(saveFileNameTextField.getText().length() - 4).equals(".ser")) {
            saveFileErrorLabel.setText(saveFileNotSerError);
            return;
        }
        if (files.exists()) {
            saveFileErrorLabel.setText(saveFileExistsError);
            return;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);   // initialize content placer
            objectOutputStream.writeObject(content);        // place content inside ser file
            objectOutputStream.close();
            saveFileErrorLabel.setText(saveFileSuccess);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}


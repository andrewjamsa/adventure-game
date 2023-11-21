package ColorWay;

import javafx.scene.paint.Color;

/**
 * This interface is used to implement the colorway for the game.
 * The colorway is used to change the color of the game.
 */

public class DarkColorWay implements ColorWay {


    Color boardColor;
    Color textColor;
    Color buttonColor;
    Color textBoxColor;

    /**
     * This method is the constructor for the DarkColorWay class.
     */
    public DarkColorWay() {
        this.boardColor = Color.BLACK;
        this.textColor = Color.WHITE;
        this.buttonColor = Color.GREEN;
        this.textBoxColor = Color.WHITE;
    }


    /**
     * This method returns the color of the board.
     *
     * @return the color of the board
     */
    public Color getBoardColor() {
    return this.boardColor;
    }
/**
     * This method returns the color of the text.
     *
     * @return the color of the text
     */
    @Override
    public Color getTextBoxColor() {
        return this.textBoxColor;
    }
/**
     * This method returns the color of the buttons.
     *
     * @return the color of the buttons
     */
    @Override
    public Color getButtonColor() {
        return this.buttonColor;
    }
/**
     * This method returns the color of the text boxes.
     *
     * @return the color of the text boxes
     */
    @Override
    public Color getTextColor() {
        return this.textColor;
    }
}

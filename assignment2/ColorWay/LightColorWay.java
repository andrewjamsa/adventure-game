package ColorWay;

import javafx.scene.paint.Color;

/**
 * This interface is used to implement the colorway for the game.
 * The colorway is used to change the color of the game.
 */

public class LightColorWay implements ColorWay {
    Color boardColor = Color.WHITE;
    Color textColor = Color.BLACK;
    Color buttonColor = Color.LIGHTBLUE;
    Color textBoxColor = Color.WHITE;

    /**
     * This method is the constructor for the DarkColorWay class.
     */
    public LightColorWay() {
        this.boardColor = Color.WHITE;
        this.textColor = Color.BLACK;
        this.buttonColor = Color.LIGHTBLUE;
        this.textBoxColor = Color.WHITE;
    }

    @Override
    public Color getBoardColor() {
        return this.boardColor;
    }
/**
     * This method returns the color of the text.
     *
     * @return the color of the text
     */
    @Override
    public Color getTextColor() {
        return this.textColor;
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
    public Color getTextBoxColor() {
        return this.textBoxColor;
    }

}

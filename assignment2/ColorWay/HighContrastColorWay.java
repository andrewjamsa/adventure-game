package ColorWay;

import javafx.scene.paint.Color;

public class HighContrastColorWay implements ColorWay{
    Color boardColor = Color.BLACK;
    Color textColor = Color.BLUE;
    Color buttonColor = Color.YELLOW;
    Color textBoxColor = Color.WHITE;
    /**
     * This method is the constructor for the DarkColorWay class.
     */
    public HighContrastColorWay() {
        this.boardColor = Color.BLACK;
        this.textColor = Color.BLUE;
        this.buttonColor = Color.YELLOW;
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
    public Color getTextColor() {
        return this.textColor;
    }
/**
     * This method returns the color of the buttons.
     *
     * @return the color of the buttons
     */
    public Color getButtonColor() {
        return this.buttonColor;
    }
/**
     * This method returns the color of the text boxes.
     *
     * @return the color of the text boxes
     */
    public Color getTextBoxColor() {
        return this.textBoxColor;
    }
}

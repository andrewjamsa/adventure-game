package ColorWay;

import javafx.scene.paint.Color;

public class HighContrastColorWay implements ColorWay{
    Color boardColor;
    Color textColor;
    Color buttonColor;
    Color textBoxColor;
    Color buttonTextColor;
    /**
     * This method is the constructor for the HighContrastColorWay class.
     */
    public HighContrastColorWay() {
        this.boardColor = Color.WHITE;
        this.textColor = Color.BLUE;
        this.buttonColor = Color.BLUE;
        this.textBoxColor = Color.BLUE;
        this.buttonTextColor = Color.WHITE;
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

    /**
     * This method returns the color of the button text.
     *
     * @return the color of the button text
     */
    @Override
    public Color getButtonTextColor() {
        return this.buttonTextColor;
    }
}

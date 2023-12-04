package ColorWay;
import javafx.scene.paint.Color;
/**
 * This interface is used to implement the colorway for the game.
 * The colorway is used to change the color of the game.
 */
public interface ColorWay {
    /**
     * This method returns the color of the board.
     *
     * @return the color of the board
     */
    public Color getBoardColor();
    /**
     * This method returns the color of the text.
     *
     * @return the color of the text
     */
    public Color getTextColor();
    /**
     * This method returns the color of the buttons.
     *
     * @return the color of the buttons
     */
    public Color getButtonColor();
    /**
     * This method returns the color of the text boxes.
     *
     * @return the color of the text boxes
     */
    public Color getTextBoxColor();
}

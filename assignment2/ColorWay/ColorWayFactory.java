package ColorWay;

public class ColorWayFactory {
    /**
     * This method returns the colorway of the game.
     *
     * @param colorWayType the type of colorway
     * @return the colorway of the game
     */
    public ColorWay getColorWay(String colorWayType) {
        if (colorWayType == null) {
            return null;
        }
        if (colorWayType.equalsIgnoreCase("DARK")) {
            return new DarkColorWay();
        } else if (colorWayType.equalsIgnoreCase("LIGHT")) {
            return new LightColorWay();
        } else if (colorWayType.equalsIgnoreCase("HIGHCONTRAST")) {
            return new HighContrastColorWay();
        }
        return null;
    }
}

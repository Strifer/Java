package hr.fer.zemris.java.hw12.color;

import java.awt.Color;

/**
 * This interface defines a single subject capable of informing its listeners
 * every time a color change happens.
 * 
 * @author Filip Džidić
 *
 */
public interface IColorProvider {
	/**
	 * Getter method for the current color property of the object.
	 * 
	 * @return the current color
	 */
	public Color getCurrentColor();
}

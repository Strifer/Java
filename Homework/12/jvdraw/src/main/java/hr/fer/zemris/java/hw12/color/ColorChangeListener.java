package hr.fer.zemris.java.hw12.color;

import java.awt.Color;

/**
 * This interface defines a single listener which can be registered to
 * <code>IColorProvider</code>.
 * 
 * @author Filip Džidić
 *
 */
public interface ColorChangeListener {
	/**
	 * This method defines the appropriate action performed every time a color
	 * change occurs.
	 * 
	 * @param source
	 *            the subject being observed
	 * @param oldColor
	 *            the old color
	 * @param newColor
	 *            the new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor);
}

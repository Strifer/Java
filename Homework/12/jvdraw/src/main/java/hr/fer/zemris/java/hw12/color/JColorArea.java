package hr.fer.zemris.java.hw12.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

/**
 * <code>JColorArea</code> represents an object capable of storing color values.
 * It comes equipped with methods for informing listeners any time the color
 * inside the area is changed.
 * 
 * @author Filip Džidić
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/** preferred dimension */
	private static final Dimension PREF_DIM = new Dimension(15, 15);
	/** serial ID */
	private static final long serialVersionUID = 8112421002285385717L;
	/** color of the component */
	private Color selectedColor;
	/** list of all registered listeners */
	private List<ColorChangeListener> listenerList;

	/**
	 * Constructs a new <code>JColorArea</code> with a user provided initial
	 * color.
	 * 
	 * @param initial
	 *            initial color of the area
	 */
	public JColorArea(Color initial) {
		this.selectedColor = initial;
		this.setBackground(selectedColor);
		this.setOpaque(true);
		this.paint(getGraphics());
		this.listenerList = new ArrayList<>();
		this.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this,
						"Color chooser", JColorArea.this.selectedColor);
				if (newColor == null) {
					return;
				}
				for (ColorChangeListener l : listenerList) {
					l.newColorSelected(JColorArea.this,
							JColorArea.this.selectedColor, newColor);
				}
				JColorArea.this.selectedColor = newColor;
				JColorArea.this.setBackground(newColor);
				JColorArea.this.repaint();

			}
		});
	}

	@Override
	public Dimension getPreferredSize() {
		return PREF_DIM;
	}

	public void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	};

	@Override
	public Color getCurrentColor() {
		return this.selectedColor;
	}

	/**
	 * Method for registering new <code>IColorChangeListener</code>.
	 * 
	 * @param l
	 *            the new listener for this object
	 */
	public void addColorChangeListener(ColorChangeListener l) {
		this.listenerList.add(l);
	}

	/**
	 * Method for removing old <code>IColorChangeListener</code> from the
	 * registered listeners list.
	 * 
	 * @param l
	 *            some listener of this object
	 */
	public void removeColorChangeListener(ColorChangeListener l) {
		this.listenerList.remove(this.listenerList.indexOf(l));
	}

}

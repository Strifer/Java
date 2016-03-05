package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This is a utility class for creating necessary JComponents
 * 
 * @author Filip Džidić
 *
 */
public class NotepadUtility {
	/** the red icon signifies that unsaved changes exist within a file */
	static final Icon RED = new ImageIcon("./Icons/bullet_red.png");
	/** the green icon signifies that there are no unsaved changes within a file */
	static final Icon GREEN = new ImageIcon("./Icons/bullet_green.png");
	/** the red cross icon is used for tab closing buttons */
	static final Icon CROSS = new ImageIcon("./Icons/cross.png");

	/**
	 * This method creates a new JPanel consisting of an icon, title text and a
	 * button for closing the tab.
	 * 
	 * @param title
	 *            the title displayed on the panel
	 * @param icon
	 *            the icon used on the panel
	 * @param notepad
	 *            the notepad who will use the panel
	 * @param tabIndex
	 *            the index of the tab where we are adding the panel
	 * @return newly created JPanel
	 */
	public static JPanel tabTitle(String title, String icon,
			JNotepadPP notepad, int tabIndex) {
		JPanel tabTitle = new JPanel(new FlowLayout());
		tabTitle.setOpaque(true);
		JLabel image = null;
		switch (icon.toUpperCase()) {
			case "RED":
				image = new JLabel(RED);
				break;
			case "GREEN":
				image = new JLabel(GREEN);
				break;
			case "CROSS":
				image = new JLabel(CROSS);
				break;
			default:
				throw new IllegalArgumentException("Undefined icon");
		}

		image.setOpaque(false);
		JLabel name = new JLabel(title);
		name.setOpaque(false);
		JButton closeButton = new JButton(CROSS);
		tabTitle.setOpaque(false);
		closeButton.setPreferredSize(new Dimension(9, 9));
		closeButton.setBorderPainted(false);
		closeButton.setOpaque(false);
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				notepad.editor.setSelectedIndex(notepad.editor
						.indexOfTabComponent(tabTitle));
				notepad.close();

			}
		});
		tabTitle.add(image);
		tabTitle.add(name);
		tabTitle.add(closeButton);
		tabTitle.setOpaque(false);
		return tabTitle;
	}
}

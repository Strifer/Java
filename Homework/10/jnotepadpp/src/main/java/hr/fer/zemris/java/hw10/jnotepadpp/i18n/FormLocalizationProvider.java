package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * This class provides a connection between a given <code>JFrame</code> and
 * <code>ILocalizationProvider</code>
 * 
 * @author Filip Džidić
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	/**
	 * Adds a window listener to the provided frame which establishes a
	 * connection upon opening the program and closes it while exiting.
	 * 
	 * @param provider
	 *            the subject
	 * @param frame
	 *            the frame that is being connected
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();

			}

			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
			}
		});
	}

}

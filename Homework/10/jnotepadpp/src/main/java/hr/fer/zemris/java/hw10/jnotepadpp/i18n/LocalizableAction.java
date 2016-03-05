package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

import java.awt.event.ActionEvent;
import java.util.MissingResourceException;

import javax.swing.AbstractAction;

/**
 * This class represents a localized action. It's main purpose is to change an
 * action parameters to new ones dictated by the language settings.
 * 
 * @author Filip Džidić
 *
 */
public abstract class LocalizableAction extends AbstractAction {
	/** serial id */
	private static final long serialVersionUID = 1L;
	/** key representing the language setting variable */
	private String key;
	/**
	 * this listener changes the action's name and description to apropriate new
	 * language
	 */
	private ILocalizationListener listener;
	/**
	 * the main provider in which we're adding our listeners
	 */
	private ILocalizationProvider provider;

	/**
	 * This constructor creates a new LocalizableAction whose parameters change
	 * dynamically whenever the language
	 * 
	 * @param key
	 *            the language properties parameter
	 * @param lp
	 *            the main provider which holds all of the registered observers
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.provider = lp;

		putValue(NAME, lp.getString(this.key));

		try {
			putValue(SHORT_DESCRIPTION, lp.getString(key + "Desc"));
		} catch (MissingResourceException ignorable) {
		}
		this.listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {

				putValue(NAME, lp.getString(LocalizableAction.this.key));

				try {
					putValue(SHORT_DESCRIPTION, lp.getString(key + "Desc"));
				} catch (MissingResourceException ignorable) {
				}
			}
		};
		provider.addLocalizationListener(listener);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}

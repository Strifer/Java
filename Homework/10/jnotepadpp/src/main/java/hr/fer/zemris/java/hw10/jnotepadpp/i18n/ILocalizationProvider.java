package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

/**
 * This interface models a single subject which keeps track of observers by
 * keeping them in list.
 * 
 * @author Filip Džidić
 *
 */
public interface ILocalizationProvider {
	/**
	 * Registers the provided listener to its list of observers unless it's
	 * already present.
	 * 
	 * @param listener
	 *            the new listener being registered
	 */
	public void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Deregisters the listener from the list of observers.
	 * 
	 * @param listener
	 */
	public void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * This method returns the current localization result for the provided key.
	 * 
	 * @param key
	 *            the key of some language parameter
	 * @return the value of that parameter in the current language setting
	 */
	public String getString(String key);
}

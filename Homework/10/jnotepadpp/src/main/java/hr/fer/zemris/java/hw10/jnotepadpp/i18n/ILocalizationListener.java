package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

/**
 * This interface models a single listener which performs a given action each
 * time the localization settings are changed.
 * 
 * @author Filip Džidić
 *
 */
public interface ILocalizationListener {
	/**
	 * This method defines some action that is invoked every time the
	 * localization settings are changed.
	 */
	public void localizationChanged();
}

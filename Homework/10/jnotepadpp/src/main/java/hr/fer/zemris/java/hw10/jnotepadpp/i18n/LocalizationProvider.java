package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This singleton class enables multiple JFrames to base their i18n on a single
 * object.
 * <p>
 * It is a concrete implementation of <code>AbstractLocalizationProvider</code>
 * and offers methods for getting the proper values for components in the set
 * language.
 * 
 * @author Filip Džidić
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/** this variable stores the currently set language */
	private String language;
	/** this bundle contains all of our defined language parameters */
	private ResourceBundle bundle;
	/** name of the package where the language properties are stored */
	private static final String PACKAGE_NAME = "hr.fer.zemris.java.hw10.jnotepadpp.languages.prijevodi";
	/**
	 * this instance of the singleton is created at the start of the program and
	 * it is the only instance of this class
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Default constructor sets the language to English by default.
	 */
	private LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(PACKAGE_NAME, locale);
	}

	/**
	 * Changes the currently set language.
	 * 
	 * @param language
	 *            the new language that is being set
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(PACKAGE_NAME, locale);
		fire();
	}

	/**
	 * Returns the single constant instance of this class.
	 * 
	 * @return reference to the created singleton
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}

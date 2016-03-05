package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

/**
 * This class is a decorator class for <code>ILocalizationProvider</code>. It's
 * main purpose is to enable proper dereferencing of attached JFrames so the
 * garbage collector can do it's job and no memory leaks can happen.
 * 
 * @author Filip Džidić
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/** signifies weather a connection has been established */
	private boolean connected;
	/** reference to the stored provider */
	private ILocalizationProvider provider;
	/**
	 * this listener's job is to merely inform every listener in the listener
	 * list that a change has occured
	 */
	private ILocalizationListener listener;

	/**
	 * This constructor initializes our helper variables and wraps the provider
	 * provider for further connection.
	 * 
	 * @param provider
	 *            the main <code>ILocalizationProvider</code> which will hold
	 *            all of the listeners
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		connected = false;
		listener = new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fire();
			}
		};
	}

	/**
	 * Establishes a new connection
	 */
	public void connect() {
		if (connected) {
			provider.addLocalizationListener(listener);
			connected = true;
		}
	}

	/**
	 * Disables the connection which dereferences all uneccesary components.
	 */
	public void disconnect() {
		provider.removeLocalizationListener(listener);
		connected = false;
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

}

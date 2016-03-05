package hr.fer.zemris.java.hw10.jnotepadpp.i18n;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;

/**
 * This class defines the three main methods used for implementing the observer
 * pattern.
 * 
 * @author Filip Džidić
 *
 */
public abstract class AbstractLocalizationProvider implements
		ILocalizationProvider {

	/** observers are held in this list */
	private static List<ILocalizationListener> listenerList;

	/**
	 * This constructor initializes the observer list.
	 */
	public AbstractLocalizationProvider() {
		listenerList = new ArrayList<ILocalizationListener>();
	}

	public void addLocalizationListener(ILocalizationListener listener) {
		if (!listenerList.contains(listener)) {
			listenerList.add(listener);
		}
	}

	public void removeLocalizationListener(ILocalizationListener listener) {
		int index = listenerList.indexOf(listener);
		if (index != -1) {
			listenerList.remove(index);
		}
	}

	/**
	 * This method is called when a locale change occurs and it notifies each
	 * subscribed observer of this change.
	 */
	public void fire() {
		for (ILocalizationListener listener : listenerList) {
			if (listener instanceof JButton) {
				((JButton) listener).setText("");
			}
			listener.localizationChanged();
		}
	}

}

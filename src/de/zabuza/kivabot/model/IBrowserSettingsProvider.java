package de.zabuza.kivabot.model;

import de.zabuza.sparkle.webdriver.EBrowser;

/**
 * Interface for objects that provide settings for browsers.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface IBrowserSettingsProvider {
	/**
	 * Gets the binary for the browser to use.
	 * 
	 * @return The binary for the browser to use or <tt>null</tt> if not set
	 */
	public String getBrowserBinary();

	/**
	 * Gets the driver for a given browser if set.
	 * 
	 * @param browser
	 *            Browser to get driver for
	 * @return The driver for the browser or <tt>null</tt> if not set
	 */
	public String getDriverForBrowser(final EBrowser browser);
}

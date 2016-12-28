package de.zabuza.kivabot.controller.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import de.zabuza.kivabot.controller.logging.Logger;

/**
 * Class for the tool settings.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class Settings {
	/**
	 * Comment for the configuration file.
	 */
	private static final String FILE_COMMENT = "Configuration settings for KivaBot.";
	/**
	 * File path of the settings.
	 */
	private static final String FILEPATH = "config.ini";
	/**
	 * The logger used by this object.
	 */
	private final Logger mLogger;
	/**
	 * Properties object which holds the saved settings.
	 */
	private final Properties mProperties;

	/**
	 * Create a new settings object.
	 * 
	 * @param logger
	 *            The logger to use
	 */
	public Settings(final Logger logger) {
		mProperties = new Properties();
		mLogger = logger;
	}

	/**
	 * Loads settings of the saved file and applies the properties to the
	 * provider settings.
	 * 
	 * @param provider
	 *            Provider which settings will be affected
	 */
	public final void loadSettings(final ISettingsProvider provider) {
		mLogger.logInfo("Loading settings...", Logger.TOP_LEVEL);
		try {
			try {
				mProperties.load(new FileInputStream(FILEPATH));
			} catch (FileNotFoundException e) {
				saveSettings(provider);
				mProperties.load(new FileInputStream(FILEPATH));
			}

			// Fetch and set every saved setting
			for (Entry<Object, Object> entry : mProperties.entrySet()) {
				provider.setSetting((String) entry.getKey(), (String) entry.getValue());
			}
			mLogger.logInfo("Settings loaded.", Logger.FIRST_LEVEL);
		} catch (IOException e) {
			mLogger.logError("IO-error while loading settings from : " + FILEPATH, Logger.FIRST_LEVEL);
			e.printStackTrace();
		}
	}

	/**
	 * Saves the current settings of the provider in a file.
	 * 
	 * @param provider
	 *            Provider which settings will be affected
	 */
	public final void saveSettings(final ISettingsProvider provider) {
		mLogger.logInfo("Saving settings...", Logger.TOP_LEVEL);
		FileOutputStream target = null;
		try {
			// Fetch and put every setting
			for (Entry<String, String> entry : provider.getAllSettings().entrySet()) {
				mProperties.put(entry.getKey(), entry.getValue());
			}

			// Save the settings
			target = new FileOutputStream(new File(FILEPATH));
			mProperties.store(target, FILE_COMMENT);
			mLogger.logInfo("Settings saved.", Logger.FIRST_LEVEL);
		} catch (IOException e) {
			mLogger.logError("IO-error while saving settings to : " + FILEPATH, Logger.FIRST_LEVEL);
			e.printStackTrace();
		} finally {
			if (target != null) {
				try {
					target.close();
				} catch (IOException e) {
					mLogger.logUnknownError(e);
					e.printStackTrace();
				}
			}
		}
	}
}
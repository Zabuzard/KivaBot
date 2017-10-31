package de.zabuza.kivabot.controller.settings;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JTextField;

import de.zabuza.kivabot.controller.listener.CloseAtCancelActionListener;
import de.zabuza.kivabot.controller.listener.ClosingCallbackWindowListener;
import de.zabuza.kivabot.controller.listener.FileChooseSetActionListener;
import de.zabuza.kivabot.controller.listener.SaveActionListener;
import de.zabuza.kivabot.controller.listener.SettingsActionListener;
import de.zabuza.kivabot.controller.logging.Logger;
import de.zabuza.kivabot.model.IBrowserSettingsProvider;
import de.zabuza.kivabot.model.tasks.EKivaTask;
import de.zabuza.kivabot.view.MainFrameView;
import de.zabuza.kivabot.view.SettingsDialog;
import de.zabuza.sparkle.freewar.EWorld;
import de.zabuza.sparkle.freewar.movement.network.EMoveType;
import de.zabuza.sparkle.webdriver.EBrowser;

/**
 * The controller of the settings.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class SettingsController implements ISettingsProvider, IBrowserSettingsProvider {
	/**
	 * Text to save for a value if a key is unknown.
	 */
	public static final String UNKNOWN_KEY_VALUE = "";
	/**
	 * Key identifier for binary setting.
	 */
	private static final String KEY_IDENTIFIER_BINARY = "binary";
	/**
	 * Key identifier for the selected browser.
	 */
	private static final String KEY_IDENTIFIER_BROWSER = "browser";
	/**
	 * Key identifier for driver settings.
	 */
	private static final String KEY_IDENTIFIER_DRIVER = "driver";
	/**
	 * Key identifier for the movement options.
	 */
	private static final String KEY_IDENTIFIER_MOVEMENT_OPTION = "movement_option";
	/**
	 * Key identifier for the password.
	 */
	private static final String KEY_IDENTIFIER_PASSWORD = "password";
	/**
	 * Key identifier for the protection spell setting.
	 */
	private static final String KEY_IDENTIFIER_PROTECTION_SPELL = "protection_spell";
	/**
	 * Key identifier for the tasks.
	 */
	private static final String KEY_IDENTIFIER_TASK = "task";
	/**
	 * Key identifier for the use of the protection spell.
	 */
	private static final String KEY_IDENTIFIER_USE_PROTECTION_SPELL = "use_protection_spell";
	/**
	 * Key identifier for the use of the special skill.
	 */
	private static final String KEY_IDENTIFIER_USE_SPECIAL_SKILL = "use_special_skill";
	/**
	 * Key identifier for user profile setting.
	 */
	private static final String KEY_IDENTIFIER_USER_PROFILE = "userProfile";
	/**
	 * Key identifier for the username.
	 */
	private static final String KEY_IDENTIFIER_USERNAME = "username";
	/**
	 * Key identifier for the selected world.
	 */
	private static final String KEY_IDENTIFIER_WORLD = "world";
	/**
	 * Separator which separates several information in a key.
	 */
	private static final String KEY_INFO_SEPARATOR = "@";
	/**
	 * The logger used by this object.
	 */
	private final Logger mLogger;
	/**
	 * The owning frame of this controller.
	 */
	private final JFrame mOwner;
	/**
	 * The object for the settings.
	 */
	private final Settings mSettings;
	/**
	 * The settings dialog or <tt>null</tt> if currently not opened.
	 */
	private SettingsDialog mSettingsDialog;
	/**
	 * Structure which saves all currently loaded settings.
	 */
	private final Map<String, String> mSettingsStore;
	/**
	 * The view of the main frame.
	 */
	private final MainFrameView mView;

	/**
	 * Creates a new controller of the settings.
	 * 
	 * @param owner
	 *            The owning frame of this controller
	 * @param view
	 *            The view to control
	 * @param logger
	 *            The logger to use
	 */
	public SettingsController(final JFrame owner, final MainFrameView view, final Logger logger) {
		this.mView = view;
		this.mLogger = logger;
		this.mOwner = owner;

		this.mSettingsStore = new HashMap<>();
		this.mSettings = new Settings(this.mLogger);
		this.mSettingsDialog = null;
	}

	/**
	 * Call whenever the settings dialog is closing. This is used as callback to
	 * free the parent window of the dialog.
	 */
	public void closingSettingsDialog() {
		this.mView.setAllInputEnabled(true);
		this.mView.setStartButtonEnabled(true);
		this.mView.setStopButtonEnabled(false);
		this.mView.setSettingsButtonEnabled(true);
		this.mSettingsDialog = null;
	}

	/**
	 * Call whenever the save action is to be executed. This will save all settings
	 * and close the settings dialog, if opened.
	 */
	public void executeSaveAction() {
		// Save dialog settings if dialog is opened
		if (this.mSettingsDialog != null) {
			// Driver settings
			for (final EBrowser browser : EBrowser.values()) {
				final JTextField field = this.mSettingsDialog.getBrowserDriverField(browser);
				final String value = field.getText();
				if (!value.equals(UNKNOWN_KEY_VALUE)) {
					final String key = KEY_IDENTIFIER_DRIVER + KEY_INFO_SEPARATOR + browser;
					setSetting(key, value);
				}
			}

			// Binary setting
			final JTextField binaryField = this.mSettingsDialog.getBinaryField();
			final String binaryValue = binaryField.getText();
			if (!binaryValue.equals(UNKNOWN_KEY_VALUE)) {
				final String key = KEY_IDENTIFIER_BINARY;
				setSetting(key, binaryValue);
			}

			// User profile setting
			final JTextField userProfileField = this.mSettingsDialog.getUserProfileField();
			final String userProfileValue = userProfileField.getText();
			if (!userProfileValue.equals(UNKNOWN_KEY_VALUE)) {
				final String key = KEY_IDENTIFIER_USER_PROFILE;
				setSetting(key, userProfileValue);
			}

			// Protection spell setting
			final JTextField protectionSpellField = this.mSettingsDialog.getProtectionSpellField();
			final String protectionSpellValue = protectionSpellField.getText();
			if (!protectionSpellValue.equals(UNKNOWN_KEY_VALUE)) {
				final String key = KEY_IDENTIFIER_PROTECTION_SPELL;
				setSetting(key, protectionSpellValue);
			}
		}

		// Save the current content of the main view
		// Username
		final String username = this.mView.getUsername();
		if (!username.equals(UNKNOWN_KEY_VALUE)) {
			final String key = KEY_IDENTIFIER_USERNAME;
			setSetting(key, username);
		}

		// Password
		final String password = this.mView.getPassword();
		if (!password.equals(UNKNOWN_KEY_VALUE)) {
			final String key = KEY_IDENTIFIER_PASSWORD;
			setSetting(key, password);
		}

		// World
		final EWorld world = this.mView.getWorld();
		if (world != null) {
			final String key = KEY_IDENTIFIER_WORLD;
			setSetting(key, world.toString());
		}

		// Selected browser
		final EBrowser browser = this.mView.getBrowser();
		if (browser != null) {
			final String key = KEY_IDENTIFIER_BROWSER;
			setSetting(key, browser.toString());
		}

		// Movement options
		final Set<EMoveType> selectedOptions = this.mView.getMovementOptions();
		for (final EMoveType moveType : EMoveType.values()) {
			final boolean value = selectedOptions.contains(moveType);
			final String key = KEY_IDENTIFIER_MOVEMENT_OPTION + KEY_INFO_SEPARATOR + moveType;
			setSetting(key, Boolean.toString(value));
		}

		// Tasks
		final Set<EKivaTask> selectedTasks = this.mView.getKivaTasks();
		for (final EKivaTask task : EKivaTask.values()) {
			final boolean value = selectedTasks.contains(task);
			final String key = KEY_IDENTIFIER_TASK + KEY_INFO_SEPARATOR + task;
			setSetting(key, Boolean.toString(value));
		}

		// Use protection spell setting
		final boolean useProtectionSpell = this.mView.isUseProtectionSpellChecked();
		String key = KEY_IDENTIFIER_USE_PROTECTION_SPELL;
		setSetting(key, Boolean.toString(useProtectionSpell));

		// Use special skill setting
		final boolean useSpecialSkill = this.mView.isUseSpecialSkillChecked();
		key = KEY_IDENTIFIER_USE_SPECIAL_SKILL;
		setSetting(key, Boolean.toString(useSpecialSkill));

		// Save settings
		this.mSettings.saveSettings(this);

		// Close the settings dialog, if opened
		if (this.mSettingsDialog != null) {
			this.mSettingsDialog.dispatchEvent(new WindowEvent(this.mSettingsDialog, WindowEvent.WINDOW_CLOSING));
		}
	}

	/**
	 * Call whenever the settings action is to be executed. This will open a
	 * settings dialog.
	 */
	public void executeSettingsAction() {
		// Deactivate all actions until the settings dialog has closed
		this.mView.setAllInputEnabled(false);
		this.mView.setStartButtonEnabled(false);
		this.mView.setStopButtonEnabled(false);
		this.mView.setSettingsButtonEnabled(false);

		// Open the dialog
		this.mSettingsDialog = new SettingsDialog(this.mOwner);
		linkDialogListener();

		// Load settings to the store
		passSettingsToSettingsDialogView();
		this.mSettingsDialog.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.kivabot.controller.settings.ISettingsProvider#getAllSettings()
	 */
	@Override
	public Map<String, String> getAllSettings() {
		return this.mSettingsStore;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.kivabot.model.IBrowserSettingsProvider#getBrowserBinary()
	 */
	@Override
	public String getBrowserBinary() {
		final String binary = getSetting(KEY_IDENTIFIER_BINARY);
		if (binary.equals(UNKNOWN_KEY_VALUE)) {
			return null;
		}
		return binary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.kivabot.model.IBrowserSettingsProvider#getDriverForBrowser(de.
	 * zabuza.sparkle.webdriver.EBrowser)
	 */
	@Override
	public String getDriverForBrowser(final EBrowser browser) {
		final String key = KEY_IDENTIFIER_DRIVER + KEY_INFO_SEPARATOR + browser;
		final String driver = getSetting(key);
		if (driver.equals(UNKNOWN_KEY_VALUE)) {
			return null;
		}
		return driver;
	}

	/**
	 * Gets the name of the protection spell to use.
	 * 
	 * @return The name of the protection spell to use or <tt>null</tt> if not set
	 */
	public String getProtectionSpell() {
		final String protectionSpell = getSetting(KEY_IDENTIFIER_PROTECTION_SPELL);
		if (protectionSpell.equals(UNKNOWN_KEY_VALUE)) {
			return null;
		}
		return protectionSpell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.kivabot.controller.settings.ISettingsProvider#getSetting(java.
	 * lang.String)
	 */
	@Override
	public String getSetting(final String key) {
		String value = this.mSettingsStore.get(key);
		if (value == null) {
			value = UNKNOWN_KEY_VALUE;
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.beedlebot.logindialog.controller.settings.
	 * IBrowserSettingsProvider#getUserProfile()
	 */
	@Override
	public String getUserProfile() {
		final String userProfile = getSetting(KEY_IDENTIFIER_USER_PROFILE);
		if (userProfile.equals(UNKNOWN_KEY_VALUE)) {
			return null;
		}
		return userProfile;
	}

	/**
	 * Initializes the controller.
	 */
	public void initialize() {
		linkListener();
		this.mSettings.loadSettings(this);
	}

	/**
	 * Passes the settings of the store to the main view for display.
	 */
	public void passSettingsToMainView() {
		for (final Entry<String, String> entry : this.mSettingsStore.entrySet()) {
			final String[] keySplit = entry.getKey().split(KEY_INFO_SEPARATOR);
			final String keyIdentifier = keySplit[0];

			if (keyIdentifier.equals(KEY_IDENTIFIER_USERNAME)) {
				// Username
				this.mView.setUsername(entry.getValue());
			} else if (keyIdentifier.equals(KEY_IDENTIFIER_PASSWORD)) {
				// Password
				this.mView.setPassword(entry.getValue());
			} else if (keyIdentifier.equals(KEY_IDENTIFIER_WORLD)) {
				// World
				this.mView.setWorld(EWorld.valueOf(entry.getValue()));
			} else if (keyIdentifier.equals(KEY_IDENTIFIER_BROWSER)) {
				// Browser
				this.mView.setBrowser(EBrowser.valueOf(entry.getValue()));
			} else if (keyIdentifier.equals(KEY_IDENTIFIER_MOVEMENT_OPTION)) {
				// Movement option
				final EMoveType moveType = EMoveType.valueOf(keySplit[1]);
				final boolean isSelected = Boolean.valueOf(entry.getValue()).booleanValue();
				this.mView.setMovementOption(moveType, isSelected);
			} else if (keyIdentifier.equals(KEY_IDENTIFIER_TASK)) {
				// Task
				final EKivaTask task = EKivaTask.valueOf(keySplit[1]);
				final boolean isSelected = Boolean.valueOf(entry.getValue()).booleanValue();
				this.mView.setKivaTask(task, isSelected);
			} else if (keyIdentifier.equals(KEY_IDENTIFIER_USE_PROTECTION_SPELL)) {
				// Use protection spell setting
				this.mView.setUseProtectionSpell(Boolean.valueOf(entry.getValue()).booleanValue());
			} else if (keyIdentifier.equals(KEY_IDENTIFIER_USE_SPECIAL_SKILL)) {
				// Use special skill setting
				this.mView.setUseSpecialSkill(Boolean.valueOf(entry.getValue()).booleanValue());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.kivabot.controller.settings.ISettingsProvider#setSetting(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public void setSetting(final String key, final String value) {
		this.mSettingsStore.put(key, value);
	}

	/**
	 * Links the listener of the dialog to it.
	 */
	private void linkDialogListener() {
		// Window listener
		this.mSettingsDialog.addWindowListener(new ClosingCallbackWindowListener(this));

		// Browser field listener
		for (final EBrowser browser : EBrowser.values()) {
			final ActionListener listener = new FileChooseSetActionListener(this.mSettingsDialog,
					this.mSettingsDialog.getBrowserDriverField(browser), false);
			this.mSettingsDialog.addListenerToBrowserDriverSelectionAction(browser, listener);
		}

		// Binary listener
		final ActionListener binaryListener = new FileChooseSetActionListener(this.mSettingsDialog,
				this.mSettingsDialog.getBinaryField(), false);
		this.mSettingsDialog.addListenerToBinarySelectionAction(binaryListener);

		// User profile listener
		final ActionListener userProfileListener = new FileChooseSetActionListener(this.mSettingsDialog,
				this.mSettingsDialog.getUserProfileField(), true);
		this.mSettingsDialog.addListenerToUserProfileSelectionAction(userProfileListener);

		// Save and cancel listener
		this.mSettingsDialog.addListenerToSaveAction(new SaveActionListener(this));
		this.mSettingsDialog.addListenerToCancelAction(new CloseAtCancelActionListener(this.mSettingsDialog));
	}

	/**
	 * Links the listener to the view.
	 */
	private void linkListener() {
		this.mView.addListenerToSettingsAction(new SettingsActionListener(this));
	}

	/**
	 * Passes the settings of the store to the settings dialog view for display.
	 */
	private void passSettingsToSettingsDialogView() {
		for (final Entry<String, String> entry : this.mSettingsStore.entrySet()) {
			final String[] keySplit = entry.getKey().split(KEY_INFO_SEPARATOR);
			final String keyIdentifier = keySplit[0];

			if (keyIdentifier.equals(KEY_IDENTIFIER_DRIVER)) {
				// Driver settings
				final EBrowser browser = EBrowser.valueOf(keySplit[1]);
				final JTextField field = this.mSettingsDialog.getBrowserDriverField(browser);
				field.setText(entry.getValue());
			} else if (keyIdentifier.equals(KEY_IDENTIFIER_BINARY)) {
				// Binary settings
				final JTextField field = this.mSettingsDialog.getBinaryField();
				field.setText(entry.getValue());
			} else if (keyIdentifier.equals(KEY_IDENTIFIER_USER_PROFILE)) {
				// User profile settings
				final JTextField field = this.mSettingsDialog.getUserProfileField();
				field.setText(entry.getValue());
			} else if (keyIdentifier.equals(KEY_IDENTIFIER_PROTECTION_SPELL)) {
				// Protection spell settings
				final JTextField field = this.mSettingsDialog.getProtectionSpellField();
				field.setText(entry.getValue());
			}
		}
	}
}

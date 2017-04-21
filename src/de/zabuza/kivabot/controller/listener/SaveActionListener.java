package de.zabuza.kivabot.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.zabuza.kivabot.controller.settings.SettingsController;

/**
 * Listener of the save action. Activates a callback which saves the settings
 * and closes the settings dialog.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class SaveActionListener implements ActionListener {
	/**
	 * The controller of the settings.
	 */
	private final SettingsController mController;

	/**
	 * Creates a new listener of the save action. Activates a callback which
	 * saves the settings and closes the settings dialog.
	 * 
	 * @param controller
	 *            The controller of the settings
	 */
	public SaveActionListener(final SettingsController controller) {
		this.mController = controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		this.mController.executeSaveAction();
	}
}

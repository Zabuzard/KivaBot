package de.zabuza.kivabot.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.zabuza.kivabot.controller.settings.SettingsController;

/**
 * Listener of the settings action.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class SettingsActionListener implements ActionListener {
	/**
	 * The controller of the settings.
	 */
	private final SettingsController mController;

	/**
	 * Creates a new listener of the start action.
	 * 
	 * @param controller
	 *            Controller of the settings
	 */
	public SettingsActionListener(final SettingsController controller) {
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
		this.mController.executeSettingsAction();
	}
}

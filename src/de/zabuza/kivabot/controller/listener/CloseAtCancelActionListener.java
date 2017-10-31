package de.zabuza.kivabot.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import de.zabuza.kivabot.view.SettingsDialog;

/**
 * Listener of the cancel action. Closes the dialog at activation.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class CloseAtCancelActionListener implements ActionListener {
	/**
	 * The controller of the settings.
	 */
	private final SettingsDialog mDialog;

	/**
	 * Creates a new listener of the cancel action. Closes the dialog at activation.
	 * 
	 * @param dialog
	 *            The dialog of the settings
	 */
	public CloseAtCancelActionListener(final SettingsDialog dialog) {
		this.mDialog = dialog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		this.mDialog.dispatchEvent(new WindowEvent(this.mDialog, WindowEvent.WINDOW_CLOSING));
	}
}

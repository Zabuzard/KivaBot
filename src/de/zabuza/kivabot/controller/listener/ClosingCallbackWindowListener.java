package de.zabuza.kivabot.controller.listener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import de.zabuza.kivabot.controller.settings.SettingsController;

/**
 * Activates a callback for window closing which frees the parent window.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class ClosingCallbackWindowListener implements WindowListener {
	/**
	 * The controller of the settings.
	 */
	private final SettingsController mController;

	/**
	 * Creates a new window listener which activates a callback for window
	 * closing which frees the parent window.
	 * 
	 * @param controller
	 *            Controller of the settings
	 */
	public ClosingCallbackWindowListener(final SettingsController controller) {
		this.mController = controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(final WindowEvent event) {
		// Nothing to do yet
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(final WindowEvent event) {
		// Nothing to do yet
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(final WindowEvent event) {
		this.mController.closingSettingsDialog();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.
	 * WindowEvent)
	 */
	@Override
	public void windowDeactivated(final WindowEvent event) {
		// Nothing to do yet
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.
	 * WindowEvent)
	 */
	@Override
	public void windowDeiconified(final WindowEvent event) {
		// Nothing to do yet
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(final WindowEvent event) {
		// Nothing to do yet
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(final WindowEvent event) {
		// Nothing to do yet
	}

}

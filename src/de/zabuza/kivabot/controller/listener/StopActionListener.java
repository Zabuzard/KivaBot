package de.zabuza.kivabot.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.zabuza.kivabot.controller.MainFrameController;

/**
 * Listener of the stop action.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class StopActionListener implements ActionListener {
	/**
	 * The controller of the main frame.
	 */
	private final MainFrameController mController;

	/**
	 * Creates a new listener of the stop action.
	 * 
	 * @param view
	 *            View of the main frame
	 * @param controller
	 *            Controller of the main frame
	 */
	public StopActionListener(final MainFrameController controller) {
		mController = controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		mController.stopRoutine();
	}
}

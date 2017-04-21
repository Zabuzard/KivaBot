package de.zabuza.kivabot.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.zabuza.kivabot.controller.MainFrameController;

/**
 * Listener of the start action.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class StartActionListener implements ActionListener {
	/**
	 * The controller of the main frame.
	 */
	private final MainFrameController mController;

	/**
	 * Creates a new listener of the start action.
	 * 
	 * @param controller
	 *            Controller of the main frame
	 */
	public StartActionListener(final MainFrameController controller) {
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
		this.mController.startRoutine();
	}
}

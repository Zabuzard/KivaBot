package de.zabuza.kivabot.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import de.zabuza.kivabot.view.SettingsDialog;

/**
 * Listener of the file choose and set action. Opens a file choose dialog and
 * sets the resulting path to a given text field.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class FileChooseSetActionListener implements ActionListener {
	/**
	 * The title of the file chooser dialog.
	 */
	private final static String DIALOG_TITLE = "Select a file";
	/**
	 * The file chooser to use.
	 */
	private final JFileChooser mFileChooser;
	/**
	 * The parent dialog owning the text field.
	 */
	private final SettingsDialog mParent;
	/**
	 * The text field to set the file path to.
	 */
	private final JTextField mTextField;

	/**
	 * Creates a new listener of the file choose and set action. Opens a file
	 * choose dialog and sets the resulting path to a given text field.
	 * 
	 * @param parent
	 *            The parent dialog owning the text field
	 * @param textField
	 *            The text field to set the file path to
	 */
	public FileChooseSetActionListener(final SettingsDialog parent, final JTextField textField) {
		mTextField = textField;
		mParent = parent;
		mFileChooser = new JFileChooser();
		mFileChooser.setDialogTitle(DIALOG_TITLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		String filePath = null;

		// Open file chooser dialog
		int option = mFileChooser.showOpenDialog(mParent);
		if (option == JFileChooser.APPROVE_OPTION) {
			filePath = mFileChooser.getSelectedFile().getAbsolutePath();
		} else if (option == JFileChooser.CANCEL_OPTION) {
			// Do nothing, just let filePath stay null
		} else {
			throw new IllegalStateException("An unknown error occurred during file selection.");
		}

		// Put the path to the file in the text field
		if (filePath != null) {
			mTextField.setText(filePath);
		}
	}
}

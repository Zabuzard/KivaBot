package de.zabuza.kivabot.view;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Button which looks like a clickable hyperlink text.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class LinkButton extends JButton {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Predecessing text of the button title.
	 */
	private static final String TITLE_PRE = "<HTML><FONT color=\"#000099\">";

	/**
	 * Successing text of the button title.
	 */
	private static final String TITLE_SUCC = "</FONT></HTML>";

	/**
	 * Creates a new link button.
	 * 
	 * @param text
	 *            The text of the button
	 */
	public LinkButton(final String title) {
		super(TITLE_PRE + title + TITLE_SUCC);
		setBorderPainted(false);
		setOpaque(false);
		setBackground(Color.WHITE);
	}

}

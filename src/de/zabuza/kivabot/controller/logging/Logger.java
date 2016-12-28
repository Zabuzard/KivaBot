package de.zabuza.kivabot.controller.logging;

import de.zabuza.kivabot.view.MainFrameView;

/**
 * Logger of the main frame.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class Logger {
	/**
	 * First logging level, has a small indent.
	 */
	public static final int FIRST_LEVEL = 1;
	/**
	 * Second logging level, has a medium indent.
	 */
	public static final int SECOND_LEVEL = 2;
	/**
	 * Top logging level, has no indent.
	 */
	public static final int TOP_LEVEL = 0;

	/**
	 * Indent to be used for every logging level.
	 */
	private static final String LOG_LEVEL_INDENT = "  ";
	/**
	 * Prompt text which should be displayed in front of every output
	 */
	private static final String PROMPT = ">";

	/**
	 * View of the main frame.
	 */
	private final MainFrameView mView;

	/**
	 * Creates a new Logger using the view of the main frame.
	 * 
	 * @param view
	 *            view of the main frame
	 */
	public Logger(final MainFrameView view) {
		mView = view;
	}

	/**
	 * Logs an error message.
	 * 
	 * @param message
	 *            The error message to log
	 * @param level
	 *            The logging level
	 */
	public void logError(final String message, final int level) {
		if (mView != null) {
			mView.logError(createLevelIndent(level) + PROMPT + message);
		}
	}

	/**
	 * Logs an information.
	 * 
	 * @param message
	 *            The message to log
	 * @param level
	 *            The logging level
	 */
	public void logInfo(final String message, final int level) {
		if (mView != null) {
			mView.log(createLevelIndent(level) + PROMPT + message);
		}
	}

	/**
	 * Logs an unknown error.
	 * 
	 * @param e
	 *            The error to log
	 */
	public void logUnknownError(final Exception e) {
		if (mView != null) {
			mView.logError(PROMPT + "An unknown error occurred:");
			if (e.getMessage() != null) {
				mView.logError(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates a logging level indent.
	 * 
	 * @param level
	 *            Logging level of the indent.
	 * @return The indent for the logging level
	 */
	private String createLevelIndent(final int level) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < level; i++) {
			builder.append(LOG_LEVEL_INDENT);
		}
		return builder.toString();
	}
}
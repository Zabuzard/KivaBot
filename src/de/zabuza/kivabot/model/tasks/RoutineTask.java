package de.zabuza.kivabot.model.tasks;

import org.openqa.selenium.Capabilities;

import de.zabuza.kivabot.controller.MainFrameController;
import de.zabuza.kivabot.controller.logging.Logger;
import de.zabuza.kivabot.model.AbortTaskException;
import de.zabuza.kivabot.model.IBrowserSettingsProvider;
import de.zabuza.sparkle.IFreewarAPI;
import de.zabuza.sparkle.Sparkle;
import de.zabuza.sparkle.freewar.EWorld;
import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.webdriver.EBrowser;

/**
 * The KivaBot routine which uses the Sparkle API to login to Freewar and
 * collect all ressources.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RoutineTask extends Thread implements ITask {

	/**
	 * The Freewar API to use.
	 */
	private IFreewarAPI mApi;
	/**
	 * The browser to use.
	 */
	private final EBrowser mBrowser;
	/**
	 * The browser driver provider.
	 */
	private IBrowserSettingsProvider mBrowserSettingsProvider;
	/**
	 * The controller of the main frame.
	 */
	private final MainFrameController mController;
	/**
	 * The current executing sub task.
	 */
	private ITask mCurrentSubTask;
	/**
	 * The Freewar instance to use.
	 */
	private IFreewarInstance mInstance;
	/**
	 * The logger to use.
	 */
	private final Logger mLogger;
	/**
	 * The password of the user to act with.
	 */
	private final String mPassword;
	/**
	 * The name of the user to act with.
	 */
	private final String mUsername;

	/**
	 * Creates a new routine task.
	 * 
	 * @param username
	 *            The name of the user to act with
	 * @param password
	 *            The password of the user to act with
	 * @param browser
	 *            The browser to use
	 * @param logger
	 *            The logger to use
	 * @param controller
	 *            The controller of the main frame
	 * @param browserSettingsProvider
	 *            The browser settings provider
	 */
	public RoutineTask(final String username, final String password, final EBrowser browser, final Logger logger,
			final MainFrameController controller, final IBrowserSettingsProvider browserSettingsProvider) {
		mUsername = username;
		mPassword = password;
		mBrowser = browser;
		mLogger = logger;
		mController = controller;
		mBrowserSettingsProvider = browserSettingsProvider;

		mApi = null;
		mInstance = null;
		mCurrentSubTask = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#interrupt()
	 */
	@Override
	public void interrupt() {
		if (mCurrentSubTask != null) {
			mCurrentSubTask.interrupt();
		}
		super.interrupt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			// Create sparkle API
			mLogger.logInfo("Starting Sparkle...", Logger.TOP_LEVEL);
			mApi = new Sparkle(mBrowser);
			final Capabilities capabilities = mApi.createCapabilities(mBrowser,
					mBrowserSettingsProvider.getDriverForBrowser(mBrowser),
					mBrowserSettingsProvider.getBrowserBinary());
			mApi.setCapabilities(capabilities);
			mLogger.logInfo("Sparkle started.", Logger.FIRST_LEVEL);

			// Login and create an instance
			mLogger.logInfo("Creating instance...", Logger.TOP_LEVEL);
			if (mUsername == null || mUsername.equals("") || mPassword == null || mPassword.equals("")) {
				mLogger.logError("Invalid username or password.", Logger.FIRST_LEVEL);
				throw new AbortTaskException();
			}
			// TODO Add World as parameter
			mInstance = mApi.login(mUsername, mPassword, EWorld.ONE);
			mLogger.logInfo("Instance created.", Logger.FIRST_LEVEL);

			// Collect oil
			registerAndStartSubTask(new CollectOilTask(mInstance, mLogger));
			if (isInterrupted()) {
				return;
			}

			// TODO Add other tasks

		} catch (AbortTaskException e) {
			// Known exception, just terminate
		} catch (Exception e) {
			mLogger.logUnknownError(e);
		} finally {
			terminate();
			mController.routineFinished();
		}
	}

	/**
	 * Registers the given sub task as the current and starts it.
	 * 
	 * @param subTask
	 *            Sub task to register and start
	 */
	private void registerAndStartSubTask(final ITask subTask) {
		mCurrentSubTask = subTask;
		mCurrentSubTask.start();
	}

	/**
	 * Terminates the current task and shuts down the instance and API.
	 */
	private void terminate() {
		if (mApi != null) {
			if (mInstance != null) {
				mApi.logout(mInstance);
				mInstance = null;
			}
			mApi.shutdown();
		}
	}
}

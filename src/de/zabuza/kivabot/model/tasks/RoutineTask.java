package de.zabuza.kivabot.model.tasks;

import java.awt.Point;
import java.util.Optional;
import java.util.Set;

import org.openqa.selenium.remote.DesiredCapabilities;

import de.zabuza.kivabot.controller.MainFrameController;
import de.zabuza.kivabot.controller.logging.Logger;
import de.zabuza.kivabot.model.AbortTaskException;
import de.zabuza.kivabot.model.IBrowserSettingsProvider;
import de.zabuza.sparkle.IFreewarAPI;
import de.zabuza.sparkle.Sparkle;
import de.zabuza.sparkle.freewar.EWorld;
import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.movement.network.EMoveType;
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
	private final IBrowserSettingsProvider mBrowserSettingsProvider;
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
	 * A set containing all movement options allowed to use.
	 */
	private final Set<EMoveType> mMovementOptions;
	/**
	 * The password of the user to act with.
	 */
	private final String mPassword;
	/**
	 * If present, the name of the protection spell item which is used prior of
	 * movement.
	 */
	private final Optional<String> mProtectionSpell;
	/**
	 * A set containing all sub tasks to execute.
	 */
	private final Set<EKivaTask> mSubTasks;
	/**
	 * The name of the user to act with.
	 */
	private final String mUsername;
	/**
	 * Whether the special skill gets activated prior to movement.
	 */
	private final boolean mUseSpecialSkill;
	/**
	 * The world of the user to act with.
	 */
	private final EWorld mWorld;

	/**
	 * Creates a new routine task.
	 * 
	 * @param username
	 *            The name of the user to act with
	 * @param password
	 *            The password of the user to act with
	 * @param world
	 *            The world of the user to act with
	 * @param browser
	 *            The browser to use
	 * @param movementOptions
	 *            A set containing all movement options allowed to use
	 * @param protectionSpell
	 *            If present, the name of the protection spell item to use prior
	 *            of movement
	 * @param useSpecialSkill
	 *            Whether the special skill should get activated prior to
	 *            movement
	 * @param subTasks
	 *            A set containing all sub tasks to execute
	 * @param logger
	 *            The logger to use
	 * @param controller
	 *            The controller of the main frame
	 * @param browserSettingsProvider
	 *            The browser settings provider
	 */
	public RoutineTask(final String username, final String password, final EWorld world, final EBrowser browser,
			final Set<EMoveType> movementOptions, final Optional<String> protectionSpell, final boolean useSpecialSkill,
			final Set<EKivaTask> subTasks, final Logger logger, final MainFrameController controller,
			final IBrowserSettingsProvider browserSettingsProvider) {
		this.mUsername = username;
		this.mPassword = password;
		this.mWorld = world;
		this.mBrowser = browser;
		this.mMovementOptions = movementOptions;
		this.mProtectionSpell = protectionSpell;
		this.mUseSpecialSkill = useSpecialSkill;
		this.mSubTasks = subTasks;
		this.mLogger = logger;
		this.mController = controller;
		this.mBrowserSettingsProvider = browserSettingsProvider;

		this.mApi = null;
		this.mInstance = null;
		this.mCurrentSubTask = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#interrupt()
	 */
	@Override
	public void interrupt() {
		if (this.mCurrentSubTask != null) {
			this.mCurrentSubTask.interrupt();
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
			this.mLogger.logInfo("Starting Sparkle...", Logger.TOP_LEVEL);
			this.mApi = new Sparkle(this.mBrowser);
			final DesiredCapabilities capabilities = this.mApi.createCapabilities(this.mBrowser,
					this.mBrowserSettingsProvider.getDriverForBrowser(this.mBrowser),
					this.mBrowserSettingsProvider.getBrowserBinary(), this.mBrowserSettingsProvider.getUserProfile());

			this.mApi.setCapabilities(capabilities);
			this.mLogger.logInfo("Sparkle started.", Logger.FIRST_LEVEL);

			// Login and create an instance
			this.mLogger.logInfo("Creating instance...", Logger.TOP_LEVEL);
			if (this.mUsername == null || this.mUsername.equals("") || this.mPassword == null
					|| this.mPassword.equals("")) {
				this.mLogger.logError("Invalid username or password.", Logger.FIRST_LEVEL);
				throw new AbortTaskException();
			}
			this.mInstance = this.mApi.login(this.mUsername, this.mPassword, this.mWorld);
			this.mLogger.logInfo("Instance created.", Logger.FIRST_LEVEL);

			// Ensure protection if desired
			if (this.mProtectionSpell.isPresent()) {
				final String protectionSpellName = this.mProtectionSpell.get();
				registerAndStartSubTask(new EnsureProtectionTask(this.mInstance, protectionSpellName, this.mLogger));
			}

			// Activate the special skill if desired
			if (this.mUseSpecialSkill) {
				registerAndStartSubTask(new ActivateSpecialSkillTask(this.mInstance, this.mLogger));
			}

			// Collect baru corn
			if (this.mSubTasks.contains(EKivaTask.BARU_CORN)) {
				collectResource(new Point(115, 94), "corn storehouse", "Getreide mitnehmen", "baru corn");
			}
			// Collect glodo fish
			if (this.mSubTasks.contains(EKivaTask.GLODO_FISH)) {
				collectResource(new Point(68, 116), "fish storehouse", "Fische mitnehmen", "glodo fish");
			}
			// Collect marsh gas
			if (this.mSubTasks.contains(EKivaTask.MARSH_GAS)) {
				collectResource(new Point(76, 104), "gas storehouse", "Sumpfgasflaschen mitnehmen", "marsh gas");
			}
			// Collect oil barrel
			if (this.mSubTasks.contains(EKivaTask.OIL_BARREL)) {
				collectResource(new Point(103, 117), "oil storehouse", "Ölfässer mitnehmen", "oil barrel");
			}
			// Collect gold from the universal foundation
			if (this.mSubTasks.contains(EKivaTask.UNIVERSAL_FOUNDATION)) {
				collectResource(new Point(87, 112), "universal foundation", "Goldmünzen abholen", "gold");
			}
		} catch (final AbortTaskException e) {
			// Known exception, just terminate
		} catch (final Exception e) {
			this.mLogger.logUnknownError(e);
		} finally {
			terminate();
			this.mController.routineFinished();
		}
	}

	/**
	 * Starts a collect resource task which moves to the given destination and
	 * collects the given resource by clicking an anchor.
	 * 
	 * @param destination
	 *            The destination where the resource is located at
	 * @param destinationName
	 *            The name of the destination used for logging purpose
	 * @param resourceAnchorText
	 *            The text of the anchor which collects the given resource
	 * @param resourceName
	 *            The name of the resource to collect used for logging purpose
	 * @throws AbortTaskException
	 *             Thrown when the task was aborted, for example when it was
	 *             canceled or an error occurred.
	 */
	private void collectResource(final Point destination, final String destinationName, final String resourceAnchorText,
			final String resourceName) throws AbortTaskException {
		try {
			registerAndStartSubTask(new CollectResourceTask(this.mInstance, destination, destinationName,
					this.mMovementOptions, resourceAnchorText, resourceName, this.mLogger));
		} catch (final AbortTaskException e) {
			// Known error, just abort the current task and continue
		}
		if (isInterrupted()) {
			throw new AbortTaskException();
		}
	}

	/**
	 * Registers the given sub task as the current and starts it.
	 * 
	 * @param subTask
	 *            Sub task to register and start
	 */
	private void registerAndStartSubTask(final ITask subTask) {
		this.mCurrentSubTask = subTask;
		this.mCurrentSubTask.start();
	}

	/**
	 * Terminates the current task and shuts down the instance and API.
	 */
	private void terminate() {
		if (this.mApi != null) {
			if (this.mInstance != null) {
				this.mApi.logout(this.mInstance, false);
				this.mInstance = null;
			}
			this.mApi.shutdown(false);
		}
	}
}

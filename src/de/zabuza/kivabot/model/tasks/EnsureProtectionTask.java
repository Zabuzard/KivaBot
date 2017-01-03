package de.zabuza.kivabot.model.tasks;

import de.zabuza.kivabot.controller.logging.Logger;
import de.zabuza.kivabot.model.AbortTaskException;
import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.inventory.IInventory;
import de.zabuza.sparkle.freewar.player.IPlayer;

/**
 * A task which ensures that the player is protected by activating a given spell
 * if necessary.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class EnsureProtectionTask implements ITask {
	/**
	 * The name of the protection status.
	 */
	private static final String STATUS_PROTECTION_NAME = "Schutz";
	/**
	 * The Freewar instance to use.
	 */
	private IFreewarInstance mInstance;
	/**
	 * Whether interrupted flag of the task is set.
	 */
	private boolean mInterrupted;
	/**
	 * The logger to use.
	 */
	private final Logger mLogger;
	/**
	 * The name of the protection spell item to use if necessary.
	 */
	private final String mProtectionSpellName;

	/**
	 * Creates a task which ensures that the player is protected by activating a
	 * given spell if necessary.
	 * 
	 * @param instance
	 *            The Freewar instance to use
	 * @param protectionSpellName
	 *            The name of the protection spell item to use if necessary
	 * @param logger
	 *            The logger to use
	 */
	public EnsureProtectionTask(final IFreewarInstance instance, final String protectionSpellName,
			final Logger logger) {
		mInstance = instance;
		mProtectionSpellName = protectionSpellName;
		mLogger = logger;
		mInterrupted = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.kivabot.model.tasks.ITask#interrupt()
	 */
	@Override
	public void interrupt() {
		mInterrupted = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.kivabot.model.tasks.ITask#isInterrupted()
	 */
	@Override
	public boolean isInterrupted() {
		return mInterrupted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.kivabot.model.tasks.ITask#start()
	 */
	@Override
	public void start() {
		// Ensuring a protection
		mLogger.logInfo("Ensuring protection...", Logger.TOP_LEVEL);

		// Check whether the player is already protected
		final IPlayer player = mInstance.getPlayer();
		final String status = player.getStatus();
		if (status.contains(STATUS_PROTECTION_NAME)) {
			mLogger.logInfo("Protection is already active.", Logger.FIRST_LEVEL);
		} else {
			// Activate the protection spell item
			final IInventory inventory = mInstance.getInventory();
			if (inventory.hasItem(mProtectionSpellName) && inventory.activateItem(mProtectionSpellName)) {
				mLogger.logInfo("Activated protection spell.", Logger.FIRST_LEVEL);
			} else {
				mLogger.logError("Protection spell not found.", Logger.FIRST_LEVEL);
				throw new AbortTaskException();
			}
		}
	}

}

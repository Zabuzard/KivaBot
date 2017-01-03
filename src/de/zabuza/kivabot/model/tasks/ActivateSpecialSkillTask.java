package de.zabuza.kivabot.model.tasks;

import de.zabuza.kivabot.controller.logging.Logger;
import de.zabuza.kivabot.model.AbortTaskException;
import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.player.IPlayer;

/**
 * A task which activates the special ability of the player.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class ActivateSpecialSkillTask implements ITask {
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
	 * Creates a task which activates the special ability of the player.
	 * 
	 * @param instance
	 *            The Freewar instance to use
	 * @param logger
	 *            The logger to use
	 */
	public ActivateSpecialSkillTask(final IFreewarInstance instance, final Logger logger) {
		mInstance = instance;
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
		// Activate the special skill
		mLogger.logInfo("Activating special skill...", Logger.TOP_LEVEL);
		final IPlayer player = mInstance.getPlayer();
		// TODO Exchange condition with activation of special skill
		if (player.getAttackPoints() != 0) {
			mLogger.logInfo("Activated special skill.", Logger.FIRST_LEVEL);
		} else {
			mLogger.logError("Failed to activate special skill.", Logger.FIRST_LEVEL);
			throw new AbortTaskException();
		}
	}

}

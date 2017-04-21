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
	private final IFreewarInstance mInstance;
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
		this.mInstance = instance;
		this.mLogger = logger;
		this.mInterrupted = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.kivabot.model.tasks.ITask#interrupt()
	 */
	@Override
	public void interrupt() {
		this.mInterrupted = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.kivabot.model.tasks.ITask#isInterrupted()
	 */
	@Override
	public boolean isInterrupted() {
		return this.mInterrupted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.kivabot.model.tasks.ITask#start()
	 */
	@Override
	public void start() {
		// Activate the special skill
		this.mLogger.logInfo("Activating special skill...", Logger.TOP_LEVEL);
		final IPlayer player = this.mInstance.getPlayer();
		if (player.activateSpecialSkill()) {
			this.mLogger.logInfo("Activated special skill.", Logger.FIRST_LEVEL);
		} else {
			this.mLogger.logError("Failed to activate special skill.", Logger.FIRST_LEVEL);
			throw new AbortTaskException();
		}
	}

}

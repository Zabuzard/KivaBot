package de.zabuza.kivabot.model.tasks;

import de.zabuza.kivabot.controller.logging.Logger;
import de.zabuza.sparkle.freewar.IFreewarInstance;

/**
 * A task which moves the given Freewar instance to the oil storehouse and
 * collects oil.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class CollectOilTask implements ITask {

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
	 * Creates a task which moves the given Freewar instance to the oil
	 * storehouse and collects oil.
	 * 
	 * @param instance
	 *            The Freewar instance to use
	 * @param logger
	 *            The logger to use
	 */
	public CollectOilTask(final IFreewarInstance instance, final Logger logger) {
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
		mLogger.logInfo("Moving to oil storehouse...", Logger.TOP_LEVEL);
		// TODO Implement something
		mLogger.logInfo("Arrival at oil storehouse.", Logger.FIRST_LEVEL);

		mLogger.logInfo("Collecting oil...", Logger.TOP_LEVEL);
		// TODO Implement something
		mLogger.logInfo("Oil collected.", Logger.FIRST_LEVEL);
	}

}

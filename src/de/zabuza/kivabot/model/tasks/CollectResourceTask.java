package de.zabuza.kivabot.model.tasks;

import java.awt.Point;
import java.util.Set;

import de.zabuza.kivabot.controller.logging.Logger;
import de.zabuza.kivabot.model.AbortTaskException;
import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.movement.IMovement;
import de.zabuza.sparkle.freewar.movement.network.EMoveType;

/**
 * A task which moves the given Freewar instance to a given destination and
 * collects a given resource by clicking a link.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class CollectResourceTask implements ITask {

	/**
	 * Timeout to check for movement completion.
	 */
	private final static int MOVEMENT_CHECK_TIMEOUT = 500;
	/**
	 * The point representing the x and y coordinations of the destination to move
	 * to.
	 */
	private final Point mDestination;
	/**
	 * The name of the destination used by the logger.
	 */
	private final String mDestinationName;
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
	 * The set containing all movement options allowed for movement.
	 */
	private final Set<EMoveType> mMovementOptions;
	/**
	 * The text of the resource collection anchor.
	 */
	private final String mResourceAnchorText;
	/**
	 * The name of the resource used by the logger.
	 */
	private final String mResourceName;

	/**
	 * Creates a task which moves the given Freewar instance to a given destination
	 * and collects a given resource by clicking a link.
	 * 
	 * @param instance
	 *            The Freewar instance to use
	 * @param destination
	 *            A point representing the x and y coordinations of the destination
	 *            to move to
	 * @param destinationName
	 *            The name of the destination used by the logger
	 * @param movementOptions
	 *            A set containing all movement options allowed for movement
	 * @param resourceAnchorText
	 *            The text of the resource collection anchor
	 * @param resourceName
	 *            The name of the resource used by the logger
	 * @param logger
	 *            The logger to use
	 */
	public CollectResourceTask(final IFreewarInstance instance, final Point destination, final String destinationName,
			final Set<EMoveType> movementOptions, final String resourceAnchorText, final String resourceName,
			final Logger logger) {
		this.mInstance = instance;
		this.mDestination = destination;
		this.mDestinationName = destinationName;
		this.mMovementOptions = movementOptions;
		this.mResourceAnchorText = resourceAnchorText;
		this.mResourceName = resourceName;
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
		// Move to the destination
		this.mLogger.logInfo("Moving to " + this.mDestinationName + "...", Logger.TOP_LEVEL);
		final IMovement movement = this.mInstance.getMovement();
		movement.moveTo((int) this.mDestination.getX(), (int) this.mDestination.getY(), this.mMovementOptions);
		while (movement.hasMovementTask()) {
			try {
				Thread.sleep(MOVEMENT_CHECK_TIMEOUT);
			} catch (final InterruptedException e) {
				movement.cancelMovementTask();
				if (!isInterrupted()) {
					this.mLogger.logUnknownError(e);
				}
				throw new AbortTaskException();
			}
		}
		if (!movement.wasTaskSuccessful()) {
			this.mLogger.logError("Movement was aborted.", Logger.FIRST_LEVEL);
			throw new AbortTaskException();
		}
		this.mLogger.logInfo("Arrival at " + this.mDestinationName + ".", Logger.FIRST_LEVEL);

		// Collect the resource
		this.mLogger.logInfo("Collecting " + this.mResourceName + "...", Logger.TOP_LEVEL);
		final boolean anchorClicked = this.mInstance.clickAnchorByContent(EFrame.MAIN, this.mResourceAnchorText);
		if (!anchorClicked) {
			this.mLogger.logError("Collection anchor not found.", Logger.FIRST_LEVEL);
			throw new AbortTaskException();
		}
		this.mLogger.logInfo("Collected " + this.mResourceName + ".", Logger.FIRST_LEVEL);
	}

}

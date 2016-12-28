package de.zabuza.kivabot.model.tasks;

/**
 * Interface for tasks. A Task can be started and interrupted.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface ITask {
	/**
	 * Sets an interrupted flag which is checked by the task to terminate.
	 */
	public void interrupt();

	/**
	 * Whether the interrupted flag is set.
	 * 
	 * @return <tt>True</tt> if the interrupted flag is set, <tt>false</tt>
	 *         else.
	 */
	public boolean isInterrupted();

	/**
	 * Starts the task.
	 * 
	 * @throws AbortTaskException
	 *             Thrown whenever the task should be aborted because it
	 *             encountered an exception. This can be used to catch known
	 *             errors.
	 */
	public void start();
}

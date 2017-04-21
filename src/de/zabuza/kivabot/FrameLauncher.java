package de.zabuza.kivabot;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import de.zabuza.kivabot.controller.MainFrameController;
import de.zabuza.kivabot.controller.logging.Logger;
import de.zabuza.kivabot.view.MainFrameView;

/**
 * Starts the tool in a frame.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class FrameLauncher {
	/**
	 * The title of the tool.
	 */
	private static final String TITLE = "KivaBot";

	/**
	 * Launch the view.
	 * 
	 * @param args
	 *            Not supported
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				JFrame frame = null;
				MainFrameView window = null;
				Logger logger = null;
				try {
					frame = new JFrame();
					frame.setResizable(false);
					frame.setTitle(TITLE);
					frame.setBounds(0, 0, MainFrameView.WIDTH, MainFrameView.HEIGHT);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.getContentPane().setLayout(null);
					final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation((screenSize.width - frame.getWidth()) / 2,
							(screenSize.height - frame.getHeight()) / 2);

					window = new MainFrameView(frame);
					logger = new Logger(window);
					final MainFrameController controller = new MainFrameController(frame, window, logger);
					controller.initialize();
					controller.start();
				} catch (final Exception e) {
					if (logger != null) {
						logger.logUnknownError(e);
					}
				} finally {
					if (frame != null) {
						frame.setVisible(true);
					}
				}
			}
		});
	}

	/**
	 * Utility class. No implementation.
	 */
	private FrameLauncher() {

	}
}

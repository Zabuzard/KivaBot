package de.zabuza.kivabot.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import de.zabuza.kivabot.model.tasks.EKivaTask;
import de.zabuza.sparkle.freewar.EWorld;
import de.zabuza.sparkle.freewar.movement.network.EMoveType;
import de.zabuza.sparkle.webdriver.EBrowser;

/**
 * View of the main frame.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class MainFrameView {
	/**
	 * Height of the view.
	 */
	public static final int HEIGHT = 425;
	/**
	 * Width of the view.
	 */
	public static final int WIDTH = 450;
	/**
	 * The default amount of columns for fields of the view.
	 */
	private static final int DEFAULT_FIELD_COLUMNS = 10;
	/**
	 * The default font of the view.
	 */
	private static final String DEFAULT_FONT = "Tahoma";
	/**
	 * The default font size of the view.
	 */
	private static final int DEFAULT_FONT_SIZE = 11;
	/**
	 * Check box for the blue sphere movement option.
	 */
	private JCheckBox mBlueSphereMovementOptionsBox;
	/**
	 * The browser choice of the view.
	 */
	private JComboBox<EBrowser> mBrowserChoiceBox;
	/**
	 * Container of the view.
	 */
	private final Container mContainer;
	/**
	 * The frame of the view.
	 */
	private final JFrame mFrame;
	/**
	 * List of all input elements.
	 */
	private final List<JComponent> mInputElements;
	/**
	 * Log area of the view.
	 */
	private JTextPane mLogArea;
	/**
	 * Log pane of the view.
	 */
	private JScrollPane mLogPane;
	/**
	 * The main panel of the view.
	 */
	private JPanel mMainPanel;
	/**
	 * Password field of the view.
	 */
	private JTextField mPasswordField;
	/**
	 * Settings button of the view.
	 */
	private JButton mSettingsBtn;
	/**
	 * Start button of the view.
	 */
	private JButton mStartBtn;
	/**
	 * Stop button of the view.
	 */
	private JButton mStopBtn;
	/**
	 * A list of all selectable tasks.
	 */
	private List<JCheckBox> mTaskList;
	/**
	 * The trailer panel of the view.
	 */
	private JPanel mTrailerPanel;
	/**
	 * Check box for the protection spell option.
	 */
	private JCheckBox mUseProtectionSpell;
	/**
	 * Username field of the view.
	 */
	private JTextField mUsernameField;
	/**
	 * Check box for the special skill option.
	 */
	private JCheckBox mUseSpecialSkill;
	/**
	 * The world choice of the view.
	 */
	private JComboBox<EWorld> mWorldChoiceBox;

	/**
	 * Creates the view.
	 * 
	 * @param frame
	 *            Frame of the view
	 */
	public MainFrameView(final JFrame frame) {
		this.mFrame = frame;
		this.mContainer = frame.getContentPane();
		this.mInputElements = new LinkedList<>();
		initialize();
	}

	/**
	 * Adds an action listener to the settings action.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToSettingsAction(final ActionListener listener) {
		this.mSettingsBtn.addActionListener(listener);
	}

	/**
	 * Adds an action listener to the start action.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToStartAction(final ActionListener listener) {
		this.mStartBtn.addActionListener(listener);
	}

	/**
	 * Adds an action listener to the stop action.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToStopAction(final ActionListener listener) {
		this.mStopBtn.addActionListener(listener);
	}

	/**
	 * Adds a window listener to the view window.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addWindowListener(final WindowListener listener) {
		this.mFrame.addWindowListener(listener);
	}

	/**
	 * Gets the selected input browser.
	 * 
	 * @return The selected input browser
	 */
	public EBrowser getBrowser() {
		return (EBrowser) this.mBrowserChoiceBox.getSelectedItem();
	}

	/**
	 * Gets the selected kiva tasks.
	 * 
	 * @return A set containing all selected kiva tasks
	 */
	public Set<EKivaTask> getKivaTasks() {
		final Set<EKivaTask> kivaTasks = new HashSet<>();
		for (final JCheckBox taskBox : this.mTaskList) {
			if (taskBox.isSelected()) {
				final String text = taskBox.getText();
				kivaTasks.add(EKivaTask.valueOf(text));
			}
		}
		return kivaTasks;
	}

	/**
	 * Gets the selected movement options.
	 * 
	 * @return A set containing all selected movement options
	 */
	public Set<EMoveType> getMovementOptions() {
		final Set<EMoveType> movementOptions = new HashSet<>();
		if (this.mBlueSphereMovementOptionsBox.isSelected()) {
			movementOptions.add(EMoveType.BLUE_SPHERE);
		}
		return movementOptions;
	}

	/**
	 * Gets the input password.
	 * 
	 * @return The input password
	 */
	public String getPassword() {
		return this.mPasswordField.getText();
	}

	/**
	 * Gets the input username.
	 * 
	 * @return The input username
	 */
	public String getUsername() {
		return this.mUsernameField.getText();
	}

	/**
	 * Gets the selected input world.
	 * 
	 * @return The selected input world
	 */
	public EWorld getWorld() {
		return (EWorld) this.mWorldChoiceBox.getSelectedItem();
	}

	/**
	 * Gets whether the use protection spell box is checked or not.
	 * 
	 * @return <tt>True</tt> if the use protection spell box is checked,
	 *         <tt>false</tt> otherwise
	 */
	public boolean isUseProtectionSpellChecked() {
		return this.mUseProtectionSpell.isSelected();
	}

	/**
	 * Gets whether the use special skill box is checked or not.
	 * 
	 * @return <tt>True</tt> if the use special skill box is checked,
	 *         <tt>false</tt> otherwise
	 */
	public boolean isUseSpecialSkillChecked() {
		return this.mUseSpecialSkill.isSelected();
	}

	/**
	 * Appends a line to the log area.
	 * 
	 * @param line
	 *            line to append
	 */
	public void log(final String line) {
		appendToLog(line + "\n", Color.BLACK);
	}

	/**
	 * Appends a line to the log area using a red font.
	 * 
	 * @param line
	 *            line to append
	 */
	public void logError(final String line) {
		appendToLog(line + "\n", Color.RED);
	}

	/**
	 * Enables or disables all input fields.
	 * 
	 * @param enabled
	 *            Whether the fields should be enabled or disabled
	 */
	public void setAllInputEnabled(final boolean enabled) {
		for (final JComponent element : this.mInputElements) {
			element.setEnabled(enabled);
		}
	}

	/**
	 * Sets the selected browser.
	 * 
	 * @param browser
	 *            The browser to select
	 */
	public void setBrowser(final EBrowser browser) {
		this.mBrowserChoiceBox.setSelectedItem(browser);
	}

	/**
	 * Sets the selection state of the given kiva task option.
	 * 
	 * @param task
	 *            The option to set its state
	 * @param isSelected
	 *            Whether the corresponding option box should be selected or not
	 */
	public void setKivaTask(final EKivaTask task, final boolean isSelected) {
		for (final JCheckBox taskBox : this.mTaskList) {
			final EKivaTask currentTask = EKivaTask.valueOf(taskBox.getText());
			if (task == currentTask) {
				taskBox.setSelected(isSelected);
				break;
			}
		}
	}

	/**
	 * Sets the selection state of the given movement option.
	 * 
	 * @param movementOption
	 *            The option to set its state
	 * @param isSelected
	 *            Whether the corresponding option box should be selected or not
	 */
	public void setMovementOption(final EMoveType movementOption, final boolean isSelected) {
		if (movementOption == EMoveType.BLUE_SPHERE) {
			this.mBlueSphereMovementOptionsBox.setSelected(isSelected);
		}
	}

	/**
	 * Sets the input password.
	 * 
	 * @param password
	 *            The password to set
	 */
	public void setPassword(final String password) {
		this.mPasswordField.setText(password);
	}

	/**
	 * Enables or disables the settings button.
	 * 
	 * @param enabled
	 *            Whether the button should be enabled or disabled
	 */
	public void setSettingsButtonEnabled(final boolean enabled) {
		this.mSettingsBtn.setEnabled(enabled);
	}

	/**
	 * Enables or disables the start button.
	 * 
	 * @param enabled
	 *            Whether the button should be enabled or disabled
	 */
	public void setStartButtonEnabled(final boolean enabled) {
		this.mStartBtn.setEnabled(enabled);
	}

	/**
	 * Enables or disables the stop button.
	 * 
	 * @param enabled
	 *            Whether the button should be enabled or disabled
	 */
	public void setStopButtonEnabled(final boolean enabled) {
		this.mStopBtn.setEnabled(enabled);
	}

	/**
	 * Sets whether the use protection spell box is checked or not.
	 * 
	 * @param isChecked
	 *            Whether the protection spell box should be checked or not
	 */
	public void setUseProtectionSpell(final boolean isChecked) {
		this.mUseProtectionSpell.setSelected(isChecked);
	}

	/**
	 * Sets the input username.
	 * 
	 * @param username
	 *            The username to set
	 */
	public void setUsername(final String username) {
		this.mUsernameField.setText(username);
	}

	/**
	 * Sets whether the use special skill box is checked or not.
	 * 
	 * @param isChecked
	 *            Whether the special skill box should be checked or not
	 */
	public void setUseSpecialSkill(final boolean isChecked) {
		this.mUseSpecialSkill.setSelected(isChecked);
	}

	/**
	 * Sets the selected world.
	 * 
	 * @param world
	 *            The world to select
	 */
	public void setWorld(final EWorld world) {
		this.mWorldChoiceBox.setSelectedItem(world);
	}

	/**
	 * Appends a message to the logging area.
	 * 
	 * @param message
	 *            Message to add
	 * @param color
	 *            Color of the message
	 */
	private void appendToLog(final String message, final Color color) {
		final StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, DEFAULT_FONT);
		aset = sc.addAttribute(aset, StyleConstants.Alignment, Integer.valueOf(StyleConstants.ALIGN_JUSTIFIED));

		final int len = this.mLogArea.getDocument().getLength();
		this.mLogArea.setCaretPosition(len);
		this.mLogArea.setCharacterAttributes(aset, false);
		this.mLogArea.setEditable(true);
		this.mLogArea.replaceSelection(message);
		this.mLogArea.setEditable(false);
	}

	/**
	 * Initialize the contents of the view.
	 */
	private void initialize() {
		initializePanels();
		initializeLabels();
		initializeButtons();
		initializeInputFields();
		initializeTextAreas();

		setStopButtonEnabled(false);
	}

	/**
	 * Initialize the buttons.
	 */
	private void initializeButtons() {
		this.mStartBtn = new JButton("Start");
		this.mStartBtn.setBounds(200, 210, 100, 23);
		this.mMainPanel.add(this.mStartBtn);

		this.mStopBtn = new JButton("Stop");
		this.mStopBtn.setBounds(320, 210, 100, 23);
		this.mMainPanel.add(this.mStopBtn);

		this.mSettingsBtn = new LinkButton("Settings");
		this.mSettingsBtn.setBounds(350, 0, 90, 23);
		this.mTrailerPanel.add(this.mSettingsBtn);
	}

	/**
	 * Initialize the text fields.
	 */
	private void initializeInputFields() {
		this.mBlueSphereMovementOptionsBox = new JCheckBox(EMoveType.BLUE_SPHERE.name(), true);
		this.mBlueSphereMovementOptionsBox.setHorizontalAlignment(SwingConstants.LEFT);
		this.mBlueSphereMovementOptionsBox.setBounds(0, 20, 150, 20);
		this.mMainPanel.add(this.mBlueSphereMovementOptionsBox);
		this.mInputElements.add(this.mBlueSphereMovementOptionsBox);

		this.mUseProtectionSpell = new JCheckBox("Use protection spell", true);
		this.mUseProtectionSpell.setHorizontalAlignment(SwingConstants.LEFT);
		this.mUseProtectionSpell.setBounds(0, 70, 150, 20);
		this.mMainPanel.add(this.mUseProtectionSpell);
		this.mInputElements.add(this.mUseProtectionSpell);

		this.mUseSpecialSkill = new JCheckBox("Use special skill", false);
		this.mUseSpecialSkill.setHorizontalAlignment(SwingConstants.LEFT);
		this.mUseSpecialSkill.setBounds(0, 90, 150, 20);
		this.mMainPanel.add(this.mUseSpecialSkill);
		this.mInputElements.add(this.mUseSpecialSkill);

		final int taskBoxInitialY = 140;
		final int taskBoxYPadding = 20;
		final EKivaTask[] tasks = EKivaTask.values();
		this.mTaskList = new LinkedList<>();
		for (int i = 0; i < tasks.length; i++) {
			final EKivaTask task = tasks[i];
			final JCheckBox taskBox = new JCheckBox(task.name(), true);
			taskBox.setHorizontalAlignment(SwingConstants.LEFT);
			taskBox.setBounds(0, taskBoxInitialY + (i * taskBoxYPadding), 180, 20);
			this.mMainPanel.add(taskBox);
			this.mInputElements.add(taskBox);
			this.mTaskList.add(taskBox);
		}

		this.mUsernameField = new JTextField();
		this.mUsernameField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mUsernameField.setBounds((this.mMainPanel.getWidth() / 2) + 90, 0, 123, 20);
		this.mMainPanel.add(this.mUsernameField);
		this.mInputElements.add(this.mUsernameField);
		this.mUsernameField.setColumns(DEFAULT_FIELD_COLUMNS);

		this.mPasswordField = new JPasswordField();
		this.mPasswordField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mPasswordField.setBounds((this.mMainPanel.getWidth() / 2) + 90, 30, 123, 20);
		this.mMainPanel.add(this.mPasswordField);
		this.mInputElements.add(this.mPasswordField);
		this.mPasswordField.setColumns(DEFAULT_FIELD_COLUMNS);

		this.mWorldChoiceBox = new JComboBox<>();
		for (final EWorld world : EWorld.values()) {
			this.mWorldChoiceBox.addItem(world);
			if (world == EWorld.ONE) {
				this.mWorldChoiceBox.setSelectedItem(world);
			}
		}
		this.mWorldChoiceBox.setBounds((this.mMainPanel.getWidth() / 2) + 90, 60, 123, 20);
		this.mMainPanel.add(this.mWorldChoiceBox);
		this.mInputElements.add(this.mWorldChoiceBox);

		this.mBrowserChoiceBox = new JComboBox<>();
		for (final EBrowser browser : EBrowser.values()) {
			this.mBrowserChoiceBox.addItem(browser);
			if (browser == EBrowser.CHROME) {
				this.mBrowserChoiceBox.setSelectedItem(browser);
			}
		}
		this.mBrowserChoiceBox.setBounds((this.mMainPanel.getWidth() / 2) + 90, 170, 123, 20);
		this.mMainPanel.add(this.mBrowserChoiceBox);
		this.mInputElements.add(this.mBrowserChoiceBox);
	}

	/**
	 * Initialize the labels.
	 */
	private void initializeLabels() {
		final JLabel movementOptionsLbl = new JLabel("Movement options:");
		movementOptionsLbl.setHorizontalAlignment(SwingConstants.LEFT);
		movementOptionsLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		movementOptionsLbl.setBounds(0, 0, 120, 14);
		this.mMainPanel.add(movementOptionsLbl);

		final JLabel additionalOptionsLbl = new JLabel("Additional options:");
		additionalOptionsLbl.setHorizontalAlignment(SwingConstants.LEFT);
		additionalOptionsLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		additionalOptionsLbl.setBounds(0, 50, 120, 14);
		this.mMainPanel.add(additionalOptionsLbl);

		final JLabel tasksLbl = new JLabel("Tasks:");
		tasksLbl.setHorizontalAlignment(SwingConstants.LEFT);
		tasksLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		tasksLbl.setBounds(0, 120, 60, 14);
		this.mMainPanel.add(tasksLbl);

		final JLabel usernameLbl = new JLabel("Username:");
		usernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		usernameLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		usernameLbl.setBounds((this.mMainPanel.getWidth() / 2) + 20, 0, 65, 14);
		this.mMainPanel.add(usernameLbl);

		final JLabel passwordLbl = new JLabel("Password:");
		passwordLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		passwordLbl.setBounds((this.mMainPanel.getWidth() / 2) + 20, 30, 65, 14);
		this.mMainPanel.add(passwordLbl);

		final JLabel worldChoiceLbl = new JLabel("World:");
		worldChoiceLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		worldChoiceLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		worldChoiceLbl.setBounds((this.mMainPanel.getWidth() / 2) + 20, 60, 65, 14);
		this.mMainPanel.add(worldChoiceLbl);

		final JLabel browserChoiceLbl = new JLabel("Browser:");
		browserChoiceLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		browserChoiceLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		browserChoiceLbl.setBounds((this.mMainPanel.getWidth() / 2) + 20, 170, 65, 14);
		this.mMainPanel.add(browserChoiceLbl);
	}

	/**
	 * Initialize the panels.
	 */
	private void initializePanels() {
		this.mMainPanel = new JPanel();
		this.mMainPanel.setBounds(10, 10, WIDTH - 25, 240);
		this.mContainer.add(this.mMainPanel);
		this.mMainPanel.setLayout(null);

		this.mLogPane = new JScrollPane();
		this.mLogPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.mLogPane.setBounds(10, 270, WIDTH - 25, 100);
		this.mContainer.add(this.mLogPane);

		this.mTrailerPanel = new JPanel();
		this.mTrailerPanel.setBounds(10, 370, WIDTH - 25, 50);
		this.mContainer.add(this.mTrailerPanel);
		this.mTrailerPanel.setLayout(null);
	}

	/**
	 * Initialize the logging area.
	 */
	private void initializeTextAreas() {
		this.mLogArea = new JTextPane();
		this.mLogArea.setEditable(false);
		this.mLogPane.setViewportView(this.mLogArea);
	}
}

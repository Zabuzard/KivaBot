package de.zabuza.kivabot.view;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import de.zabuza.sparkle.webdriver.EBrowser;

/**
 * Dialog window for changing the settings of the tool.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class SettingsDialog extends JDialog {
	/**
	 * The title of the browser panel.
	 */
	private final static String BROWSER_TITLE = "Browser";
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
	 * The default name of the protection spell.
	 */
	private final static String DEFAULT_PROTECTION_SPELL = "Schutzzauber";
	/**
	 * The title of the dialog window.
	 */
	private final static String DIALOG_TITLE = "Settings";
	/**
	 * The title of the driver panel.
	 */
	private final static String DRIVER_TITLE = "Driver";
	/**
	 * The height of the dialog.
	 */
	private final static int HEIGHT = 480;
	/**
	 * The title of the miscellaneous panel.
	 */
	private final static String MISCELLANEOUS_TITLE = "Miscellaneous";
	/**
	 * The origin offset of the dialog to the owner, in both directions.
	 */
	private final static int OWNER_OFFSET = 50;
	/**
	 * The title of the buttons for selection.
	 */
	private static final String SELECT_TITLE = "Search";
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The width of the dialog.
	 */
	private final static int WIDTH = 400;
	/**
	 * Select binary button of the dialog for browsers.
	 */
	private JButton mBinaryBtn;
	/**
	 * The browser binary input field.
	 */
	private JTextField mBinaryField;
	/**
	 * The browser panel of the dialog.
	 */
	private JPanel mBrowserPanel;
	/**
	 * The button of the dialog for canceling.
	 */
	private JButton mCancelBtn;
	/**
	 * Select driver button of the dialog for Chrome.
	 */
	private JButton mChromeBtn;
	/**
	 * The driver input field for Chrome.
	 */
	private JTextField mChromeDriverField;
	/**
	 * Container of the dialog.
	 */
	private final Container mContainer;
	/**
	 * The driver panel of the dialog.
	 */
	private JPanel mDriverPanel;
	/**
	 * List of all elements of this dialog.
	 */
	private final List<JComponent> mElements;
	/**
	 * Select driver button of the dialog for Firefox.
	 */
	private JButton mFirefoxBtn;
	/**
	 * The driver input field for Firefox.
	 */
	private JTextField mFirefoxDriverField;
	/**
	 * Select driver button of the dialog for Internet Explorer.
	 */
	private JButton mInternetExplorerBtn;
	/**
	 * The driver input field for Internet Explorer.
	 */
	private JTextField mInternetExplorerDriverField;
	/**
	 * The miscellaneous panel of the dialog.
	 */
	private JPanel mMiscellaneousPanel;
	/**
	 * Select driver button of the dialog for Microsoft Edge.
	 */
	private JButton mMsEdgeBtn;
	/**
	 * The driver input field for Microsoft Edge.
	 */
	private JTextField mMsEdgeDriverField;
	/**
	 * Select driver button of the dialog for Opera.
	 */
	private JButton mOperaBtn;
	/**
	 * The driver input field for Opera.
	 */
	private JTextField mOperaDriverField;
	/**
	 * The protection spell miscellaneous input field.
	 */
	private JTextField mProtectionSpellMiscellaneousField;
	/**
	 * Select driver button of the dialog for Safari.
	 */
	private JButton mSafariBtn;
	/**
	 * The driver input field for Safari.
	 */
	private JTextField mSafariDriverField;
	/**
	 * The button of the dialog for saving.
	 */
	private JButton mSaveBtn;
	/**
	 * The trailer panel of the dialog.
	 */
	private JPanel mTrailerPanel;
	/**
	 * Select user profile button of the dialog for browsers.
	 */
	private JButton mUserProfileBtn;
	/**
	 * The browser user profile input field.
	 */
	private JTextField mUserProfileField;

	/**
	 * Creates a new settings dialog window.
	 * 
	 * @param owner
	 *            The owning frame of this dialog
	 */
	public SettingsDialog(final JFrame owner) {
		super(owner, DIALOG_TITLE);
		mContainer = getContentPane();
		mElements = new LinkedList<>();
		initialize(owner);
	}

	/**
	 * Adds an action listener to the given browser binary selection action.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToBinarySelectionAction(final ActionListener listener) {
		mBinaryBtn.addActionListener(listener);
	}

	/**
	 * Adds an action listener to the given browser driver selection action.
	 * 
	 * @param browser
	 *            Browser to add listener to its corresponding driver selection
	 *            action
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToBrowserDriverSelectionAction(final EBrowser browser, final ActionListener listener) {
		if (browser == EBrowser.CHROME) {
			mChromeBtn.addActionListener(listener);
		} else if (browser == EBrowser.FIREFOX) {
			mFirefoxBtn.addActionListener(listener);
		} else if (browser == EBrowser.INTERNET_EXPLORER) {
			mInternetExplorerBtn.addActionListener(listener);
		} else if (browser == EBrowser.MS_EDGE) {
			mMsEdgeBtn.addActionListener(listener);
		} else if (browser == EBrowser.OPERA) {
			mOperaBtn.addActionListener(listener);
		} else if (browser == EBrowser.SAFARI) {
			mSafariBtn.addActionListener(listener);
		} else {
			throw new IllegalArgumentException("The given browser is not supported by this operation: " + browser);
		}
	}

	/**
	 * Adds an action listener to the cancel action
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToCancelAction(final ActionListener listener) {
		mCancelBtn.addActionListener(listener);
	}

	/**
	 * Adds an action listener to the save action
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToSaveAction(final ActionListener listener) {
		mSaveBtn.addActionListener(listener);
	}

	/**
	 * Adds an action listener to the given browser user binary selection
	 * action.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToUserProfileSelectionAction(final ActionListener listener) {
		mUserProfileBtn.addActionListener(listener);
	}

	/**
	 * Gets the binary field.
	 * 
	 * @return The binary field
	 */
	public JTextField getBinaryField() {
		return mBinaryField;
	}

	/**
	 * Gets the driver field of the corresponding given browser.
	 * 
	 * @param browser
	 *            Browser to get its driver field
	 * @return The driver field corresponding to the given browser
	 */
	public JTextField getBrowserDriverField(final EBrowser browser) {
		if (browser == EBrowser.CHROME) {
			return mChromeDriverField;
		} else if (browser == EBrowser.FIREFOX) {
			return mFirefoxDriverField;
		} else if (browser == EBrowser.INTERNET_EXPLORER) {
			return mInternetExplorerDriverField;
		} else if (browser == EBrowser.MS_EDGE) {
			return mMsEdgeDriverField;
		} else if (browser == EBrowser.OPERA) {
			return mOperaDriverField;
		} else if (browser == EBrowser.SAFARI) {
			return mSafariDriverField;
		} else {
			throw new IllegalArgumentException("The given browser is not supported by this operation: " + browser);
		}
	}

	/**
	 * Gets the protection spell field.
	 * 
	 * @return The protection spell field
	 */
	public JTextField getProtectionSpellField() {
		return mProtectionSpellMiscellaneousField;
	}

	/**
	 * Gets the user profile field.
	 * 
	 * @return The user profile field
	 */
	public JTextField getUserProfileField() {
		return mUserProfileField;
	}

	/**
	 * Enables or disables all elements of the dialog.
	 * 
	 * @param enabled
	 *            Whether the elements should be enabled or disabled
	 */
	public void setAllElementsEnabled(final boolean enabled) {
		for (JComponent element : mElements) {
			element.setEnabled(enabled);
		}
	}

	/**
	 * Initialize the contents of the view.
	 * 
	 * @param owner
	 *            The owning frame of this dialog
	 */
	private void initialize(final JFrame owner) {
		setBounds(owner.getX() + OWNER_OFFSET, owner.getY() + OWNER_OFFSET, WIDTH, HEIGHT);
		setResizable(false);
		initializePanels();
		initializeLabels();
		initializeButtons();
		initializeInputFields();
	}

	/**
	 * Initialize the buttons.
	 */
	private void initializeButtons() {
		mChromeBtn = new JButton(SELECT_TITLE);
		mChromeBtn.setBounds(290, 30, 80, 20);
		mDriverPanel.add(mChromeBtn);
		mElements.add(mChromeBtn);

		mFirefoxBtn = new JButton(SELECT_TITLE);
		mFirefoxBtn.setBounds(290, 60, 80, 20);
		mDriverPanel.add(mFirefoxBtn);
		mElements.add(mFirefoxBtn);

		mInternetExplorerBtn = new JButton(SELECT_TITLE);
		mInternetExplorerBtn.setBounds(290, 90, 80, 20);
		mDriverPanel.add(mInternetExplorerBtn);
		mElements.add(mInternetExplorerBtn);

		mMsEdgeBtn = new JButton(SELECT_TITLE);
		mMsEdgeBtn.setBounds(290, 120, 80, 20);
		mDriverPanel.add(mMsEdgeBtn);
		mElements.add(mMsEdgeBtn);

		mOperaBtn = new JButton(SELECT_TITLE);
		mOperaBtn.setBounds(290, 150, 80, 20);
		mDriverPanel.add(mOperaBtn);
		mElements.add(mOperaBtn);

		mSafariBtn = new JButton(SELECT_TITLE);
		mSafariBtn.setBounds(290, 180, 80, 20);
		mDriverPanel.add(mSafariBtn);
		mElements.add(mSafariBtn);

		mBinaryBtn = new JButton(SELECT_TITLE);
		mBinaryBtn.setBounds(290, 30, 80, 20);
		mBrowserPanel.add(mBinaryBtn);
		mElements.add(mBinaryBtn);

		mUserProfileBtn = new JButton(SELECT_TITLE);
		mUserProfileBtn.setBounds(290, 60, 80, 20);
		mBrowserPanel.add(mUserProfileBtn);
		mElements.add(mUserProfileBtn);

		mSaveBtn = new JButton("Save");
		mSaveBtn.setBounds((WIDTH / 2) - 100, 410, 80, 20);
		mTrailerPanel.add(mSaveBtn);
		mElements.add(mSaveBtn);

		mCancelBtn = new JButton("Cancel");
		mCancelBtn.setBounds((WIDTH / 2) + 20, 410, 80, 20);
		mTrailerPanel.add(mCancelBtn);
		mElements.add(mCancelBtn);
	}

	/**
	 * Initialize the text fields.
	 */
	private void initializeInputFields() {
		mChromeDriverField = new JTextField();
		mChromeDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		mChromeDriverField.setBounds(80, 30, 200, 20);
		mDriverPanel.add(mChromeDriverField);
		mElements.add(mChromeDriverField);
		mChromeDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		mFirefoxDriverField = new JTextField();
		mFirefoxDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		mFirefoxDriverField.setBounds(80, 60, 200, 20);
		mDriverPanel.add(mFirefoxDriverField);
		mElements.add(mFirefoxDriverField);
		mFirefoxDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		mInternetExplorerDriverField = new JTextField();
		mInternetExplorerDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		mInternetExplorerDriverField.setBounds(80, 90, 200, 20);
		mDriverPanel.add(mInternetExplorerDriverField);
		mElements.add(mInternetExplorerDriverField);
		mInternetExplorerDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		mMsEdgeDriverField = new JTextField();
		mMsEdgeDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		mMsEdgeDriverField.setBounds(80, 120, 200, 20);
		mDriverPanel.add(mMsEdgeDriverField);
		mElements.add(mMsEdgeDriverField);
		mMsEdgeDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		mOperaDriverField = new JTextField();
		mOperaDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		mOperaDriverField.setBounds(80, 150, 200, 20);
		mDriverPanel.add(mOperaDriverField);
		mElements.add(mOperaDriverField);
		mOperaDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		mSafariDriverField = new JTextField();
		mSafariDriverField.setHorizontalAlignment(SwingConstants.LEFT);
		mSafariDriverField.setBounds(80, 180, 200, 20);
		mDriverPanel.add(mSafariDriverField);
		mElements.add(mSafariDriverField);
		mSafariDriverField.setColumns(DEFAULT_FIELD_COLUMNS);

		mBinaryField = new JTextField();
		mBinaryField.setHorizontalAlignment(SwingConstants.LEFT);
		mBinaryField.setBounds(80, 30, 200, 20);
		mBrowserPanel.add(mBinaryField);
		mElements.add(mBinaryField);
		mBinaryField.setColumns(DEFAULT_FIELD_COLUMNS);

		mUserProfileField = new JTextField();
		mUserProfileField.setHorizontalAlignment(SwingConstants.LEFT);
		mUserProfileField.setBounds(80, 60, 200, 20);
		mBrowserPanel.add(mUserProfileField);
		mElements.add(mUserProfileField);
		mUserProfileField.setColumns(DEFAULT_FIELD_COLUMNS);

		mProtectionSpellMiscellaneousField = new JTextField(DEFAULT_PROTECTION_SPELL);
		mProtectionSpellMiscellaneousField.setHorizontalAlignment(SwingConstants.LEFT);
		mProtectionSpellMiscellaneousField.setBounds(120, 30, 240, 20);
		mMiscellaneousPanel.add(mProtectionSpellMiscellaneousField);
		mElements.add(mProtectionSpellMiscellaneousField);
		mProtectionSpellMiscellaneousField.setColumns(DEFAULT_FIELD_COLUMNS);
	}

	/**
	 * Initialize the labels.
	 */
	private void initializeLabels() {
		JLabel mChromeDriverLbl = new JLabel("Chrome:");
		mChromeDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mChromeDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mChromeDriverLbl.setBounds(10, 30, 60, 14);
		mDriverPanel.add(mChromeDriverLbl);

		JLabel mFirefoxDriverLbl = new JLabel("Firefox:");
		mFirefoxDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mFirefoxDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mFirefoxDriverLbl.setBounds(10, 60, 60, 14);
		mDriverPanel.add(mFirefoxDriverLbl);

		JLabel mInternetExplorerDriverLbl = new JLabel("IE:");
		mInternetExplorerDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mInternetExplorerDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mInternetExplorerDriverLbl.setBounds(10, 90, 60, 14);
		mDriverPanel.add(mInternetExplorerDriverLbl);

		JLabel mMsEdgeDriverLbl = new JLabel("MS Edge:");
		mMsEdgeDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mMsEdgeDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mMsEdgeDriverLbl.setBounds(10, 120, 60, 14);
		mDriverPanel.add(mMsEdgeDriverLbl);

		JLabel mOperaDriverLbl = new JLabel("Opera:");
		mOperaDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mOperaDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mOperaDriverLbl.setBounds(10, 150, 60, 14);
		mDriverPanel.add(mOperaDriverLbl);

		JLabel mSafariDriverLbl = new JLabel("Safari:");
		mSafariDriverLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mSafariDriverLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mSafariDriverLbl.setBounds(10, 180, 60, 14);
		mDriverPanel.add(mSafariDriverLbl);

		JLabel mBinaryLbl = new JLabel("Binary:");
		mBinaryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mBinaryLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mBinaryLbl.setBounds(10, 30, 60, 14);
		mBrowserPanel.add(mBinaryLbl);

		JLabel mUserProfileLbl = new JLabel("Profile:");
		mUserProfileLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mUserProfileLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mUserProfileLbl.setBounds(10, 60, 60, 14);
		mBrowserPanel.add(mUserProfileLbl);

		JLabel mProtectionSpellMiscellaneousLbl = new JLabel("Protection spell:");
		mProtectionSpellMiscellaneousLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mProtectionSpellMiscellaneousLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mProtectionSpellMiscellaneousLbl.setBounds(10, 30, 100, 14);
		mMiscellaneousPanel.add(mProtectionSpellMiscellaneousLbl);
	}

	/**
	 * Initialize the panels.
	 */
	private void initializePanels() {
		mDriverPanel = new JPanel();
		mDriverPanel.setBounds(10, 10, WIDTH - 25, 220);
		TitledBorder titledBorderDriver = BorderFactory.createTitledBorder(DRIVER_TITLE);
		mDriverPanel.setBorder(titledBorderDriver);
		mContainer.add(mDriverPanel);
		mDriverPanel.setLayout(null);

		mBrowserPanel = new JPanel();
		mBrowserPanel.setBounds(10, 230, WIDTH - 25, 100);
		TitledBorder titledBorderBinary = BorderFactory.createTitledBorder(BROWSER_TITLE);
		mBrowserPanel.setBorder(titledBorderBinary);
		mContainer.add(mBrowserPanel);
		mBrowserPanel.setLayout(null);

		mMiscellaneousPanel = new JPanel();
		mMiscellaneousPanel.setBounds(10, 330, WIDTH - 25, 70);
		TitledBorder titledBorderMiscellaneous = BorderFactory.createTitledBorder(MISCELLANEOUS_TITLE);
		mMiscellaneousPanel.setBorder(titledBorderMiscellaneous);
		mContainer.add(mMiscellaneousPanel);
		mMiscellaneousPanel.setLayout(null);

		mTrailerPanel = new JPanel();
		mTrailerPanel.setBounds(10, 400, WIDTH - 25, 80);
		mContainer.add(mTrailerPanel);
		mTrailerPanel.setLayout(null);
	}
}

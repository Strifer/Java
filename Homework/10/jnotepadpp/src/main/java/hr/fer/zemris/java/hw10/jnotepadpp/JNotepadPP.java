package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.jnotepadpp.i18n.FormLocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.ILocalizationListener;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizableAction;
import hr.fer.zemris.java.hw10.jnotepadpp.i18n.LocalizationProvider;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * This class represents a simple text editor capable of editing multiple
 * documents at once. It comes with all of the basic editing capabilities as
 * well as a few specific line manipulation methods such as sorting and deleting
 * duplicate lines.
 * 
 * <p>
 * It also comes equipped with dynamic language changing. Currently only three
 * languages are supported, german, english and croatian.
 * 
 * @author Filip Džidić
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	/** counts the number of lines within a document */
	private JLabel lineCounter;
	/** displays data about the caret's position */
	private JLabel statDisplay;
	/** displays the current time */
	private JLabel time;

	/** enables dynamic internationalization */
	private FormLocalizationProvider flp = new FormLocalizationProvider(
			LocalizationProvider.getInstance(), this);
	/** the main jcomponent which holds all of the tabs and documents */
	protected JTabbedPane editor;

	/** clipboard for storing copied and cut parts of documents */
	private String clipboard;

	/** Storage for all the tabs and their associated file names */
	private HashMap<JPanel, Path> tabTable;

	/** counts the number of new tabs opened */
	private int newCounter;

	/** the red icon signifies that unsaved changes exist within a file */
	private ImageIcon red = new ImageIcon("./Icons/bullet_red.png");
	/** the green icon signifies that there are no unsaved changes within a file */
	private ImageIcon green = new ImageIcon("./Icons/bullet_green.png");

	/** timer for displaying current time */
	private Timer timer;

	/** our toolbar buttons are stored here */
	private ArrayList<JButton> buttons;

	/**
	 * The main method simply initializes and creates a new window of our
	 * editor.
	 * 
	 * @param args
	 *            no arguments should be provided
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
		;
	}

	/**
	 * This constructor initializes all of the necessary GUI components inside
	 * our program.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 500);
		buttons = new ArrayList<JButton>();
		initGUi();
	}

	/**
	 * This method initializies all of the necessary GUI Swing components and
	 * Listeners
	 */
	private void initGUi() {
		tabTable = new HashMap<>();
		editor = new JTabbedPane();
		editor.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JComponent textArea = (JComponent) editor
						.getSelectedComponent();
				if (textArea == null) {
					setTitle("JNotepad++");
					return;
				}
				JPanel parent = (JPanel) editor.getSelectedComponent();

				JViewport viewPort = ((JScrollPane) parent.getComponent(0))
						.getViewport();
				JTextArea ta = (JTextArea) viewPort.getView();
				updateStatus(ta);
				String title = editor.getToolTipTextAt(editor
						.indexOfComponent(textArea));

				setTitle(title);

			}
		});
		setTitle("JNotepad++");
		editor.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		newCounter = 1;
		JPanel statPanel = new JPanel(new GridLayout(1, 3));
		lineCounter = new JLabel();
		statDisplay = new JLabel();
		time = new JLabel();

		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time.setText(JNotepadPP.this.dateFormat(Calendar.getInstance()));
			}
		});
		timer.start();

		statPanel.add(lineCounter);
		statPanel.add(statDisplay);
		statPanel.add(time);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(editor, BorderLayout.CENTER);
		getContentPane().add(statPanel, BorderLayout.PAGE_END);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				JNotepadPP.this.checkWindows();
			}

		});
		createActions();
		createMenus();
		createToolbar();

	}

	/**
	 * This method checks for unsaved changes in every tab and prompts the user
	 * to save or discard the changes. If the user selects cancel upon the
	 * entire selection process is stopped.
	 */
	protected void checkWindows() {
		int tabCount = editor.getTabCount();
		if (tabCount == 0) {
			timer.stop();
			dispose();
		}
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < tabCount; i++) {
			JPanel panel = (JPanel) editor.getTabComponentAt(i);
			Icon icon = ((JLabel) panel.getComponent(0)).getIcon();
			if (icon == green) {
				list.add(i);
				// editor.removeTabAt(i);
			} else {
				editor.setSelectedIndex(i);
				int r = JOptionPane
						.showConfirmDialog(this,
								"There are unsaved changes in this file. Do you wish to save them?");
				if (r == JOptionPane.CANCEL_OPTION) {
					return;
				}
				if (r == JOptionPane.NO_OPTION) {
					list.add(i);
					continue;
				}

				JNotepadPP.this.save();
				list.add(i);

			}

		}
		for (int j = list.size() - 1; j >= 0; j--) {
			editor.removeTabAt(list.get(j));
		}
		if (editor.getTabCount() == 0) {
			timer.stop();
			dispose();
		}

	}

	/**
	 * This method returns a new String representing the current time in the
	 * following format
	 * <p>
	 * <code> year/month/day hour:minute:second </code>
	 * 
	 * @param date
	 *            <code>Calendar</code> whose time data we're extracting
	 * @return current time and date
	 */
	private String dateFormat(Calendar date) {
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH);
		int day = date.get(Calendar.DAY_OF_MONTH);
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int minutes = date.get(Calendar.MINUTE);
		int seconds = date.get(Calendar.SECOND);
		return String.format("%d/%02d/%02d %02d:%02d:%02d", year, month, day,
				hour, minutes, seconds);

	}

	/**
	 * This method initializes all of our existing action components and their
	 * key accelerators. <br>
	 * The entire editor consists of 19 different actions which can be divided
	 * into four main categories.
	 * <p>
	 * 1)File actions (new, save, save as, close exit) deal with opening files
	 * and managing tabs <br>
	 * 2)Edit actions (copy, paste, cut, sort, case, unique) deal with
	 * manipulating selected text <br>
	 * 3)Tool actions (statistics) show file statistics <br>
	 * 4)Language actions dynamically change the language of the program,
	 * currently three languages are supported Croatian, German and English.
	 */
	private void createActions() {
		openDocumentAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		newDocumentAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		saveAsDocAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("shift control S"));
		saveAsDocAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		saveDocAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control S"));
		saveDocAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		closeDocAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control Q"));
		closeDocAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

		exitAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control E"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		copyTextAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control C"));
		copyTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		pasteTextAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control V"));
		pasteTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);

		cutTextAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control X"));
		cutTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

		ascendingAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control shift A"));
		ascendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		descendingAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control shift D"));
		descendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

		uniqueAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control shift U"));
		uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		invertCaseAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control I"));
		invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);

		toUppercaseAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control U"));
		toUppercaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		toLowercaseAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control L"));
		toLowercaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

		statisticsAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control shift T"));
		statisticsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

	}

	/**
	 * This method creates our menu.<br>
	 * The menu is divided into four main categories.
	 * <p>
	 * 1)File actions (new, save, save as, close exit) deal with opening files
	 * and managing tabs <br>
	 * 2)Edit actions (copy, paste, cut, sort, case, unique) deal with
	 * manipulating selected text <br>
	 * 3)Tool actions (statistics) show file statistics <br>
	 * 4)Language actions dynamically change the language of the program,
	 * currently three languages are supported Croatian, German and English.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(new LocalizableAction("file", flp) {
			private static final long serialVersionUID = 1L;
		});
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(saveDocAction));
		fileMenu.add(new JMenuItem(saveAsDocAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeDocAction));
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu = new JMenu(new LocalizableAction("edit", flp) {
			private static final long serialVersionUID = 1L;
		});
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(copyTextAction));
		editMenu.add(new JMenuItem(pasteTextAction));
		editMenu.add(new JMenuItem(cutTextAction));
		editMenu.addSeparator();
		JMenu caseMenu = new JMenu(new LocalizableAction("case", flp) {
			private static final long serialVersionUID = 1L;
		});
		caseMenu.add(new JMenuItem(toUppercaseAction));
		caseMenu.add(new JMenuItem(toLowercaseAction));
		caseMenu.add(new JMenuItem(invertCaseAction));
		editMenu.add(caseMenu);

		JMenu sortMenu = new JMenu(new LocalizableAction("sort", flp) {
			private static final long serialVersionUID = 1L;
		});
		sortMenu.add(new JMenuItem(ascendingAction));
		sortMenu.add(new JMenuItem(descendingAction));
		editMenu.add(sortMenu);
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(uniqueAction));

		JMenu toolMenu = new JMenu(new LocalizableAction("tools", flp) {
			private static final long serialVersionUID = 1L;
		});
		toolMenu.add(statisticsAction);
		menuBar.add(toolMenu);

		JMenu languageMenu = new JMenu(new LocalizableAction("language", flp) {
			private static final long serialVersionUID = 1L;
		});
		languageMenu.add(deAction);
		languageMenu.add(enAction);
		languageMenu.add(hrAction);

		menuBar.add(languageMenu);
		setJMenuBar(menuBar);
	}

	/**
	 * This method creates our floatable toolbar consisting of only icons for a
	 * more compact view. <br>
	 * The user is free to move the toolbar around. Hovering over an icon will
	 * show a description of what action the icon is representing.
	 */
	private void createToolbar() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);
		createAndAddButton("./Icons/new.png", newDocumentAction, toolBar);
		createAndAddButton("./Icons/open.png", openDocumentAction, toolBar);
		createAndAddButton("./Icons/save.png", saveDocAction, toolBar);
		createAndAddButton("./Icons/saveas.png", saveAsDocAction, toolBar);
		createAndAddButton("./Icons/close.png", closeDocAction, toolBar);
		toolBar.addSeparator();
		createAndAddButton("./Icons/copy.png", copyTextAction, toolBar);
		createAndAddButton("./Icons/cut.png", cutTextAction, toolBar);
		createAndAddButton("./Icons/paste.png", pasteTextAction, toolBar);
		toolBar.addSeparator();
		createAndAddButton("./Icons/upper.png", toUppercaseAction, toolBar);
		createAndAddButton("./Icons/lower.png", toLowercaseAction, toolBar);
		createAndAddButton("./Icons/invert.png", invertCaseAction, toolBar);
		createAndAddButton("./Icons/asc.png", ascendingAction, toolBar);
		createAndAddButton("./Icons/desc.png", descendingAction, toolBar);
		createAndAddButton("./Icons/unique.png", uniqueAction, toolBar);
		toolBar.addSeparator();
		createAndAddButton("./Icons/stat.png", statisticsAction, toolBar);
		createAndAddButton("./Icons/exit.png", exitAction, toolBar);
		toolBar.addSeparator();
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				for (JButton button : buttons) {
					button.setText("");
				}

			}
		});
	}

	/**
	 * This helper method creates a single JButton and adds it to the toolbar.
	 * 
	 * @param fileName
	 *            path to the button's icon
	 * @param action
	 *            the action which the button will perform
	 * @param toolBar
	 *            buttons will be added here
	 */
	private void createAndAddButton(String fileName, Action action,
			JToolBar toolBar) {
		JButton button = new JButton(action);
		button.setIcon(new ImageIcon(fileName));
		button.setText("");
		button.setSize(9, 9);
		button.setOpaque(true);
		toolBar.add(button);
		buttons.add(button);
	}

	/**
	 * This performs the save action. <br>
	 * Saves any content made to the opened file.
	 * <p>
	 * If the user invokes this action in a new document it automatically
	 * delegates work to the save as action.
	 */
	private void save() {
		if (editor.getTabCount() == 0) {
			return;
		}
		Component pane = this.editor.getSelectedComponent();
		Component tab = editor.getTabComponentAt(editor.indexOfComponent(pane));
		JPanel pan = (JPanel) tab;
		Path filepath = tabTable.get(pane);
		if (filepath == null) {
			saveAs();
			return;
		}
		JPanel parent = (JPanel) editor.getSelectedComponent();
		JViewport viewPort = ((JScrollPane) parent.getComponent(0))
				.getViewport();
		JTextArea text = (JTextArea) viewPort.getView();
		try {
			Files.write(filepath,
					text.getText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(JNotepadPP.this,
					"Pogreška pri snimanju datoteke: " + e1.getMessage(),
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}
		setTitle(filepath.toString());
		((JLabel) pan.getComponent(0)).setIcon(green);
	}

	/**
	 * This method saves any existing changes in the current selected tab to a
	 * new file. If the file already exists on the file system the user will be
	 * prompted if he or she wants to overwrite it.
	 */
	private void saveAs() {
		if (editor.getTabCount() == 0) {
			return;
		}
		JComponent pane = (JComponent) this.editor.getSelectedComponent();
		Component tab = editor.getTabComponentAt(editor.indexOfComponent(pane));
		JPanel pan = (JPanel) tab;
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save document");
		if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(this, "Nothing was saved",
					"Info message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Path file = fc.getSelectedFile().toPath();
		if (Files.exists(file)) {
			int r = JOptionPane.showConfirmDialog(this, "File " + file
					+ " already exists. Do you want to overwrite it?",
					"Warning", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (r != JOptionPane.YES_OPTION) {
				return;
			}
		}
		JPanel parent = (JPanel) editor.getSelectedComponent();
		JViewport viewPort = ((JScrollPane) parent.getComponent(0))
				.getViewport();
		JTextArea text = (JTextArea) viewPort.getView();
		try {
			Files.write(file, text.getText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane
					.showMessageDialog(
							JNotepadPP.this,
							"Error while attempting to create file: "
									+ e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
			return;
		}
		setTitle(file.toString());
		((JLabel) pan.getComponent(0)).setIcon(green);
		((JLabel) pan.getComponent(1)).setText(file.getFileName().toString());
		editor.setToolTipTextAt(editor.indexOfTabComponent(pan),
				file.toString());
		tabTable.put((JPanel) pane, file);

	}

	/**
	 * This method invokes the selected case editing on the selected text as
	 * determined by the argument in option.
	 * 
	 * @param option
	 *            the case operation we are performing
	 */
	private void inv(String option) {
		if (editor.getTabCount() == 0) {
			return;
		}
		JPanel parent = (JPanel) editor.getSelectedComponent();
		JViewport viewPort = ((JScrollPane) parent.getComponent(0))
				.getViewport();
		JTextArea text = (JTextArea) viewPort.getView();

		Document doc = text.getDocument();
		int pocetak = Math.min(text.getCaret().getDot(), text.getCaret()
				.getMark());
		int duljina = Math.max(text.getCaret().getDot(), text.getCaret()
				.getMark())
				- pocetak;
		try {
			String string = doc.getText(pocetak, duljina);
			string = toggleCase(string, option);
			doc.remove(pocetak, duljina);
			doc.insertString(pocetak, string, null);
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * This method sorts the provided text by ascending or descending order as
	 * determined by the option argument.
	 * 
	 * @param text
	 *            the text we are sorting
	 * @param option
	 *            the sorting we are performing (ascending or descending)
	 * @return the sorted text
	 */
	private String sort(String text, String option) {

		Locale locale = new Locale("hr");
		Collator collator = Collator.getInstance(locale);

		String[] lines = text.split("\n");

		List<String> finalLines = new ArrayList<>();
		for (int i = 0, length = lines.length; i < length; i++) {
			finalLines.add(lines[i]);
		}

		if (option.equals("descending")) {
			for (int i = 0, length = finalLines.size() - 1; i < length; i++) {
				int index = -1;
				String first = finalLines.get(i);
				for (int j = i + 1, length2 = length + 1; j < length2; j++) {
					String second = finalLines.get(j);
					int r = collator.compare(first, second);
					if (r < 0) {
						index = j;
					}
				}
				if (index >= 0) {
					String temp = finalLines.get(i);
					finalLines.remove(i);
					finalLines.add(i, finalLines.get(index - 1));
					finalLines.remove(index);
					finalLines.add(index, temp);
				}
			}
		} else if (option.equals("ascending")) {
			for (int i = 0, length = finalLines.size() - 1; i < length; i++) {
				int index = -1;
				String first = finalLines.get(i);
				for (int j = i + 1, length2 = length + 1; j < length2; j++) {
					String second = finalLines.get(j);
					int r = collator.compare(first, second);
					if (r > 0) {
						index = j;
					}
				}
				if (index >= 0) {
					String temp = finalLines.get(i);
					finalLines.remove(i);
					finalLines.add(i, finalLines.get(index - 1));
					finalLines.remove(index);
					finalLines.add(index, temp);
				}
			}
		}
		String finalText = "";
		int length = finalLines.size();
		int i = 0;
		for (; i < length - 1; i++) {
			finalText += finalLines.get(i) + "\n";
		}
		finalText += finalLines.get(i);

		return finalText;
	}

	/**
	 * This method opens an existing file on the filesystem as a new tab inside
	 * our program. If the file is already opened the view is switched to that
	 * file.
	 */
	private void open() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path openedFilePath = fc.getSelectedFile().toPath();
		if (tabTable.containsValue(openedFilePath)) {
			JPanel existing = null;
			for (Entry<JPanel, Path> entry : tabTable.entrySet()) {
				if (entry.getValue() != null
						&& entry.getValue().equals(openedFilePath)) {
					existing = entry.getKey();
					break;
				}
			}
			if (existing != null) {
				editor.setSelectedComponent(existing);
				return;
			}
		}
		if (!Files.isReadable(openedFilePath)) {
			JOptionPane.showMessageDialog(JNotepadPP.this, "File "
					+ openedFilePath + " does not exist", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(openedFilePath);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(JNotepadPP.this,
					"Error upon opening file: " + e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		String title = openedFilePath.getFileName().toString();
		JPanel tab = new JPanel(new BorderLayout());
		JTextArea text = new JTextArea();
		setTitle(openedFilePath.toString());
		text.setText(new String(bytes));
		text.setLineWrap(true);
		text.setWrapStyleWord(false);
		JScrollPane pane = new JScrollPane(text);
		tab.add(pane);
		// this caret listener disables editing actions if no text is selected
		text.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				JPanel parent = (JPanel) editor.getSelectedComponent();
				JViewport viewPort = ((JScrollPane) parent.getComponent(0))
						.getViewport();
				JTextArea ta = (JTextArea) viewPort.getView();
				int pocetak = Math.min(ta.getCaret().getDot(), ta.getCaret()
						.getMark());
				int duljina = Math.max(ta.getCaret().getDot(), ta.getCaret()
						.getMark())
						- pocetak;

				updateStatus(text);

				if (duljina <= 0) {
					ascendingAction.setEnabled(false);
					descendingAction.setEnabled(false);
					uniqueAction.setEnabled(false);
					cutTextAction.setEnabled(false);
					copyTextAction.setEnabled(false);
					pasteTextAction.setEnabled(false);
					invertCaseAction.setEnabled(false);
					toLowercaseAction.setEnabled(false);
					toUppercaseAction.setEnabled(false);
				} else {
					ascendingAction.setEnabled(true);
					descendingAction.setEnabled(true);
					uniqueAction.setEnabled(true);
					cutTextAction.setEnabled(true);
					copyTextAction.setEnabled(true);
					pasteTextAction.setEnabled(true);
					invertCaseAction.setEnabled(true);
					toLowercaseAction.setEnabled(true);
					toUppercaseAction.setEnabled(true);
				}
			}
		});
		editor.addTab(title, tab);
		int index = editor.indexOfComponent(tab);
		JPanel tabTitle = NotepadUtility.tabTitle(title, "green", this,
				editor.indexOfComponent(tab));

		tab.setToolTipText(openedFilePath.toString());
		editor.setTabComponentAt(index, tabTitle);
		tabTable.put(tab, openedFilePath);
		editor.setSelectedComponent(tab);
		editor.setToolTipTextAt(editor.indexOfTabComponent(tabTitle),
				openedFilePath.toString());
		// this document listener updates the unsaved changes indicator inside
		// tabs
		text.getDocument().addDocumentListener(new DocumentListener() {
			JPanel tabTitle = (JPanel) editor.getTabComponentAt(editor
					.indexOfComponent(tab));

			@Override
			public void removeUpdate(DocumentEvent e) {
				((JLabel) tabTitle.getComponent(0)).setIcon(red);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				((JLabel) tabTitle.getComponent(0)).setIcon(red);

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}

		});

	}

	/**
	 * This method performs the case operation specified by the user. It can
	 * change the text to upperCase, lowerCase or it can invert the case of each
	 * letter.
	 * 
	 * @param text
	 *            the text whose case we are changing
	 * @param option
	 *            the case option we are performing
	 * @return the converted text
	 */
	private String toggleCase(String text, String option) {
		char[] znakovi = text.toCharArray();
		if (option.equals("toggle")) {
			for (int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if (Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
		} else if (option.equals("upper")) {
			return text.toUpperCase();
		} else if (option.equals("lower")) {
			return text.toLowerCase();
		}
		return new String(znakovi);
	}

	/**
	 * This method attempts to close the currently selected tab. <br>
	 * If there are any unsaved changes the user is prompted for confirmation.
	 */
	protected void close() {
		JComponent textArea = (JComponent) this.editor.getSelectedComponent();
		if (textArea == null) {
			return;
		}
		Component tab = editor.getTabComponentAt(editor
				.indexOfComponent(textArea));
		// the tab's title
		JPanel pan = (JPanel) tab;
		Icon icon = ((JLabel) pan.getComponent(0)).getIcon();
		if (icon == red) {
			int r = JOptionPane
					.showConfirmDialog(
							this,
							"The file has unsaved content. Are you sure you want to close it?",
							"Warning", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			if (r != JOptionPane.YES_OPTION) {
				return;
			}
		}

		editor.remove(textArea);
		tabTable.remove(textArea);

	}

	/**
	 * This method updates the status of our statusBar at the bottom of the
	 * winow. The status bar keeps track of four things:<br>
	 * 1)Line count - the number of lines inside the selected document<br>
	 * 2)Current line - the line where the caret is located<br>
	 * 3)Current column - the column where the caret is located<br>
	 * 4)Selected characters - the length of our selected text<br>
	 * 
	 * @param text
	 *            the text area of the currently selected document tab
	 */
	private void updateStatus(JTextArea text) {
		lineCounter.setText("length:" + text.getLineCount());
		int position = text.getCaretPosition();
		int line = 0;
		int columnnum = 0;
		int sel = 0;
		try {
			line = text.getLineOfOffset(position);
			columnnum = position - text.getLineStartOffset(line);
			line++;
			String data = text.getSelectedText();
			if (data == null) {
				sel = 0;
			} else {
				sel = data.length();
			}
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		statDisplay.setText("Ln:" + line + "  Col:" + columnnum + "  Sel:"
				+ sel);
	}

	/**
	 * This method creates a new empty document inside a new tab in our program.
	 * <p>
	 * The program is capable of keeping track of how many new tabs were created
	 * since starting the program. This is indicated by the number in the new
	 * tab's title.
	 */
	private void newTab() {
		String title = "new " + newCounter;
		JPanel tab = new JPanel(new BorderLayout());
		JTextArea text = new JTextArea();
		text.setLineWrap(true);
		text.setWrapStyleWord(false);
		JScrollPane pane = new JScrollPane(text);
		tab.add(pane);
		editor.addTab(title, tab);
		int index = editor.indexOfComponent(tab);
		JPanel tabTitle = NotepadUtility.tabTitle(title, "green", this,
				editor.indexOfComponent(tab));

		editor.setTabComponentAt(index, tabTitle);
		editor.setToolTipTextAt(editor.indexOfTabComponent(tabTitle), title);
		tabTable.put(tab, null);
		setTitle(title);
		newCounter++;
		editor.setSelectedComponent(tab);
		text.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				JPanel parent = (JPanel) editor.getSelectedComponent();
				JViewport viewPort = ((JScrollPane) parent.getComponent(0))
						.getViewport();
				JTextArea ta = (JTextArea) viewPort.getView();
				int pocetak = Math.min(ta.getCaret().getDot(), ta.getCaret()
						.getMark());
				int duljina = Math.max(ta.getCaret().getDot(), ta.getCaret()
						.getMark())
						- pocetak;
				updateStatus(text);

				if (duljina <= 0) {
					ascendingAction.setEnabled(false);
					descendingAction.setEnabled(false);
					uniqueAction.setEnabled(false);
					cutTextAction.setEnabled(false);
					copyTextAction.setEnabled(false);
					pasteTextAction.setEnabled(false);
					invertCaseAction.setEnabled(false);
					toLowercaseAction.setEnabled(false);
					toUppercaseAction.setEnabled(false);
				} else {
					ascendingAction.setEnabled(true);
					descendingAction.setEnabled(true);
					uniqueAction.setEnabled(true);
					cutTextAction.setEnabled(true);
					copyTextAction.setEnabled(true);
					pasteTextAction.setEnabled(true);
					invertCaseAction.setEnabled(true);
					toLowercaseAction.setEnabled(true);
					toUppercaseAction.setEnabled(true);
				}
			}
		});
		text.getDocument().addDocumentListener(new DocumentListener() {
			JPanel tabTitle = (JPanel) editor.getTabComponentAt(editor
					.indexOfComponent(tab));

			@Override
			public void removeUpdate(DocumentEvent e) {
				((JLabel) tabTitle.getComponent(0)).setIcon(red);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				((JLabel) tabTitle.getComponent(0)).setIcon(red);

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}

		});

	}

	/**
	 * This action is used for cutting selected text and storing it to the
	 * clipboard for later retrieval.
	 */
	private LocalizableAction cutTextAction = new LocalizableAction("cut", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (editor.getTabCount() == 0) {
				return;
			}
			JPanel parent = (JPanel) editor.getSelectedComponent();
			JViewport viewPort = ((JScrollPane) parent.getComponent(0))
					.getViewport();
			JTextArea text = (JTextArea) viewPort.getView();

			Document doc = text.getDocument();
			int pocetak = Math.min(text.getCaret().getDot(), text.getCaret()
					.getMark());
			int duljina = Math.max(text.getCaret().getDot(), text.getCaret()
					.getMark())
					- pocetak;

			try {
				clipboard = doc.getText(pocetak, duljina);
				doc.remove(pocetak, duljina);
			} catch (BadLocationException ignorable) {
			}

		}
	};
	/**
	 * This action is used for copying selected text into the clipboard for
	 * later retrieval.
	 */
	private Action copyTextAction = new LocalizableAction("copy", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (editor.getTabCount() == 0) {
				return;
			}
			JPanel parent = (JPanel) editor.getSelectedComponent();
			JViewport viewPort = ((JScrollPane) parent.getComponent(0))
					.getViewport();
			JTextArea text = (JTextArea) viewPort.getView();

			Document doc = text.getDocument();
			int pocetak = Math.min(text.getCaret().getDot(), text.getCaret()
					.getMark());
			int duljina = Math.max(text.getCaret().getDot(), text.getCaret()
					.getMark())
					- pocetak;

			try {
				clipboard = doc.getText(pocetak, duljina);
			} catch (BadLocationException ignorable) {
			}

		}
	};
	/**
	 * This method is used for pasting selected test by extracting it from the
	 * clipboard.
	 */
	private Action pasteTextAction = new LocalizableAction("paste", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (editor.getTabCount() == 0) {
				return;
			}
			JPanel parent = (JPanel) editor.getSelectedComponent();
			JViewport viewPort = ((JScrollPane) parent.getComponent(0))
					.getViewport();
			JTextArea text = (JTextArea) viewPort.getView();

			Document doc = text.getDocument();
			int pocetak = Math.min(text.getCaret().getDot(), text.getCaret()
					.getMark());
			try {
				doc.insertString(pocetak, clipboard, null);
			} catch (BadLocationException ignorable) {
			}

		}
	};
	/**
	 * This action defines a closing action for closing currently selected tabs.
	 */
	public Action closeDocAction = new LocalizableAction("close", flp) {

		/**
		 * Serial id
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.close();

		}
	};
	/**
	 * This action defines a saving action for saving the contents of currently
	 * selected tab.
	 */
	private Action saveDocAction = new LocalizableAction("save", flp) {

		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.save();
		}
	};
	/**
	 * This action defines a saving action for saving the contents of a
	 * currently selected tab inside a new file.
	 */
	private Action saveAsDocAction = new LocalizableAction("saveas", flp) {

		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.saveAs();
		}
	};
	/**
	 * This action defines an action for creating new empty tabs.
	 */
	private Action newDocumentAction = new LocalizableAction("new", flp) {

		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.newTab();

		}
	};

	/**
	 * This action defines an action for opening existing documents into new
	 * tabs.
	 */
	private Action openDocumentAction = new LocalizableAction("open", flp) {

		/**
		 * serial ID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.open();
		}
	};
	/**
	 * This action defines an action for exiting the program.
	 */
	private Action exitAction = new LocalizableAction("exit", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			checkWindows();
		}
	};
	/**
	 * This action defines an action for changing the current language to
	 * Croatian.
	 */
	private Action hrAction = new LocalizableAction("languageHR", flp) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");

		}
	};
	/**
	 * This action defines an action for changing the current language to
	 * German.
	 */
	private Action deAction = new LocalizableAction("languageDE", flp) {

		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");

		}
	};
	/**
	 * This action defines an action for changing the current language to
	 * English.
	 */
	private Action enAction = new LocalizableAction("languageEN", flp) {

		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");

		}
	};

	/**
	 * This action defines an action for removing duplicate lines from selected
	 * text
	 */
	private Action uniqueAction = new LocalizableAction("unique", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (editor.getTabCount() == 0) {
				return;
			}
			JPanel parent = (JPanel) editor.getSelectedComponent();
			JViewport viewPort = ((JScrollPane) parent.getComponent(0))
					.getViewport();
			JTextArea text = (JTextArea) viewPort.getView();

			Document doc = text.getDocument();
			int pocetak = Math.min(text.getCaret().getDot(), text.getCaret()
					.getMark());
			int duljina = Math.max(text.getCaret().getDot(), text.getCaret()
					.getMark())
					- pocetak;
			try {
				String string = doc.getText(pocetak, duljina);
				doc.remove(pocetak, duljina);
				string = removeOccurences(string);
				doc.insertString(pocetak, string, null);
			} catch (BadLocationException ignorable) {
			}

		}

		/**
		 * Removes multiple occurrences of given text
		 * 
		 * @param text
		 *            the text whose duplicated lines we are removing
		 * @return text with no duplicate lines
		 */
		private String removeOccurences(String text) {
			String newText = "";
			String[] lines = text.split("\n");
			List<String> finalLines = new ArrayList<>();
			for (int i = 0, length = lines.length - 1; i < length; i++) {
				if (!finalLines.contains(lines[i])) {
					finalLines.add(lines[i]);
					newText += lines[i] + "\n";
				}
			}
			return newText;
		}
	};

	/**
	 * This action defines an action for presenting statistics about the
	 * currently selected tab.
	 */
	private Action statisticsAction = new LocalizableAction("statistics", flp) {

		/**
		 * Serial ID
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (editor.getTabCount() == 0) {
				return;
			}
			JPanel parent = (JPanel) editor.getSelectedComponent();
			if (parent == null) {
				return;
			}
			JViewport viewPort = ((JScrollPane) parent.getComponent(0))
					.getViewport();
			JTextArea text = (JTextArea) viewPort.getView();

			String allText = text.getText();
			int lineBreakCount = text.getLineCount();
			int charCount = allText.length();
			int nonEmptyCount = allText.replaceAll("\\s+", "").length();

			JOptionPane
					.showMessageDialog(JNotepadPP.this, "Your document has "
							+ charCount + " characters, " + nonEmptyCount
							+ " non-blank characters and " + lineBreakCount
							+ " lines.");

		}
	};
	/**
	 * This action defines an action for sorting selected text by descending
	 * order.The selected text is always rounded to the nearest line, so only
	 * full lines are accounted for in the selection
	 */
	private Action descendingAction = new LocalizableAction("descending", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (editor.getTabCount() == 0) {
				return;
			}
			JPanel parent = (JPanel) editor.getSelectedComponent();
			JViewport viewPort = ((JScrollPane) parent.getComponent(0))
					.getViewport();
			JTextArea text = (JTextArea) viewPort.getView();

			Document doc = text.getDocument();
			int pocetak = Math.min(text.getCaret().getDot(), text.getCaret()
					.getMark());
			int duljina = Math.max(text.getCaret().getDot(), text.getCaret()
					.getMark())
					- pocetak;

			String allText = text.getText();
			String beggining = allText.substring(0, pocetak);
			String ending = allText.substring(pocetak + duljina);

			pocetak = beggining.lastIndexOf('\n');
			if (pocetak == -1) {
				pocetak = 0;
			}
			int end = ending.indexOf('\n');
			if (end == -1) {
				end = allText.length();
			} else {
				end += text.getSelectedText().length() + beggining.length();
			}
			duljina = end - pocetak;

			try {
				String string = doc.getText(pocetak, duljina);
				doc.remove(pocetak, duljina);
				string = sort(string, "descending");
				doc.insertString(pocetak, string, null);
			} catch (BadLocationException ignorable) {
			}
		}

	};

	/**
	 * This action defines an action for sorting selected text by ascending
	 * order. The selected text is always rounded to the nearest line, so only
	 * full lines are accounted for in the selection
	 */
	private Action ascendingAction = new LocalizableAction("ascending", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (editor.getTabCount() == 0) {
				return;
			}
			JPanel parent = (JPanel) editor.getSelectedComponent();
			JViewport viewPort = ((JScrollPane) parent.getComponent(0))
					.getViewport();
			JTextArea text = (JTextArea) viewPort.getView();

			Document doc = text.getDocument();

			int pocetak = Math.min(text.getCaret().getDot(), text.getCaret()
					.getMark());
			int duljina = Math.max(text.getCaret().getDot(), text.getCaret()
					.getMark())
					- pocetak;

			String allText = text.getText();
			String beggining = allText.substring(0, pocetak);
			String ending = allText.substring(pocetak + duljina);

			pocetak = beggining.lastIndexOf('\n');
			if (pocetak == -1) {
				pocetak = 0;
			}
			int end = ending.indexOf('\n');
			if (end == -1) {
				end = allText.length();
			} else {
				end += text.getSelectedText().length() + beggining.length();
			}
			duljina = end - pocetak;

			try {
				String string = doc.getText(pocetak, duljina);
				doc.remove(pocetak, duljina);
				string = sort(string, "ascending");
				doc.insertString(pocetak, string, null);
			} catch (BadLocationException ignorable) {
			}
		}

	};

	/**
	 * This action defines an action for changing the case of the currently
	 * selected text to upper case.
	 */
	private Action toUppercaseAction = new LocalizableAction("upper", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.inv("upper");

		}
	};

	/**
	 * This action defines an action for changing the case of the currently
	 * selected text to lower case.
	 */
	private Action toLowercaseAction = new LocalizableAction("lower", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.inv("lower");
		}
	};

	/**
	 * This action defines an action for inverting the case of the currently
	 * selected text.
	 */
	private Action invertCaseAction = new LocalizableAction("invert", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JNotepadPP.this.inv("toggle");

		};
	};

}

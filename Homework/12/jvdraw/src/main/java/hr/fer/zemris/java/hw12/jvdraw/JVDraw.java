package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.color.ColorChangeListener;
import hr.fer.zemris.java.hw12.color.IColorProvider;
import hr.fer.zemris.java.hw12.color.JColorArea;
import hr.fer.zemris.java.hw12.drawingmodel.Circle;
import hr.fer.zemris.java.hw12.drawingmodel.FCircle;
import hr.fer.zemris.java.hw12.drawingmodel.GeometricalObject;
import hr.fer.zemris.java.hw12.drawingmodel.JDrawingCanvas;
import hr.fer.zemris.java.hw12.drawingmodel.Line;
import hr.fer.zemris.java.hw12.list.DrawingModel;
import hr.fer.zemris.java.hw12.list.DrawingModelList;
import hr.fer.zemris.java.hw12.list.DrawingObjectListModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * <code>JVDraw</code> is a program which can be used to make models of simple
 * geometrical objects in RGB colors.
 * 
 * Currently this class supports the creation of three main models, lines,
 * circles and filled circles.
 * 
 * <p>
 * Created models can be saved and opened for later work in the form of special
 * .jvd text files. Created models can also be exported as pure .png, .jpg, or
 * .gif images.
 * 
 * @author Filip Džidić
 *
 */
public class JVDraw extends JFrame {

	/** serial ID */
	private static final long serialVersionUID = 1164717557705582573L;
	/** the canvas upon which we draw all of our geometrical objects */
	private JDrawingCanvas canvas;
	/** the current drawing mode is recorded within this string */
	private String mode;
	/** foreground color is kept in this component */
	private JColorArea foreground;
	/** background color is kept in this component */
	private JColorArea background;
	/** this drawing model keeps track of all our created geometrical objects */
	private DrawingModel drawModel;
	/**
	 * this component shows a list of all created geometrical objects in our
	 * GUI, they can be accessed and changed by double clicking
	 */
	private JList<GeometricalObject> scrollList;
	/**
	 * this represents the list Model used to represent and keep track of data
	 * used by the JList
	 */
	private DrawingObjectListModel listModel;
	/**
	 * we record our current opened .jvd file here, is appropriately updated on
	 * every save
	 */
	private Path currentModel;
	/** this variable keeps track of any new changes made to the model */
	private Boolean stateChanged;
	/**
	 * this variable keeps track of the topLeft point of the bounding rectangle
	 * of all our drawn images
	 */
	private Point topLeft;

	/**
	 * Action performed whenever the user attempts to save the model as a new
	 * file.
	 */
	private Action saveAsAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JVDraw.this.saveAs();

		}
	};

	/**
	 * Action performed whenever the user attempts to open a new model.
	 */
	private Action openAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JVDraw.this.open();

		}
	};

	/**
	 * Action performed whenever the user attempts to save a model.
	 */
	private Action saveAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			save();

		}
	};

	/**
	 * Action performed whenever the user attempts to export the model as an
	 * image file.
	 */
	private Action exportAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			export();

		}
	};
	/**
	 * Action performed upon attempting to exit the program.
	 */
	private Action exitAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			exit();

		}
	};

	/**
	 * This action is performed every time we switch drawing modes by using
	 * toggle buttons.
	 */
	private AbstractAction modeAction = new AbstractAction() {
		private static final long serialVersionUID = -5080896965585347950L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JVDraw.this.mode = ((JToggleButton) e.getSource()).getText();
		}
	};

	/**
	 * The main method creates and initializes the GUI.
	 * 
	 * @param args
	 *            no arguments should be provided
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			setupApp();
		});
	}

	/**
	 * This method further defines all of our actions by naming them and
	 * creating keyboard shortcuts for activating them.
	 */
	private void defineActions() {
		openAction.putValue(Action.NAME, "Open");
		openAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control O"));
		saveAsAction.putValue(Action.NAME, "Save as");
		saveAsAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("shift control S"));
		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control S"));
		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control E"));
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, "control Q");

	}

	/**
	 * THis method creates all the components found on the program's toolbar.
	 * Currently they are two color pickers for controling the program's
	 * foreground and background colors as well as tools for creating lines and
	 * circles.
	 */
	private void createToolbar() {
		JToolBar toolBar = new JToolBar("Tools");
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel();
		getContentPane().add(label, BorderLayout.PAGE_END);
		label.setText("Foreground color : (255, 255, 255), background color: (0, 0, 0).");
		toolBar.setFloatable(true);
		foreground = new JColorArea(Color.BLACK);
		foreground.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		background = new JColorArea(Color.WHITE);
		background.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		foreground.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor,
					Color newColor) {
				String original = label.getText();
				int index = original.indexOf('b');
				String background = original.substring(index, original.length());
				String foreground = "Foreground color: (" + newColor.getRed()
						+ ", " + newColor.getGreen() + ", "
						+ newColor.getBlue() + "), ";
				label.setText(foreground + background);

			}
		});
		background.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor,
					Color newColor) {
				String original = label.getText();
				int index = original.indexOf('b');
				String foreground = original.substring(0, index);
				String background = "background color: (" + newColor.getRed()
						+ ", " + newColor.getGreen() + ", "
						+ newColor.getBlue() + ").";
				label.setText(foreground + background);

			}
		});
		panel.add(foreground);
		panel.add(background);
		createButtons(panel);
		toolBar.add(panel);
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * This method is used for creating our menu. The menu currently contains
	 * clickable action for switching models or exporting them as image files.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (drawModel.getSize() == 0) {
					return;
				}
				for (int i = 0; i < drawModel.getSize(); i++) {
					drawModel.getObject(i).paintComponent(getGraphics());
				}

			}
		});
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(openAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.addSeparator();
		fileMenu.add(exportAction);
		fileMenu.add(new JMenuItem("Exit"));
		setJMenuBar(menuBar);

	}

	/**
	 * This constructor initializes all of our main GUI components in the
	 * program and opens a new window.
	 */
	public JVDraw() {
		stateChanged = false;
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		this.setSize(500, 500);
		drawModel = new DrawingModelList();
		canvas = new JDrawingCanvas();
		canvas.setBackground(Color.WHITE);

		drawModel.addDrawingModelListener(canvas);
		listModel = new DrawingObjectListModel((DrawingModelList) drawModel);
		drawModel.addDrawingModelListener(listModel);
		scrollList = new JList<GeometricalObject>(listModel);
		JScrollPane scroll = new JScrollPane(scrollList);
		scroll.setPreferredSize(new Dimension(90, 300));
		canvas.setForeground(Color.white);
		JPanel pane = new JPanel(new BorderLayout());
		pane.add(canvas, BorderLayout.CENTER);
		pane.add(scroll, BorderLayout.EAST);
		pane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				canvas.setBackground(Color.white);
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(),
						canvas.getHeight());
				for (int i = 0; i < drawModel.getSize(); i++) {
					drawModel.getObject(i).paintComponent(getGraphics());
				}

			}
		});
		addListeners();
		getContentPane().add(pane, BorderLayout.CENTER);
		defineActions();
		createMenus();
		createToolbar();
		setTitle("JVDraw");
	}

	/**
	 * This method adds all the necessary listeners to our main components.
	 */
	private void addListeners() {
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				exit();
			}

		});

		canvas.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				((JDrawingCanvas) e.getComponent()).setStart(e.getPoint());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Point start = ((JDrawingCanvas) e.getComponent()).getStart();
				Point end = e.getPoint();
				GeometricalObject obj = null;
				if (mode == null) {
					return;
				}
				switch (mode) {
					case "Line":
						obj = new Line(start, end, JVDraw.this.foreground
								.getCurrentColor());
						break;
					case "Circle":
						obj = new Circle(start, end, JVDraw.this.foreground
								.getCurrentColor());
						break;
					default:
						obj = new FCircle(start, end, JVDraw.this.foreground
								.getCurrentColor(), JVDraw.this.background
								.getCurrentColor());

				}
				canvas.add(obj);
				drawModel.add(obj);
				stateChanged = true;
			}

		});
		canvas.addMouseMotionListener(new MouseMotionListener() {
			public void update(GeometricalObject obj) {
				canvas.setBackground(Color.white);
				canvas.getGraphics().clearRect(0, 0, canvas.getWidth(),
						canvas.getHeight());
				if (drawModel.getSize() == 0) {
					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(),
							canvas.getHeight());
					obj.paintComponent(canvas.getGraphics());
					return;
				}
				for (int i = 0; i < drawModel.getSize(); i++) {
					drawModel.getObject(i).paintComponent(getGraphics());
				}
				obj.paintComponent(canvas.getGraphics());
			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (JVDraw.this.mode == null) {
					return;
				}
				Point start = ((JDrawingCanvas) e.getComponent()).getStart();
				Point end = e.getPoint();
				GeometricalObject obj = null;
				JVDraw.this.setBackground(Color.white);
				switch (mode) {
					case "Line":
						obj = new Line(start, end, JVDraw.this.foreground
								.getCurrentColor());
						update(obj);
						break;
					case "Circle":
						obj = new Circle(start, end, JVDraw.this.foreground
								.getCurrentColor());
						update(obj);
						break;
					default:
						obj = new FCircle(start, end, JVDraw.this.foreground
								.getCurrentColor(), JVDraw.this.background
								.getCurrentColor());
						update(obj);

				}

			}
		});

		scrollList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					GeometricalObject go = (GeometricalObject) ((JList<?>) e
							.getSource()).getSelectedValue();
					JPanel myPanel = makeJPanel(go);

					int result = JOptionPane.showConfirmDialog(null, myPanel,
							"Please provide new values",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.CANCEL_OPTION) {
						return;
					} else {
						if (go instanceof Line) {
							go.setStart(new Point(Integer
									.parseInt(((JTextField) myPanel
											.getComponent(1)).getText()),
									Integer.parseInt(((JTextField) myPanel
											.getComponent(3)).getText())));
							go.setEnd(new Point(Integer
									.parseInt(((JTextField) myPanel
											.getComponent(5)).getText()),
									Integer.parseInt(((JTextField) myPanel
											.getComponent(7)).getText())));
							go.setBase(((JColorArea) myPanel.getComponent(9))
									.getCurrentColor());
						} else if (go instanceof Circle) {
							Point start = new Point(Integer
									.parseInt(((JTextField) myPanel
											.getComponent(1)).getText()),
									Integer.parseInt(((JTextField) myPanel
											.getComponent(3)).getText()));
							go.setStart(start);
							double radius = Double
									.parseDouble(((JTextField) myPanel
											.getComponent(5)).getText());
							((Circle) go).setRadius((int) radius);
							go.setEnd(new Point(start.x + (int) radius, start.y));
							go.setBase(((JColorArea) myPanel.getComponent(7))
									.getCurrentColor());
						} else if (go instanceof FCircle) {
							Point start = new Point(Integer
									.parseInt(((JTextField) myPanel
											.getComponent(1)).getText()),
									Integer.parseInt(((JTextField) myPanel
											.getComponent(3)).getText()));
							go.setStart(start);
							double radius = Double
									.parseDouble(((JTextField) myPanel
											.getComponent(5)).getText());
							((FCircle) go).setRadius((int) radius);
							go.setEnd(new Point(start.x + (int) radius, start.y));
							go.setBase(((JColorArea) myPanel.getComponent(7))
									.getCurrentColor());
							((FCircle) go).setFill(((JColorArea) myPanel
									.getComponent(9)).getCurrentColor());
						}

					}

					canvas.getGraphics().clearRect(0, 0, canvas.getWidth(),
							canvas.getHeight());
					if (drawModel.getSize() == 0) {
						return;
					}
					for (int i = 0; i < drawModel.getSize(); i++) {
						drawModel.getObject(i).paintComponent(getGraphics());
					}
					stateChanged = true;
				}

			}
		});

	}

	/**
	 * This method creates the toolBar buttons for drawing lines and circles.
	 * 
	 * @param panel
	 *            the panel which will contain all of the buttons
	 */
	private void createButtons(JPanel panel) {
		JToggleButton line = new JToggleButton("Line");
		JToggleButton circle = new JToggleButton("Circle");
		JToggleButton fcircle = new JToggleButton("Filled Circle");

		ButtonGroup bg = new ButtonGroup();

		bg.add(line);
		bg.add(circle);
		bg.add(fcircle);

		line.addActionListener(modeAction);
		circle.addActionListener(modeAction);
		fcircle.addActionListener(modeAction);

		panel.add(line);
		panel.add(circle);
		panel.add(fcircle);

	}

	/**
	 * This method starts the GUI initialization process.
	 */
	private static void setupApp() {
		new JVDraw().setVisible(true);
	}

	/**
	 * This method makes a new option dialoge window used for chaning the
	 * parameters of a provided GeometricalObject.
	 * 
	 * @param o
	 *            the GeometricalObject whose parameters we are changing
	 * @return a panel containing all the necessary parts for creating a
	 *         dialogue window
	 */
	private static JPanel makeJPanel(GeometricalObject o) {
		JTextField startX = new JTextField(4);
		JTextField startY = new JTextField(4);
		JTextField endX = new JTextField(4);
		JTextField endY = new JTextField(4);
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		if (o instanceof Line) {
			myPanel.add(new JLabel("StartX:" + o.getStart().x));
			myPanel.add(startX);
			myPanel.add(new JLabel("StartY:" + o.getStart().y));
			myPanel.add(startY);
			myPanel.add(new JLabel("EndX:" + o.getEnd().x));
			myPanel.add(endX);
			myPanel.add(new JLabel("EndY:" + o.getEnd().y));
			myPanel.add(endY);
			myPanel.add(new JLabel("Color"));
			myPanel.add(new JColorArea(o.getBase()));
		} else if (o instanceof Circle) {
			myPanel.add(new JLabel("Current CenterX: " + o.getStart().x));
			myPanel.add(startX);
			myPanel.add(new JLabel("Current CenterY: " + o.getStart().y));
			myPanel.add(startY);
			myPanel.add(new JLabel("Radius: " + ((Circle) o).getRadius()));
			myPanel.add(endX);
			myPanel.add(new JLabel("Color"));
			myPanel.add(new JColorArea(o.getBase()));
		} else if (o instanceof FCircle) {
			myPanel.add(new JLabel("Current CenterX: " + o.getStart().x));
			myPanel.add(startX);
			myPanel.add(new JLabel("Current CenterY: " + o.getStart().y));
			myPanel.add(startY);
			myPanel.add(new JLabel("Radius: " + ((FCircle) o).getRadius()));
			myPanel.add(endX);
			myPanel.add(new JLabel("Outer Color"));
			myPanel.add(new JColorArea(o.getBase()));
			myPanel.add(new JLabel("Inner Color"));
			myPanel.add(new JColorArea(((FCircle) o).getFill()));
		}

		return myPanel;
	}

	/**
	 * This method is called whenever the user attemts to exit the program. It
	 * prompts the user to save any unsaved changes in the model before closing
	 * the program.
	 */
	private void exit() {
		if (stateChanged) {
			int r = JOptionPane
					.showConfirmDialog(this,
							"There are unsaved changes in this model, do you wish to save them?");

			if (r == JOptionPane.CANCEL_OPTION) {
				return;
			}

			if (r == JOptionPane.NO_OPTION) {
				dispose();
			}

			if (r == JOptionPane.YES_OPTION) {
				save();
				dispose();
			}
		}

		dispose();

	}

	/**
	 * This method is used for exporting the current model as an image file.
	 * Currently only three formats are supported: jpg, gif and png.
	 * <p>
	 * The model is exported as a minimal image representation defined by the
	 * bounding rectangle which contains all the drawn objects.
	 */
	protected void export() {
		if (drawModel.getSize() == 0) {
			JOptionPane.showMessageDialog(this, "There is nothing to export",
					"", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Object[] possibilities = { "png", "gif", "jpg" };
		String s = (String) JOptionPane.showInputDialog(this,
				"Select the format of the exported file.", "Export format",
				JOptionPane.PLAIN_MESSAGE, null, possibilities, "png");

		if ((s != null) && (s.length() > 0)) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Saving as *." + s + " file...");
			if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			Path file = fc.getSelectedFile().toPath();
			if (!file.toString().endsWith(s)) {
				JOptionPane
						.showMessageDialog(this, "File format must be \"" + s
								+ "\"", "Invalid format",
								JOptionPane.ERROR_MESSAGE);
				return;
			}
			topLeft = new Point(0, 0);
			Dimension dim = makeBoundingDimension();
			BufferedImage image = new BufferedImage(dim.width, dim.height,
					BufferedImage.TYPE_3BYTE_BGR);

			Graphics2D g = image.createGraphics();
			produceImage(g, dim);
			g.dispose();
			try {
				ImageIO.write(image, s, file.toFile());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this,
						"IO failure while attempting to export", "IO Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(this, "File successfuly exported to "
					+ file.toString());
			return;
		} else {
			return;
		}
	}

	/**
	 * This method is used for copying all our drawn objects to an image we're
	 * exporting.
	 * 
	 * @param g
	 *            the object used to render objects to the image
	 * @param dim
	 *            the dimensions of the bounding rectangle of the image
	 */
	private void produceImage(Graphics2D g, Dimension dim) {
		g.setColor(Color.white);
		g.fillRect(0, 0, dim.width, dim.height);
		for (int i = 0, max = drawModel.getSize(); i < max; i++) {
			GeometricalObject obj = drawModel.getObject(i);
			if (obj instanceof Line) {
				g.setColor(obj.getBase());
				g.drawLine(obj.getStart().x - topLeft.x, obj.getStart().y
						- topLeft.y, obj.getEnd().x - topLeft.x, obj.getEnd().y
						- topLeft.y);
			} else if (obj instanceof Circle) {
				g.setColor(obj.getBase());
				int radius = ((Circle) obj).getRadius();
				g.drawOval(obj.getStart().x - radius - topLeft.x,
						obj.getStart().y - radius - topLeft.y, 2 * radius,
						2 * radius);
			} else if (obj instanceof FCircle) {
				g.setColor(((FCircle) obj).getFill());
				int radius = ((FCircle) obj).getRadius();
				g.fillOval(obj.getStart().x - radius - topLeft.x,
						obj.getStart().y - radius - topLeft.y, 2 * radius,
						2 * radius);
				g.setColor(obj.getBase());
				g.drawOval(obj.getStart().x - radius - topLeft.x,
						obj.getStart().y - radius - topLeft.y, 2 * radius,
						2 * radius);

			}
		}

	}

	/**
	 * Finds out the dimensions of the bounding rectangle of all the drawn
	 * images inside the model.
	 * <p>
	 * 
	 * @return the bounding rectangle's dimension
	 */
	private Dimension makeBoundingDimension() {
		int MinX = canvas.getSize().width, MaxX = 0, MinY = canvas.getSize().height, MaxY = 0;
		for (int i = 0, max = drawModel.getSize(); i < max; i++) {
			GeometricalObject obj = drawModel.getObject(i);
			if (obj instanceof Line) {
				MinX = Math.min(MinX,
						Math.min(obj.getStart().x, obj.getEnd().x));
				MaxX = Math.max(MaxX,
						Math.max(obj.getStart().x, obj.getEnd().x));
				MinY = Math.min(MinY,
						Math.min(obj.getStart().y, obj.getEnd().y));
				MaxY = Math.max(MaxY,
						Math.max(obj.getStart().y, obj.getEnd().y));
			} else if (obj instanceof Circle) {
				MinX = Math.min(MinX,
						obj.getStart().x - ((Circle) obj).getRadius());
				MaxX = Math.max(MaxX,
						obj.getStart().x + ((Circle) obj).getRadius());
				MinY = Math.min(MinY,
						obj.getStart().y - ((Circle) obj).getRadius());
				MaxY = Math.max(MaxY,
						obj.getStart().y + ((Circle) obj).getRadius());
			} else if (obj instanceof FCircle) {
				MinX = Math.min(MinX,
						obj.getStart().x - ((FCircle) obj).getRadius());
				MaxX = Math.max(MaxX,
						obj.getStart().x + ((FCircle) obj).getRadius());
				MinY = Math.min(MinY,
						obj.getStart().y - ((FCircle) obj).getRadius());
				MaxY = Math.max(MaxY,
						obj.getStart().y + ((FCircle) obj).getRadius());
			}
		}
		topLeft = new Point(MinX, MinY);
		return new Dimension(MaxX - MinX, MaxY - MinY);
	}

	/**
	 * This method is called any time the user attempts to open a saved model.
	 * Can only open .jvd files representing saved models.
	 */
	protected void open() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path openedFilePath = fc.getSelectedFile().toPath();
		if (!Files.isReadable(openedFilePath)) {
			JOptionPane.showMessageDialog(this, "File " + openedFilePath
					+ " does not exist", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!openedFilePath.toString().endsWith(".jvd")) {
			JOptionPane.showMessageDialog(this, "File " + openedFilePath
					+ " is not in valid format", "Invalid format",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		currentModel = openedFilePath;

		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(openedFilePath);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "Error upon opening file: "
					+ e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String text = new String(bytes, StandardCharsets.UTF_8);
		parse(text);
		stateChanged = false;

	}

	/**
	 * This method is called any time the user attempts to save changes to the
	 * current model.
	 * <p>
	 * If the model doesn't have a currently associated filepath (it's the first
	 * time being saved, in other words) the user is prompted to define the new
	 * pathname before saving.
	 */
	protected void save() {
		if (currentModel == null) {
			saveAs();
			return;
		}
		String text = makeFile();
		try {
			Files.write(currentModel, text.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane
					.showMessageDialog(
							this,
							"Error while attempting to create file: "
									+ e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
			return;
		}
		stateChanged = false;

	}

	/**
	 * This method is called when saving the current model as a new .jvd file.
	 */
	protected void saveAs() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save document");
		if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(this, "Nothing was saved",
					"Info message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Path file = fc.getSelectedFile().toPath();
		if (!file.toString().endsWith(".jvd")) {
			JOptionPane.showMessageDialog(this, "File format must be \".jvd\"",
					"Invalid format", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (Files.exists(file)) {
			int r = JOptionPane.showConfirmDialog(this, "File " + file
					+ " already exists. Do you want to overwrite it?",
					"Warning", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (r != JOptionPane.YES_OPTION) {
				return;
			}
		}
		currentModel = file;
		String text = makeFile();
		try {
			Files.write(file, text.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane
					.showMessageDialog(
							this,
							"Error while attempting to create file: "
									+ e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
			return;
		}
		stateChanged = false;

	}

	/**
	 * This method makes a new .jvd text representation of the model being
	 * saved.
	 * 
	 * @return the text representation of our model in .jvd format
	 */
	private String makeFile() {
		StringBuilder buildy = new StringBuilder();
		for (int i = 0; i < drawModel.getSize(); i++) {
			GeometricalObject o = drawModel.getObject(i);
			if (o instanceof Line) {
				buildy.append("LINE ")
						.append(o.getStart().x + " " + o.getStart().y + " ")
						.append(o.getEnd().x + " " + o.getEnd().y + " ");

				buildy.append(o.getBase().getRed() + " "
						+ o.getBase().getGreen() + " " + o.getBase().getBlue());
			} else if (o instanceof Circle) {
				buildy.append("CIRCLE ")
						.append(o.getStart().x + " " + o.getStart().y + " ")
						.append(((Circle) o).getRadius() + " ");
				buildy.append(o.getBase().getRed() + " "
						+ o.getBase().getGreen() + " " + o.getBase().getBlue());
			} else {
				buildy.append("FCIRCLE ")
						.append(o.getStart().x + " " + o.getStart().y + " ")
						.append(((FCircle) o).getRadius() + " ");
				buildy.append(o.getBase().getRed() + " "
						+ o.getBase().getGreen() + " " + o.getBase().getBlue()
						+ " ");
				Color fill = ((FCircle) o).getFill();
				buildy.append(fill.getRed() + " " + fill.getGreen() + " "
						+ fill.getBlue());
			}
			if (i != drawModel.getSize() - 1) {
				buildy.append('\n');
			}
		}
		return buildy.toString();
	}

	/**
	 * This method parses text to see if it's a valid .jvd file.
	 * 
	 * @param text
	 *            <code>String</code> representation of a saved model in .jvd
	 *            format
	 */
	private void parse(String text) {
		ArrayList<GeometricalObject> list = new ArrayList<>();
		String[] objects = text.split("\n");
		for (String object : objects) {
			String[] parameters = object.split(" ");
			if (parameters.length < 7 || parameters.length > 10) {
				showError();
				return;
			}
			switch (parameters[0]) {
				case "LINE":
					if (parameters.length != 8) {
						showError();
						return;
					}
					try {
						Point start = new Point(
								Integer.parseInt(parameters[1]),
								Integer.parseInt(parameters[2]));
						Point end = new Point(Integer.parseInt(parameters[3]),
								Integer.parseInt(parameters[4]));
						Color c = new Color(Integer.parseInt(parameters[5]),
								Integer.parseInt(parameters[6]),
								Integer.parseInt(parameters[7]));
						list.add(new Line(start, end, c));
					} catch (NumberFormatException e) {
						showError();
						return;
					}
					break;
				case "CIRCLE":
					if (parameters.length != 7) {
						showError();
						return;
					}
					try {
						Point center = new Point(
								Integer.parseInt(parameters[1]),
								Integer.parseInt(parameters[2]));
						int radius = Integer.parseInt(parameters[3]);
						Color c = new Color(Integer.parseInt(parameters[4]),
								Integer.parseInt(parameters[5]),
								Integer.parseInt(parameters[6]));
						list.add(new Circle(center, new Point(
								center.x + radius, center.y), c));
					} catch (NumberFormatException e) {
						showError();
						return;
					}
					break;
				case "FCIRCLE":
					if (parameters.length != 10) {
						showError();
						return;
					}
					try {
						Point center = new Point(
								Integer.parseInt(parameters[1]),
								Integer.parseInt(parameters[2]));
						int radius = Integer.parseInt(parameters[3]);
						Color c1 = new Color(Integer.parseInt(parameters[4]),
								Integer.parseInt(parameters[5]),
								Integer.parseInt(parameters[6]));
						Color c2 = new Color(Integer.parseInt(parameters[7]),
								Integer.parseInt(parameters[8]),
								Integer.parseInt(parameters[9]));

						list.add(new FCircle(center, new Point(center.x
								+ radius, center.y), c1, c2));
					} catch (NumberFormatException e) {
						showError();
						return;
					}
					break;
				default:
					showError();
					return;
			}
			canvas.removeAll();
			canvas.getGraphics().clearRect(0, 0, canvas.getWidth(),
					canvas.getHeight());
			this.listModel.clear();
			for (GeometricalObject o : list) {
				listModel.addElement(o);
				scrollList.setModel(listModel);
				canvas.add(o);
			}

		}

	}

	/**
	 * This method informs the user with a dialogue window of any parsing errors
	 * found in a .jvd file.
	 */
	public void showError() {
		JOptionPane.showMessageDialog(this, "Error upon parsing file: "
				+ currentModel.getFileName(), "Prase Error",
				JOptionPane.ERROR_MESSAGE);
		currentModel = null;
	}

}

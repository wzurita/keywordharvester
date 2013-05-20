package wzurita;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class KeywordHarvesterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField keywordField = null;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel toolPane = null;
	private JPanel optionsPane = null;
	private Properties toolOptions = null; // @jve:decl-index=0:
	private JScrollPane jScrollPane = null;
	private JTable relatedKeywordsTable = null;
	private JProgressBar jProgressBar = null;
	private GUIProcessingRunnable process = new GUIProcessingRunnable(); // @jve:decl-index=0:
	private JTable optionsTable = null;
	private DefaultTableModel optionsModel;
	private DefaultTableModel dataModel;
	private JScrollPane jScrollPane1 = null;

	/**
	 * This is the default constructor
	 */
	public KeywordHarvesterGUI() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 400);
		this.setContentPane(getJContentPane());
		this.setTitle("Keyword Harvester");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.out.println("windowClosing()");
				System.exit(0);
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new CardLayout());
			jContentPane.add(getJTabbedPane(), getJTabbedPane().getName());
		}
		return jContentPane;
	}

	/**
	 * This method initializes keywordField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getKeywordField() {
		if (keywordField == null) {
			keywordField = new JTextField();
		}
		return keywordField;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getKeywordField(), BorderLayout.CENTER);
			jPanel.add(getJButton(), BorderLayout.EAST);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("harvest!");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String keyword = getKeywordField().getText();
					toolOptions = getToolOptions();
					if (keyword != null && !(keyword.trim().equals(""))) {
						process.setDataModel(dataModel);
						process.setKeyword(keyword);
						process.setToolOptions(toolOptions);
						process.setProgressBar(getJProgressBar());
						process.setJButton(getJButton());
						(new Thread(process)).start();
					}
				}
			});
		}
		return jButton;
	}

	private Properties getToolOptions() {
		if (this.toolOptions == null) {
			this.toolOptions = new Properties();
			try {
				ResourceBundle bundle = ResourceBundle.getBundle("parameters");
				for (String key : bundle.keySet()) {
					toolOptions.put(key, bundle.getObject(key));
				}
			} catch (MissingResourceException mre) {
				toolOptions.put("stop_words", "of,but");
			}
		}
		return this.toolOptions;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setName("jTabbedPane");
			jTabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jTabbedPane.addTab("Tool", null, getToolPane(), null);
			jTabbedPane.addTab("Options", null, getOptionsPane(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes toolPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getToolPane() {
		if (toolPane == null) {
			toolPane = new JPanel();
			toolPane.setLayout(new BorderLayout());
			toolPane.add(getJPanel(), BorderLayout.NORTH);
			toolPane.add(getJScrollPane(), BorderLayout.CENTER);
			toolPane.add(getJProgressBar(), BorderLayout.SOUTH);
		}
		return toolPane;
	}

	/**
	 * This method initializes optionsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getOptionsPane() {
		if (optionsPane == null) {
			optionsPane = new JPanel();
			optionsPane.setLayout(new BorderLayout());
			optionsPane.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return optionsPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getRelatedKeywordsTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes relatedKeywordsTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getRelatedKeywordsTable() {
		if (relatedKeywordsTable == null) {
			this.dataModel = new DefaultTableModel();
			relatedKeywordsTable = new JTable(this.dataModel);
			relatedKeywordsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			relatedKeywordsTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					int row = relatedKeywordsTable.getSelectedRow();
					getKeywordField().setText(process.getRelatedKeywords()[row]);
				}
			});
		}
		return relatedKeywordsTable;
	}

	/**
	 * This method initializes jProgressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setVisible(false);
		}
		return jProgressBar;
	}

	/**
	 * This method initializes optionsTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getOptionsTable() {
		if (optionsTable == null) {
			this.optionsModel = new DefaultTableModel();
			initOptionsModel();
			this.optionsModel.addTableModelListener(new TableModelListener() {

				@Override
				public void tableChanged(TableModelEvent e) {
					toolOptions.clear();
					int rows = optionsModel.getRowCount();
					for (int i = 0; i < rows; i++) {
						toolOptions.put(optionsModel.getValueAt(i, 0), optionsModel.getValueAt(i, 1));
					}

				}

			});
			optionsTable = new JTable(optionsModel);
			optionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return optionsTable;
	}

	private void initOptionsModel() {
		Properties toolOptionsLocal = getToolOptions();
		Object[][] dataVector = new Object[toolOptionsLocal.size()][2];
		int i = 0;
		for (Object key : toolOptionsLocal.keySet()) {
			dataVector[i][0] = key;
			dataVector[i][1] = toolOptionsLocal.get(key);
			i++;
		}
		Object[] columnIdentifiers = {"Key","Value"};
		this.optionsModel.setDataVector(dataVector, columnIdentifiers);
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getOptionsTable());
		}
		return jScrollPane1;
	}

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		KeywordHarvesterGUI gui = new KeywordHarvesterGUI();
		gui.setVisible(true);
	}

}

package siima.app.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import siima.app.control.MainAppController;
import siima.app.model.tree.ElementModel;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class MainFrame extends JFrame implements ActionListener { // TreeSelectionListener
																	// {
	public String eraProjectHomeDirectory = ".";
	public String latestOpenedFolder = ".";
	private final static String newline="\n";
	//private JPanel contentPane;
	private JFileChooser fileChooser;
	private JSplitPane m_contentPane;
	private JMenuItem mntmOpen;

	private File mainOpenFile;
	private JMenuItem mntmSave;

	public MainAppController appControl;
	private JScrollPane hierarchyTreeScrollPane;
	private JScrollPane hierarchyTreeScrollPane2;
	private JScrollPane hierarchyTreeScrollPane3;
	private JScrollPane hierarchyTreeScrollPane4;
	private JScrollPane consoleScrollPane;
	private JScrollPane resultScrollPane;
	private JScrollPane spinCommandFileScrollPane;
	private JMenuItem mntmGenerateJmonkey;
	private JTextArea bottomLeftTextArea;
	private JTextArea txtrConsoleOutput;
	private JTextArea txtrResultOutput;
	private JTextArea txtrSpinCommandFileOutput;
	private JMenuItem mntmLoadRules;
	private JMenuItem mntmInvokeReasoner;
	private JMenuItem mntmSaveResultModels;
	private JMenuItem mntmCaexToAsp;
	private JTabbedPane tabbedPane;
	private JTabbedPane bottomRightTabbedPane;
	private JMenuItem mntmConfigureSchema;
	private JMenuItem mntmExit;
	private JMenuItem mntmInvokeTransform;
	private JMenuItem mntmSetTransformContext;
	private JMenuItem mntmGenOntologyModel;
	private JMenuItem mntmNewProject;
	private JMenuItem mntmSaveProjectAs;
	private JMenuItem mntmSaveProject;
	private JMenuItem mntmOpenProject;
	private JMenuItem mntmAspSolver;
	private JMenuItem mntmSaveOntologyModel;
	private JMenuItem mntmMergeModels;
	private JMenuItem mntmLoadSpinCommands;
	private JMenuItem mntmInvokeSpinCommands;
	// private JTree tree;
	private JRadioButtonMenuItem rbMenuItem1;
	private JRadioButtonMenuItem rbMenuItem2;
	private JRadioButtonMenuItem rbMenuItem3;
	private JRadioButtonMenuItem rbMenuItem4;
	private JRadioButtonMenuItem rbMenuItem5;
	private JRadioButtonMenuItem rbMenuItem6;
	
	//For Search CSMCommand block
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	//For found CSMCommand block
	private JTextField textField11;
	private JTextField textField12;
	private JTextField textField13;
	private JTextField textField14;
	private JTextField textField15;
	private JTextField textField16;
	
	private JButton btnSearchCommandButton;
	private  JTextArea oneJsonCommandTextArea;
	private JButton btnUpdateCSMCommandButton;
	private JButton btnSequenceRunButton;
	private JTextField idxsequencetext; 
	private Map<String,JTextField> dataDisplayMap;
	//protected JLabel actionLabel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600); //(100, 100, 450, 300);
		setTitle("ERAmlHandler");

		this.appControl = new MainAppController(this);

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File("./data"));

		/*
		 * MenuBar
		 * 
		 */

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmOpen = new JMenuItem("Open Caex File");
		mntmOpen.addActionListener(this); // See: method											// actionPerformed(ActionEvent arg0)
		mnFile.add(mntmOpen);
		
		mntmSave = new JMenuItem("Save Caex File");
		mntmSave.addActionListener(this);// See: method											// actionPerformed(ActionEvent arg0)
		mnFile.add(mntmSave);
		
		mnFile.addSeparator();

		mntmOpenProject = new JMenuItem("Open Project...");
		mntmOpenProject.addActionListener(this);
		mnFile.add(mntmOpenProject);
		
		mntmNewProject = new JMenuItem("New Project...");
		mntmNewProject.addActionListener(this);
		mnFile.add(mntmNewProject);
		
		mntmSaveProject = new JMenuItem("Save Project");
		mntmSaveProject.addActionListener(this);
		mnFile.add(mntmSaveProject);
		
		mntmSaveProjectAs = new JMenuItem("Save Project As...");
		mntmSaveProjectAs.addActionListener(this);
		mnFile.add(mntmSaveProjectAs);
		
		mnFile.addSeparator();
		
		mntmConfigureSchema = new JMenuItem("Configure Schema...");
		mntmConfigureSchema.addActionListener(this);
		mnFile.add(mntmConfigureSchema);
		
		mnFile.addSeparator();
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				appControl.exitBackup();
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnTransform = new JMenu("Transform");
		menuBar.add(mnTransform);

		mntmGenerateJmonkey = new JMenuItem("Caex to JMonkey");
		mntmGenerateJmonkey.addActionListener(this);
		mnTransform.add(mntmGenerateJmonkey);

		mntmCaexToAsp = new JMenuItem("Caex to Asp Facts...");
		mntmCaexToAsp.addActionListener(this);
		mnTransform.add(mntmCaexToAsp);
		
		mnTransform.addSeparator();
		//NEW SUBMENU
		JMenu submenu = new JMenu("Transform Context");
		mntmSetTransformContext = new JMenuItem("Set Context...");
		mntmSetTransformContext.addActionListener(this);
		submenu.add(mntmSetTransformContext);

		mntmInvokeTransform = new JMenuItem("Run Transform");
		mntmInvokeTransform.addActionListener(this);
		submenu.add(mntmInvokeTransform);

		mnTransform.add(submenu);
		/*
		 *  Ontology JMenu
		 */
		
		JMenu mnOntology = new JMenu("Ontology");
		menuBar.add(mnOntology);
		//NEW SUBMENU
		JMenu mnOntSubmenu = new JMenu("Generate Ontology Model");

		mntmGenOntologyModel = new JMenuItem("Default Model");
		mntmGenOntologyModel.addActionListener(this);		
		mnOntSubmenu.add(mntmGenOntologyModel);
		mnOntSubmenu.addSeparator();
		// Radio buttons: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuLookDemoProject/src/components/MenuLookDemo.java
		ButtonGroup group = new ButtonGroup();
		
		rbMenuItem1 = new JRadioButtonMenuItem("InstanceHierarchy");
        rbMenuItem1.setSelected(false);
        //rbMenuItem1.setMnemonic(KeyEvent.VK_R);
        rbMenuItem1.addActionListener(this);       
        group.add(rbMenuItem1);
        mnOntSubmenu.add(rbMenuItem1);
 
        rbMenuItem2 = new JRadioButtonMenuItem("SystemUnitClassLib");
        rbMenuItem2.setSelected(false);
        //rbMenuItem2.setMnemonic(KeyEvent.VK_O);
        rbMenuItem2.addActionListener(this);       
        group.add(rbMenuItem2);
        mnOntSubmenu.add(rbMenuItem2);
		
		rbMenuItem3 = new JRadioButtonMenuItem("RoleClassLib");
        rbMenuItem3.setSelected(false);
        //rbMenuItem1.setMnemonic(KeyEvent.VK_T);
        rbMenuItem3.addActionListener(this);       
        group.add(rbMenuItem3);
        mnOntSubmenu.add(rbMenuItem3);
        
		rbMenuItem4 = new JRadioButtonMenuItem("InterfaceClassLib");
        rbMenuItem4.setSelected(false);
        //rbMenuItem1.setMnemonic(KeyEvent.VK_U);
        rbMenuItem4.addActionListener(this);       
        group.add(rbMenuItem4);
        mnOntSubmenu.add(rbMenuItem4);
        
		rbMenuItem5 = new JRadioButtonMenuItem("AllModels");
        rbMenuItem5.setSelected(false);
        //rbMenuItem1.setMnemonic(KeyEvent.VK_U);
        rbMenuItem5.addActionListener(this);       
        group.add(rbMenuItem5);
        mnOntSubmenu.add(rbMenuItem5);
        
        rbMenuItem6 = new JRadioButtonMenuItem("none");
        rbMenuItem6.setSelected(true);
        //rbMenuItem1.setMnemonic(KeyEvent.VK_T);
        rbMenuItem6.addActionListener(this);       
        group.add(rbMenuItem6);
        mnOntSubmenu.add(rbMenuItem6);
        
		mnOntology.add(mnOntSubmenu);
		
		mntmMergeModels = new JMenuItem("Merge Existing Models");
		mntmMergeModels.addActionListener(this);
		mnOntology.add(mntmMergeModels);
		
		mntmSaveOntologyModel = new JMenuItem("Save Ontology Model...");
		mntmSaveOntologyModel.addActionListener(this);
		mnOntology.add(mntmSaveOntologyModel);

		/*
		 *  Rules JMenu
		 */
		JMenu mnAsp = new JMenu("Rules");
		menuBar.add(mnAsp);
		//NEW SUBMENU
		JMenu mnAspSubmenu = new JMenu("Asp Reasoner");
		
		mntmLoadRules = new JMenuItem("Load Rules...");
		mntmLoadRules.addActionListener(this);
		mnAspSubmenu.add(mntmLoadRules);
		
		mntmInvokeReasoner = new JMenuItem("Invoke Reasoner");
		mntmInvokeReasoner.addActionListener(this);
		mnAspSubmenu.add(mntmInvokeReasoner);
	
		mntmSaveResultModels = new JMenuItem("Save Result Models...");
		mntmSaveResultModels.addActionListener(this);
		mnAspSubmenu.add(mntmSaveResultModels);
		
		mnAspSubmenu.addSeparator();
		
		mntmAspSolver = new JMenuItem("ASP Solver Engine...");
		mntmAspSolver.addActionListener(this);
		mnAspSubmenu.add(mntmAspSolver);

		mnAsp.add(mnAspSubmenu);
		
		/*
		 *  SPARQL JMenu
		 */
		
		JMenu mnSparql = new JMenu("Sparql");
		menuBar.add(mnSparql);

		mntmLoadSpinCommands = new JMenuItem("Load Spin Commands...");
		mntmLoadSpinCommands.addActionListener(this);
		mnSparql.add(mntmLoadSpinCommands);
		
		mntmInvokeSpinCommands = new JMenuItem("Invoke Spin Commands");
		mntmInvokeSpinCommands.addActionListener(this);
		mnSparql.add(mntmInvokeSpinCommands);
		
		/*
		 * Main Window
		 * 
		 */

		/*----Main contentPane ----*/
		m_contentPane = new JSplitPane();
		m_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(m_contentPane);

		/*----Left Side JSplitPane----*/
		JSplitPane LeftVerticalSplitPane = new JSplitPane();
		LeftVerticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		m_contentPane.setLeftComponent(LeftVerticalSplitPane);

		/*----Top Left Panel ----*/

		JPanel topLeftPanel = new JPanel();
		LeftVerticalSplitPane.setLeftComponent(topLeftPanel);
		GridBagLayout gbl_topLeftPanel = new GridBagLayout();
		gbl_topLeftPanel.columnWidths = new int[] { 400, 0 };
		gbl_topLeftPanel.rowHeights = new int[] { 10, 300, 0, 0 };//{ 50, 200, 0, 0 };
		gbl_topLeftPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_topLeftPanel.rowWeights = new double[] { 0.5, 1.0, 0.5, Double.MIN_VALUE };//{ 1.0, 1.0, 1.0, Double.MIN_VALUE };
		topLeftPanel.setLayout(gbl_topLeftPanel);

		/* -- New JTabbedPane with 4 tabs for 4 caex hierarchy jtrees */

		tabbedPane = new JTabbedPane();
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		/*
		 * tabbedPane.addChangeListener(new ChangeListener() { public void
		 * stateChanged(ChangeEvent e) {
		 * System.out.println("TEEEST:Tab Changed: " +
		 * tabbedPane.getSelectedIndex()); } });
		 */
		hierarchyTreeScrollPane = new JScrollPane();
		tabbedPane.insertTab("InstanceH", null, hierarchyTreeScrollPane, "InternalElements", 0);

		hierarchyTreeScrollPane2 = new JScrollPane();
		tabbedPane.insertTab("SystemUnitCL", null, hierarchyTreeScrollPane2, "SystemUnitClasses", 1);
		
		hierarchyTreeScrollPane3 = new JScrollPane();
		tabbedPane.insertTab("RoleCL", null, hierarchyTreeScrollPane3, "RoleClasses", 2);

		hierarchyTreeScrollPane4 = new JScrollPane();
		tabbedPane.insertTab("InterfaceCL", null, hierarchyTreeScrollPane4, "InterfaceClasses", 3);
		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		topLeftPanel.add(tabbedPane, gbc_tabbedPane);

		/*----Bottom Left Panel ----*/
		JPanel bottomLeftPanel = new JPanel();
		LeftVerticalSplitPane.setRightComponent(bottomLeftPanel);
		GridBagLayout gbl_bottomLeftPanel = new GridBagLayout();
		gbl_bottomLeftPanel.columnWidths = new int[] { 0, 0 };
		gbl_bottomLeftPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_bottomLeftPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_bottomLeftPanel.rowWeights = new double[] { 1.0, 0.2, Double.MIN_VALUE };
		bottomLeftPanel.setLayout(gbl_bottomLeftPanel);

		JScrollPane bottomLeftScrollPane = new JScrollPane();
		GridBagConstraints gbc_bottomLeftScrollPane = new GridBagConstraints();
		gbc_bottomLeftScrollPane.fill = GridBagConstraints.BOTH;
		gbc_bottomLeftScrollPane.gridx = 0;
		gbc_bottomLeftScrollPane.gridy = 0;
		bottomLeftPanel.add(bottomLeftScrollPane, gbc_bottomLeftScrollPane);

		bottomLeftTextArea = new JTextArea();
		bottomLeftTextArea.setRows(30);
		bottomLeftTextArea.setColumns(100);
		bottomLeftTextArea.setText("Selected Element:\n");
		/*
		 * GridBagConstraints gbc_bottomLeftTextArea = new GridBagConstraints();
		 * gbc_bottomLeftTextArea.insets = new Insets(0, 0, 5, 0);
		 * gbc_bottomLeftTextArea.fill = GridBagConstraints.BOTH;
		 * gbc_bottomLeftTextArea.gridx = 1; gbc_bottomLeftTextArea.gridy = 0;
		 */
		// bottomLeftPanel.add(bottomLeftTextArea, gbc_bottomLeftTextArea);

		bottomLeftScrollPane.setViewportView(bottomLeftTextArea);

		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.insets = new Insets(0, 0, 5, 0);
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.gridx = 0;
		gbc_buttonPanel.gridy = 1;
		bottomLeftPanel.add(buttonPanel, gbc_buttonPanel);

		JButton btnShowDetailsButton = new JButton("Description");
		btnShowDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int tabnumber = tabbedPane.getSelectedIndex();

				String info = appControl.getSelectedElementInfo(tabnumber);

				bottomLeftTextArea.setText(info);
			}
		});
		buttonPanel.add(btnShowDetailsButton);

		/*----Right Side JSplitPane----*/
		JSplitPane rightVerticalSplitPane = new JSplitPane();
		rightVerticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		m_contentPane.setRightComponent(rightVerticalSplitPane);

		/* ==== Right Top side ==== */
		
		/*----Right Top Side JSplitPane----*/		
		JSplitPane rightTopVerticalSplitPane = new JSplitPane();
		rightTopVerticalSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);		
		rightVerticalSplitPane.setLeftComponent(rightTopVerticalSplitPane);
		
		/* ==== Right top_left_panel ==== */
		JPanel rightTopLeftPanel = new JPanel();
		rightTopVerticalSplitPane.setLeftComponent(rightTopLeftPanel);
		
		/* =============================== 
		 * 		Right top_right_panel 
		 * =============================== */
		JPanel rightTopRightPanel = new JPanel();
		rightTopVerticalSplitPane.setRightComponent(rightTopRightPanel);
		GridBagLayout gbl_right_top_right_panel = new GridBagLayout();
		gbl_right_top_right_panel.columnWidths = new int[] {1, 100, 10, 0};
		gbl_right_top_right_panel.rowHeights = new int[] {1, 20, 30, 60, 5, 5, 0};
		gbl_right_top_right_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_right_top_right_panel.rowWeights = new double[]{0.0, 0.2, 0.3, 0.6, 0.1, 0.1, Double.MIN_VALUE};
		rightTopRightPanel.setLayout(gbl_right_top_right_panel);
		
		/* TextFields for Search CSMCommand 
		 * See: https://docs.oracle.com/javase/tutorial/uiswing/components/editorpane.html
		 * */
		String textFieldString1 = "S_idcode"; //"TextField1";
		String textFieldString2 = "S_index"; //"TextField2";
		String textFieldString3 = "S_commandType"; //"TextField3";
		
		//Create a regular text field.
        textField1 = new JTextField(10); //index
        textField1.setActionCommand(textFieldString1);
        //textField1.addActionListener(this);
        textField2 = new JTextField(10); //idcode
        textField2.setActionCommand(textFieldString2);
        //textField2.addActionListener(this);
        textField3 = new JTextField(10); //commandtype
        textField3.setActionCommand(textFieldString3);
        //textField3.addActionListener(this);
        
        //Create some labels for the fields.
        JLabel textFieldLabel1 = new JLabel(textFieldString1 + ": ");
        textFieldLabel1.setLabelFor(textField1);
        JLabel textFieldLabel2 = new JLabel(textFieldString2 + ": ");
        textFieldLabel2.setLabelFor(textField2);
        JLabel textFieldLabel3 = new JLabel(textFieldString3 + ": ");
        textFieldLabel3.setLabelFor(textField3);
        
      //Create a label to put messages during an action event.
      //  actionLabel = new JLabel("Type text in a field and press Enter.");
      //  actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        
        btnSearchCommandButton = new JButton("Search");
        btnSearchCommandButton.setEnabled(false);
        btnSearchCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String idcode = textField1.getText();
				String index = textField2.getText();
				String commandtype = textField3.getText();
				System.out.println("SEARCH BUTTON PRESSED......");
				// fieldKeyDataMap Map filled in CommandFileSpinMng class searchCSMCommandContent() method
				Map<String,String> fieldKeyDataMap = appControl.searchCSMCommandContent(idcode, index, commandtype);
				// dataDisplayMap Map filled in this class below
				//textFieldKey11
				dataDisplayMap.get("idcode").setText(fieldKeyDataMap.get("idcode"));
				dataDisplayMap.get("index").setText(fieldKeyDataMap.get("index"));
				dataDisplayMap.get("commandtype").setText(fieldKeyDataMap.get("commandtype"));
				dataDisplayMap.get("stepnote").setText(fieldKeyDataMap.get("stepnote"));
				dataDisplayMap.get("comment").setText(fieldKeyDataMap.get("comment"));
				dataDisplayMap.get("bodyobjectkey").setText(fieldKeyDataMap.get("bodyobjectkey"));
				String bodyobjectContent = fieldKeyDataMap.get(fieldKeyDataMap.get("bodyobjectkey"));

				oneJsonCommandTextArea.setText(bodyobjectContent.replaceAll(",", ",\n"));
				oneJsonCommandTextArea.setCaretPosition(0);
				
				//TODO NEW:
				dataDisplayMap.get("idxsequence").setText(fieldKeyDataMap.get("idxsequence"));
					
				btnUpdateCSMCommandButton.setEnabled(true);
				btnSequenceRunButton.setEnabled(true);
			}
		});
        
		//Layout the search panel and the labels.
        JPanel textSearchPanel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
 
        textSearchPanel.setLayout(gridbag);
 
        JLabel[] labels = {textFieldLabel1, textFieldLabel2, textFieldLabel3};
        JTextField[] textFields = {textField1, textField2, textField3};
        addLabelTextRows(labels, textFields, gridbag, textSearchPanel);
 
        c.gridwidth = GridBagConstraints.REMAINDER; //last
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1.0;
        textSearchPanel.add(btnSearchCommandButton, c); //(actionLabel, c);
        textSearchPanel.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("CSMCommand Search"), //"Text Fields"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		//MY ADD
        GridBagConstraints gbc_textSearchPanel = new GridBagConstraints();
        gbc_textSearchPanel.insets = new Insets(0, 0, 0, 0);
        gbc_textSearchPanel.fill = GridBagConstraints.BOTH;
        gbc_textSearchPanel.gridx = 1;
        gbc_textSearchPanel.gridy = 1;
		
        rightTopRightPanel.add(textSearchPanel, gbc_textSearchPanel);
        
    	/* TextFields for one found CSMCommand content info 
		 * See: https://docs.oracle.com/javase/tutorial/uiswing/components/editorpane.html
		 **/
        //NOTE: DO NOT CHANGE, because these strings are used also as dataDisplayMap keys
		String textFieldKey11 = "idcode"; //"TextField11";
		String textFieldKey12 = "index"; //"TextField12";
		String textFieldKey13 = "commandtype"; //"TextField13";
		String textFieldKey14 = "stepnote"; //"TextField14";
		String textFieldKey15 = "comment"; //"TextField15";
		String textFieldKey16 = "bodyobjectkey"; //"TextField16";
		
		//Create a regular text field.
        textField11 = new JTextField(10); //index
        textField11.setActionCommand(textFieldKey11);
        //textField11.addActionListener(this);
        textField12 = new JTextField(10); //idcode
        textField12.setActionCommand(textFieldKey12);
        //textField12.addActionListener(this);
        textField13 = new JTextField(10); //commandtype
        textField13.setActionCommand(textFieldKey13);
        //textField13.addActionListener(this);
        textField14 = new JTextField(10); //index
        textField14.setActionCommand(textFieldKey14);
        //textField14.addActionListener(this);
        textField15 = new JTextField(10); //idcode
        textField15.setActionCommand(textFieldKey15);
        //textField15.addActionListener(this);
        textField16 = new JTextField(10); //commandtype
        textField16.setActionCommand(textFieldKey16);
        //textField16.addActionListener(this);
        
        dataDisplayMap = new HashMap<String,JTextField>();
        dataDisplayMap.put(textFieldKey11, textField11);
        dataDisplayMap.put(textFieldKey12, textField12);
        dataDisplayMap.put(textFieldKey13, textField13);
        dataDisplayMap.put(textFieldKey14, textField14);
        dataDisplayMap.put(textFieldKey15, textField15);
        dataDisplayMap.put(textFieldKey16, textField16);
        //dataDisplayMap.put("idxsequence", idxsequencetext);
        //NOTE:JTextfield idxsequencetext added to this map later below
        
        //Create some labels for the fields.
        JLabel textFieldLabel11 = new JLabel(textFieldKey11 + ": ");
        textFieldLabel1.setLabelFor(textField11);
        JLabel textFieldLabel12 = new JLabel(textFieldKey12 + ": ");
        textFieldLabel2.setLabelFor(textField12);
        JLabel textFieldLabel13 = new JLabel(textFieldKey13 + ": ");
        textFieldLabel3.setLabelFor(textField13);
        JLabel textFieldLabel14 = new JLabel(textFieldKey14 + ": ");
        textFieldLabel1.setLabelFor(textField14);
        JLabel textFieldLabel15 = new JLabel(textFieldKey15 + ": ");
        textFieldLabel2.setLabelFor(textField15);
        JLabel textFieldLabel16 = new JLabel(textFieldKey16 + ": ");
        textFieldLabel3.setLabelFor(textField16);
        
      //Layout the search panel and the labels.
        JPanel textCSMCommandPanel = new JPanel();
        //GridBagLayout gridbag = new GridBagLayout();
        //GridBagConstraints c = new GridBagConstraints();
 
        textCSMCommandPanel.setLayout(gridbag);
 
        JLabel[] comlabels = {textFieldLabel11, textFieldLabel12, textFieldLabel13, textFieldLabel14, textFieldLabel15, textFieldLabel16};
        JTextField[] comtextFields = {textField11, textField12, textField13, textField14, textField15, textField16};
        addLabelTextRows(comlabels, comtextFields, gridbag, textCSMCommandPanel);
        
        textCSMCommandPanel.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("CSMCommand"), 
                                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		//MY ADD
        GridBagConstraints gbc_textCSMCommandPanel = new GridBagConstraints();
        gbc_textCSMCommandPanel.insets = new Insets(0, 0, 0, 0); //(0, 0, 5, 0);
        gbc_textCSMCommandPanel.fill = GridBagConstraints.BOTH;
        gbc_textCSMCommandPanel.gridx = 1;
        gbc_textCSMCommandPanel.gridy = 2;
		
        rightTopRightPanel.add(textCSMCommandPanel, gbc_textCSMCommandPanel);
        
        
        //Create a JTextArea for CSMCommand body object.
        oneJsonCommandTextArea = new JTextArea(
                "This is an editable JTextArea. " +
                "A text area is a \"plain\" text component, " +
                "which means that although it can display text " +
                "in any font, all of the text is in the same font."
        );
        //oneJsonCommandTextArea.setFont(new Font("Serif", Font.ITALIC, 16));
        oneJsonCommandTextArea.setLineWrap(true);
        oneJsonCommandTextArea.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(oneJsonCommandTextArea);
        areaScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 300));
        /* TEMP
        areaScrollPane.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("CSMCommand Body"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                areaScrollPane.getBorder()));
         TEMP */
      //MY ADD
        GridBagConstraints gbc_areaScrollPane = new GridBagConstraints();
        gbc_areaScrollPane.insets = new Insets(0, 0, 0, 0);
        gbc_areaScrollPane.fill = GridBagConstraints.BOTH;
        gbc_areaScrollPane.gridx = 1;
        gbc_areaScrollPane.gridy = 3;
		
        rightTopRightPanel.add(areaScrollPane, gbc_areaScrollPane);
       
        btnUpdateCSMCommandButton = new JButton("Update");
        btnUpdateCSMCommandButton.setEnabled(false);
        btnUpdateCSMCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				System.out.println("--- CSMCommand UPDATE BUTTON PRESSED......");
				Map<String,String> fieldKeyDataMap = new HashMap<String,String>();
				
				fieldKeyDataMap.put("idcode", dataDisplayMap.get("idcode").getText()); //fieldKeyDataMap.get("idcode"));
				fieldKeyDataMap.put("index", dataDisplayMap.get("index").getText()); //.setText(fieldKeyDataMap.get("index"));
				fieldKeyDataMap.put("commandtype", dataDisplayMap.get("commandtype").getText()); //.setText(fieldKeyDataMap.get("commandtype"));
				fieldKeyDataMap.put("stepnote", dataDisplayMap.get("stepnote").getText()); //.setText(fieldKeyDataMap.get("stepnote"));
				fieldKeyDataMap.put("comment", dataDisplayMap.get("comment").getText()); //.setText(fieldKeyDataMap.get("comment"));
				
				fieldKeyDataMap.put("bodyobjectkey", dataDisplayMap.get("bodyobjectkey").getText()); //.setText(fieldKeyDataMap.get("bodyobjectkey"));
				String bodyobject = dataDisplayMap.get("bodyobjectkey").getText();
				fieldKeyDataMap.put(bodyobject, oneJsonCommandTextArea.getText());
				
				//TODO: Update indexes sequence in CSMHeader 
				fieldKeyDataMap.put("idxsequence", dataDisplayMap.get("idxsequence").getText());
										
				appControl.updateCSMCommandJsonObject(fieldKeyDataMap );
				//Printing updated json content to SpinCommands tab
				StringBuffer sbuf = appControl.getCSMCommandContent(true);
				//-- File Printing
				txtrSpinCommandFileOutput.setText(null); // CLear old text
				txtrSpinCommandFileOutput.append(sbuf.toString() + newline);
				txtrSpinCommandFileOutput.setCaretPosition(0);

				bottomRightTabbedPane.setSelectedIndex(2);
				
			}
		});
        
        /* Update button moved to sequencepanel
        GridBagConstraints gbc_UpdateCSMCommandButton = new GridBagConstraints();
        //gbc_UpdateCSMCommandButton.insets = new Insets(0, 0, 5, 0);
        gbc_UpdateCSMCommandButton.fill = GridBagConstraints.WEST;
        gbc_UpdateCSMCommandButton.gridx = 1;
        gbc_UpdateCSMCommandButton.gridy = 4;
        rightTopRightPanel.add(btnUpdateCSMCommandButton, gbc_UpdateCSMCommandButton);     
        */
        JPanel sequencepanel = new JPanel();
        sequencepanel.setLayout(gridbag);
        
        btnSequenceRunButton = new JButton("Run");
        btnSequenceRunButton.setEnabled(false);
        btnSequenceRunButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				System.out.println("--- RUN BUTTON PRESSED...");
				//First UPDATE indexes sequence in CSMHeader (map key: "idxsequence")
				Map<String,String> fieldKeyDataMap = new HashMap<String,String>();
				fieldKeyDataMap.put("idxsequence", dataDisplayMap.get("idxsequence").getText());									
				appControl.updateCSMHeaderJsonObject(fieldKeyDataMap );
				// RUN CSMCommands
				StringBuffer resultbuf = appControl.invokeCSMCommandWorkflow();			
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: CSM (SPIN) COMMANDS INVOKED");
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				//-- Result Printing
				txtrResultOutput.setText(null); // CLear old text
				txtrResultOutput.append(resultbuf.toString() + newline);
				txtrResultOutput.setCaretPosition(0); //txtrResultOutput.getText().length());
				
				bottomRightTabbedPane.setSelectedIndex(1);
				
			}
			});
        
		idxsequencetext = new JTextField(10);
		dataDisplayMap.put("idxsequence", idxsequencetext);
		
	    GridBagConstraints gbc_sequencecomp = new GridBagConstraints();
	    gbc_sequencecomp.anchor = GridBagConstraints.EAST;
	    gbc_sequencecomp.insets = new Insets(0, 5, 0, 0);
		
	    gbc_sequencecomp.gridwidth = GridBagConstraints.EAST; // next-to-last
	    gbc_sequencecomp.fill = GridBagConstraints.NONE; // reset to default
	    gbc_sequencecomp.weightx = 0.0; // reset to default
	    
	    sequencepanel.add(btnUpdateCSMCommandButton, gbc_sequencecomp);
	    gbc_sequencecomp.gridwidth = GridBagConstraints.RELATIVE; // next-to-last
	    sequencepanel.add(btnSequenceRunButton, gbc_sequencecomp);

		gbc_sequencecomp.gridwidth = GridBagConstraints.REMAINDER; // end row
		gbc_sequencecomp.fill = GridBagConstraints.HORIZONTAL;
		gbc_sequencecomp.weightx = 1.0;
		sequencepanel.add(idxsequencetext, gbc_sequencecomp);
	       
		GridBagConstraints gbc_sequencepanel = new GridBagConstraints();
		gbc_sequencepanel.insets = new Insets(0, 0, 0, 0); // (0, 0, 5, 0);
		gbc_sequencepanel.fill = GridBagConstraints.BOTH;
		gbc_sequencepanel.gridx = 1;
		gbc_sequencepanel.gridy = 4;

		rightTopRightPanel.add(sequencepanel, gbc_sequencepanel);    
	        
		/* ================================= 
		 * 			Right Bottom side 
		 * ================================== */
        
		/* ==== Right bottom_right_panel ==== */
		JPanel bottom_right_panel = new JPanel();
		rightVerticalSplitPane.setRightComponent(bottom_right_panel);
		GridBagLayout gbl_bottom_right_panel = new GridBagLayout();
		gbl_bottom_right_panel.columnWidths = new int[] {10, 200, 10, 0};
		gbl_bottom_right_panel.rowHeights = new int[] {5, 20, 5, 5};
		gbl_bottom_right_panel.columnWeights = new double[]{0.0, 1.0, 0.1, Double.MIN_VALUE};
		gbl_bottom_right_panel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		bottom_right_panel.setLayout(gbl_bottom_right_panel);
		
		/* -- New JTabbedPane for bottom_right_panel */

		bottomRightTabbedPane = new JTabbedPane();
		GridBagConstraints gbc_bottomRightTabbedPane = new GridBagConstraints();
		gbc_bottomRightTabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_bottomRightTabbedPane.fill = GridBagConstraints.BOTH;
		gbc_bottomRightTabbedPane.gridx = 1;
		gbc_bottomRightTabbedPane.gridy = 1;
		
		// The following line enables to use scrolling tabs.
		bottomRightTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		bottom_right_panel.add(bottomRightTabbedPane, gbc_bottomRightTabbedPane);
		
		consoleScrollPane = new JScrollPane();
		bottomRightTabbedPane.insertTab("Console", null, consoleScrollPane, "Console", 0);
		
		txtrConsoleOutput = new JTextArea();
		txtrConsoleOutput.setRows(20);
		txtrConsoleOutput.setColumns(400);
		txtrConsoleOutput.setText("--- CONSOLE LOG ---");
		consoleScrollPane.setViewportView(txtrConsoleOutput);
		
		resultScrollPane = new JScrollPane();
		bottomRightTabbedPane.insertTab("Result", null, resultScrollPane, "Result", 1);
		
		txtrResultOutput = new JTextArea();
		txtrResultOutput.setRows(1000);
		txtrResultOutput.setColumns(600);
		txtrResultOutput.setLineWrap(true);
		txtrResultOutput.setText(""); //"--- RESULTS ---"
		resultScrollPane.setViewportView(txtrResultOutput);
		
		spinCommandFileScrollPane = new JScrollPane();
		bottomRightTabbedPane.insertTab("SpinCommands", null, spinCommandFileScrollPane, "SpinCommands", 2);
		
		txtrSpinCommandFileOutput = new JTextArea();
		txtrSpinCommandFileOutput.setRows(1000);
		txtrSpinCommandFileOutput.setColumns(600);
		txtrSpinCommandFileOutput.setLineWrap(true);
		txtrSpinCommandFileOutput.setText(""); 
		spinCommandFileScrollPane.setViewportView(txtrSpinCommandFileOutput);
		
		
	}

	public String getEraProjectHomeDirectory() {
		return eraProjectHomeDirectory;
	}

	public void setEraProjectHomeDirectory(String eraProjectHomeDirectory) {
		this.eraProjectHomeDirectory = eraProjectHomeDirectory;
	}

	public JScrollPane getHierarchyTreeScrollPane() {
		// For InternalElements hierarchy
		return hierarchyTreeScrollPane;
	}

	public JScrollPane getHierarchyTreeScrollPane2() {
		// For SystemUnitClassLib hierarchy
		return hierarchyTreeScrollPane2;
	}

	
	public JScrollPane getHierarchyTreeScrollPane3() {
		return hierarchyTreeScrollPane3;
	}

	public JScrollPane getHierarchyTreeScrollPane4() {
		return hierarchyTreeScrollPane4;
	}

	/*
	 * actionPerformed() method for Menu and Button actions
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */


	@Override
	public void actionPerformed(ActionEvent arg0) {
		/*
		 * For File Open and Save action dialogs AND For mntmGenerateJmonkey AND
		 * for mntmLoadRules AND mntmInvokeReasoner AND mntmSaveResultModels AND
		 * mntmCaexToAsp AND mntmConfigureSchema AND mntmSetTransformContext AND
		 * mntmInvokeTransform AND mntmGenOntologyModel AND mntmNewProject AND
		 * mntmSaveProjectAs AND mntmSaveProject AND mntmOpenProject AND
		 * mntmAspSolver AND mntmSaveOntologyModel
		 * rbMenuItem1-6 AND
		 * mntmMergeModels AND mntmLoadSpinCommands
		 * mntmInvokeSpinCommands
		 */
		
		if (arg0.getSource() == mntmInvokeSpinCommands) {
			
			StringBuffer resultbuf = appControl.invokeCSMCommandWorkflow();			
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: CSM (SPIN) COMMANDS INVOKED");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			//-- Result Printing
			txtrResultOutput.setText(null); // CLear old text
			txtrResultOutput.append(resultbuf.toString() + newline);
			txtrResultOutput.setCaretPosition(0); //txtrResultOutput.getText().length());
			bottomRightTabbedPane.setSelectedIndex(1);
			
		} else if (arg0.getSource() == mntmLoadSpinCommands) {
			fileChooser.setDialogTitle("LOAD SPIN COMMAND FILE");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);			
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				File selectedfile = fileChooser.getSelectedFile();
				System.out.println("-- Selected file: " + selectedfile.getPath());
				StringBuffer sbuf = appControl.initCommandFileSpinMng(selectedfile.getPath());
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: SPIN COMMAND FILE: " + selectedfile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				//-- File Printing
				txtrSpinCommandFileOutput.setText(null); // CLear old text
				txtrSpinCommandFileOutput.append(sbuf.toString() + newline);
				txtrSpinCommandFileOutput.setCaretPosition(0);
				btnSearchCommandButton.setEnabled(true);
				bottomRightTabbedPane.setSelectedIndex(2);
			} else {
				System.out.println("Frame: No SPIN COMMAND File Selected!");
			}
			fileChooser.setSelectedFile(new File(""));
			
		} else if (arg0.getSource() == mntmMergeModels) {
			
			appControl.mergeExistingRDFModels();
			
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: MERGE EXISTING ONTOLOGY MODELS");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			//-- Result Printing
			String serialized = appControl.getSerializeRdfModel(null); // if null, use default format
			txtrResultOutput.setText(null); // CLear old text
			txtrResultOutput.append(serialized + newline);
			txtrResultOutput.setCaretPosition(txtrResultOutput.getText().length());
			
		} else if ((arg0.getSource() == rbMenuItem1)||(arg0.getSource() == rbMenuItem2)||(arg0.getSource() == rbMenuItem3)||(arg0.getSource() == rbMenuItem4)||(arg0.getSource() == rbMenuItem5)||(arg0.getSource() == rbMenuItem6)) {
			String radiocommand =arg0.getActionCommand();
			appControl.genereteCaexOntologyModel(radiocommand);
			rbMenuItem1.setSelected(false);
			rbMenuItem2.setSelected(false);
			rbMenuItem3.setSelected(false);
			rbMenuItem4.setSelected(false);
			rbMenuItem5.setSelected(false);
			rbMenuItem6.setSelected(true);
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: SELECTED:" + radiocommand);
			txtrConsoleOutput.append(newline + "LOG: ONTOLOGY MODEL GENERATED FROM THE MAIN CAEX MODEL! ");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			//-- Result Printing
			String serialized = appControl.getSerializeRdfModel(null); // if null, use default format
			txtrResultOutput.setText(null); // CLear old text
			txtrResultOutput.append(serialized + newline);
			txtrResultOutput.setCaretPosition(txtrResultOutput.getText().length());
			bottomRightTabbedPane.setEnabledAt(1, true);
			
		} if (arg0.getSource() == mntmSaveOntologyModel) {
			fileChooser.setDialogTitle("SAVE CAEX SOURCE ONTOLOGY MODEL TO FILE (.ttl, .owl)");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			

			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File saveFile = fileChooser.getSelectedFile();
				appControl.saveCaexOntologyModel(saveFile.getPath());
				System.out.println("-- Saved file: " + saveFile.getName());
				String dir = saveFile.getParent();
				this.latestOpenedFolder = "dir";
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: CAEX SOURCE ONTOLOGY MODEL SAVED INTO FILE: " + saveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Save File Selected!");
			}

		} else if (arg0.getSource() == mntmAspSolver) {
			fileChooser.setDialogTitle("SELECT ASP SOLVER EXE (dlv.mingw.exe)");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);			
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				File solverExefile = fileChooser.getSelectedFile();
				System.out.println("-- Selected file: " + solverExefile.getPath());
				appControl.setAspSolverEngine(solverExefile.getPath());
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: ASP SOLVER EXE: " + solverExefile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

			} else {
				System.out.println("Frame: No ASP Solver Exe File Selected!");
			}
			fileChooser.setSelectedFile(new File(""));
			
		} else if (arg0.getSource() == mntmOpenProject) {
			fileChooser.setDialogTitle("OPEN ERA PROJECT");
			fileChooser.setCurrentDirectory((new File(this.eraProjectHomeDirectory)).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);
			
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				File openProjectDirectory = fileChooser.getSelectedFile();
				appControl.openProjectInFolder(openProjectDirectory.getPath());
				this.eraProjectHomeDirectory = openProjectDirectory.getPath();
				this.latestOpenedFolder = openProjectDirectory.getPath();
				System.out.println("-- Project Folder Opened: " + openProjectDirectory.getName());
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: ERA PROJECT OPENED: " + openProjectDirectory.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Project Folder Selected!");
			}
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			
		} else if (arg0.getSource() == mntmSaveProject) {
			
			appControl.saveProject();
			System.out.println("Frame: Current Project Saved!");
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: CURRENT ERA PROJECT SAVED!");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			
		} else if (arg0.getSource() == mntmSaveProjectAs) {
			fileChooser.setDialogTitle("SAVE PROJECT AS (create a new folder)");
			fileChooser.setCurrentDirectory((new File(this.eraProjectHomeDirectory)).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File newProjectDirectory = fileChooser.getSelectedFile();
				appControl.saveProjectInFolder(newProjectDirectory.getPath());
				this.eraProjectHomeDirectory = newProjectDirectory.getPath();
				this.latestOpenedFolder = newProjectDirectory.getPath();
				System.out.println("-- New Project Folder: " + newProjectDirectory.getName());
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: CURRENT ERA PROJECT SAVED AS: " +newProjectDirectory.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Project Folder Selected!");
			}
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		} else if (arg0.getSource() == mntmNewProject) {
			appControl.clearProject();
			this.getHierarchyTreeScrollPane().setViewportView(null);
			this.getHierarchyTreeScrollPane2().setViewportView(null);
			this.getHierarchyTreeScrollPane3().setViewportView(null);
			this.getHierarchyTreeScrollPane4().setViewportView(null);			
			this.bottomLeftTextArea.setText("Selected Element:\n");		
			System.out.println("-- createNewProject(); Element Tree Cleared! ");
			
			fileChooser.setDialogTitle("CREATE A NEW PROJECT INTO (create a new folder)");
			fileChooser.setCurrentDirectory((new File(this.eraProjectHomeDirectory)).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File newProjectDirectory = fileChooser.getSelectedFile();
				appControl.saveProjectInFolder(newProjectDirectory.getPath());
				this.eraProjectHomeDirectory = newProjectDirectory.getPath();
				this.latestOpenedFolder = newProjectDirectory.getPath();
				System.out.println("-- New Project Home Directory: " + newProjectDirectory.getName());
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: NEW ERA PROJECT HOME DIRECTORY: " +newProjectDirectory.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Project Folder Selected!");
			}
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
		} else if (arg0.getSource() == mntmGenOntologyModel) {
			appControl.genereteCaexOntologyModel("default");;
			System.out.println("-- genereteCaexOntologyModel();! ");
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: ONTOLOGY MODEL GENERATED FROM THE MAIN CAEX MODEL! ");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			//-- Result Printing
			String serialized = appControl.getSerializeRdfModel(null); // if null, use default format
			txtrResultOutput.setText(null); // CLear old text
			txtrResultOutput.append(serialized + newline);
			txtrResultOutput.setCaretPosition(txtrResultOutput.getText().length());
			bottomRightTabbedPane.setEnabledAt(1, true);
	
		} else if (arg0.getSource() == mntmInvokeTransform) {
				appControl.invokeXslContextTransform();
				System.out.println("-- invokeXslContextTransform()! ");
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: XSL TRANSFORM INVOKED!");
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
		
		} else if (arg0.getSource() == mntmSetTransformContext) {
			fileChooser.setDialogTitle("SELECT XSL TRANSFORM CONTEXT FILES (xsl,xml,trg)");
			
			if(!".".equals(eraProjectHomeDirectory))
				fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory));
			else fileChooser.setCurrentDirectory(new File(this.latestOpenedFolder));
			
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setMultiSelectionEnabled(true);
			fileChooser.setSelectedFiles(new File[]{new File("")});
			
			int retVal = fileChooser.showOpenDialog(MainFrame.this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				File[] files = fileChooser.getSelectedFiles();
				System.out.println("-- TRANSFORM CONTEXT files selected: # " + files.length);
				this.appControl.initXslContext(files);
				String dir = files[0].getParent();
				System.out.println("-- TRANSFORM CONTEXT parent folder: " + dir);
				this.latestOpenedFolder = "dir";
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: XSL TRANSFORM CONTEXT FILES SELECTED: (DIR: " + dir + ")");
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

			} else {
				System.out.println("Frame: No Xsl context files selected!");
			}
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setMultiSelectionEnabled(false);

		} else 	if (arg0.getSource() == mntmConfigureSchema) {
			fileChooser.setDialogTitle("SELECT XML VALIDATIN SCHEMA FILE");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/configure"));
			
			int retVal = fileChooser.showOpenDialog(MainFrame.this);			
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				//mainOpenFile = fileChooser.getSelectedFile();
				File schemafile = fileChooser.getSelectedFile();
				System.out.println("-- Opened file: " + schemafile.getPath());
				appControl.setValidationSchema(schemafile.getPath());
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: CAEX VALIDATIN SCHEMA SELECTED:" + schemafile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

			} else {
				System.out.println("Frame: No Schema File Selected!");
			}
			fileChooser.setSelectedFile(new File(""));
			
		} else if (arg0.getSource() == mntmCaexToAsp) {
			fileChooser.setDialogTitle("GENERATE ASP FACTS FROM CAEX AND SAVE IT TO FILE:");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.latestOpenedFolder)); //"./data/genereted"));
			
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();

				appControl.xslTransform("caex2aspfacts", null, mainSaveFile.getPath());
				System.out.println("-- Asp facts file (.db): " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = "dir";
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: GENERATED ASP FACTS FROM CAEX SAVED INTO FILE: " + mainSaveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No File Defined!");
			}

		} else if (arg0.getSource() == mntmSaveResultModels) {
			fileChooser.setDialogTitle("SAVE ASP SOLVER RESULT MODELS TO FILE:");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			

			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();
				appControl.saveAspModel(mainSaveFile.getPath());
				System.out.println("-- Saved file: " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = "dir";
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: ASP SOLVER RESULT MODELS SAVED TO FILE:: " +mainSaveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Save File Selected!");
			}

		} else if (arg0.getSource() == mntmInvokeReasoner) {

				StringBuffer strmodels = appControl.invokeAspReasoner();
				System.out.println("-- invokeAspReasoner! ");			
				txtrResultOutput.setText(null); // CLear old text
				if(strmodels!=null)
					txtrResultOutput.append(strmodels.toString() + newline);
				else txtrResultOutput.append("ASP RESULT MODELS: null");
				txtrResultOutput.setCaretPosition(txtrResultOutput.getText().length());
				bottomRightTabbedPane.setEnabledAt(1, true);

		} else if (arg0.getSource() == mntmLoadRules) {
			fileChooser.setDialogTitle("LOAD ASP RULE AND FACT FILES");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/configure")); //"./configure/asp_dlv"));
			fileChooser.setMultiSelectionEnabled(true);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				File[] aspfiles = fileChooser.getSelectedFiles();
				System.out.println("-- Asp files selected: # " + aspfiles.length);
				this.appControl.initAspModel(aspfiles);
				String dir = aspfiles[0].getParent();
				this.latestOpenedFolder = "dir";
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: ASP RULE AND FACT FILES LOADED: " + dir);
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			
			} else {
				System.out.println("Frame: No Asp files selected!");
			}
			fileChooser.setSelectedFiles(new File[]{new File("")});
			fileChooser.setMultiSelectionEnabled(false);

		} else if (arg0.getSource() == mntmGenerateJmonkey) {
			fileChooser.setDialogTitle("GENERATE AND SAVE JMONKEY SCRIPT TO FILE:");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data")); //"./data/genereted"));
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();

				appControl.xslTransform("caex2jmonkey", null, mainSaveFile.getPath());
				System.out.println("-- JMonkey file (.jmc): " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = "dir";
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: GENERATED JMONKEY SCRIPT SAVED TO FILE: " +  mainSaveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				
			} else {
				System.out.println("Frame: No File Defined!");
			}

		} else if (arg0.getSource() == mntmOpen) {
			fileChooser.setDialogTitle("OPEN CAEX XML FILE");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			int retVal = fileChooser.showOpenDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				mainOpenFile = fileChooser.getSelectedFile();
				System.out.println("-- Opened file: " + mainOpenFile.getPath());
				// InternalElements hierarchy
				JTree elementTree = appControl.buildJaxbModel(mainOpenFile.getPath());
				if (elementTree != null)
					this.getHierarchyTreeScrollPane().setViewportView(elementTree);
				// SystemUnitClassLib hierarchy
				JTree systemUCLibTree = appControl.getSucltree();
				if (systemUCLibTree != null)
					this.getHierarchyTreeScrollPane2().setViewportView(systemUCLibTree);
				// RoleClassLib hierarchy
				JTree roleCLibTree = appControl.getRolecltree();
				if (roleCLibTree != null)
					this.getHierarchyTreeScrollPane3().setViewportView(roleCLibTree);
				// InterfaceClassLib hierarchy
				JTree interfaceCLibTree = appControl.getInterfacecltree();
				if (interfaceCLibTree != null)
					this.getHierarchyTreeScrollPane4().setViewportView(interfaceCLibTree);
				String dir = mainOpenFile.getParent();
				this.latestOpenedFolder = "dir";
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: CAEX FILE OPENED: " + mainOpenFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

			} else {
				System.out.println("Frame: No Open File Selected!");
			}

		} else if (arg0.getSource() == mntmSave) {
			fileChooser.setDialogTitle("SAVE CAEX FILE:");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();
				// Write/marshal jaxb model to xml file
				appControl.saveXMLModel(mainSaveFile.getPath());
				System.out.println("-- Saved file: " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = "dir";
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: THE MAIN CAEX FILE MARSHALLED INTO FILE: " +  mainSaveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Save File Selected!");
			}

		}

	}

	public JTextArea getBottomLeftTextArea() {
		return bottomLeftTextArea;
	}

	private void addLabelTextRows(JLabel[] labels, JTextField[] textFields, GridBagLayout gridbag,
			Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = labels.length;

		for (int i = 0; i < numLabels; i++) {
			c.gridwidth = GridBagConstraints.RELATIVE; // next-to-last
			c.fill = GridBagConstraints.NONE; // reset to default
			c.weightx = 0.0; // reset to default
			container.add(labels[i], c);

			c.gridwidth = GridBagConstraints.REMAINDER; // end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			container.add(textFields[i], c);
		}
	}
	/*
	 * Required by TreeSelectionListener interface. See Oracle:TreeDemo
	 * 
	 * public void valueChanged(TreeSelectionEvent e) {
	 * System.out.println("Frame: TreeSelectionEvent:" + e.toString());
	 * //ElementModel node = (ElementModel)tree.getLastSelectedPathComponent();
	 * Object node = tree.getLastSelectedPathComponent(); if (node == null){
	 * System.out.println("Frame: TreeSelectionEvent NODE: " + node); return; }
	 * System.out.println("Frame: TreeSelectionEvent NODE" + node.toString());
	 * //Object nodeInfo = node.getUserObject();
	 * 
	 * }
	 **/

}

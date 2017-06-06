package siima.app.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class MainFrame extends JFrame implements ActionListener { // TreeSelectionListener
																	// {
	public String eraProjectHomeDirectory = ".";
	public String latestOpenedFolder = ".";
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
	private JMenuItem mntmGenerateJmonkey;
	private JTextArea bottomLeftTextArea;
	private JMenuItem mntmLoadRules;
	private JMenuItem mntmInvokeReasoner;
	private JMenuItem mntmSaveResultModels;
	private JMenuItem mntmCaexToAsp;
	private JTabbedPane tabbedPane;
	private JMenuItem mntmConfigureSchema;
	private JMenuItem mntmExit;
	private JMenuItem mntmInvokeTransform;
	private JMenuItem mntmSetTransformContext;
	private JMenuItem mntmGenOntologyModel;
	private JMenuItem mntmNewProject;
	private JMenuItem mntmSaveProjectAs;
	private JMenuItem mntmSaveProject;
	private JMenuItem mntmOpenProject;
	private JMenuItem mntmAspSolver; //TODO
	// private JTree tree;

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
		
		JMenu mnOntology = new JMenu("Ontology");
		menuBar.add(mnOntology);

		mntmGenOntologyModel = new JMenuItem("Generate Ontology Model");
		mntmGenOntologyModel.addActionListener(this);
		mnOntology.add(mntmGenOntologyModel);
				

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

		JPanel panel = new JPanel();
		rightVerticalSplitPane.setLeftComponent(panel);

		JPanel panel_1 = new JPanel();
		rightVerticalSplitPane.setRightComponent(panel_1);
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



	@Override
	public void actionPerformed(ActionEvent arg0) {
		/*
		 * For File Open and Save action dialogs AND For mntmGenerateJmonkey AND
		 * for mntmLoadRules AND mntmInvokeReasoner AND mntmSaveResultModels AND
		 * mntmCaexToAsp AND mntmConfigureSchema AND mntmSetTransformContext AND
		 * mntmInvokeTransform AND mntmGenOntologyModel AND mntmNewProject AND
		 * mntmSaveProjectAs AND mntmSaveProject AND mntmOpenProject AND
		 * mntmAspSolver 
		 * 
		 */
		if (arg0.getSource() == mntmAspSolver) {
			fileChooser.setDialogTitle("SELECT ASP SOLVER EXE (dlv.mingw.exe)");
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setSelectedFile(null);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);			
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				File solverExefile = fileChooser.getSelectedFile();
				System.out.println("-- Selected file: " + solverExefile.getPath());
				appControl.setAspSolverEngine(solverExefile.getPath());

			} else {
				System.out.println("Frame: No ASP Solver Exe File Selected!");
			}
			fileChooser.setSelectedFile(null);
			
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
			} else {
				System.out.println("Frame: No Project Folder Selected!");
			}
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			
		} else if (arg0.getSource() == mntmSaveProject) {
			
			appControl.saveProject();
			System.out.println("Frame: Current Project Saved!");
			
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
			} else {
				System.out.println("Frame: No Project Folder Selected!");
			}
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
		} else if (arg0.getSource() == mntmGenOntologyModel) {
			appControl.genereteCaexOntologyModel();;
			System.out.println("-- genereteCaexOntologyModel();! ");
	
		} else if (arg0.getSource() == mntmInvokeTransform) {
				appControl.invokeXslContextTransform();
				System.out.println("-- invokeXslContextTransform()! ");
		
		} else if (arg0.getSource() == mntmSetTransformContext) {
			fileChooser.setDialogTitle("SELECT XSL TRANSFORM CONTEXT FILES (xsl,xml,trg)");
			
			if(!".".equals(eraProjectHomeDirectory))
				fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory));
			else fileChooser.setCurrentDirectory(new File(this.latestOpenedFolder));
			fileChooser.setSelectedFile(null);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setMultiSelectionEnabled(true);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				File[] files = fileChooser.getSelectedFiles();
				System.out.println("-- TRANSFORM CONTEXT files selected: # " + files.length);
				this.appControl.initXslContext(files);
				String dir = files[0].getParent();
				System.out.println("-- TRANSFORM CONTEXT parent folder: " + dir);
				this.latestOpenedFolder = "dir";

			} else {
				System.out.println("Frame: No Xsl context files selected!");
			}
			fileChooser.setSelectedFile(null);
			fileChooser.setMultiSelectionEnabled(false);

		} else 	if (arg0.getSource() == mntmConfigureSchema) {
			fileChooser.setDialogTitle("SELECT XML VALIDATIN SCHEMA FILE");
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/configure"));
			fileChooser.setSelectedFile(null);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);			
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				//mainOpenFile = fileChooser.getSelectedFile();
				File schemafile = fileChooser.getSelectedFile();
				System.out.println("-- Opened file: " + schemafile.getPath());
				appControl.setValidationSchema(schemafile.getPath());

			} else {
				System.out.println("Frame: No Schema File Selected!");
			}
			fileChooser.setSelectedFile(null);
			
		} else if (arg0.getSource() == mntmCaexToAsp) {
			fileChooser.setDialogTitle("GENERATE ASP FACTS FROM CAEX AND SAVE IT TO FILE:");
			fileChooser.setCurrentDirectory(new File(this.latestOpenedFolder)); //"./data/genereted"));
			fileChooser.setSelectedFile(null);
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();

				appControl.xslTransform("caex2aspfacts", null, mainSaveFile.getPath());
				System.out.println("-- Asp facts file (.db): " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = "dir";
			} else {
				System.out.println("Frame: No File Defined!");
			}

		} else if (arg0.getSource() == mntmSaveResultModels) {
			fileChooser.setDialogTitle("SAVE ASP SOLVER RESULT MODELS TO FILE:");
			fileChooser.setCurrentDirectory(new File("./data"));
			fileChooser.setSelectedFile(null);

			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();
				appControl.saveAspModel(mainSaveFile.getPath());
				System.out.println("-- Saved file: " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = "dir";
			} else {
				System.out.println("Frame: No Save File Selected!");
			}

		} else if (arg0.getSource() == mntmInvokeReasoner) {

			if (true) {
				appControl.invokeAspReasoner();
				System.out.println("-- invokeAspReasoner! ");
			} else {
				System.out.println("-- invokeAspReasoner: NO ??? ");

			}

		} else if (arg0.getSource() == mntmLoadRules) {
			fileChooser.setDialogTitle("LOAD ASP RULE AND FACT FILES");
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/configure")); //"./configure/asp_dlv"));
			fileChooser.setSelectedFile(null);
			fileChooser.setMultiSelectionEnabled(true);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				File[] aspfiles = fileChooser.getSelectedFiles();
				System.out.println("-- Asp files selected: # " + aspfiles.length);
				this.appControl.initAspModel(aspfiles);
				String dir = aspfiles[0].getParent();
				this.latestOpenedFolder = "dir";

			} else {
				System.out.println("Frame: No Asp files selected!");
			}
			fileChooser.setSelectedFile(null);
			fileChooser.setMultiSelectionEnabled(false);

		} else if (arg0.getSource() == mntmGenerateJmonkey) {
			fileChooser.setDialogTitle("GENERATE AND SAVE JMONKEY SCRIPT TO FILE:");
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data")); //"./data/genereted"));
			fileChooser.setSelectedFile(null);
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();

				appControl.xslTransform("caex2jmonkey", null, mainSaveFile.getPath());
				System.out.println("-- JMonkey file (.jmc): " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = "dir";
				
			} else {
				System.out.println("Frame: No File Defined!");
			}

		} else if (arg0.getSource() == mntmOpen) {
			fileChooser.setDialogTitle("OPEN CAEX XML FILE");
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			fileChooser.setSelectedFile(null);
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

			} else {
				System.out.println("Frame: No OPen File Selected!");
			}

		} else if (arg0.getSource() == mntmSave) {
			fileChooser.setDialogTitle("SAVE CAEX FILE:");
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			fileChooser.setSelectedFile(null);
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();
				// Write/marshal jaxb model to xml file
				appControl.saveXMLModel(mainSaveFile.getPath());
				System.out.println("-- Saved file: " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = "dir";
			} else {
				System.out.println("Frame: No Save File Selected!");
			}

		}

	}

	public JTextArea getBottomLeftTextArea() {
		return bottomLeftTextArea;
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
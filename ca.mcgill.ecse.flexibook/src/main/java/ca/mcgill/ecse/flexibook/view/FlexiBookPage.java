package ca.mcgill.ecse.flexibook.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Properties;
import java.net.URL;

import javax.swing.border.LineBorder;
import javax.swing.*;
import javax.imageio.ImageIO;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.model.Owner;
import ca.mcgill.ecse.flexibook.model.Service;

public class FlexiBookPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;

	//top bar for owner
	private JPanel topPanelOwner;
	//top bar for customer
	private JPanel topPanelCustomer;
	//panel for log in
	private JPanel logInPanel;
	//panel for each button on top bar
	private JPanel infoOwnerPanel;
	private JPanel infoCustomerPanel;
	
	private JPanel infoPanel; //temp
	
	private JPanel singleServicesPanel;
	private JPanel comboServicesPanel;
	private JPanel calendarOwnerPanel;
	private JPanel calendarCustomerPanel;
	private JPanel businessHoursPanel;
	private JPanel businessDetailsPanel;
	private JPanel logOutOwnerPanel;
	private JPanel logOutCustomerPanel;
	private JPanel bookAppointmentPanel;
	
	//top bar buttons
	private JButton infoOwnerButton;
	private JButton infoCustomerButton;
	private JButton singleServicesButton;
	private JButton comboServicesButton;
	private JButton calendarOwnerButton;
	private JButton calendarCustomerButton;
	private JButton businessHoursButton;
	private JButton businessDetailsButton;
	private JButton logOutOwnerButton;
	private JButton logOutCustomerButton;
	private JButton bookAppointmentButton;

	//log in page buttons
	private JButton logInOwnerButton;
	private JButton logInCustomerButton;

	//top bar icons
	private ImageIcon infoIconDark;
	private ImageIcon infoIconLight;
	private ImageIcon logOutIconDark;
	private ImageIcon logOutIconLight;

	//page labels

	private JLabel singleServicesLabel;
	private JLabel comboServicesLabel;
	private JLabel calendarLabel;
	private JLabel businessHoursLabel;
	private JLabel businessDetailsLabel;
	private JLabel logOutLabel;
	
	//Info panel view
	private JLabel infoLabel; //manage your account	
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameBox; 
	private JTextField passwordBox; 
	private JButton saveAccountInfo; //Save button
	private JLabel bookAppointmentLabel;


	//tracking last page
	private JButton previousButton;
	private JPanel previousPanel;

	//color of top bar
	private Color darkGrey = new Color(62,62,62);
	
	// Error message 
	private JLabel errorMessage;
	private String error = null;


	/** Creates new form FlexiBookPage */
	public FlexiBookPage() {
		initComponents();
		refreshData();
	}

	/** This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		
		// initialize error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		//initialize frame
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("FlexiBook");
		setPreferredSize(new Dimension(1100,640));
		setResizable(false);

		//initialize log in page
		initLogInPage();
		initTopBarOwner();
		initTopBarCustomer();

		//add log in page to the frame
		getContentPane().add(logInPanel);

		//refresh page
		pack();

	}

	//initialize log in page
	private void initLogInPage(){
		//initialize log in panel layout
		FlowLayout logInLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
		logInPanel = new JPanel();
		logInPanel.setLayout(logInLayout);
		logInPanel.setPreferredSize(new Dimension(1100,40));
		//initialize owner log in button
		logInOwnerButton = new JButton();
		logInOwnerButton.setText("Owner Log In");
		logInOwnerButton.setPreferredSize(new Dimension(200, 40));
		logInOwnerButton.setBorder(new LineBorder(darkGrey));
		logInOwnerButton.setBackground(Color.WHITE);
		logInOwnerButton.setOpaque(true);
		logInOwnerButton.setForeground(darkGrey);
		//initialize customer log in button
		logInCustomerButton = new JButton();
		logInCustomerButton.setText("Customer Log In");
		logInCustomerButton.setPreferredSize(new Dimension(200, 40));
		logInCustomerButton.setBorder(new LineBorder(darkGrey));
		logInCustomerButton.setBackground(Color.WHITE);
		logInCustomerButton.setOpaque(true);
		logInCustomerButton.setForeground(darkGrey);

		logInPanel.add(logInOwnerButton);
		logInPanel.add(logInCustomerButton);

		//initialize owner log in button listener
		logInOwnerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logInOwnerButtonActionPerformed(evt);
			}
		});

		//initialize customer log in button listener
		logInCustomerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logInCustomerButtonActionPerformed(evt);
			}
		});

	}

	//initialize top bar for owner
	private void initTopBarOwner(){
		//initialize top bar layout
		FlowLayout topLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
		topPanelOwner = new JPanel();
		topPanelOwner.setLayout(topLayout);
		topPanelOwner.setPreferredSize(new Dimension(1100,40));

		//initialize info panel
		initInfoOwnerPanel();

		//initialize single service panel
		initSingleServicesPanel();

		//initialize combo service panel
		initComboServicesPanel();

		//initialize calendar panel
		initCalendarOwnerPanel();

		//initialize business hour panel
		initBusinessHoursPanel();

		//initialize business details panel
		initBusinessDetailsPanel();

		//initialize log out panel
		initLogOutOwnerPanel();

		//initialize image icons
		try{
			infoIconDark = new ImageIcon(ImageIO.read(new URL("https://raw.githubusercontent.com/F2020-ECSE223/ecse223-group-project-p14/master/ca.mcgill.ecse.flexibook/src/main/java/infoIconDark.jpg?token=AHN6XYAHZPYQ3EVVJGPEYFS7YPOBW")));
			infoIconLight = new ImageIcon(ImageIO.read(new URL("https://raw.githubusercontent.com/F2020-ECSE223/ecse223-group-project-p14/master/ca.mcgill.ecse.flexibook/src/main/java/infoIconLight.jpg?token=AHN6XYGVK75VSGPSLW4HYY27YPOF4")));
			logOutIconDark = new ImageIcon(ImageIO.read(new URL("https://raw.githubusercontent.com/F2020-ECSE223/ecse223-group-project-p14/master/ca.mcgill.ecse.flexibook/src/main/java/logOutIconDark.jpg?token=AHN6XYFMB5RAZPE6ORTWDGK7YPOI2")));
			logOutIconLight = new ImageIcon(ImageIO.read(new URL("https://raw.githubusercontent.com/F2020-ECSE223/ecse223-group-project-p14/master/ca.mcgill.ecse.flexibook/src/main/java/logOutIconLight.jpg?token=AHN6XYA4BHIRTJSLSM2QN2C7YPOKI")));
		} catch(Exception exp) {
			error += exp.getMessage();
		}
		
		//initialize info button
		infoOwnerButton = new JButton();
		infoOwnerButton.setIcon(infoIconDark);
		infoOwnerButton.setPreferredSize(new Dimension(50, 40));
		infoOwnerButton.setBorder(new LineBorder(darkGrey));
		infoOwnerButton.setBackground(darkGrey);
		infoOwnerButton.setOpaque(true);
		infoOwnerButton.setForeground(Color.WHITE);

		//initialize single service button
		singleServicesButton = new JButton();
		singleServicesButton.setText("Single Services");
		singleServicesButton.setPreferredSize(new Dimension(200, 40));
		singleServicesButton.setBorder(new LineBorder(darkGrey));
		singleServicesButton.setBackground(darkGrey);
		singleServicesButton.setOpaque(true);
		singleServicesButton.setForeground(Color.WHITE);

		//initialize combo service button
		comboServicesButton = new JButton();
		comboServicesButton.setText("Combo Services");
		comboServicesButton.setPreferredSize(new Dimension(200, 40));
		comboServicesButton.setBorder(new LineBorder(darkGrey));
		comboServicesButton.setBackground(darkGrey);
		comboServicesButton.setOpaque(true);
		comboServicesButton.setForeground(Color.WHITE);

		//initialize calendar button
		calendarOwnerButton = new JButton();
		calendarOwnerButton.setText("Calendar");
		calendarOwnerButton.setPreferredSize(new Dimension(200, 40));
		calendarOwnerButton.setBorder(new LineBorder(Color.WHITE));
		calendarOwnerButton.setBackground(Color.WHITE);
		calendarOwnerButton.setOpaque(true);
		calendarOwnerButton.setForeground(darkGrey);

		//initialize business hours button
		businessHoursButton = new JButton();
		businessHoursButton.setText("Business Hours");
		businessHoursButton.setPreferredSize(new Dimension(200, 40));
		businessHoursButton.setBorder(new LineBorder(darkGrey));
		businessHoursButton.setBackground(darkGrey);
		businessHoursButton.setOpaque(true);
		businessHoursButton.setForeground(Color.WHITE);

		//initialize business details button
		businessDetailsButton = new JButton();
		businessDetailsButton.setText("Business Details");
		businessDetailsButton.setPreferredSize(new Dimension(200, 40));
		businessDetailsButton.setBorder(new LineBorder(darkGrey));
		businessDetailsButton.setBackground(darkGrey);
		businessDetailsButton.setOpaque(true);
		businessDetailsButton.setForeground(Color.WHITE);

		//initialize log out button
		logOutOwnerButton = new JButton();
		logOutOwnerButton.setIcon(logOutIconDark);
		logOutOwnerButton.setPreferredSize(new Dimension(50, 40));
		logOutOwnerButton.setBorder(new LineBorder(darkGrey));
		logOutOwnerButton.setBackground(darkGrey);
		logOutOwnerButton.setOpaque(true);
		logOutOwnerButton.setForeground(Color.WHITE);

		//add buttons to top bar
		topPanelOwner.add(infoOwnerButton);
		topPanelOwner.add(singleServicesButton);
		topPanelOwner.add(comboServicesButton);
		topPanelOwner.add(calendarOwnerButton);
		topPanelOwner.add(businessHoursButton);
		topPanelOwner.add(businessDetailsButton);
		topPanelOwner.add(logOutOwnerButton);

		//initialize info button listener
		infoOwnerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				infoOwnerButtonActionPerformed(evt);
			}
		});

		//initialize single service button listener
		singleServicesButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				singleServicesButtonActionPerformed(evt);
			}
		});

		//initialize combo service button listener
		comboServicesButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				comboServicesButtonActionPerformed(evt);
			}
		});

		//initialize calendar button listener
		calendarOwnerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				calendarOwnerButtonActionPerformed(evt);
			}
		});

		//initialize business hours button listener
		businessHoursButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				businessHoursButtonActionPerformed(evt);
			}
		});

		//initialize business details button listener
		businessDetailsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				businessDetailsButtonActionPerformed(evt);
			}
		});

		//initialize log out button listener
		logOutOwnerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logOutOwnerButtonActionPerformed(evt);
			}
		});

	}

	//initialize top bar for customer
	private void initTopBarCustomer(){
		//initialize top bar layout
		FlowLayout topLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
		topPanelCustomer = new JPanel();
		topPanelCustomer.setLayout(topLayout);
		topPanelCustomer.setPreferredSize(new Dimension(1100,40));

		//initialize info panel
		initInfoCustomerPanel();

		//initialize calendar panel
		initCalendarCustomerPanel();

		//initialize business details panel
		initBookAppointmentPanel();

		//initialize log out panel
		initLogOutCustomerPanel();

		//initialize image icons
		try{
			infoIconDark = new ImageIcon(ImageIO.read(new URL("https://raw.githubusercontent.com/F2020-ECSE223/ecse223-group-project-p14/master/ca.mcgill.ecse.flexibook/src/main/java/infoIconDark.jpg?token=AHN6XYAHZPYQ3EVVJGPEYFS7YPOBW")));
			infoIconLight = new ImageIcon(ImageIO.read(new URL("https://raw.githubusercontent.com/F2020-ECSE223/ecse223-group-project-p14/master/ca.mcgill.ecse.flexibook/src/main/java/infoIconLight.jpg?token=AHN6XYGVK75VSGPSLW4HYY27YPOF4")));
			logOutIconDark = new ImageIcon(ImageIO.read(new URL("https://raw.githubusercontent.com/F2020-ECSE223/ecse223-group-project-p14/master/ca.mcgill.ecse.flexibook/src/main/java/logOutIconDark.jpg?token=AHN6XYFMB5RAZPE6ORTWDGK7YPOI2")));
			logOutIconLight = new ImageIcon(ImageIO.read(new URL("https://raw.githubusercontent.com/F2020-ECSE223/ecse223-group-project-p14/master/ca.mcgill.ecse.flexibook/src/main/java/logOutIconLight.jpg?token=AHN6XYA4BHIRTJSLSM2QN2C7YPOKI")));
		} catch(Exception exp) {
			error += exp.getMessage();
		}
		
		//initialize info button
		infoCustomerButton = new JButton();
		infoCustomerButton.setIcon(infoIconDark);
		infoCustomerButton.setPreferredSize(new Dimension(50, 40));
		infoCustomerButton.setBorder(new LineBorder(darkGrey));
		infoCustomerButton.setBackground(darkGrey);
		infoCustomerButton.setOpaque(true);
		infoCustomerButton.setForeground(Color.WHITE);

		//initialize single service button
		bookAppointmentButton = new JButton();
		bookAppointmentButton.setText("Book Appointment");
		bookAppointmentButton.setPreferredSize(new Dimension(500, 40));
		bookAppointmentButton.setBorder(new LineBorder(darkGrey));
		bookAppointmentButton.setBackground(darkGrey);
		bookAppointmentButton.setOpaque(true);
		bookAppointmentButton.setForeground(Color.WHITE);

		//initialize calendar button
		calendarCustomerButton = new JButton();
		calendarCustomerButton.setText("Calendar");
		calendarCustomerButton.setPreferredSize(new Dimension(500, 40));
		calendarCustomerButton.setBorder(new LineBorder(Color.WHITE));
		calendarCustomerButton.setBackground(Color.WHITE);
		calendarCustomerButton.setOpaque(true);
		calendarCustomerButton.setForeground(darkGrey);

		//initialize log out button
		logOutCustomerButton = new JButton();
		logOutCustomerButton.setIcon(logOutIconDark);
		logOutCustomerButton.setPreferredSize(new Dimension(50, 40));
		logOutCustomerButton.setBorder(new LineBorder(darkGrey));
		logOutCustomerButton.setBackground(darkGrey);
		logOutCustomerButton.setOpaque(true);
		logOutCustomerButton.setForeground(Color.WHITE);

		//add buttons to top bar
		topPanelCustomer.add(infoCustomerButton);
		topPanelCustomer.add(bookAppointmentButton);
		topPanelCustomer.add(calendarCustomerButton);
		topPanelCustomer.add(logOutCustomerButton);

		//initialize info button listener
		infoCustomerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				infoCustomerButtonActionPerformed(evt);
			}
		});

		//initialize book appointment button listener
		bookAppointmentButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bookAppointmentButtonActionPerformed(evt);
			}
		});

		//initialize calendar button listener
		calendarCustomerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				calendarCustomerButtonActionPerformed(evt);
			}
		});

		//initialize log out button listener
		logOutCustomerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logOutCustomerButtonActionPerformed(evt);
			}
		});

	}

	//initialize info panel for owner
	private void initInfoOwnerPanel(){
		infoOwnerPanel = new JPanel();
		infoLabel = new JLabel("Info Page");
		infoOwnerPanel.setPreferredSize(new Dimension(1100,600));
		infoOwnerPanel.setBackground(Color.WHITE);
		infoOwnerPanel.setOpaque(true);
		infoOwnerPanel.setForeground(Color.WHITE);
		infoOwnerPanel.add(infoLabel);

		//TO DO
	}
	//initialize info panel
	private void initInfoPanel(){
		
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(1100,600));
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setOpaque(true);
		infoPanel.setForeground(Color.darkGray);
		
		infoLabel = new JLabel("Manage your Account");
		infoLabel.setPreferredSize(new Dimension(200, 40));
		infoLabel.setBackground(Color.WHITE);
		infoLabel.setOpaque(true);
		infoLabel.setForeground(Color.darkGray);
		
		usernameLabel = new JLabel("New Username");
		usernameLabel.setBackground(Color.WHITE);
		usernameLabel.setOpaque(true);
		usernameLabel.setForeground(Color.darkGray);
		usernameLabel.setPreferredSize(new Dimension(200, 40));
		
		usernameBox = new JTextField();
		usernameBox.setColumns(10);
		usernameBox.setPreferredSize(new Dimension(200, 40));
		
		passwordLabel = new JLabel("New Password");
		passwordLabel.setBackground(Color.WHITE);
		passwordLabel.setOpaque(true);
		passwordLabel.setForeground(Color.darkGray);
		passwordLabel.setPreferredSize(new Dimension(200, 40));
		
		passwordBox = new JTextField(); 
		passwordBox.setColumns(10);
		passwordBox.setPreferredSize(new Dimension(200, 40));
		
		saveAccountInfo = new JButton("Save");
		saveAccountInfo.setPreferredSize(new Dimension(50, 40));
		saveAccountInfo.setBorder(new LineBorder(Color.darkGray));
		saveAccountInfo.setBackground(Color.darkGray);
		saveAccountInfo.setOpaque(true);
		saveAccountInfo.setForeground(Color.WHITE);
		
		JPanel nested1 = new JPanel(); // first line
		nested1.add(infoPanel.add(infoLabel));
		
		JPanel nested2 = new JPanel(); // second line
		nested2.add(infoPanel.add(usernameLabel));
		nested2.add(infoPanel.add(usernameBox));
		
		JPanel nested3 = new JPanel(); //third line
		nested3.add(infoPanel.add(passwordLabel));
		nested3.add(infoPanel.add(passwordBox));
		
		JPanel nested4 = new JPanel(); // fourth line
		nested3.add(infoPanel.add(saveAccountInfo));
		  
		JPanel outer = new JPanel(new BorderLayout());
		outer.add(nested1, BorderLayout.CENTER);
		outer.add(nested2, BorderLayout.CENTER);
		outer.add(nested3, BorderLayout.CENTER);
		outer.add(nested4, BorderLayout.CENTER);

		getContentPane().add(outer);

		saveAccountInfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveAccountInfoActionPerformed(evt);
			}
		});
	}

	//initialize info panel for customer
	private void initInfoCustomerPanel(){
		infoCustomerPanel = new JPanel();
		infoLabel = new JLabel("Info Page");
		infoCustomerPanel.setPreferredSize(new Dimension(1100,600));
		infoCustomerPanel.setBackground(Color.WHITE);
		infoCustomerPanel.setOpaque(true);
		infoCustomerPanel.setForeground(Color.WHITE);
		infoCustomerPanel.add(infoLabel);


	}


	//initialize single services panel
	private void initSingleServicesPanel(){
		singleServicesPanel = new JPanel();
		singleServicesLabel = new JLabel("Single Service Page");
		singleServicesPanel.setPreferredSize(new Dimension(1100,600));
		singleServicesPanel.setBackground(Color.WHITE);
		singleServicesPanel.setOpaque(true);
		singleServicesPanel.setForeground(Color.WHITE);
		singleServicesPanel.add(singleServicesLabel);

		//TO DO
	}

	//initialize combo services panel
	private void initComboServicesPanel(){
		comboServicesPanel = new JPanel();
		comboServicesLabel = new JLabel("Combo Service Page");
		comboServicesPanel.setPreferredSize(new Dimension(1100,600));
		comboServicesPanel.setBackground(Color.WHITE);
		comboServicesPanel.setOpaque(true);
		comboServicesPanel.setForeground(Color.WHITE);
		comboServicesPanel.add(comboServicesLabel);

		//TO DO
	}

	//initialize calendar services panel for owner
	private void initCalendarOwnerPanel(){
		calendarOwnerPanel = new JPanel();
		calendarLabel = new JLabel("Calendar Page");
		calendarOwnerPanel.setPreferredSize(new Dimension(1100,600));
		calendarOwnerPanel.setBackground(Color.WHITE);
		calendarOwnerPanel.setOpaque(true);
		calendarOwnerPanel.setForeground(Color.WHITE);
		calendarOwnerPanel.add(calendarLabel);

		//TO DO
	}

	//initialize calendar services panel for customer
	private void initCalendarCustomerPanel(){
		calendarCustomerPanel = new JPanel();
		calendarLabel = new JLabel("Calendar Page");
		calendarCustomerPanel.setPreferredSize(new Dimension(1100,600));
		calendarCustomerPanel.setBackground(Color.WHITE);
		calendarCustomerPanel.setOpaque(true);
		calendarCustomerPanel.setForeground(Color.WHITE);
		calendarCustomerPanel.add(calendarLabel);

		//TO DO
	}

	//initialize business hours services panel
	private void initBusinessHoursPanel(){
		businessHoursPanel = new JPanel();
		businessHoursLabel = new JLabel("Business Hour Page");
		businessHoursPanel.setPreferredSize(new Dimension(1100,600));
		businessHoursPanel.setBackground(Color.WHITE);
		businessHoursPanel.setOpaque(true);
		businessHoursPanel.setForeground(Color.WHITE);
		businessHoursPanel.add(businessHoursLabel);

		//TO DO
	}

	//initialize business details panel
	private void initBusinessDetailsPanel(){
		businessDetailsPanel = new JPanel();
		businessDetailsLabel = new JLabel("Business Detail Page");
		businessDetailsPanel.setPreferredSize(new Dimension(1100,600));
		businessDetailsPanel.setBackground(Color.WHITE);
		businessDetailsPanel.setOpaque(true);
		businessDetailsPanel.setForeground(Color.WHITE);
		businessDetailsPanel.add(businessDetailsLabel);

		//TO DO
	}

	//initialize log out panel for owner
	private void initLogOutOwnerPanel(){
		logOutOwnerPanel = new JPanel();
		logOutLabel = new JLabel("Log Out Page");
		logOutOwnerPanel.setPreferredSize(new Dimension(1100,600));
		logOutOwnerPanel.setBackground(Color.WHITE);
		logOutOwnerPanel.setOpaque(true);
		logOutOwnerPanel.setForeground(Color.WHITE);
		logOutOwnerPanel.add(logOutLabel);

		//TO DO
	}

	//initialize log out panel for customer
	private void initLogOutCustomerPanel(){
		logOutCustomerPanel = new JPanel();
		logOutLabel = new JLabel("Log Out Page");
		logOutCustomerPanel.setPreferredSize(new Dimension(1100,600));
		logOutCustomerPanel.setBackground(Color.WHITE);
		logOutCustomerPanel.setOpaque(true);
		logOutCustomerPanel.setForeground(Color.WHITE);
		logOutCustomerPanel.add(logOutLabel);

		//TO DO
	}

	//initialize business details panel
	private void initBookAppointmentPanel(){
		bookAppointmentPanel = new JPanel();
		bookAppointmentLabel = new JLabel("Book Appointment Page");
		bookAppointmentPanel.setPreferredSize(new Dimension(1100,600));
		bookAppointmentPanel.setBackground(Color.WHITE);
		bookAppointmentPanel.setOpaque(true);
		bookAppointmentPanel.setForeground(Color.WHITE);
		bookAppointmentPanel.add(bookAppointmentLabel);

		//TO DO
	}

	//refresh frame
	private void refreshData() {
		pack();
		repaint();
		
		
	}

	//method called when log in owner button pressed
	private void logInOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//remove log in panel
		getContentPane().remove(logInPanel);
		//add owner top bar and calendar panel to frame
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		c.ipadx = 1100;
		getContentPane().add(topPanelOwner, c);
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 587;
		c.ipadx = 1100;
		getContentPane().add(calendarOwnerPanel, c);
		//set calendar to initial state
		previousPanel = calendarOwnerPanel;
		previousButton = calendarOwnerButton;
		//reset calendar button
		calendarOwnerButton.setBorder(new LineBorder(Color.WHITE));
		calendarOwnerButton.setBackground(Color.WHITE);
		calendarOwnerButton.setOpaque(true);
		calendarOwnerButton.setForeground(darkGrey);
		//refresh page
		refreshData();
	}

	//method called when log in customer button pressed
	private void logInCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//remove log in panel
		getContentPane().remove(logInPanel);
		//add customer top bar and calendar panel to frame
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		c.ipadx = 1100;
		getContentPane().add(topPanelCustomer, c);
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 587;
		c.ipadx = 1100;
		getContentPane().add(calendarCustomerPanel, c);
		//set calendar to initial state
		previousPanel = calendarCustomerPanel;
		previousButton = calendarCustomerButton;
		//reset calendar button
		calendarCustomerButton.setBorder(new LineBorder(Color.WHITE));
		calendarCustomerButton.setBackground(Color.WHITE);
		calendarCustomerButton.setOpaque(true);
		calendarCustomerButton.setForeground(darkGrey);
		//refresh page
		refreshData();
	}

	//method called when owner info button pressed
	private void infoOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoOwnerButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutOwnerButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = infoOwnerButton;
		//set this button to white background
		infoOwnerButton.setBorder(new LineBorder(Color.WHITE));
		infoOwnerButton.setIcon(infoIconLight);
		infoOwnerButton.setBackground(Color.WHITE);
		infoOwnerButton.setOpaque(true);
		infoOwnerButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(infoOwnerPanel, c);
		//set this panel as the current panel
		previousPanel = infoOwnerPanel;
		//refresh page
		refreshData();
	}
	
	//method called when customer info button pressed
	private void infoCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoCustomerButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutCustomerButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = infoCustomerButton;
		//set this button to white background
		infoCustomerButton.setBorder(new LineBorder(Color.WHITE));
		infoCustomerButton.setIcon(infoIconLight);
		infoCustomerButton.setBackground(Color.WHITE);
		infoCustomerButton.setOpaque(true);
		infoCustomerButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(infoCustomerPanel, c);
		//set this panel as the current panel
		previousPanel = infoCustomerPanel;
		//refresh page
		refreshData();
	}

	//method called when single service button pressed
	private void singleServicesButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoOwnerButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutOwnerButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = singleServicesButton;
		//set this button to white background
		singleServicesButton.setBorder(new LineBorder(Color.WHITE));
		singleServicesButton.setBackground(Color.WHITE);
		singleServicesButton.setOpaque(true);
		singleServicesButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(singleServicesPanel, c);
		//set this panel as the current panel
		previousPanel = singleServicesPanel;
		//refresh page
		refreshData();
	}

	//method called when combo service button pressed
	private void comboServicesButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoOwnerButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutOwnerButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = comboServicesButton;
		//set this button to white background
		comboServicesButton.setBorder(new LineBorder(Color.WHITE));
		comboServicesButton.setBackground(Color.WHITE);
		comboServicesButton.setOpaque(true);
		comboServicesButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(comboServicesPanel, c);
		//set this panel as the current panel
		previousPanel = comboServicesPanel;
		//refresh page
		refreshData();
	}

	//method called when owner calendar button pressed
	private void calendarOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoOwnerButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutOwnerButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = calendarOwnerButton;
		//set this button to white background
		calendarOwnerButton.setBorder(new LineBorder(Color.WHITE));
		calendarOwnerButton.setBackground(Color.WHITE);
		calendarOwnerButton.setOpaque(true);
		calendarOwnerButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(calendarOwnerPanel, c);
		//set this panel as the current panel
		previousPanel = calendarOwnerPanel;
		//refresh page
		refreshData();
	}

	//method called when customer calendar button pressed
	private void calendarCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoCustomerButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutCustomerButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = calendarCustomerButton;
		//set this button to white background
		calendarCustomerButton.setBorder(new LineBorder(Color.WHITE));
		calendarCustomerButton.setBackground(Color.WHITE);
		calendarCustomerButton.setOpaque(true);
		calendarCustomerButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(calendarCustomerPanel, c);
		//set this panel as the current panel
		previousPanel = calendarCustomerPanel;
		//refresh page
		refreshData();
	}

	//method called when business hours button pressed
	private void businessHoursButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoOwnerButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutOwnerButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = businessHoursButton;
		//set this button to white background
		businessHoursButton.setBorder(new LineBorder(Color.WHITE));
		businessHoursButton.setBackground(Color.WHITE);
		businessHoursButton.setOpaque(true);
		businessHoursButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(businessHoursPanel, c);
		//set this panel as the current panel
		previousPanel = businessHoursPanel;
		//refresh page
		refreshData();
	}

	//method called when business details button pressed
	private void businessDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoOwnerButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutOwnerButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = businessDetailsButton;
		//set this button to white background
		businessDetailsButton.setBorder(new LineBorder(Color.WHITE));
		businessDetailsButton.setBackground(Color.WHITE);
		businessDetailsButton.setOpaque(true);
		businessDetailsButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(businessDetailsPanel, c);
		//set this panel as the current panel
		previousPanel = businessDetailsPanel;
		//refresh page
		refreshData();
	}

	//method called when owner log out button pressed
	private void logOutOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoOwnerButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutOwnerButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//remove previous panels
		getContentPane().remove(previousPanel);
		getContentPane().remove(topPanelOwner);
		//set new panel
		getContentPane().add(logInPanel);
		//refresh page
		refreshData();
	}

	//method called when customer log out button pressed
	private void logOutCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoCustomerButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutCustomerButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//remove previous panels
		getContentPane().remove(previousPanel);
		getContentPane().remove(topPanelCustomer);
		//set new panel
		getContentPane().add(logInPanel);
		//refresh page
		refreshData();
	}

	//method called when book appointment button pressed
	private void bookAppointmentButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoCustomerButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutCustomerButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = bookAppointmentButton;
		//set this button to white background
		bookAppointmentButton.setBorder(new LineBorder(Color.WHITE));
		bookAppointmentButton.setBackground(Color.WHITE);
		bookAppointmentButton.setOpaque(true);
		bookAppointmentButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(bookAppointmentPanel, c);
		//set this panel as the current panel
		previousPanel = bookAppointmentPanel;
		//refresh page
		refreshData();
	}
	
	//method called when save button pressed while editing an account
	private void saveAccountInfoActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		error = null;
				
		// call the controller
		try {
			FlexiBookController.updateUserAccount(FlexiBookApplication.getCurrentLoginUser().getUsername(), usernameBox.getText(), passwordBox.getText());
				} catch (InvalidInputException e) {
					error = e.getMessage();
				}
		
		// update visuals
		refreshData();
		
	}

}
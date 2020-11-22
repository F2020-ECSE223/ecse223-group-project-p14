package ca.mcgill.ecse.flexibook.view;

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

import javax.swing.border.LineBorder;
import javax.swing.*;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.model.Owner;
import ca.mcgill.ecse.flexibook.model.Service;

public class FlexiBookPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;

	//top bar
	private JPanel topPanel;
	//panel for each button on top bar
	private JPanel infoPanel;
	private JPanel singleServicesPanel;
	private JPanel comboServicesPanel;
	private JPanel calendarPanel;
	private JPanel businessHoursPanel;
	private JPanel businessDetailsPanel;
	private JPanel logOutPanel;
	
	//top bar buttons
	private JButton infoButton;
	private JButton singleServicesButton;
	private JButton comboServicesButton;
	private JButton calendarButton;
	private JButton businessHoursButton;
	private JButton businessDetailsButton;
	private JButton logOutButton;

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
	

	//tracking last page
	private JButton previousButton;
	private JPanel previousPanel;

	//color of top bar
	private Color darkGrey = new Color(62,62,62);
	
	// Error message 
	private JLabel errorMessage;
	private String error = null;


	/** Creates new form BtmsPage */
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
		//setResizable(false);

		//initialize top bar layout
		FlowLayout topLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
		topPanel = new JPanel();
		topPanel.setLayout(topLayout);
		topPanel.setPreferredSize(new Dimension(1100,40));

		//initialize info panel
		initInfoPanel();

		//initialize single service panel
		initSingleServicesPanel();

		//initialize combo service panel
		initComboServicesPanel();

		//initialize calendar panel
		initCalendarPanel();

		//initialize business hour panel
		initBusinessHoursPanel();

		//initialize business details panel
		initBusinessDetailsPanel();

		//initialize log out panel
		initLogOutPanel();

		//initialize image icons
		infoIconDark = new ImageIcon("infoIconDark.jpg");
		infoIconLight = new ImageIcon("infoIconLight.jpg");
		logOutIconDark = new ImageIcon("logOutIconDark.jpg");
		logOutIconLight = new ImageIcon("logOutIconLight.jpg");

		//initialize info button
		infoButton = new JButton();
		infoButton.setIcon(infoIconDark);
		infoButton.setPreferredSize(new Dimension(50, 40));
		infoButton.setBorder(new LineBorder(darkGrey));
		infoButton.setBackground(darkGrey);
		infoButton.setOpaque(true);
		infoButton.setForeground(Color.WHITE);

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
		calendarButton = new JButton();
		calendarButton.setText("Calendar");
		calendarButton.setPreferredSize(new Dimension(200, 40));
		calendarButton.setBorder(new LineBorder(Color.WHITE));
		calendarButton.setBackground(Color.WHITE);
		calendarButton.setOpaque(true);
		calendarButton.setForeground(darkGrey);

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
		logOutButton = new JButton();
		logOutButton.setIcon(logOutIconDark);
		logOutButton.setPreferredSize(new Dimension(50, 40));
		logOutButton.setBorder(new LineBorder(darkGrey));
		logOutButton.setBackground(darkGrey);
		logOutButton.setOpaque(true);
		logOutButton.setForeground(Color.WHITE);

		//add buttons to top bar
		topPanel.add(infoButton);
		topPanel.add(singleServicesButton);
		topPanel.add(comboServicesButton);
		topPanel.add(calendarButton);
		topPanel.add(businessHoursButton);
		topPanel.add(businessDetailsButton);
		topPanel.add(logOutButton);

		//set calendar as initial page
		previousButton = calendarButton;
		previousPanel = calendarPanel;

		//add top bar to frame
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		c.ipadx = 1100;
		getContentPane().add(topPanel, c);
		//add calendar panel to frame
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 587;
		c.ipadx = 1100;
		getContentPane().add(calendarPanel, c);


		pack();

		//initialize info button listener
		infoButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				infoButtonActionPerformed(evt);
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
		calendarButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				calendarButtonActionPerformed(evt);
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
		logOutButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logOutButtonActionPerformed(evt);
			}
		});

	}

	//initialize info panel
	private void initInfoPanel(){
		
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(1100,600));
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setOpaque(true);
		infoPanel.setForeground(Color.darkGray);
		
		infoLabel = new JLabel("Manage your Account");
		//infoLabel.setPreferredSize(new Dimension(200, 40));
		infoLabel.setBackground(Color.WHITE);
		infoLabel.setOpaque(true);
		infoLabel.setForeground(Color.darkGray);
		//infoLabel.setBounds(45, 25, 312, 16);
		
		usernameLabel = new JLabel("New Username");
		usernameLabel.setBackground(Color.WHITE);
		usernameLabel.setOpaque(true);
		usernameLabel.setForeground(Color.darkGray);
		//usernameLabel.setPreferredSize(new Dimension(200, 40));
		
		usernameBox = new JTextField();
		usernameBox.setColumns(10);
		//usernameBox.setPreferredSize(new Dimension(200, 40));
		
		passwordLabel = new JLabel("New Password");
		passwordLabel.setBackground(Color.WHITE);
		passwordLabel.setOpaque(true);
		passwordLabel.setForeground(Color.darkGray);
		//passwordLabel.setPreferredSize(new Dimension(200, 40));
		
		passwordBox = new JTextField(); 
		passwordBox.setColumns(10);
		//passwordBox.setPreferredSize(new Dimension(200, 40));
		
		saveAccountInfo = new JButton("Save");
		//saveAccountInfo.setPreferredSize(new Dimension(50, 40));
		saveAccountInfo.setBorder(new LineBorder(Color.darkGray));
		saveAccountInfo.setBackground(Color.darkGray);
		saveAccountInfo.setOpaque(true);
		saveAccountInfo.setForeground(Color.WHITE);
		
		// add elements
//		infoPanel.add(infoLabel);
//		infoPanel.add(usernameLabel);
//		infoPanel.add(passwordLabel);
//		infoPanel.add(usernameBox);
//		infoPanel.add(passwordBox);
//		infoPanel.add(saveAccountInfo);




		saveAccountInfo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveAccountInfoActionPerformed(evt);
			}
		});
		
		GroupLayout layout = new GroupLayout(infoPanel);
		infoPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addComponent(infoLabel)
				
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(usernameLabel)
								.addComponent(passwordLabel))
						.addGroup(layout.createParallelGroup()
								.addComponent(usernameBox, 200, 200, 400)
								.addComponent(passwordBox, 200, 200, 400)
								.addComponent(saveAccountInfo))));
		
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {usernameBox, passwordBox});
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {saveAccountInfo, passwordBox});
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(errorMessage)
				.addComponent(infoLabel)
				.addGroup(layout.createParallelGroup()
						.addComponent(usernameLabel)
						.addComponent(usernameBox, 40, 40, 40))	
				.addGroup(layout.createParallelGroup()
						.addComponent(passwordLabel)
						.addComponent(passwordBox, 40, 40, 40))
				.addGroup(layout.createParallelGroup()
						.addComponent(saveAccountInfo)));
		pack();
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

	//initialize calendar services panel
	private void initCalendarPanel(){
		calendarPanel = new JPanel();
		calendarLabel = new JLabel("Calendar Page");
		calendarPanel.setPreferredSize(new Dimension(1100,600));
		calendarPanel.setBackground(Color.WHITE);
		calendarPanel.setOpaque(true);
		calendarPanel.setForeground(Color.WHITE);
		calendarPanel.add(calendarLabel);

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

	//initialize log out panel
	private void initLogOutPanel(){
		logOutPanel = new JPanel();
		logOutLabel = new JLabel("Log Out Page");
		logOutPanel.setPreferredSize(new Dimension(1100,600));
		logOutPanel.setBackground(Color.WHITE);
		logOutPanel.setOpaque(true);
		logOutPanel.setForeground(Color.WHITE);
		logOutPanel.add(logOutLabel);

		//TO DO
	}

	//refresh frame
	private void refreshData() {
		pack();
		repaint();
		
		
	}

	
	//method called when info button pressed
	private void infoButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = infoButton;
		//set this button to white background
		infoButton.setBorder(new LineBorder(Color.WHITE));
		infoButton.setIcon(infoIconLight);
		infoButton.setBackground(Color.WHITE);
		infoButton.setOpaque(true);
		infoButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(infoPanel, c);
		//set this panel as the current panel
		previousPanel = infoPanel;
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
		if(previousButton.equals(infoButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutButton)){
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
		if(previousButton.equals(infoButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutButton)){
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

	//method called when calendar button pressed
	private void calendarButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = calendarButton;
		//set this button to white background
		calendarButton.setBorder(new LineBorder(Color.WHITE));
		calendarButton.setBackground(Color.WHITE);
		calendarButton.setOpaque(true);
		calendarButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(calendarPanel, c);
		//set this panel as the current panel
		previousPanel = calendarPanel;
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
		if(previousButton.equals(infoButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutButton)){
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
		if(previousButton.equals(infoButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutButton)){
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

	//method called when log out button pressed
	private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		if(previousButton.equals(infoButton)){
			previousButton.setIcon(infoIconDark);
		} else if(previousButton.equals(logOutButton)){
			previousButton.setIcon(logOutIconDark);
		}
		//set this button to current button pressed
		previousButton = logOutButton;
		//set this button to white background
		logOutButton.setBorder(new LineBorder(Color.WHITE));
		logOutButton.setIcon(logOutIconLight);
		logOutButton.setBackground(Color.WHITE);
		logOutButton.setOpaque(true);
		logOutButton.setForeground(darkGrey);
		//remove previous panel
		getContentPane().remove(previousPanel);
		//set new panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1100;
		c.ipady = 587;
		getContentPane().add(logOutPanel, c);
		//set this panel as the current panel
		previousPanel = logOutPanel;
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
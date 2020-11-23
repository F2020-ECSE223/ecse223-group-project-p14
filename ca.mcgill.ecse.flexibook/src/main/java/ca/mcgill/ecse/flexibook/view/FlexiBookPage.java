package ca.mcgill.ecse.flexibook.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import ca.mcgill.ecse.flexibook.model.Service;
import ca.mcgill.ecse.flexibook.controller.TOBusiness;
import ca.mcgill.ecse.flexibook.controller.TOBusinessHour;

public class FlexiBookPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;

	//top bar for owner
	private JPanel topPanelOwner;
	//top bar for customer
	private JPanel topPanelCustomer;
	//panel for log in
	private JPanel logInPanel;
	//panel for set-up business information
	private JPanel setUpInPanel;
	//panel for each button on top bar
	private JPanel infoOwnerPanel;
	private JPanel infoCustomerPanel;
	private JPanel singleServicesPanel;
	private JPanel comboServicesPanel;
	private JPanel calendarOwnerPanel;
	private JPanel calendarCustomerPanel;
	private JPanel businessHoursPanel;
	private JPanel businessDetailsPanel;
	private JPanel logOutOwnerPanel;
	private JPanel logOutCustomerPanel;
	private JPanel bookAppointmentPanel;
	//panels for calendar tab
	private JPanel calendarWeeklyViewPanel;
	
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
	
	//set up business information button
	private JButton setDetailBtn;
	//set up business information JLabels and text
	private JLabel toSelect;
	private JLabel startTimeLabel;
	private JLabel endTimeLabel;
	private JLabel or;
	private JLabel updateBusinessHours;
	
	private JTextField startTime;
	private JTextField endTime;
	private JTextField businessNameSetText;
	private JTextField txtPhoneNumberUpdate;
	private JTextField txtEmailUpdate;
	private JTextField txtAdressUpdate;
	
	
	//update business information JLabels and text
	
	private JTextField txtBusinessNameSet;
	private JTextField txtAdressSet;
	private JTextField txtPhoneNumberSet;
	private JTextField txtEmailSet;

	//update and remove business information JButton 
	private JButton updateBusinessHour;
	private JButton removeBusinessHour;

	//top bar icons
	private ImageIcon infoIconDark;
	private ImageIcon infoIconLight;
	private ImageIcon logOutIconDark;
	private ImageIcon logOutIconLight;
	
	//calendar page icons
	private ImageIcon calendarWithTimesIcon;
	private ImageIcon calendarWithoutTimesIcon;

	//page labels
	private JLabel infoLabel;
	private JLabel singleServicesLabel;
	private JLabel comboServicesLabel;
	private JLabel calendarLabel;
	private JLabel businessHoursLabel;
	private JLabel businessDetailsLabel;
	private JLabel logOutLabel;
	private JLabel bookAppointmentLabel;
	private JLabel setUpBusinessInfoLabel;

	//tracking last page
	private JButton previousButton;
	private JPanel previousPanel;

	//color of top bar
	private Color darkGrey = new Color(62,62,62);

	private String error;


	/** Creates new form FlexiBookPage */
	public FlexiBookPage() {
		initComponents();
		refreshData();
	}

	/** This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {

		//initialize frame
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("FlexiBook");
		setPreferredSize(new Dimension(1100,740));
		setResizable(false);

		//initialize log in page
		initLogInPage();
		initSetBusinessInfo();
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
		
		//initialize customer log in button listener
		logInCustomerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logInCustomerButtonActionPerformed(evt);
			}
		});

		if(FlexiBookApplication.getFlexiBook().getBusiness()==null) {
			//initialize owner log in button listener
			logInOwnerButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					logInOwnerButtonToSetUpActionPerformed(evt);
				}
			});
		}
		else {
			logInOwnerButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					logInOwnerButtonActionPerformed(evt);
				}
			});		
		}

	}
	
	//initialize the business information set-up
	private void initSetBusinessInfo() {

		setUpInPanel = new JPanel();
		setUpInPanel.setLayout(null);
		setUpBusinessInfoLabel = new JLabel("Info Page");
		setUpInPanel.setPreferredSize(new Dimension(1100,700));
		setUpInPanel.setBackground(Color.WHITE);
		setUpInPanel.setOpaque(true);
		setUpInPanel.setForeground(Color.WHITE);
		setUpInPanel.add(setUpBusinessInfoLabel);

		setDetailBtn = new JButton("Set");
		setDetailBtn.setBounds(500,400,89,23);
		setUpInPanel.add(setDetailBtn);

		//Setting the UI for setting business information
		JLabel businessNameSet = new JLabel("Business name");
		businessNameSet.setBounds(500, 100, 150, 23);
		setUpInPanel.add(businessNameSet);

		txtBusinessNameSet = new JTextField();
		txtBusinessNameSet.setBounds(500, 130, 200, 23);
		setUpInPanel.add(txtBusinessNameSet);
		txtBusinessNameSet.setColumns(10);

		JLabel adressSet = new JLabel("Address");
		adressSet.setBounds(500, 170, 70, 23);
		setUpInPanel.add(adressSet);

		txtAdressSet= new JTextField();
		txtAdressSet.setText("");
		txtAdressSet.setBounds(500, 200, 200, 23);
		setUpInPanel.add(txtAdressSet);
		txtAdressSet.setColumns(10);

		JLabel phoneNumberSet  = new JLabel("Phone Number");
		phoneNumberSet.setBounds(500, 230, 150, 23);
		setUpInPanel.add(phoneNumberSet);

		txtPhoneNumberSet = new JTextField();
		txtPhoneNumberSet.setBounds(500, 260, 200, 23);
		setUpInPanel.add(txtPhoneNumberSet);
		txtPhoneNumberSet.setColumns(10);

		JLabel emailSet = new JLabel("Email");
		emailSet.setBounds(500, 290, 200, 23);
		setUpInPanel.add(emailSet);

		txtEmailSet = new JTextField();
		txtEmailSet.setBounds(500, 320, 200, 23);
		setUpInPanel.add(txtEmailSet);
		txtEmailSet.setColumns(10);	

			setDetailBtn.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					setUpBusinessInformation(evt);
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
		initBusinessDetailsPanelForUpdate();

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
		infoOwnerPanel.setPreferredSize(new Dimension(1100,700));
		infoOwnerPanel.setBackground(Color.WHITE);
		infoOwnerPanel.setOpaque(true);
		infoOwnerPanel.setForeground(Color.WHITE);
		infoOwnerPanel.add(infoLabel);

		//TO DO
	}

	//initialize info panel for customer
	private void initInfoCustomerPanel(){
		infoCustomerPanel = new JPanel();
		infoLabel = new JLabel("Info Page");
		infoCustomerPanel.setPreferredSize(new Dimension(1100,700));
		infoCustomerPanel.setBackground(Color.WHITE);
		infoCustomerPanel.setOpaque(true);
		infoCustomerPanel.setForeground(Color.WHITE);
		infoCustomerPanel.add(infoLabel);

		//TO DO
	}

	//initialize single services panel
	private void initSingleServicesPanel(){
		singleServicesPanel = new JPanel();
		singleServicesLabel = new JLabel("Single Service Page");
		singleServicesPanel.setPreferredSize(new Dimension(1100,700));
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
		comboServicesPanel.setPreferredSize(new Dimension(1100,700));
		comboServicesPanel.setBackground(Color.WHITE);
		comboServicesPanel.setOpaque(true);
		comboServicesPanel.setForeground(Color.WHITE);
		comboServicesPanel.add(comboServicesLabel);

		//TO DO
	}

	//initialize calendar services panel for owner
	private void initCalendarOwnerPanel(){
		//create new JPanel
		calendarOwnerPanel = new JPanel();
		//set null layout
		calendarOwnerPanel.setLayout(null);
		//set size
		calendarOwnerPanel.setPreferredSize(new Dimension(1100,700));
		//set background
		calendarOwnerPanel.setBackground(Color.WHITE);
		calendarOwnerPanel.setOpaque(true);
		calendarOwnerPanel.setForeground(Color.WHITE);
		//create calendar image panel
		calendarWeeklyViewPanel = new JPanel();
		calendarOwnerPanel.setPreferredSize(new Dimension(900,900));
		//initialize image icons
		try{
			calendarWithTimesIcon = new ImageIcon(ImageIO.read(new URL("https://raw.githubusercontent.com/F2020-ECSE223/ecse223-group-project-p14/master/ca.mcgill.ecse.flexibook/src/main/java/Calendar_withNumbers.jpeg?token=AHN6XYD2BF76CM4PFQPYJ7C7YQ66A")));
			calendarWithoutTimesIcon = new ImageIcon(ImageIO.read(new URL("https://raw.githubusercontent.com/F2020-ECSE223/ecse223-group-project-p14/master/ca.mcgill.ecse.flexibook/src/main/java/Calendar_noTimes.jpeg?token=AHN6XYCSNWHRW6GPZDKBVEC7YQ66Y")));
		} catch(Exception exp) {
			error += exp.getMessage();
		}
		calendarWeeklyViewPanel.add(new JLabel(calendarWithTimesIcon));
		calendarOwnerPanel.add(calendarWeeklyViewPanel);
		calendarWeeklyViewPanel.setBounds(100,100,900,900);

		//TO DO
	}

	//initialize calendar services panel for customer
	private void initCalendarCustomerPanel(){
		calendarCustomerPanel = new JPanel();
		calendarLabel = new JLabel("Calendar Page");
		calendarCustomerPanel.setPreferredSize(new Dimension(1100,700));
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
		businessHoursPanel.setPreferredSize(new Dimension(1100,700));
		businessHoursPanel.setBackground(Color.WHITE);
		businessHoursPanel.setOpaque(true);
		businessHoursPanel.setForeground(Color.WHITE);
		businessHoursPanel.add(businessHoursLabel);

		//TO DO
	}
	
	//initialize business information for customer
	private void initBusinessDetailsPanel(){
		businessDetailsPanel = new JPanel();
		businessDetailsPanel.setLayout(null);
		businessDetailsLabel = new JLabel("Business Detail Page");
		businessDetailsPanel.setPreferredSize(new Dimension(1100,600));
		businessDetailsPanel.setBackground(Color.WHITE);
		businessDetailsPanel.setOpaque(true);
		businessDetailsPanel.setForeground(Color.WHITE);
		businessDetailsPanel.add(businessDetailsLabel);
		
		//Current business details 
		JLabel businessName = new JLabel("Business name: "+ FlexiBookController.getBusinessInfo().getName());
		businessName.setBounds(500, 50, 150, 23);
		businessDetailsPanel.add(businessName);

		JLabel adress =  new JLabel("Address: "+ FlexiBookController.getBusinessInfo().getAdress());
		adress.setBounds(500, 100, 150, 23);
		businessDetailsPanel.add(adress);

		JLabel phoneNumber  = new JLabel("Phone Number: "+FlexiBookController.getBusinessInfo().getPhoneNumber());
		phoneNumber.setBounds(500, 150, 150, 23);
		businessDetailsPanel.add(phoneNumber);

		JLabel email = new JLabel("Email: "+FlexiBookController.getBusinessInfo().getEmail());
		email.setBounds(500, 200, 250, 23);
		businessDetailsPanel.add(email);
		
		
	}
	
	

	//initialize business details panel for an owner 
	private void initBusinessDetailsPanelForUpdate(){
		
		businessDetailsPanel = new JPanel();
		businessDetailsPanel.setLayout(null);
		businessDetailsLabel = new JLabel("Business Detail Page");
		businessDetailsPanel.setPreferredSize(new Dimension(1100,600));
		businessDetailsPanel.setBackground(Color.WHITE);
		businessDetailsPanel.setOpaque(true);
		businessDetailsPanel.setForeground(Color.WHITE);
		businessDetailsPanel.add(businessDetailsLabel);
		
		
		//initializing the business hours for the owner 
		
		toSelect = new JLabel("Select a business hour to update or remove");
		toSelect.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		toSelect.setBounds(100, 300, 500, 25);
		businessDetailsPanel.add(toSelect);
		
		startTimeLabel = new JLabel("New Start Time:");
		startTimeLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
		startTimeLabel.setBounds(100, 400, 500, 25);
		businessDetailsPanel.add(startTimeLabel);
		
		startTime = new JTextField();
		startTime.setText("");
		startTime.setBounds(225, 400, 100, 25);
		businessDetailsPanel.add(startTime);
		startTime.setColumns(10);
		
		endTimeLabel = new JLabel("New End Time:");
		endTimeLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
		endTimeLabel.setBounds(100, 450, 500, 25);
		businessDetailsPanel.add(endTimeLabel);
		
		endTime= new JTextField();
		endTime.setText("");
		endTime.setBounds(225, 450, 100, 25);
		businessDetailsPanel.add(endTime);
		endTime.setColumns(10);
		
		or = new JLabel("OR ");
		or.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		or.setBounds(100, 550, 500, 25);
		businessDetailsPanel.add(or);
		
		JComboBox comboBoxBusinessHours = new JComboBox();
		comboBoxBusinessHours.setBounds(200, 350, 90, 23);
		businessDetailsPanel.add(comboBoxBusinessHours);
		
		removeBusinessHour = new JButton("Remove");
		removeBusinessHour.setBounds(100,600,90,23);
		businessDetailsPanel.add(removeBusinessHour);
		
		updateBusinessHour = new JButton("Update");
		updateBusinessHour.setBounds(100,500,90,23);
		businessDetailsPanel.add(updateBusinessHour);
		
		//current the business hours of the business
		
		 updateBusinessHours = new JLabel("Current Business Hours");
		updateBusinessHours.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
		updateBusinessHours.setBounds(100, 50, 300, 25);
		businessDetailsPanel.add(updateBusinessHours);
		
		JLabel Monday = new JLabel("Mon:");
		Monday.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
		Monday.setBounds(100, 90, 150, 23);
		businessDetailsPanel.add(Monday);
		
		JLabel Tuesday = new JLabel("Tue:");
		Tuesday.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
		Tuesday.setBounds(100, 120, 150, 23);
		businessDetailsPanel.add(Tuesday);
		
		JLabel Wednesday = new JLabel("Wed:");
		Wednesday.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
		Wednesday.setBounds(100, 150, 150, 23);
		businessDetailsPanel.add(Wednesday);
		
		JLabel Thursday = new JLabel("Thu:");
		Thursday.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
		Thursday.setBounds(100, 180, 150, 23);
		businessDetailsPanel.add(Thursday);
		
		JLabel Friday = new JLabel("Fri:");
		Friday.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
		Friday.setBounds(100, 210, 150, 23);
		businessDetailsPanel.add(Friday);
		
		JLabel Saturday = new JLabel("Sat:");
		Saturday.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
		Saturday.setBounds(100, 240, 150, 23);
		businessDetailsPanel.add(Saturday);
		
		JLabel Sunday = new JLabel("Sun:");
		Sunday.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
		Sunday.setBounds(100, 270, 150, 23);
		businessDetailsPanel.add(Sunday);

		//initializing the business information page for the owner 
		
		JButton updateDetailBtn = new JButton("Update");
		updateDetailBtn.setBounds(750,500,89,23);
		businessDetailsPanel.add(updateDetailBtn);
		
		//Updating the UI for setting business information
		
		JLabel updateBusinessInfo = new JLabel("Updating Business Information");
		updateBusinessInfo.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
		updateBusinessInfo.setBounds(700, 230, 300, 25);
		businessDetailsPanel.add(updateBusinessInfo); 
		
		JLabel businessNameUpdate = new JLabel("Business name");
		businessNameUpdate.setBounds(700, 260, 150, 23);
		businessDetailsPanel.add(businessNameUpdate);
		
		businessNameSetText = new JTextField();
		businessNameSetText.setBounds(700, 290, 250, 23);
		businessDetailsPanel.add(businessNameSetText);
		businessNameSetText.setColumns(10);
		
		JLabel adressUpdate = new JLabel("Address");
		adressUpdate.setBounds(700, 320, 100, 23);
		businessDetailsPanel.add(adressUpdate);
		
		txtAdressUpdate= new JTextField();
		txtAdressUpdate.setText("");
		txtAdressUpdate.setBounds(700, 350, 250, 23);
		businessDetailsPanel.add(txtAdressUpdate);
		txtAdressUpdate.setColumns(10);
		
		JLabel phoneNumberUpdate  = new JLabel("Phone Number");
		phoneNumberUpdate.setBounds(700, 380, 150, 23);
		businessDetailsPanel.add(phoneNumberUpdate);
		
		txtPhoneNumberUpdate = new JTextField();
		txtPhoneNumberUpdate.setBounds(700, 410, 150, 23);
		businessDetailsPanel.add(txtPhoneNumberUpdate);
		txtPhoneNumberUpdate.setColumns(10);
		
		JLabel emailUpdate = new JLabel("Email");
		emailUpdate.setBounds(700, 440, 280, 23);
		businessDetailsPanel.add(emailUpdate);
		
		txtEmailUpdate = new JTextField();
		txtEmailUpdate.setBounds(700, 470, 310, 23);
		businessDetailsPanel.add(txtEmailUpdate);
		txtEmailUpdate.setColumns(10);
		
		//Current business details 
		
		JLabel currentBusinessInfo = new JLabel("Current Business Information");
		currentBusinessInfo.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
		currentBusinessInfo.setBounds(700, 25, 300, 25);
		businessDetailsPanel.add(currentBusinessInfo);
		
		JLabel businessName = new JLabel("Business name: "+ FlexiBookController.getBusinessInfo().getName());
		businessName.setBounds(700, 70, 150, 23);
		businessDetailsPanel.add(businessName);

		JLabel adress =  new JLabel("Address: "+ FlexiBookController.getBusinessInfo().getAdress());
		adress.setBounds(700, 100, 150, 23);
		businessDetailsPanel.add(adress);

		JLabel phoneNumber  = new JLabel("Phone Number: "+FlexiBookController.getBusinessInfo().getPhoneNumber());
		phoneNumber.setBounds(700, 130, 150, 23);
		businessDetailsPanel.add(phoneNumber);

		JLabel email = new JLabel("Email: "+FlexiBookController.getBusinessInfo().getEmail());
		email.setBounds(700, 160, 250, 23);
		businessDetailsPanel.add(email);		
	}

	//initialize log out panel for owner
	private void initLogOutOwnerPanel(){
		logOutOwnerPanel = new JPanel();
		logOutLabel = new JLabel("Log Out Page");
		logOutOwnerPanel.setPreferredSize(new Dimension(1100,700));
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
		logOutCustomerPanel.setPreferredSize(new Dimension(1100,700));
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
		bookAppointmentPanel.setPreferredSize(new Dimension(1100,700));
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
	
	//method called when set-up info is done 
		private void setUpBusinessInformation(java.awt.event.ActionEvent evt) {
			//remove log in panel
			getContentPane().remove(setUpInPanel);
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
			c.ipady = 687;
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
			
			try {
				if(FlexiBookApplication.getFlexiBook().getBusiness()==null) 
			FlexiBookController.setUpBusinessInfo(txtBusinessNameSet.getText(), txtAdressSet.getText(), txtPhoneNumberSet.getText(), txtEmailSet.getText());
				 
			}
			catch (InvalidInputException e) {
			}
			refreshData();
		}
		
		//method called when set-up info is done 
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
			c.ipady = 687;
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


	//method called when log in owner button pressed
	private void logInOwnerButtonToSetUpActionPerformed(java.awt.event.ActionEvent evt) {
		//remove log in panel
		getContentPane().remove(logInPanel);
		//add owner top bar and calendar panel to frame
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 700;
		c.ipadx = 1100;
		getContentPane().add(setUpInPanel, c);
//		c.gridx = 0;
//		c.gridy = 1;
//		c.ipady = 687;
//		c.ipadx = 1100;
//		getContentPane().add(calendarOwnerPanel, c);
		//set calendar to initial state
		previousPanel = setUpInPanel;
//		previousButton = calendarOwnerButton;
		//reset calendar button
//		calendarOwnerButton.setBorder(new LineBorder(Color.WHITE));
//		calendarOwnerButton.setBackground(Color.WHITE);
//		calendarOwnerButton.setOpaque(true);
//		calendarOwnerButton.setForeground(darkGrey);
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
		c.ipady = 687;
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
		c.ipady = 687;
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
		c.ipady = 687;
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
		c.ipady = 687;
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
		c.ipady = 687;
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
		c.ipady = 687;
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
		c.ipady = 687;
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
		c.ipady = 687;
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
		c.ipady = 687;
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
		c.ipady = 687;
		getContentPane().add(bookAppointmentPanel, c);
		//set this panel as the current panel
		previousPanel = bookAppointmentPanel;
		//refresh page
		refreshData();
	}

}
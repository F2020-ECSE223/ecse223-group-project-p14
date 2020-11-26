package ca.mcgill.ecse.flexibook.view;

import java.awt.BorderLayout;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Locale;
import java.net.URL;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.DateFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.imageio.ImageIO;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOAppointment;

import ca.mcgill.ecse.flexibook.model.Service;
import ca.mcgill.ecse.flexibook.model.TimeSlot;
import ca.mcgill.ecse.flexibook.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse.flexibook.model.Owner;
import ca.mcgill.ecse.flexibook.model.Service;  // @ TODO remove model stuff
import ca.mcgill.ecse.flexibook.controller.TOBusiness;
import ca.mcgill.ecse.flexibook.controller.TOBusinessHour;
import ca.mcgill.ecse.flexibook.controller.TOService;
import ca.mcgill.ecse.flexibook.controller.TOTimeSlot;

public class FlexiBookPage extends JFrame {
	

	private static final long serialVersionUID = -4426310869335015542L;
	
	

	//top bar for owner
	private JPanel topPanelOwner;
	//top bar for customer
	private JPanel topPanelCustomer;
	//panel for log in
	private JPanel LoginPane;
	//panel for set-up business information
	private JPanel setUpInPanel;
	private JPanel backgroundPanel;
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
	private JPanel customerSignUpPanel;
	//panels for calendar tab
	private JPanel calendarWeeklyViewPanel;
	private JPanel calendarMonthlyViewPanel;
	private JPanel calendarMonthlyViewGridPanel;
	private JPanel calendarMonthlyViewTopPanel;
	private JPanel calendarTimes;
	private JPanel calendarBusinessSlots;
	
	
	// initial login panel
	//private JPanel LoginPane;
	private String addLoginError;
	private String addLoginSuccess;
	private String addSignUpError;
	private String addSignUpSuccess;
	private String logOutErrorMessage; 
	private String logOutSuccessMessage; 
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	int xx,xy;
	
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
	
	//list of variables for calendar month view
	private ArrayList<JButton> calendarButtonList = new ArrayList<JButton>();
	private JButton calendarLeftButton;
	private JButton calendarRightButton;
	private JButton previousCalendarButton;
	private int calendarMonth;
	private int calendarYear;
	private int calendarDay;
	private JLabel monthNameLabel;
	private ArrayList<JLabel> dayLabelList = new ArrayList<JLabel>();

	//log in page buttons
	private JButton signUpButton;
	private JButton LogInButton;
	private JButton logInOwnerButton;
	private JButton logInCustomerButton;
	private JButton signUpNewCustomerButton; //may be unnecessary

	private JLabel logINTextLable;
	private JLabel successMessageLogInLabel;
	private JLabel errorMessageLogInLabel;
	private JLabel successMessageSignInLabel;
	private JLabel errorMessageSignInLabel;
	private JLabel successMessageSetUpLabel;
	private JLabel errorMessageSetUpLabel;
	
	//string for errors
	private String addBHError;
	private String deleteBHError;
	private String updateBHError ;
	private String deleteBHSuccess ;
	private String addSetUpSuccess; 
	private String addSetUpError;
	
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
	DefaultTableModel modelBusHour;
	
	//BusinessHour Components
	private JLabel successMessageBusinessHourLabel;
	private JTable existingBusHoursTable;
	private JComboBox<Integer> deleteBusinessHourBox;
	private JComboBox<Integer> updateBusinessHourBox;
	private JComboBox<String> addDayOfWeek;
	private JComboBox<String> updateDayOfWeek;
	private JSpinner addStartTimeSpin;
	private JSpinner addEndTimeSpin;
	private JSpinner updateStartTimeSpin;
	private JSpinner updateEndTimeSpin;
	
	//top bar icons
	private ImageIcon infoIconDark;
	private ImageIcon infoIconLight;
	private ImageIcon logOutIconDark;
	private ImageIcon logOutIconLight;
	
	//calendar page icons
	private ImageIcon calendarWithTimesIcon;
	private ImageIcon calendarWithoutTimesIcon;
	private ImageIcon calendarLeftIcon;
	private ImageIcon calendarRightIcon;

	//page labels

	private JLabel singleServicesLabel;
	private JLabel comboServicesLabel;
	private JLabel calendarLabel;
	private JLabel businessHoursLabel;
	private JLabel businessDetailsLabel;
	private JLabel logOutLabel;
	
	//Info panels
	private ImageIcon infoUserIcon;
	private JLabel infoUserLabel; // holds the icon
	private JLabel infoLabel; //manage your account	
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel confirmPasswordLabel;
	private JTextField usernameBox; 
	private JPasswordField passwordBox; 
	private JPasswordField confirmPasswordBox;
	private JButton saveAccountButton; //Save button
	private JButton deleteAccountButton; //delete button
	private JLabel bookAppointmentLabel;
	private JLabel setUpBusinessInfoLabel;
	private JLabel updateSuccessful; 
	private String successful = null;

	//tracking last page
	private JButton previousButton;
	private JPanel previousPanel;

	//color of top bar
	private Color darkGrey = new Color(62,62,62);
	private Color lightBlue = new Color(31,184,252);
	

	// Error message 
	private JLabel errorMessage;
	private String error = null;
	
	

	/**
	 * Appointment page
	 */
	private JTextField newAppDateT;
	private JTextField optionalServiceNamesT; // optional service name
	private JTextField serviceNameT; // service Name
	private JTextField selectedAppDateT;
	private JTextField newUpdateAppDateT;
	private JTextField updateComboItemNameL;
	private JTable viewAppForCurCustomerTable;
	private JScrollPane  viewAppForCurCustomerScrollPane;
	private DefaultTableModel vAFCCTableModel;
	private String vAFCCTableColumnNames[] = {"Service Name", " start at", "Downtimes", "end at"} ;
	private JDatePickerImpl newDatePicker;
	private JDatePickerImpl registerDatePicker;
	private JDatePickerImpl selectDatePicker;
	private JSpinner registerTimeSpinner;
	private JSpinner newTimeSpinner;
	private JSpinner selectTimeSpinner;
	private JButton updateContentB;
	private JButton updateTimeB;
	private JButton addAppForSingleServiceB;
	private JButton addAppForComboB;
	private String appSectionError;
	private JComboBox<String> updateActionComboBox;
	private JComboBox<String> existingCb;
	private JLabel errorMsgLabel;
	private JButton cancelAppB;
	
	private double initLogInPageScalingFactor = 740/490;
	/**
	 * Appointment page end
	 */
	
	// singe service page 
	private JTextField newServiceNameTextField;
	private JTextField newServiceDurationTextField;
	private JTextField newServiceDowntimeDurationTextField;
	private JTextField newServiceDowntimeStartTextField;
	private JComboBox<String> deleteServiceComboBox;
	private JTextField updateServiceNameTextField;
	private JTextField updateServiceDurationTextField;
	private JTextField updateDowntimeDurationTextField;
	private JTextField updateDowntimeStartTextField;
	private String errorMessageSingleService = null;
	private JLabel deleteSuccessLabel;
	private JLabel addSuccessLabel;
	private JLabel errorMessageSingleServiceLabel; 
	private String addSuccess = null;
	private String deleteSuccess = null;
	private String updateSuccess = null;
	Object[] row;
	private JTable existingServiceTable;
	JComboBox<String> updateServiceComboBox;
	JLabel updateErrorLabel;
	JLabel updateSuccessLabel;
	DefaultTableModel modelModifySingleService;
	

	/** Creates new form FlexiBookPage */
	public FlexiBookPage() {
		// this manually overides the look and feel of the UI. If we can't fix an OS issue, we can use this 
		try {
			UIManager.setLookAndFeel( "javax.swing.plaf.metal.MetalLookAndFeel"); 	// change to "com.sun.java.swing.plaf.motif.MotifLookAndFeel"
																					// change to "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
			| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
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
		setPreferredSize(new Dimension(1100,740));
		setResizable(false);
		getContentPane().setLayout(null);

		//initialize log in page
		initLogInPage();
		//initLogInOwnerPanel();
		
		initSetBusinessInfo();
		initTopBarOwner();
		initTopBarCustomer();

		//add log in page to the frame
		getContentPane().add(LoginPane);
		LoginPane.setBounds(0,0, 1100, 740);

		//refresh page
		pack();

	}

	//initialize log in page
	private void initLogInPage(){
//		//initialize log in panel layout
//		FlowLayout logInLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
//		LoginPane = new JPanel();
//		
//		LoginPane.setLayout(logInLayout);
//		LoginPane.setPreferredSize(new Dimension(1100,40));
//		//initialize owner log in button
//		signUpButton = new JButton();
//		signUpButton.setText("Owner");
//		signUpButton.setPreferredSize(new Dimension(200, 40));
//		signUpButton.setBorder(new LineBorder(darkGrey));
//		signUpButton.setBackground(Color.WHITE);
//		signUpButton.setOpaque(true);
//		signUpButton.setForeground(darkGrey);
//		//initialize customer log in button
//		LogInButton = new JButton();
//		LogInButton.setText("Customer");
//		LogInButton.setPreferredSize(new Dimension(200, 40));
//		LogInButton.setBorder(new LineBorder(darkGrey));
//		LogInButton.setBackground(Color.WHITE);
//		LogInButton.setOpaque(true);
//		LogInButton.setForeground(darkGrey);
//		
//		//initialize Text JLabel 
//		logINTextLable = new JLabel("I'm A/An:  ");
//		logINTextLable.setHorizontalAlignment(SwingConstants.LEFT);
//		logINTextLable.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 20));
//		logINTextLable.setBounds(155, 313, 115, 21);
//		
//		LoginPane.add(logINTextLable);
//		LoginPane.add(signUpButton);
//		LoginPane.add(LogInButton);
		LoginPane = new JPanel();
		LoginPane.setBackground(Color.DARK_GRAY);
		LoginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(LoginPane);
		LoginPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 500, 740);
		LoginPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hi,");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblNewLabel.setForeground(new Color(240, 248, 255));
		lblNewLabel.setBounds((int)(139*initLogInPageScalingFactor), (int)(305*initLogInPageScalingFactor), (int)(84*initLogInPageScalingFactor), (int)(27*initLogInPageScalingFactor));
		panel.add(lblNewLabel);
		
//		JLabel lblWeGotYou = new JLabel("Thanks for choosing us!");
//		lblWeGotYou.setHorizontalAlignment(SwingConstants.CENTER);
//		lblWeGotYou.setForeground(new Color(240, 248, 255));
//		lblWeGotYou.setFont(new Font("Tahoma", Font.PLAIN, 13));
//		lblWeGotYou.setBounds((int)(111*initLogInPageScalingFactor),(int)(343*initLogInPageScalingFactor), (int)(141*initLogInPageScalingFactor), 50);
//		panel.add(lblWeGotYou);
		
		JLabel label = new JLabel("");
//		
//		label.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				
//				 xx = e.getX();
//			     xy = e.getY();
//			}
//		});
//		label.addMouseMotionListener(new MouseMotionAdapter() {
//			@Override
//			public void mouseDragged(MouseEvent arg0) {
//				
//				int x = arg0.getXOnScreen();
//	            int y = arg0.getYOnScreen();
//	            FlexiBookPage.this.setLocation(x - xx, y - xy);  
//			}
//		});
		label.setBounds(0, 0, 1000, 1000);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon("src/main/resources/bg.jpg"));
		panel.add(label);
		
		
		Button signUpButton = new Button("SignUp");
		signUpButton.setForeground(Color.WHITE);
		signUpButton.setBackground(new Color(241, 57, 83));
		signUpButton.setBounds(600, (int)(363*initLogInPageScalingFactor), (int)(100*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
		LoginPane.add(signUpButton);
		
		Button LogInButton = new Button("Login");
		LogInButton.setForeground(Color.WHITE);
		LogInButton.setBackground(new Color(29, 209, 152));
		LogInButton.setBounds(600+(int)(100*initLogInPageScalingFactor+20), (int)(363*initLogInPageScalingFactor), (int)(100*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
		LoginPane.add(LogInButton);
		
//		textField = new JTextField();
//		textField.setBounds(600, (int)(83*initLogInPageScalingFactor), (int)(283*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
//		LoginPane.add(textField);
//		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setBounds(600, (int)(132*initLogInPageScalingFactor), (int)(114*initLogInPageScalingFactor), (int)(14*initLogInPageScalingFactor));
		lblUsername.setForeground(new Color(240, 248, 255));
		LoginPane.add(lblUsername);
		
//		JLabel lblEmail = new JLabel("EMAIL");
//		lblEmail.setBounds(395, 132, 54, 14);
//		LoginPane.add(lblEmail);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(600, (int)(157*initLogInPageScalingFactor), (int)(283*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
		
		LoginPane.add(textField_1);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(600,(int)(204*initLogInPageScalingFactor), (int)(96*initLogInPageScalingFactor), (int)(14*initLogInPageScalingFactor));
		lblPassword.setForeground(new Color(240, 248, 255));
		LoginPane.add(lblPassword);
		
		JLabel lblRepeatPassword = new JLabel("REPEAT PASSWORD");
		lblRepeatPassword.setBounds(600, (int)(275*initLogInPageScalingFactor), (int)(133*initLogInPageScalingFactor), (int)(14*initLogInPageScalingFactor));
		lblRepeatPassword.setForeground(new Color(240, 248, 255));
		lblRepeatPassword.setText("");
		LoginPane.add(lblRepeatPassword);
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(600, (int)(229*initLogInPageScalingFactor), (int)(283*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
		LoginPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(600, (int)(293*initLogInPageScalingFactor), (int)(283*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
		LoginPane.add(passwordField_1);
		
		successMessageLogInLabel = new JLabel("");
		successMessageLogInLabel.setForeground(Color.GREEN);
		successMessageLogInLabel.setBounds(600, 600, 600, 50);
		LoginPane.add(successMessageLogInLabel);

		errorMessageLogInLabel = new JLabel("");
		errorMessageLogInLabel.setForeground(Color.RED);
		errorMessageLogInLabel.setBounds(600, 600, 600, 50);
		LoginPane.add(errorMessageLogInLabel);
		
		successMessageSignInLabel = new JLabel("");
		successMessageSignInLabel.setForeground(Color.GREEN);
		successMessageSignInLabel.setBounds(600, 600, 600, 50);
		LoginPane.add(successMessageSignInLabel);
		
		errorMessageSignInLabel = new JLabel("");
		errorMessageSignInLabel.setForeground(Color.RED);
		errorMessageSignInLabel.setBounds(600, 600, 600, 50);
		LoginPane.add(errorMessageSignInLabel);
		

//		JLabel lbl_close = new JLabel("Back");
//		lbl_close.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				
//				System.exit(0);
//			}
//		});
//		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
//		lbl_close.setForeground(new Color(241, 57, 83));
//		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
//		lbl_close.setBounds(900, (int)(0*initLogInPageScalingFactor), (int)(37*initLogInPageScalingFactor), (int)(27*initLogInPageScalingFactor));
//		LoginPane.add(lbl_close);
//		// Mike add this end ---
		
		
		//initialize customer log in button listener
		LogInButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginUserButtonPerformed(evt);
			}
		});
		
//		logInOwnerButton.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				if(!FlexiBookApplication.getFlexiBook().hasBusiness()) {
//				logInOwnerButtonToSetUpActionPerformed(evt);
//				}else {
//					logInOwnerButtonActionPerformed(evt);
//				}
//			}
//			});
		
		//To remove once the code is done 
//		if(!FlexiBookApplication.getFlexiBook().hasBusiness()) {
//			//initialize owner log in button listener
//			logInOwnerButton.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent evt) {
//					logInOwnerButtonToSetUpActionPerformed(evt);
//					
//				}
//			});
//		}
//		
//		else {
//			logInOwnerButton.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent evt) {
//					logInOwnerButtonActionPerformed(evt);
//					//logInCustomerButtonActionPerformed(evt);
//				}
//			});		
//		}

//		if(FlexiBookApplication.getFlexiBook().getBusiness()==null) {
//			//initialize owner log in button listener
//			signUpButton.addActionListener(new java.awt.event.ActionListener() {
//				public void actionPerformed(java.awt.event.ActionEvent evt) {
//					//logInCustomerButtonActionPerformed(evt);
//					
//					
//				}
//			});
//		}
		
//		else {
			signUpButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					SignUpCustomerButtonPerformed(evt);
					//logInCustomerButtonActionPerformed(evt);
				}
			});		

//		}



	}
	
	//initialize the business information set-up
	private void initSetBusinessInfo() {
		
		//JLayeredPane lpane = new JLayeredPane();
		//backgroundPanel = new JPanel();
		//lpane.setBounds(0,0,1100,740);
		//backgroundPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//backgroundPanel.setLayout(null);
		// backgroundPanel.setPreferredSize(new Dimension(1100,740));
		//backgroundPanel.setBounds(0, 0, 1100, 740);
		
//		JLabel initSetBusinesslabel = new JLabel("");
//		initSetBusinesslabel.setBounds(0, 0, 1100, 740);
//		initSetBusinesslabel.setVerticalAlignment(SwingConstants.TOP);
//		initSetBusinesslabel.setIcon(new ImageIcon("src/main/resources/bg.jpg"));
		//backgroundPanel.add(initSetBusinesslabel);
		//backgroundPanel.setOpaque(true);
		
		setUpInPanel = new JPanel();
		setUpInPanel.setBackground(Color.DARK_GRAY);
		setUpInPanel.setBorder (new EmptyBorder(5, 5, 5, 5));
		setUpInPanel.setLayout(null);
		setUpBusinessInfoLabel = new JLabel("Info Page");
		
		//setUpInPanel.setBounds(400,200,400,700);

		//setUpInPanel.setOpaque(true);
		setUpInPanel.add(setUpBusinessInfoLabel);

		setDetailBtn = new JButton("Set");
		setDetailBtn.setBounds(500,400,89,23);
		setDetailBtn.setBackground(Color.GREEN);
		setDetailBtn.setForeground(Color.DARK_GRAY);
		setUpInPanel.add(setDetailBtn);
		//backgroundPanel.add(setUpInPanel,BorderLayout.CENTER);


		//lpane.add(backgroundPanel, 0, 0);
		//lpane.add(setUpInPanel, 1, 0);
		
		//Setting the UI for setting business information
		JLabel businessNameSet = new JLabel("Business name");
		businessNameSet.setBounds(500, 100, 150, 23);
		businessNameSet.setForeground(new Color(240, 248, 255));
		setUpInPanel.add(businessNameSet);

		txtBusinessNameSet = new JTextField();
		txtBusinessNameSet.setBounds(500, 130, 200, 23);
		setUpInPanel.add(txtBusinessNameSet);
		txtBusinessNameSet.setColumns(10);

		JLabel adressSet = new JLabel("Address");
		adressSet.setBounds(500, 170, 70, 23);
		adressSet.setForeground(new Color(240, 248, 255));
		setUpInPanel.add(adressSet);

		txtAdressSet= new JTextField();
		txtAdressSet.setText("");
		txtAdressSet.setBounds(500, 200, 200, 23);
		setUpInPanel.add(txtAdressSet);
		txtAdressSet.setColumns(10);

		JLabel phoneNumberSet  = new JLabel("Phone Number");
		phoneNumberSet.setBounds(500, 230, 150, 23);
		phoneNumberSet.setForeground(new Color(240, 248, 255));
		setUpInPanel.add(phoneNumberSet);

		txtPhoneNumberSet = new JTextField();
		txtPhoneNumberSet.setBounds(500, 260, 200, 23);
		setUpInPanel.add(txtPhoneNumberSet);
		txtPhoneNumberSet.setColumns(10);

		JLabel emailSet = new JLabel("Email");
		emailSet.setBounds(500, 290, 200, 23);
		emailSet.setForeground(new Color(240, 248, 255));
		setUpInPanel.add(emailSet);

		txtEmailSet = new JTextField();
		txtEmailSet.setBounds(500, 320, 200, 23);
		setUpInPanel.add(txtEmailSet);
		txtEmailSet.setColumns(10);	
		
		successMessageSetUpLabel = new JLabel("");
		successMessageSetUpLabel.setForeground(Color.GREEN);
		successMessageSetUpLabel.setBounds(600, 600, 600, 50);
		setUpInPanel.add(successMessageSetUpLabel);
		
		errorMessageSetUpLabel = new JLabel("");
		errorMessageSetUpLabel.setForeground(Color.RED);
		errorMessageSetUpLabel.setBounds(600, 600, 600, 50);
		setUpInPanel.add(errorMessageSetUpLabel);

		setDetailBtn.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent evt) {
					addSetUpSuccess = null; 
					addSetUpError = null;
					if (txtBusinessNameSet.getText().equals("") || txtAdressSet.getText().equals("")
							|| txtPhoneNumberSet.getText().equals("") || txtEmailSet.getText().equals("")) {
						addSetUpError = "One of above field is empty";
					} else {
						addSetUpSuccess = "Success!";
						setUpBusinessInformation(evt);
					}
					refreshBusinessSetUp();
					
				}
			});
		
		
	}
	
//	/**
//	 * @TODO For Mike Login Owner page
//	 */
//	//initialize Login Owner page
//	private void initLogInOwnerPanel(){
////		infoOwnerPanel = new JPanel();
////		infoLabel = new JLabel("Info Page");
////		infoOwnerPanel.setPreferredSize(new Dimension(1100,700));
////		infoOwnerPanel.setBackground(Color.WHITE);
////		infoOwnerPanel.setOpaque(true);
////		infoOwnerPanel.setForeground(Color.WHITE);
////		infoOwnerPanel.add(infoLabel);
//		
//		// Mike add this start --- 
////		setBackground(Color.WHITE);
////		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		setBounds(100, 100, 729, 476);
//		LoginPane = new JPanel();
//		LoginPane.setBackground(Color.WHITE);
//		LoginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(LoginPane);
//		LoginPane.setLayout(null);
//		
//		JPanel panel = new JPanel();
//		panel.setBackground(Color.DARK_GRAY);
//		panel.setBounds(0, 0, 346, 490);
//		LoginPane.add(panel);
//		panel.setLayout(null);
//		
//		JLabel lblNewLabel = new JLabel("KeepToo");
//		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
//		lblNewLabel.setForeground(new Color(240, 248, 255));
//		lblNewLabel.setBounds(139, 305, 84, 27);
//		panel.add(lblNewLabel);
//		
////		JLabel label = new JLabel("");
////		
////		label.addMouseListener(new MouseAdapter() {
////			@Override
////			public void mousePressed(MouseEvent e) {
////				
////				 xx = e.getX();
////			     xy = e.getY();
////			}
////		});
////		label.addMouseMotionListener(new MouseMotionAdapter() {
////			@Override
////			public void mouseDragged(MouseEvent arg0) {
////				
////				int x = arg0.getXOnScreen();
////	            int y = arg0.getYOnScreen();
////	            FlexiBookPage.this.setLocation(x - xx, y - xy);  
////			}
////		});
////		label.setBounds(-38, 0, 420, 275);
////		label.setVerticalAlignment(SwingConstants.TOP);
////		// label.setIcon(new ImageIcon(FlexiBookPage.class.getResource("/images/bg.jpg")));
////		panel.add(label);
//		
//		JLabel lblWeGotYou = new JLabel("....We got you....");
//		lblWeGotYou.setHorizontalAlignment(SwingConstants.CENTER);
//		lblWeGotYou.setForeground(new Color(240, 248, 255));
//		lblWeGotYou.setFont(new Font("Tahoma", Font.PLAIN, 13));
//		lblWeGotYou.setBounds(111, 343, 141, 27);
//		panel.add(lblWeGotYou);
//		
//		Button button = new Button("SignUp");
//		button.setForeground(Color.WHITE);
//		button.setBackground(new Color(241, 57, 83));
//		button.setBounds(395, 363, 283, 36);
//		LoginPane.add(button);
//		
//		textField = new JTextField();
//		textField.setBounds(395, 83, 283, 36);
//		LoginPane.add(textField);
//		textField.setColumns(10);
//		
//		JLabel lblUsername = new JLabel("USERNAME");
//		lblUsername.setBounds(395, 58, 114, 14);
//		LoginPane.add(lblUsername);
//		
//		JLabel lblEmail = new JLabel("EMAIL");
//		lblEmail.setBounds(395, 132, 54, 14);
//		LoginPane.add(lblEmail);
//		
//		textField_1 = new JTextField();
//		textField_1.setColumns(10);
//		textField_1.setBounds(395, 157, 283, 36);
//		LoginPane.add(textField_1);
//		
//		JLabel lblPassword = new JLabel("PASSWORD");
//		lblPassword.setBounds(395, 204, 96, 14);
//		LoginPane.add(lblPassword);
//		
//		JLabel lblRepeatPassword = new JLabel("REPEAT PASSWORD");
//		lblRepeatPassword.setBounds(395, 275, 133, 14);
//		LoginPane.add(lblRepeatPassword);
//		
//		passwordField = new JPasswordField();
//		passwordField.setBounds(395, 229, 283, 36);
//		LoginPane.add(passwordField);
//		
//		passwordField_1 = new JPasswordField();
//		passwordField_1.setBounds(395, 293, 283, 36);
//		LoginPane.add(passwordField_1);
//		
//		JLabel lbl_close = new JLabel("Back");
//		lbl_close.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				
//				System.exit(0);
//			}
//		});
//		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
//		lbl_close.setForeground(new Color(241, 57, 83));
//		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
//		lbl_close.setBounds(691, 0, 37, 27);
//		LoginPane.add(lbl_close);
//		// Mike add this end ---
//	}
	
	
//	/**
//	 * @TODO For Mike Login Customer page
//	 */
//	//initialize Login Customer page
//	private void initLogInCustomerPanel(){
//		infoOwnerPanel = new JPanel();
//		infoLabel = new JLabel("Info Page");
//		infoOwnerPanel.setPreferredSize(new Dimension(1100,700));
//		infoOwnerPanel.setBackground(Color.WHITE);
//		infoOwnerPanel.setOpaque(true);
//		infoOwnerPanel.setForeground(Color.WHITE);
//		infoOwnerPanel.add(infoLabel);
//
//		//TO DO
//	}
	/**
	 * @TODO 
	 * 		- define loginOwnerComfirmeInputButton && Button related action performed method 
	 * 		- define loginCustomerComfirmeInoutButton && Button related action performed method 
	 * 		- Relink the ownerbutton pressed in login inital set page to initLogInOwnerPanel()
	 * 		- Relink the customerbutton pressed in login inital set page to initLogInCustomerPanel()
	 * 		- Link initLogInOwnerPanel() with initTopBarOwner()
	 * 		- Link initLogInCustomerPanel() with initTopBarCustomer()
	 */


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
		infoOwnerButton.setBorder(BorderFactory.createEmptyBorder());
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
		logOutOwnerButton.setBorder(BorderFactory.createEmptyBorder());
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
		infoCustomerButton.setBorder(BorderFactory.createEmptyBorder());
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
		logOutCustomerButton.setBorder(BorderFactory.createEmptyBorder());
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
				refreshAppointmentPage(); // AntoineW added This
			}
		});

	}
	

	//initialize info panel for owner
	private void initInfoOwnerPanel(){
		infoOwnerPanel = new JPanel();
		infoOwnerPanel.setLayout(null);
		infoOwnerPanel.setPreferredSize(new Dimension(1100,700));
		infoOwnerPanel.setBackground(Color.WHITE);
		infoOwnerPanel.setOpaque(true);
		infoOwnerPanel.setForeground(Color.darkGray);
		
		//initialize image icon
		infoUserIcon = new ImageIcon("src/main/resources/user.png");
		infoUserIcon.setImage(infoUserIcon.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)); //resize
		
		infoUserLabel = new JLabel();
		infoUserLabel.setIcon(infoUserIcon);
		infoUserLabel.setBounds(445, 50, 200, 200);
		infoUserLabel.setOpaque(false);
		infoUserLabel.setForeground(Color.darkGray);
		infoUserLabel.setAlignmentX(SwingConstants.CENTER);
		
		infoLabel = new JLabel("Manage Your Account");
		infoLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setBounds(470, 300, 150, 30);
		infoLabel.setBackground(Color.WHITE);
		infoLabel.setOpaque(true);
		infoLabel.setForeground(Color.darkGray);
		
		usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		usernameLabel.setBounds(350, 350, 80, 30);
		usernameLabel.setBackground(Color.WHITE);
		usernameLabel.setOpaque(true);
		usernameLabel.setForeground(Color.darkGray);
		usernameLabel.setAlignmentX(SwingConstants.CENTER);
		
		usernameBox = new JTextField(FlexiBookController.getCurrentLogInUsername());
		usernameBox.setColumns(20);
		usernameBox.setBounds(470, 350, 250, 30);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		passwordLabel.setBounds(350, 400, 80, 30);
		passwordLabel.setBackground(Color.WHITE);
		passwordLabel.setOpaque(true);
		passwordLabel.setForeground(Color.darkGray);
		passwordLabel.setAlignmentX(SwingConstants.CENTER);
		
		passwordBox = new JPasswordField();
		passwordBox.setColumns(20);
		passwordBox.setBounds(470, 400, 250, 30);
		
		confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		confirmPasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		confirmPasswordLabel.setBounds(350, 450, 80, 30);
		confirmPasswordLabel.setBackground(Color.WHITE);
		confirmPasswordLabel.setOpaque(true);
		confirmPasswordLabel.setForeground(Color.darkGray);
		confirmPasswordLabel.setAlignmentX(SwingConstants.CENTER);
		
		confirmPasswordBox = new JPasswordField();
		confirmPasswordBox.setColumns(20);
		confirmPasswordBox.setBounds(470, 450, 250, 30);
		
		saveAccountButton = new JButton("Save");
		saveAccountButton.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		saveAccountButton.setBounds(500, 500, 100, 30);
		saveAccountButton.setAlignmentX(CENTER_ALIGNMENT);
		saveAccountButton.setBorder(new LineBorder(Color.darkGray));
		saveAccountButton.setBackground(Color.darkGray);
		saveAccountButton.setOpaque(true);
		saveAccountButton.setForeground(Color.WHITE);
		
		errorMessage.setText(error);
		errorMessage.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		errorMessage.setBounds(SwingConstants.CENTER, 550, 500, 30);
		
		updateSuccessful = new JLabel(successful);
		updateSuccessful.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		updateSuccessful.setForeground(Color.GREEN);
		updateSuccessful.setHorizontalAlignment(SwingConstants.CENTER);
		updateSuccessful.setBounds(SwingConstants.CENTER, 550, 500, 30);
		
		infoOwnerPanel.add(infoUserLabel);
		infoOwnerPanel.add(infoLabel);
		infoOwnerPanel.add(usernameLabel);
		infoOwnerPanel.add(usernameBox);
		infoOwnerPanel.add(passwordLabel);
		infoOwnerPanel.add(passwordBox);
		infoOwnerPanel.add(confirmPasswordLabel);
		infoOwnerPanel.add(confirmPasswordBox);
		infoOwnerPanel.add(saveAccountButton);
		infoOwnerPanel.add(errorMessage);
		infoOwnerPanel.add(updateSuccessful);
		
		
		saveAccountButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveAccountInfoActionPerformed(evt);
			}
		});
		
		
	}

	//initialize info panel for customer
	private void initInfoCustomerPanel(){

		infoCustomerPanel = new JPanel();
		infoCustomerPanel.setLayout(null);
		infoCustomerPanel.setPreferredSize(new Dimension(1100,700));
		infoCustomerPanel.setBackground(Color.WHITE);
		infoCustomerPanel.setOpaque(true);
		infoCustomerPanel.setForeground(Color.darkGray);
		
		//initialize image icon
		infoUserIcon = new ImageIcon("src/main/resources/user.png");
		infoUserIcon.setImage(infoUserIcon.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)); //resize

		infoUserLabel = new JLabel();
		infoUserLabel.setIcon(infoUserIcon);
		infoUserLabel.setBounds(445, 50, 200, 200);
		infoUserLabel.setOpaque(false);
		infoUserLabel.setForeground(Color.darkGray);
		infoUserLabel.setAlignmentX(SwingConstants.CENTER);
		
		infoLabel = new JLabel("Manage Your Account");
		infoLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setBounds(470, 300, 150, 30);
		infoLabel.setBackground(Color.WHITE);
		infoLabel.setOpaque(true);
		infoLabel.setForeground(Color.darkGray);
		
		usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		usernameLabel.setBounds(350, 350, 80, 30);
		usernameLabel.setBackground(Color.WHITE);
		usernameLabel.setOpaque(true);
		usernameLabel.setForeground(Color.darkGray);
		usernameLabel.setAlignmentX(SwingConstants.CENTER);
		
		usernameBox = new JTextField(FlexiBookController.getCurrentLogInUsername()); 
		usernameBox.setColumns(20);
		usernameBox.setBounds(470, 350, 250, 30);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		passwordLabel.setBounds(350, 400, 80, 30);
		passwordLabel.setBackground(Color.WHITE);
		passwordLabel.setOpaque(true);
		passwordLabel.setForeground(Color.darkGray);
		passwordLabel.setAlignmentX(SwingConstants.CENTER);
		
		passwordBox = new JPasswordField();
		passwordBox.setColumns(20);
		passwordBox.setBounds(470, 400, 250, 30);
		
		confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		confirmPasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		confirmPasswordLabel.setBounds(350, 450, 80, 30);
		confirmPasswordLabel.setBackground(Color.WHITE);
		confirmPasswordLabel.setOpaque(true);
		confirmPasswordLabel.setForeground(Color.darkGray);
		confirmPasswordLabel.setAlignmentX(SwingConstants.CENTER);
		
		confirmPasswordBox = new JPasswordField();
		confirmPasswordBox.setColumns(20);
		confirmPasswordBox.setBounds(470, 450, 250, 30);
		
		saveAccountButton = new JButton("Save");
		saveAccountButton.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		saveAccountButton.setBounds(550, 500, 100, 30);
		saveAccountButton.setAlignmentX(CENTER_ALIGNMENT);
		saveAccountButton.setBorder(new LineBorder(Color.darkGray));
		saveAccountButton.setBackground(Color.darkGray);
		saveAccountButton.setOpaque(true);
		saveAccountButton.setForeground(Color.WHITE);
		
		deleteAccountButton = new JButton("Delete");
		deleteAccountButton.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		deleteAccountButton.setBounds(400, 500, 100, 30);
		deleteAccountButton.setAlignmentX(CENTER_ALIGNMENT);
		deleteAccountButton.setBorder(new LineBorder(Color.darkGray));
		deleteAccountButton.setBackground(Color.WHITE);
		deleteAccountButton.setOpaque(true);
		deleteAccountButton.setForeground(Color.darkGray);
		
		errorMessage.setText(error);
		errorMessage.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		errorMessage.setBounds(SwingConstants.CENTER, 550, 500, 30);
		
		updateSuccessful = new JLabel(successful);
		updateSuccessful.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 13));
		updateSuccessful.setForeground(Color.GREEN);
		updateSuccessful.setHorizontalAlignment(SwingConstants.CENTER);
		updateSuccessful.setBounds(SwingConstants.CENTER, 550, 500, 30);
		
		infoCustomerPanel.add(infoUserLabel);
		infoCustomerPanel.add(infoLabel);
		infoCustomerPanel.add(usernameLabel);
		infoCustomerPanel.add(usernameBox);
		infoCustomerPanel.add(passwordLabel);
		infoCustomerPanel.add(passwordBox);
		infoCustomerPanel.add(confirmPasswordLabel);
		infoCustomerPanel.add(confirmPasswordBox);
		infoCustomerPanel.add(saveAccountButton);
		infoCustomerPanel.add(deleteAccountButton);
		infoOwnerPanel.add(errorMessage);
		infoOwnerPanel.add(updateSuccessful);
		
		
		saveAccountButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveAccountInfoActionPerformed(evt);
			}
		});
		
		deleteAccountButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteAccountInfoActionPerformed(evt);
			}
		});
		

	}


	//initialize single services panel
	private void initSingleServicesPanel(){
		singleServicesPanel = new JPanel();
		singleServicesPanel.setPreferredSize(new Dimension(1100,700));
		singleServicesPanel.setBackground(Color.WHITE);
		singleServicesPanel.setOpaque(true);
		singleServicesPanel.setForeground(Color.WHITE);
		singleServicesPanel.setLayout(null);
		
		JScrollPane serviceScrollPane = new JScrollPane();
		serviceScrollPane.setBounds(479, 6, 524, 404);
		singleServicesPanel.add(serviceScrollPane);
		
		
		errorMessageSingleServiceLabel = new JLabel("");
		errorMessageSingleServiceLabel.setForeground(Color.RED);
		errorMessageSingleServiceLabel.setBounds(47, 17, 366, 16);
		singleServicesPanel.add(errorMessageSingleServiceLabel);
		
		existingServiceTable = new JTable();
		modelModifySingleService = new DefaultTableModel();
		Object[] col = {"Name","Duration","DowntimeDuration","DowntimeStart"};
		row = new Object[0];
		modelModifySingleService.setColumnIdentifiers(col);
		existingServiceTable.setModel(modelModifySingleService);
		serviceScrollPane.setViewportView(existingServiceTable);
		
		
		newServiceNameTextField = new JTextField();
		newServiceNameTextField.setColumns(10);
		newServiceNameTextField.setBounds(254, 139, 130, 26);
		singleServicesPanel.add(newServiceNameTextField);

		JLabel newServiceDurationLabel = new JLabel("New Service Duration");
		newServiceDurationLabel.setBounds(39, 170, 134, 16);
		singleServicesPanel.add(newServiceDurationLabel);

		newServiceDurationTextField = new JTextField();
		newServiceDurationTextField.setColumns(10);
		newServiceDurationTextField.setBounds(254, 165, 130, 26);
		singleServicesPanel.add(newServiceDurationTextField);

		JLabel newServiceDowntimeDurationLabel = new JLabel("New Service Downtime Duration");
		newServiceDowntimeDurationLabel.setBounds(39, 203, 202, 16);
		singleServicesPanel.add(newServiceDowntimeDurationLabel);

		newServiceDowntimeDurationTextField = new JTextField();
		newServiceDowntimeDurationTextField.setColumns(10);
		newServiceDowntimeDurationTextField.setBounds(254, 198, 130, 26);
		singleServicesPanel.add(newServiceDowntimeDurationTextField);

		JLabel newSearviceDowntimeStartLabel = new JLabel("New Service Downtime Start");
		newSearviceDowntimeStartLabel.setBounds(39, 231, 176, 16);
		singleServicesPanel.add(newSearviceDowntimeStartLabel);

		newServiceDowntimeStartTextField = new JTextField();
		newServiceDowntimeStartTextField.setColumns(10);
		newServiceDowntimeStartTextField.setBounds(254, 226, 130, 26);
		singleServicesPanel.add(newServiceDowntimeStartTextField);

		JButton confirmAddServiceButton = new JButton("Confirm");
		confirmAddServiceButton.setBounds(171, 264, 95, 29);
		singleServicesPanel.add(confirmAddServiceButton);
		
		JLabel newServiceNameLabel = new JLabel("New Service Name");
		newServiceNameLabel.setBounds(39, 144, 122, 16);
		singleServicesPanel.add(newServiceNameLabel);
		
		addSuccessLabel = new JLabel("");
		addSuccessLabel.setForeground(Color.GREEN);
		addSuccessLabel.setBounds(278, 277, 157, 16);
		singleServicesPanel.add(addSuccessLabel);
		
		deleteServiceComboBox = new JComboBox<String>();
		deleteServiceComboBox.setBounds(674, 545, 262, 48);
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				deleteServiceComboBox.addItem(service.getName());
			}
		}
		singleServicesPanel.add(deleteServiceComboBox);
		
		JLabel selectDeleteServiceLabel = new JLabel("Select the service you want to delete:");
		selectDeleteServiceLabel.setBounds(684, 500, 252, 38);
		singleServicesPanel.add(selectDeleteServiceLabel);
		
		JButton confirmDeleteServiceButton = new JButton("Confirm");
		confirmDeleteServiceButton.setBounds(750, 625, 95, 29);
		singleServicesPanel.add(confirmDeleteServiceButton);
		
		deleteSuccessLabel = new JLabel("");
		deleteSuccessLabel.setForeground(Color.GREEN);
		deleteSuccessLabel.setBounds(853, 638, 150, 16);
		singleServicesPanel.add(deleteSuccessLabel);
		
		updateServiceComboBox = new JComboBox<String>();
		updateServiceComboBox.setBounds(106, 479, 235, 27);
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				updateServiceComboBox.addItem(service.getName());
			}
		}
		singleServicesPanel.add(updateServiceComboBox);
		
		JLabel selectServiceUpdateLabel = new JLabel("Select the service you want to update:");
		selectServiceUpdateLabel.setBounds(51, 451, 312, 16);
		singleServicesPanel.add(selectServiceUpdateLabel);
		
		JLabel updateServiceNameLabel = new JLabel("New Service Name");
		updateServiceNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		updateServiceNameLabel.setBounds(46, 521, 115, 21);
		singleServicesPanel.add(updateServiceNameLabel);
		
		updateServiceNameTextField = new JTextField();
		updateServiceNameTextField.setColumns(10);
		updateServiceNameTextField.setBounds(270, 525, 130, 26);
		singleServicesPanel.add(updateServiceNameTextField);
		
		JLabel updateServiceDurationLabel = new JLabel("New Service Duration");
		updateServiceDurationLabel.setBounds(46, 549, 134, 16);
		singleServicesPanel.add(updateServiceDurationLabel);
		
		updateServiceDurationTextField = new JTextField();
		updateServiceDurationTextField.setColumns(10);
		updateServiceDurationTextField.setBounds(270, 551, 130, 26);
		singleServicesPanel.add(updateServiceDurationTextField);
		
		JLabel updateServiceDowntimeDurationLabel = new JLabel("New Service Downtime Duration");
		updateServiceDowntimeDurationLabel.setBounds(46, 572, 212, 21);
		singleServicesPanel.add(updateServiceDowntimeDurationLabel);
		
		updateDowntimeDurationTextField = new JTextField();
		updateDowntimeDurationTextField.setColumns(10);
		updateDowntimeDurationTextField.setBounds(270, 576, 130, 26);
		singleServicesPanel.add(updateDowntimeDurationTextField);
		
		JLabel updateServiceDowntimeStartLabel = new JLabel("New Service Downtime Start");
		updateServiceDowntimeStartLabel.setBounds(46, 597, 212, 21);
		singleServicesPanel.add(updateServiceDowntimeStartLabel);
		
		updateDowntimeStartTextField = new JTextField();
		updateDowntimeStartTextField.setColumns(10);
		updateDowntimeStartTextField.setBounds(270, 601, 130, 26);
		singleServicesPanel.add(updateDowntimeStartTextField);
		
		JButton confirmUpdateServiceButton = new JButton("Confirm");
		confirmUpdateServiceButton.setBounds(166, 625, 117, 29);
		singleServicesPanel.add(confirmUpdateServiceButton);
		
		updateSuccessLabel = new JLabel("");
		updateSuccessLabel.setForeground(Color.GREEN);
		updateSuccessLabel.setBounds(296, 638, 104, 16);
		singleServicesPanel.add(updateSuccessLabel);
		
		JLabel addServiceIcon = new JLabel("Add Service");
		addServiceIcon.setForeground(Color.BLUE);
		addServiceIcon.setFont(new Font("Kokonor", Font.PLAIN, 20));
		addServiceIcon.setBounds(166, 101, 134, 26);
		singleServicesPanel.add(addServiceIcon);
		
		JLabel updateServiceIcon = new JLabel("Update Service");
		updateServiceIcon.setFont(new Font("Kokonor", Font.PLAIN, 20));
		updateServiceIcon.setForeground(Color.BLUE);
		updateServiceIcon.setBounds(150, 394, 207, 45);
		singleServicesPanel.add(updateServiceIcon);
		
		JLabel deleteServiceIcon = new JLabel("Delete Service");
		deleteServiceIcon.setFont(new Font("Kokonor", Font.PLAIN, 20));
		deleteServiceIcon.setForeground(Color.BLUE);
		deleteServiceIcon.setBounds(732, 451, 176, 38);
		singleServicesPanel.add(deleteServiceIcon);
		
		confirmAddServiceButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
					addSingleServicesButtonActionPerformed(evt);			
			}		
		});
		
		confirmDeleteServiceButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (!FlexiBookController.getTOServices().isEmpty()) {
					deleteSingleServicesButtonActionPerformed(evt);
				}
			}		
		});
		
		confirmUpdateServiceButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (!FlexiBookController.getTOServices().isEmpty()) {
					updateSingleServicesButtonActionPerformed(evt);
				}
			}		
		});

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


	}

	//initialize calendar services panel for owner
	private void initCalendarOwnerPanel(){
		//set month and change month icons
		calendarMonth = 11;
		calendarYear = 2020;
		calendarDay = -1;
		//create calendar owner panel
		calendarOwnerPanel = new JPanel();
		calendarOwnerPanel.setLayout(null);
		calendarOwnerPanel.setPreferredSize(new Dimension(1100,700));
		//set background
		calendarOwnerPanel.setBackground(Color.WHITE);
		calendarOwnerPanel.setOpaque(true);
		calendarOwnerPanel.setForeground(Color.WHITE);
		//create calendar view panel
		calendarWeeklyViewPanel = new JPanel();
		calendarWeeklyViewPanel.setLayout(null);
		calendarWeeklyViewPanel.setPreferredSize(new Dimension(700,700));
		//create calendar business slot panel
		calendarBusinessSlots = new JPanel();
		calendarBusinessSlots.setLayout(null);
		calendarBusinessSlots.setOpaque(false);
		calendarBusinessSlots.setPreferredSize(new Dimension(700,700));

		calendarTimes = new JPanel();
		calendarTimes.setLayout(null);
		calendarTimes.setPreferredSize(new Dimension(40,520));
		calendarTimes.setBackground(Color.WHITE);
		//initialize image icons
		try{
			calendarLeftIcon = new ImageIcon("Calendar_LeftIcon.jpg");
			calendarRightIcon = new ImageIcon("Calendar_RightIcon.jpg");
		} catch(Exception exp) {
			error += exp.getMessage();
		}
		JLabel n = new JLabel("Mon", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(90,40));
		n.setFont(new Font("Roboto", Font.BOLD, 20));
		calendarWeeklyViewPanel.add(n);
		n.setBounds(0+50,35,90,40);
		n = new JLabel("Tue", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(90,40));
		n.setFont(new Font("Roboto", Font.BOLD, 20));
		calendarWeeklyViewPanel.add(n);
		n.setBounds(90+50,35,90,40);
		n = new JLabel("Wed", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(90,40));
		n.setFont(new Font("Roboto", Font.BOLD, 20));
		calendarWeeklyViewPanel.add(n);
		n.setBounds(180+50,35,90,40);
		n = new JLabel("Thu", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(90,40));
		n.setFont(new Font("Roboto", Font.BOLD, 20));
		calendarWeeklyViewPanel.add(n);
		n.setBounds(270+50,35,90,40);
		n = new JLabel("Fri", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(90,40));
		n.setFont(new Font("Roboto", Font.BOLD, 20));
		calendarWeeklyViewPanel.add(n);
		n.setBounds(360+50,35,90,40);
		n = new JLabel("Sat", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(90,40));
		n.setFont(new Font("Roboto", Font.BOLD, 20));
		calendarWeeklyViewPanel.add(n);
		n.setBounds(450+50,35,90,40);
		n = new JLabel("Sun", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(90,40));
		n.setFont(new Font("Roboto", Font.BOLD, 20));
		calendarWeeklyViewPanel.add(n);
		n.setBounds(540+50,35,90,40);
		JLabel p;
		for(int i = 0; i < 6; i++){
			p = new JLabel("I");
			p.setPreferredSize(new Dimension(1,600));
			p.setBackground(new Color(200,200,200));
			p.setOpaque(true);
			calendarWeeklyViewPanel.add(p);
			p.setBounds(90+50+i*90,35,1,600);
		}
		p = new JLabel("I");
		p.setPreferredSize(new Dimension(630,1));
		p.setBackground(new Color(200,200,200));
		p.setOpaque(true);
		calendarWeeklyViewPanel.add(p);
		p.setBounds(50,80+35,630,1);
		p = new JLabel("I");
		p.setPreferredSize(new Dimension(630,1));
		p.setBackground(new Color(200,200,200));
		p.setOpaque(true);
		calendarWeeklyViewPanel.add(p);
		p.setBounds(50,80+35+520,630,1);

		//calendar week days numbers
		if(calendarDay > 0){
			int tempNum = LocalDate.of(calendarYear,calendarMonth,calendarDay).getDayOfWeek().getValue();
			int tempDay = calendarDay-tempNum+1;
			for(int i = 0; i < 7; i++){
				n = new JLabel(Integer.toString(tempDay), SwingConstants.CENTER);
				if(tempDay < 1){
					if(calendarMonth-1 < 1){
						n.setText(Integer.toString(tempDay+LocalDate.of(calendarYear-1,12,1).lengthOfMonth()));
					} else {
						n.setText(Integer.toString(tempDay+LocalDate.of(calendarYear,calendarMonth-1,1).lengthOfMonth()));
					}
				} else if(tempDay > LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()){
					n.setText(Integer.toString(tempDay-LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()));
				} else {
					n.setText(Integer.toString(tempDay));
				}
				n.setPreferredSize(new Dimension(90,40));
				n.setFont(new Font("Roboto", Font.BOLD, 20));
				calendarWeeklyViewPanel.add(n);
				dayLabelList.add(n);
				n.setBounds(i*90+50,35+40,90,40);
				tempDay++;
			}
		} else {
			int tempNum = LocalDate.of(calendarYear,calendarMonth,1).getDayOfWeek().getValue();
			int tempDay = 1-tempNum+1;
			for(int i = 0; i < 7; i++){
				n = new JLabel(Integer.toString(tempDay), SwingConstants.CENTER);
				if(tempDay < 1){
					if(calendarMonth-1 < 1){
						n.setText(Integer.toString(tempDay+LocalDate.of(calendarYear-1,12,1).lengthOfMonth()));
					} else {
						n.setText(Integer.toString(tempDay+LocalDate.of(calendarYear,calendarMonth-1,1).lengthOfMonth()));
					}
				} else if(tempDay > LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()){
					n.setText(Integer.toString(tempDay-LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()));
				} else {
					n.setText(Integer.toString(tempDay));
				}
				n.setPreferredSize(new Dimension(90,40));
				n.setFont(new Font("Roboto", Font.BOLD, 20));
				calendarWeeklyViewPanel.add(n);
				dayLabelList.add(n);
				n.setBounds(i*90+50,35+40,90,40);
				tempDay++;
			}
		}
		
		calendarWeeklyViewPanel.setBackground(Color.WHITE);
		calendarWeeklyViewPanel.setOpaque(true);
		calendarWeeklyViewPanel.setForeground(Color.WHITE);
		calendarOwnerPanel.add(calendarWeeklyViewPanel);
		calendarWeeklyViewPanel.setBounds(400,0,700,700);

		//create calendar monthly view panel
		calendarMonthlyViewPanel = new JPanel();
		calendarMonthlyViewPanel.setLayout(null);
		calendarMonthlyViewPanel.setPreferredSize(new Dimension(350,350));
		//set background
		calendarMonthlyViewPanel.setBackground(Color.WHITE);
		calendarMonthlyViewPanel.setOpaque(true);
		calendarMonthlyViewPanel.setForeground(Color.WHITE);

		monthNameLabel = new JLabel(LocalDate.of(calendarYear,calendarMonth,1).getMonth().getDisplayName(TextStyle.FULL,Locale.CANADA) + " " + calendarYear);
		monthNameLabel.setPreferredSize(new Dimension(50,200));
		monthNameLabel.setFont(new Font("Roboto", Font.BOLD, 20));
		calendarMonthlyViewTopPanel = new JPanel();
		calendarMonthlyViewTopPanel.setLayout(null);
		//set background
		calendarMonthlyViewTopPanel.setBackground(Color.WHITE);
		calendarMonthlyViewTopPanel.setOpaque(true);
		calendarMonthlyViewTopPanel.setForeground(darkGrey);
		calendarMonthlyViewTopPanel.setPreferredSize(new Dimension(350,50));
		//add month and icons
		calendarMonthlyViewTopPanel.add(monthNameLabel);
		monthNameLabel.setBounds(13,10,200,50);
		calendarLeftButton = new JButton();
		calendarLeftButton.setPreferredSize(new Dimension(30,30));
		calendarLeftButton.setText("Left");
		calendarLeftButton.setIcon(calendarLeftIcon);
		calendarLeftButton.setBorder(BorderFactory.createEmptyBorder());
		calendarLeftButton.setBackground(Color.WHITE);
		calendarLeftButton.setOpaque(true);
		calendarLeftButton.setForeground(Color.WHITE);
		calendarRightButton = new JButton();
		calendarRightButton.setPreferredSize(new Dimension(30,30));
		calendarRightButton.setText("Right");
		calendarRightButton.setIcon(calendarRightIcon);
		calendarRightButton.setBorder(BorderFactory.createEmptyBorder());
		calendarRightButton.setBackground(Color.WHITE);
		calendarRightButton.setOpaque(true);
		calendarRightButton.setForeground(Color.WHITE);
		calendarMonthlyViewTopPanel.add(calendarLeftButton);
		calendarLeftButton.setBounds(250,20,30,30);
		calendarMonthlyViewTopPanel.add(calendarRightButton);
		calendarRightButton.setBounds(300,20,30,30);
		//create calendar monthly view grid
		calendarMonthlyViewGridPanel = new JPanel();
		calendarMonthlyViewGridPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		calendarMonthlyViewGridPanel.setPreferredSize(new Dimension(350,300));
		//set background
		calendarMonthlyViewGridPanel.setBackground(Color.WHITE);
		calendarMonthlyViewGridPanel.setOpaque(true);
		calendarMonthlyViewGridPanel.setForeground(Color.WHITE);
		//add days of month
		n = new JLabel("Mo", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(50,50));
		n.setFont(new Font("Roboto", Font.BOLD, 16));
		calendarMonthlyViewGridPanel.add(n);
		n = new JLabel("Tu", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(50,50));
		n.setFont(new Font("Roboto", Font.BOLD, 16));
		calendarMonthlyViewGridPanel.add(n);
		n = new JLabel("We", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(50,50));
		n.setFont(new Font("Roboto", Font.BOLD, 16));
		calendarMonthlyViewGridPanel.add(n);
		n = new JLabel("Th", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(50,50));
		n.setFont(new Font("Roboto", Font.BOLD, 16));
		calendarMonthlyViewGridPanel.add(n);
		n = new JLabel("Fr", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(50,50));
		n.setFont(new Font("Roboto", Font.BOLD, 16));
		calendarMonthlyViewGridPanel.add(n);
		n = new JLabel("Sa", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(50,50));
		n.setFont(new Font("Roboto", Font.BOLD, 16));
		calendarMonthlyViewGridPanel.add(n);
		n = new JLabel("Su", SwingConstants.CENTER);
		n.setPreferredSize(new Dimension(50,50));
		n.setFont(new Font("Roboto", Font.BOLD, 16));
		calendarMonthlyViewGridPanel.add(n);
		ActionListener monthlyCalendarListener = new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				monthlyCalendarButtonActionPerformed(evt);
			}
		};
		for(int i = 1; i < LocalDate.of(calendarYear,calendarMonth,1).getDayOfWeek().getValue(); i++){
			n = new JLabel("", SwingConstants.CENTER);
			n.setPreferredSize(new Dimension(50,40));
			calendarMonthlyViewGridPanel.add(n);
		}
		JButton b = new JButton();
		for(int i = 1; i < 32; i++){
			b = new JButton(Integer.toString(i));
			b.setPreferredSize(new Dimension(50,40));
			b.setText(Integer.toString(i));
			b.setFont(new Font("Roboto", Font.PLAIN, 16));
			b.setHorizontalAlignment(SwingConstants.CENTER);
			b.setVerticalAlignment(SwingConstants.CENTER);
			b.setBorder(BorderFactory.createEmptyBorder());
			b.setBackground(Color.WHITE);
			calendarButtonList.add(b);
			if(i < LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()+1){
				calendarMonthlyViewGridPanel.add(b);
			}
			b.addActionListener(monthlyCalendarListener);
		}
		//add grid to panel
		calendarMonthlyViewPanel.add(calendarMonthlyViewGridPanel);
		calendarMonthlyViewGridPanel.setBounds(0,50,350,300);
		calendarMonthlyViewPanel.add(calendarMonthlyViewTopPanel);
		calendarMonthlyViewTopPanel.setBounds(0,0,350,50);
		//add monthly view panel to calendar panel
		calendarOwnerPanel.add(calendarMonthlyViewPanel);
		calendarMonthlyViewPanel.setBounds(30,60,350,350);

		ActionListener calendarLeftRightButtonListener = new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				calendarLeftRightButtonActionPerformed(evt);
			}
		};
		calendarLeftButton.addActionListener(calendarLeftRightButtonListener);
		calendarRightButton.addActionListener(calendarLeftRightButtonListener);

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
		businessHoursPanel.setLayout(null);
		businessHoursPanel.setPreferredSize(new Dimension(1100,700));
		businessHoursPanel.setBackground(Color.WHITE);
		businessHoursPanel.setOpaque(true);
		businessHoursPanel.setForeground(Color.WHITE);

		JLabel addBusinessHourLabel = new JLabel("Add a Business Hour");
		addBusinessHourLabel.setHorizontalAlignment(SwingConstants.LEFT);
		addBusinessHourLabel.setBounds(200, 350, 200, 21);
		businessHoursPanel.add(addBusinessHourLabel);

		JLabel lblNewLabel_1 = new JLabel("Day of the Week");
		lblNewLabel_1.setBounds(25, 400, 134, 16);
		businessHoursPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Start Time");
		lblNewLabel_1_1.setBounds(25, 450, 212, 21);
		businessHoursPanel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("End Time");
		lblNewLabel_1_1_1.setBounds(25, 500, 212, 21);
		businessHoursPanel.add(lblNewLabel_1_1_1);

		JButton confirmAddBusinessHour= new JButton("Add");
		confirmAddBusinessHour.setBounds(50, 550, 117, 30);
		businessHoursPanel.add(confirmAddBusinessHour);
		
		JLabel updateDayOfWeekLabel = new JLabel("Update the selected business hour");
		updateDayOfWeekLabel.setBounds(700, 50, 300, 30);
		businessHoursPanel.add(updateDayOfWeekLabel);
		
		JLabel removeDayOfWeekLabel = new JLabel("Remove the selected business hour");
		removeDayOfWeekLabel.setBounds(700, 450, 300, 30);
		businessHoursPanel.add(removeDayOfWeekLabel);
		
		JLabel updateDayLabel = new JLabel("New day");
		updateDayLabel.setBounds(700, 150, 250, 21);
		businessHoursPanel.add(updateDayLabel);
		
		JLabel updateStartTimeLabel = new JLabel("New start time");
		updateStartTimeLabel.setBounds(700, 200, 250, 21);
		businessHoursPanel.add(updateStartTimeLabel);

		JLabel updateEndTimeLabel = new JLabel("New end time");
		updateEndTimeLabel.setBounds(700, 250, 250, 21);
		businessHoursPanel.add(updateEndTimeLabel);
		
		JButton confirmUpdateBusinessHour = new JButton("Update");
		confirmUpdateBusinessHour.setBounds(700, 300, 150, 29);
		businessHoursPanel.add(confirmUpdateBusinessHour);
		
		JButton confirmRemoveBusinessHour = new JButton("Remove");
		confirmRemoveBusinessHour.setBounds(700, 550, 117, 29);
		businessHoursPanel.add(confirmRemoveBusinessHour);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 42, 594, 259);
		businessHoursPanel.add(scrollPane);

		existingBusHoursTable = new JTable();
		modelBusHour = new DefaultTableModel();
		Object[] col = {"Index","Day of the Week","Start Time","End Time"};
		Object row = new Object[0];
		modelBusHour.setColumnIdentifiers(col);
		existingBusHoursTable.setModel(modelBusHour);
		scrollPane.setViewportView(existingBusHoursTable);
		
		if (!FlexiBookApplication.getFlexiBook().getHours().isEmpty()) {
			List<TOBusinessHour> bhList = FlexiBookController.getTOBusinessHour();
			for (TOBusinessHour bh : bhList) {
				int index = bhList.indexOf(bh);//FlexiBookApplication.getFlexiBook().indexOfHour(bh);
				String dayOfWeek = bh.getDayOfWeek().toString();
				String startTime = bh.getStartTime().toString();
				String endTime = bh.getEndTime().toString();
				Object[] obj = {index ,dayOfWeek, startTime, endTime};
				modelBusHour.addRow(obj);
			}
		}
		
		JLabel lblNewLabel_2 = new JLabel("Existing Business Hours");
		lblNewLabel_2.setBounds(50, 15, 200, 16);
		businessHoursPanel.add(lblNewLabel_2);

		JLabel successMessageBusinessHourLabel = new JLabel("");
		successMessageBusinessHourLabel.setForeground(Color.RED);
		successMessageBusinessHourLabel.setBounds(168, 15, 472, 24);
		businessHoursPanel.add(successMessageBusinessHourLabel);

		JLabel errorMessageBusinessHourLabel = new JLabel("");
		errorMessageBusinessHourLabel.setForeground(Color.GREEN);
		errorMessageBusinessHourLabel.setBounds(412, 437, 201, 16);
		businessHoursPanel.add(errorMessageBusinessHourLabel);
		
		updateBusinessHourBox = new JComboBox<Integer>();
		updateBusinessHourBox.setBounds(700, 100, 250, 30);
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOBusinessHour bh: FlexiBookController.getTOBusinessHour()) {
				updateBusinessHourBox.addItem(FlexiBookController.getTOBusinessHour().indexOf(bh));
			}
		}
				
		deleteBusinessHourBox = new JComboBox<Integer>();
		deleteBusinessHourBox.setBounds(700, 500, 250, 30);
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOBusinessHour bh: FlexiBookController.getTOBusinessHour()) {
				deleteBusinessHourBox.addItem(FlexiBookController.getTOBusinessHour().indexOf(bh));
			}
		}
		
		businessHoursPanel.add(updateBusinessHourBox);
		businessHoursPanel.add(deleteBusinessHourBox);
		
		addDayOfWeek = new JComboBox<String>();
		addDayOfWeek.setBounds(150, 400, 262, 25);
		addDayOfWeek.addItem("Monday");
		addDayOfWeek.addItem("Tuesday");
		addDayOfWeek.addItem("Wednesday");
		addDayOfWeek.addItem("Thursday");
		addDayOfWeek.addItem("Friday");
		addDayOfWeek.addItem("Saturday");
		addDayOfWeek.addItem("Sunday");
		businessHoursPanel.add(addDayOfWeek);
		
		updateDayOfWeek = new JComboBox<String>();
		updateDayOfWeek.setBounds(775, 150, 150, 25);
		updateDayOfWeek.addItem("Monday");
		updateDayOfWeek.addItem("Tuesday");
		updateDayOfWeek.addItem("Wednesday");
		updateDayOfWeek.addItem("Thursday");
		updateDayOfWeek.addItem("Friday");
		updateDayOfWeek.addItem("Saturday");
		updateDayOfWeek.addItem("Sunday");
		businessHoursPanel.add(updateDayOfWeek);
		
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24); 
        calendar.set(Calendar.MINUTE, 0);

        SpinnerDateModel addStartTime = new SpinnerDateModel();
        addStartTime.setValue(calendar.getTime());
        addStartTimeSpin = new JSpinner(addStartTime);
        JSpinner.DateEditor editorAddStartTime = new JSpinner.DateEditor(addStartTimeSpin, "HH:mm");
        DateFormatter formatterAddStartTime = (DateFormatter)editorAddStartTime.getTextField().getFormatter();
        formatterAddStartTime.setAllowsInvalid(false); // this makes what you want
        formatterAddStartTime.setOverwriteMode(true);
        addStartTimeSpin.setEditor(editorAddStartTime);
        businessHoursPanel.add(addStartTimeSpin);
        addStartTimeSpin.setBounds(100, 450, 86, 20);
        
        SpinnerDateModel addEndTime = new SpinnerDateModel();
        addEndTime.setValue(calendar.getTime());
        addEndTimeSpin = new JSpinner(addEndTime);
        JSpinner.DateEditor editorAddEndTime = new JSpinner.DateEditor(addEndTimeSpin, "HH:mm");
        DateFormatter formatterAddEndTime = (DateFormatter)editorAddEndTime.getTextField().getFormatter();
        formatterAddEndTime.setAllowsInvalid(false); // this makes what you want
        formatterAddEndTime.setOverwriteMode(true);
        addEndTimeSpin.setEditor(editorAddEndTime);
        businessHoursPanel.add(addEndTimeSpin);
        addEndTimeSpin.setBounds(100, 500, 86, 20);
        
        SpinnerDateModel updateStartTime = new SpinnerDateModel();
        updateStartTime.setValue(calendar.getTime());
        updateStartTimeSpin = new JSpinner(updateStartTime);
        JSpinner.DateEditor editorUpdateStartTime = new JSpinner.DateEditor(updateStartTimeSpin, "HH:mm");
        DateFormatter formatterUpdateStartTime = (DateFormatter)editorUpdateStartTime.getTextField().getFormatter();
        formatterUpdateStartTime.setAllowsInvalid(false); // this makes what you want
        formatterUpdateStartTime.setOverwriteMode(true);
        updateStartTimeSpin.setEditor(editorUpdateStartTime);
        businessHoursPanel.add(updateStartTimeSpin);
        updateStartTimeSpin.setBounds(850, 200, 86, 20);
        
        SpinnerDateModel updateEndTime = new SpinnerDateModel();
        updateEndTime.setValue(calendar.getTime());
        updateEndTimeSpin = new JSpinner(updateEndTime);
        JSpinner.DateEditor editorUpdateEndTime = new JSpinner.DateEditor(updateEndTimeSpin, "HH:mm");
        DateFormatter formatterUpdateEndTime = (DateFormatter)editorUpdateEndTime.getTextField().getFormatter();
        formatterUpdateEndTime.setAllowsInvalid(false); // this makes what you want
        formatterUpdateEndTime.setOverwriteMode(true);
        updateEndTimeSpin.setEditor(editorUpdateEndTime);
        businessHoursPanel.add(updateEndTimeSpin);
        updateEndTimeSpin.setBounds(850, 250, 86, 20);
        
        

		confirmAddBusinessHour.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
					addBusinessHourActionPerformed(evt);			
			}		
		});
		
		confirmUpdateBusinessHour.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
					updateBusinessHourActionPerformed(evt);			
			}		
		});
		
		confirmRemoveBusinessHour.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeBusinessHourActionPerformed(evt);			
		}		
	});
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
		
//		JLabel businessName = new JLabel("Business name: "+ FlexiBookController.getBusinessInfo().getName());
//		businessName.setBounds(700, 70, 150, 23);
//		businessDetailsPanel.add(businessName);
//
//		JLabel adress =  new JLabel("Address: "+ FlexiBookController.getBusinessInfo().getAdress());
//		adress.setBounds(700, 100, 150, 23);
//		businessDetailsPanel.add(adress);
//
//		JLabel phoneNumber  = new JLabel("Phone Number: "+FlexiBookController.getBusinessInfo().getPhoneNumber());
//		phoneNumber.setBounds(700, 130, 150, 23);
//		businessDetailsPanel.add(phoneNumber);
//
//		JLabel email = new JLabel("Email: "+FlexiBookController.getBusinessInfo().getEmail());
//		email.setBounds(700, 160, 250, 23);
//		businessDetailsPanel.add(email);		
	}

	/**
	 * @TODO For Mike log out page for owner 
	 */
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

	/**
	 * @TODO For Mike log out page for customer 
	 */
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
		
		appSectionError = " ";
		
		bookAppointmentPanel = new JPanel();
		bookAppointmentLabel = new JLabel("Book Appointment Page");
		bookAppointmentPanel.setPreferredSize(new Dimension(1100,700));
		bookAppointmentPanel.setBackground(Color.WHITE);
		bookAppointmentPanel.setOpaque(true);
		bookAppointmentPanel.setForeground(Color.WHITE);
		bookAppointmentPanel.add(bookAppointmentLabel);
		
		bookAppointmentPanel.setLayout(null);
		
		
//		newAppTimeT = new JTextField();
//		newAppTimeT.setBounds(174, 145, 86, 20);
//		newAppTimeT.setText("HH:MM");
//		newAppTimeT.setColumns(10);
		//bookAppointmentPanel.add(newAppTimeT);
		
		JLabel registerTime = new JLabel("Time of New Appointment");
		registerTime.setBounds(22, 148, 150, 14);
		bookAppointmentPanel.add(registerTime);
		
		newAppDateT = new JTextField();
		newAppDateT.setBounds(174, 120, 86, 20);
		newAppDateT.setText("YYYY-MM-DD");
		newAppDateT.setColumns(10);
		bookAppointmentPanel.add(newAppDateT);
		
		addAppForSingleServiceB = new JButton("Add new appointment (Single Service)");
		addAppForSingleServiceB.setBounds(22, 186, 250, 23);
		bookAppointmentPanel.add(addAppForSingleServiceB);
		
		JLabel comboAppPanelLabel = new JLabel("Select optional combo items");
		comboAppPanelLabel.setBounds(415, 79, 250, 17);
		comboAppPanelLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bookAppointmentPanel.add(comboAppPanelLabel);
		
		JLabel optionalServiceNamesLabel = new JLabel("Optional Service Names");
		optionalServiceNamesLabel.setBounds(415, 123, 150, 14);
		bookAppointmentPanel.add(optionalServiceNamesLabel);
		
		optionalServiceNamesT = new JTextField();
		optionalServiceNamesT.setBounds(564, 120, 86, 20);
		optionalServiceNamesT.setColumns(10);
		bookAppointmentPanel.add(optionalServiceNamesT);
		
		addAppForComboB = new JButton("Add new appointment (Combo)");
		addAppForComboB.setBounds(415, 186, 250, 23);
		bookAppointmentPanel.add(addAppForComboB);
		
		serviceNameT = new JTextField();
		serviceNameT.setColumns(10);
		serviceNameT.setBounds(113, 12, 120, 27);
		bookAppointmentPanel.add(serviceNameT);
		
		JLabel selectedServiceNameLabel = new JLabel("Service Name");
		selectedServiceNameLabel.setBounds(22, 0, 81, 50);
		bookAppointmentPanel.add(selectedServiceNameLabel);
		
		errorMsgLabel = new JLabel();
		errorMsgLabel.setBounds(339, 18, 546, 14);
		errorMsgLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		bookAppointmentPanel.add(errorMsgLabel);
		
		JLabel registerDate = new JLabel("Date of New Appointment");
		registerDate.setBounds(22, 123, 150, 14);
		bookAppointmentPanel.add(registerDate);
		
		JLabel singleAppPanelLabel = new JLabel("Select time for new appointment");
		singleAppPanelLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		singleAppPanelLabel.setBounds(22, 79, 227, 17);
		bookAppointmentPanel.add(singleAppPanelLabel);
		
		JLabel updateAppLabel = new JLabel("Update an existing appointment");
		updateAppLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		updateAppLabel.setBounds(22, 279, 253, 17);
		bookAppointmentPanel.add(updateAppLabel);
		
		JLabel oldDate = new JLabel("Date of the Appointment");
		oldDate.setBounds(277, 307, 190, 14);
		bookAppointmentPanel.add(oldDate);
		
//		selectedAppTimeT = new JTextField();
//		selectedAppTimeT.setText("HH:MM");
//		selectedAppTimeT.setColumns(10);
//		selectedAppTimeT.setBounds(447, 332, 86, 20);
		//bookAppointmentPanel.add(selectedAppTimeT);
		
		selectedAppDateT = new JTextField();
		selectedAppDateT.setText("YYYY-MM-DD");
		selectedAppDateT.setColumns(10);
		selectedAppDateT.setBounds(447, 304, 86, 20);
		bookAppointmentPanel.add(selectedAppDateT);
		
		JLabel oldTime = new JLabel("Time of the Appointment");
		oldTime.setBounds(277, 335, 150, 14);
		bookAppointmentPanel.add(oldTime);
		
		JLabel newDateLabel = new JLabel("New Date");
		newDateLabel.setBounds(19, 385, 160, 14);
		bookAppointmentPanel.add(newDateLabel);
		
		newUpdateAppDateT = new JTextField();
		newUpdateAppDateT.setText("YYYY-MM-DD");
		newUpdateAppDateT.setColumns(10);
		newUpdateAppDateT.setBounds(174, 382, 86, 20);
		bookAppointmentPanel.add(newUpdateAppDateT);
		
//		newUpdateAppTimeT = new JTextField();
//		newUpdateAppTimeT.setText("HH:MM");
//		newUpdateAppTimeT.setColumns(10);
//		newUpdateAppTimeT.setBounds(174, 410, 86, 20);
		//bookAppointmentPanel.add(newUpdateAppTimeT);
		
		JLabel newTimeLabel = new JLabel("New Time");
		newTimeLabel.setBounds(19, 413, 123, 14);
		bookAppointmentPanel.add(newTimeLabel);
		
//		updateTimeB = new JButton("Update to new time");
//		updateTimeB.setBounds(18, 456, 215, 23);
//		bookAppointmentPanel.add(updateTimeB);
		
		updateTimeB = new JButton("Update to new time");
		updateTimeB.setBounds(18, 456, 215, 23);
		bookAppointmentPanel.add(updateTimeB);
		
		updateActionComboBox = new JComboBox<String>();
		updateActionComboBox.setToolTipText("Select an action");
		updateActionComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"add ", "remove"}));
		updateActionComboBox.setSelectedIndex(0);
		updateActionComboBox.setMaximumRowCount(2);
		updateActionComboBox.setBounds(564, 384, 70, 17);
		bookAppointmentPanel.add(updateActionComboBox);
		
		existingCb = new JComboBox<String>();
		existingCb.setBounds(113, 50, 120, 27);
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				existingCb.addItem(service.getName());
			}
		}
		existingCb.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				serviceNameT.setText((String)existingCb.getSelectedItem());
			}
		});
		bookAppointmentPanel.add(existingCb);
		
		
		
		JLabel updateActionLabel = new JLabel("Update action");
		updateActionLabel.setLabelFor(updateActionComboBox);
		updateActionLabel.setBounds(415, 385, 160, 14);
		bookAppointmentPanel.add(updateActionLabel);
		
		JLabel chosenComboItemL = new JLabel("Choose a combo item:");
		chosenComboItemL.setBounds(415, 413, 150, 14);
		bookAppointmentPanel.add(chosenComboItemL);
		
		updateComboItemNameL = new JTextField();
		updateComboItemNameL.setColumns(10);
		updateComboItemNameL.setBounds(564, 410, 86, 20);
		bookAppointmentPanel.add(updateComboItemNameL);
		
		updateContentB = new JButton("Update to new service content");
		updateContentB.setBounds(415, 456, 215, 23);
		bookAppointmentPanel.add(updateContentB);
		
		cancelAppB = new JButton("Cancel");
		cancelAppB.setBounds(700, 456, 100, 23);
		bookAppointmentPanel.add(cancelAppB);
		
		JButton refreshB = new JButton("Refresh");
		refreshB.setBounds(900, 630, 100, 23);
		bookAppointmentPanel.add(refreshB);
		refreshB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				refreshAppointmentPage();
			}
		});
		
		//----------------------- table--------------
		viewAppForCurCustomerTable = new JTable() {
			private static final long serialVersionUID = 3493726593135015542L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (!c.getBackground().equals(getSelectionBackground())) {
					Object obj = getModel().getValueAt(row, column);
					if (obj instanceof java.lang.String) {
						//String str = (String)obj;
						c.setBackground(Color.WHITE);
					}
				}
				return c;
			}
		};
		
		bookAppointmentPanel.add(viewAppForCurCustomerTable);
		vAFCCTableModel = new DefaultTableModel(0, 0);
		viewAppForCurCustomerTable.setModel(vAFCCTableModel);
		vAFCCTableModel.setColumnIdentifiers(vAFCCTableColumnNames);
		viewAppForCurCustomerTable.setBounds(700, 10, 350, 550);
		
		viewAppForCurCustomerScrollPane = new JScrollPane(viewAppForCurCustomerTable);
		bookAppointmentPanel.add(viewAppForCurCustomerScrollPane);
//		Dimension d = viewAppForCurCustomerTable.getPreferredSize();
//		viewAppForCurCustomerScrollPane.setPreferredSize(d);
		viewAppForCurCustomerScrollPane.setBounds(10, 520, 1000, 100);
		viewAppForCurCustomerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		

		
		//---------------------- date picker little calendars ---------- 
		SqlDateModel newdateModel = new SqlDateModel();
		LocalDate now = LocalDate.now();
		newdateModel.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		newdateModel.setSelected(true);
		Properties pO = new Properties();
		pO.put("text.today", "Today");
		pO.put("text.month", "Month");
		pO.put("text.year", "Year");
		JDatePanelImpl newDatePanel = new JDatePanelImpl(newdateModel, pO);
		newDatePicker = new JDatePickerImpl(newDatePanel, new DateLabelFormatter());
		bookAppointmentPanel.add(newDatePicker);
		newDatePicker.setBounds(260, 382, 40, 20);
		
		SqlDateModel registerdateModel = new SqlDateModel();
		now = LocalDate.now();
		registerdateModel.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		registerdateModel.setSelected(true);
		JDatePanelImpl registerDatePanel = new JDatePanelImpl(registerdateModel, pO);
		registerDatePicker = new JDatePickerImpl(registerDatePanel, new DateLabelFormatter());
		bookAppointmentPanel.add(registerDatePicker);
		registerDatePicker.setBounds(260, 120, 40, 20);

		SqlDateModel selectdateModel = new SqlDateModel();
		now = LocalDate.now();
		selectdateModel.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		selectdateModel.setSelected(true);
		JDatePanelImpl selectDatePanel = new JDatePanelImpl(selectdateModel, pO);
		selectDatePicker = new JDatePickerImpl(selectDatePanel, new DateLabelFormatter());
		bookAppointmentPanel.add(selectDatePicker);
		selectDatePicker.setBounds(533, 304, 40, 20);
		
		//--------------------------- Time scroll
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        calendar.set(Calendar.MINUTE, 0);
        //calendar.set(Calendar.SECOND, 0);

        SpinnerDateModel model = new SpinnerDateModel();
        model.setValue(calendar.getTime());
        registerTimeSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(registerTimeSpinner, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);
        registerTimeSpinner.setEditor(editor);
        bookAppointmentPanel.add(registerTimeSpinner);
        registerTimeSpinner.setBounds(174, 145, 86, 20);
        
        SpinnerDateModel smodel = new SpinnerDateModel();
        smodel.setValue(calendar.getTime());
        selectTimeSpinner = new JSpinner(smodel);
        JSpinner.DateEditor selecteditor = new JSpinner.DateEditor(selectTimeSpinner, "HH:mm");
        DateFormatter selectformatter = (DateFormatter)selecteditor.getTextField().getFormatter();
        selectformatter.setAllowsInvalid(false); // this makes what you want
        selectformatter.setOverwriteMode(true);
        selectTimeSpinner.setEditor(selecteditor);
        bookAppointmentPanel.add(selectTimeSpinner);
        selectTimeSpinner.setBounds(447, 332, 86, 20);
        
        SpinnerDateModel nmodel = new SpinnerDateModel();
        nmodel.setValue(calendar.getTime());
        newTimeSpinner = new JSpinner(nmodel);
        JSpinner.DateEditor neweditor = new JSpinner.DateEditor(newTimeSpinner, "HH:mm");
        DateFormatter newformatter = (DateFormatter)neweditor.getTextField().getFormatter();
        newformatter.setAllowsInvalid(false); // this makes what you want
        newformatter.setOverwriteMode(true);
        newTimeSpinner.setEditor(neweditor);
        bookAppointmentPanel.add(newTimeSpinner);
        newTimeSpinner.setBounds(174, 410, 86, 20);
        
//    	private JButton updateContentB;
//    	private JButton updateTimeB;
//    	private JButton addAppForSingleServiceB;
//    	private JButton addAppForComboB;
//    	private JDatePickerImpl newDatePicker;
//    	private JDatePickerImpl registerDatePicker;
//    	private JDatePickerImpl selectDatePicker;
        
        updateContentB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateAppointmentContentActionPerformed(evt);
			}
		});
        updateTimeB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateAppointmentTimeActionPerformed(evt);
			}
		});
        addAppForSingleServiceB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addAppSingleServicePerformed(evt);
			}
		});
        addAppForComboB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addAppServiceComboPerformed(evt);
			}
		});
        
        cancelAppB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelAppPerformed(evt);
			}
		});
        
        newDatePicker.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				pickNewDateForUpdatePerformed(evt);
			}
		});
        registerDatePicker.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				pickDateForAddAppPerformed(evt);
			}
		});
        selectDatePicker.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				pickDateForSelectAppPerformed(evt);
			}
		});
        // will be using this later
//        try {
//			System.out.println(newformatter.valueToString(newTimeSpinner.getValue()));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


	}

//	private JLabel successMessageLogInLabel;
//	private JLabel errorMessageLogInLabel;
//	private JLabel successMessageSignInLabel;
//	private JLabel errorMessageSignInLabel;
//	private String addLoginError;
//	private String addLoginSuccess;
//	private String addSignUpError;
//	private String addSignUpSuccess;
//	private String logOutErrorMessage; 
//	private String logOutSuccessMessage; 
	
	private void refreshLogin() {
		
		successMessageLogInLabel.setText(addLoginSuccess);
		errorMessageLogInLabel.setText(addLoginError);
		successMessageSignInLabel.setText(addSignUpSuccess);
		errorMessageSignInLabel.setText(addSignUpError);
		pack();
		repaint();
		
//		deleteServiceComboBox.removeAllItems();
//		updateServiceComboBox.removeAllItems();
//		modelAddService.getDataVector().removeAllElements();
//		modelDeleteService.getDataVector().removeAllElements();
//		modelUpdateService.getDataVector().removeAllElements();
//		if (!FlexiBookController.getTOServices().isEmpty()) {
//			List<TOService> toServices = FlexiBookController.getTOServices();
//			for (TOService service : toServices) {
//				String name = service.getName();
//				String duration = Integer.toString(service.getDuration());
//				String downtimeDuration = Integer.toString(service.getDowntimeDuration());
//				String downtimeStart = Integer.toString(service.getDowntimeStart());
//				Object[] obj = {name, duration, downtimeDuration, downtimeStart};
//				modelAddService.addRow(obj);
//				modelDeleteService.addRow(obj);
//				modelUpdateService.addRow(obj);
//			}
//		}
//		if (!FlexiBookController.getTOServices().isEmpty()) {
//			for (TOService service:FlexiBookController.getTOServices()) {
//				deleteServiceComboBox.addItem(service.getName());
//				updateServiceComboBox.addItem(service.getName());
//			}
//		}
//		
//		
//		if (addError == null || addError.length() == 0) {
//			newServiceDowntimeDurationTextField.setText("");
//			newServiceDowntimeStartTextField.setText("");
//			newServiceDurationTextField.setText("");
//			newServiceNameTextField.setText("");		
//		}
//		
//		if(FlexiBookController.getTOServices().isEmpty()) {
//			deleteServiceComboBox.removeAllItems();
//			updateServiceComboBox.removeAllItems();
//			modelAddService.getDataVector().removeAllElements();
//			modelDeleteService.getDataVector().removeAllElements();
//			modelUpdateService.getDataVector().removeAllElements();
//		}
	}
	
	private void refreshBusinessSetUp() {
		successMessageSetUpLabel.setText(addSetUpSuccess);
		errorMessageSetUpLabel.setText(addSetUpError);
		pack();
		repaint();
	}
	
	//refresh frame
	private void refreshData() {
		pack();
		repaint();
		refreshSingleServiceData();
		//refreshAppointmentPage();
		refreshBusinessHourData();

	}
	
	private void refreshSignOut() {
//		initComponents();
		refreshData();
//		textField.setText("");
//		textField.setText("");
	}
	
	private void refreshAccount() {
		//if customer, refresh that way
		//if owner, refresh that way
		//set error message
		//clear textboxes
		
		//maybe just reinitiaize????
		if (FlexiBookController.getCurrentLogInUsername().equals("owner")) {
			initInfoOwnerPanel();
		}
		else {
			initInfoCustomerPanel();
		}
	}
	
	private void refreshBusinessHourData() {
		//SuccessLabel.setText(deleteSuccess);
		//errorLabel.setText(errorBHMessage);
		deleteBusinessHourBox.removeAllItems();
		updateBusinessHourBox.removeAllItems();
		modelBusHour.getDataVector().removeAllElements();
		if (!FlexiBookController.getTOBusinessHour().isEmpty()) {
			List<TOBusinessHour> toBusinessHour = FlexiBookController.getTOBusinessHour();
			for (TOBusinessHour bh : toBusinessHour) {
				int index = toBusinessHour.indexOf(bh);
				String dayOfWeek = bh.getDayOfWeek().toString();
				String startTime = bh.getStartTime().toString();
				String endTime = bh.getEndTime().toString();
				Object[] newBH = {index, dayOfWeek, startTime, endTime};
				modelBusHour.addRow(newBH);
			}
		}

		if (!FlexiBookController.getTOBusinessHour().isEmpty()) {
			List<TOBusinessHour> toBusinessHour = FlexiBookController.getTOBusinessHour();
			for (TOBusinessHour bh : toBusinessHour) {
				updateBusinessHourBox.addItem((Integer) toBusinessHour.indexOf(bh));
				deleteBusinessHourBox.addItem((Integer) toBusinessHour.indexOf(bh));
			}
		}

		if(FlexiBookController.getTOBusinessHour().isEmpty()) {
			deleteBusinessHourBox.removeAllItems();
			updateBusinessHourBox.removeAllItems();
			modelBusHour.getDataVector().removeAllElements();
		}
//		
//		if (errorMessageSingleService == null || errorMessageSingleService.length() == 0) {
//			newServiceDowntimeDurationTextField.setText("");
//			newServiceDowntimeStartTextField.setText("");
//			newServiceDurationTextField.setText("");
//			newServiceNameTextField.setText("");		
//		}
//				
	}
	
	private void addBusinessHourActionPerformed(java.awt.event.ActionEvent evt){
		deleteBHError = null; 
		deleteBHSuccess = null;
		DayOfWeek dw = null;
		FlexiBookApplication.setCurrentLoginUser(FlexiBookApplication.getFlexiBook().getOwner());
		if (addDayOfWeek.getSelectedItem().equals("Monday")) {
			dw = DayOfWeek.Monday;
		} else if (addDayOfWeek.getSelectedItem().equals("Tuesday")) {
			dw= DayOfWeek.Tuesday;
		} else if (addDayOfWeek.getSelectedItem().equals("Wednesday")) {
			dw = DayOfWeek.Wednesday;
		} else if (addDayOfWeek.getSelectedItem().equals("Thursday")) {
			dw = DayOfWeek.Thursday;
		} else if (addDayOfWeek.getSelectedItem().equals("Friday")) {
			dw = DayOfWeek.Friday;
		} else if (addDayOfWeek.getSelectedItem().equals("Saturday")) {
			dw = DayOfWeek.Saturday;
		} else if (addDayOfWeek.getSelectedItem().equals("Sunday")) {
			dw = DayOfWeek.Sunday;
		}
		
		JSpinner.DateEditor editor = new JSpinner.DateEditor(addStartTimeSpin, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        String startTimeString = "";
        String endTimeString = "";
		try {
			startTimeString = formatter.valueToString(addStartTimeSpin.getValue());
			endTimeString = formatter.valueToString(addEndTimeSpin.getValue());
		} catch (ParseException e) {
			appSectionError = e.getMessage();
		}
		
		try {
			FlexiBookController.setUpBusinessHours(stringToTime(startTimeString), stringToTime(endTimeString), dw);
			//addBHSuccess = "Success!";
		} catch (InvalidInputException e) {
			addBHError = e.getMessage();
		}
		
		refreshData();
	}
	
	
	private void removeBusinessHourActionPerformed(java.awt.event.ActionEvent evt) {
		deleteBHError = null; 
		deleteBHSuccess = null;
		FlexiBookApplication.setCurrentLoginUser(FlexiBookApplication.getFlexiBook().getOwner());
		try {
			FlexiBookController.removeBusinessHour(FlexiBookController.getTOBusinessHour().get((int) deleteBusinessHourBox.getSelectedItem()).getDayOfWeek(), FlexiBookController.getTOBusinessHour().get((int) deleteBusinessHourBox.getSelectedItem()).getStartTime()
					);
			//deleteService((String)deleteServiceComboBox.getSelectedItem());
			deleteBHSuccess = "Success!";
		} catch (InvalidInputException e) {
			deleteBHError = e.getMessage();
		}
		
		refreshData();
	}
	
	private void updateBusinessHourActionPerformed(java.awt.event.ActionEvent evt) {
		deleteBHError = null; 
		deleteBHSuccess = null;
		DayOfWeek dw = null;
		FlexiBookApplication.setCurrentLoginUser(FlexiBookApplication.getFlexiBook().getOwner());
		if (updateDayOfWeek.getSelectedItem().equals("Monday")) {
			dw = DayOfWeek.Monday;
		} else if (addDayOfWeek.getSelectedItem().equals("Tuesday")) {
			dw= DayOfWeek.Tuesday;
		} else if (addDayOfWeek.getSelectedItem().equals("Wednesday")) {
			dw = DayOfWeek.Wednesday;
		} else if (addDayOfWeek.getSelectedItem().equals("Thursday")) {
			dw = DayOfWeek.Thursday;
		} else if (addDayOfWeek.getSelectedItem().equals("Friday")) {
			dw = DayOfWeek.Friday;
		} else if (addDayOfWeek.getSelectedItem().equals("Saturday")) {
			dw = DayOfWeek.Saturday;
		} else if (addDayOfWeek.getSelectedItem().equals("Sunday")) {
			dw = DayOfWeek.Sunday;
		}
		
		JSpinner.DateEditor editor = new JSpinner.DateEditor(addStartTimeSpin, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        String startTimeString = "";
        String endTimeString = "";
		try {
			startTimeString = formatter.valueToString(updateStartTimeSpin.getValue());
			endTimeString = formatter.valueToString(updateEndTimeSpin.getValue());
		} catch (ParseException e) {
			appSectionError = e.getMessage();
		}
		
		try {
			FlexiBookController.updateBusinessHour(
					FlexiBookController.getTOBusinessHour().get((int) updateBusinessHourBox.getSelectedItem()).getDayOfWeek(), FlexiBookController.getTOBusinessHour().get((int) updateBusinessHourBox.getSelectedItem()).getStartTime(),dw,
					stringToTime(startTimeString), stringToTime(endTimeString));
			//addBHSuccess = "Success!";
		} catch (InvalidInputException e) {
			addBHError = e.getMessage();
		}
		
		refreshData();
	}

	//method called when set-up info is done 
		private void setUpBusinessInformation(java.awt.event.ActionEvent evt) {
			//remove log in panel
			getContentPane().remove(setUpInPanel);
			//add owner top bar and calendar panel to frame
			getContentPane().add(topPanelOwner);
			topPanelOwner.setBounds(0,0,1100,40);
			getContentPane().add(calendarOwnerPanel);
			calendarOwnerPanel.setBounds(0,40,1100,700);
			//set calendar to initial state
			previousPanel = calendarOwnerPanel;
			previousButton = calendarOwnerButton;
			//reset calendar button
			calendarOwnerButton.setBorder(new LineBorder(Color.WHITE));
			calendarOwnerButton.setBackground(Color.WHITE);
			calendarOwnerButton.setOpaque(true);
			calendarOwnerButton.setForeground(darkGrey);
			//create business
			try {//Need to add the errors here 
				if(FlexiBookApplication.getFlexiBook().getBusiness()==null) 
			FlexiBookController.setUpBusinessInfo(txtBusinessNameSet.getText(), txtAdressSet.getText(), txtPhoneNumberSet.getText(), txtEmailSet.getText());
				 
			}
			catch (InvalidInputException e) {
			}
			//refresh page
			refreshData();
		}
		
		
		//method called when set-up info is done 
		private void logInOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {
			//remove log in panel
			getContentPane().remove(LoginPane);
			//add owner top bar and calendar panel to frame
			getContentPane().add(topPanelOwner);
			topPanelOwner.setBounds(0,0,1100,40);
			getContentPane().add(calendarOwnerPanel);
			calendarOwnerPanel.setBounds(0,40,1100,700);
			//set calendar to initial state
			previousPanel = calendarOwnerPanel;
			previousButton = calendarOwnerButton;
			//reset calendar button
			calendarOwnerButton.setBorder(new LineBorder(Color.WHITE));
			calendarOwnerButton.setBackground(Color.WHITE);
			calendarOwnerButton.setOpaque(true);
			calendarOwnerButton.setForeground(darkGrey);
			refreshCalendarWeeklyView();
			
			refreshData();
		}


//	/**
//	 * @TODO For Mike: After user pressed the Owner button on the login page
//	 * @param evt
//	 */
//	//method called when log in owner button pressed
//	private void logInSetUpOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {
//		//remove log in panel
//		getContentPane().remove(LoginPane);
//		//add owner top bar and calendar panel to frame
//		getContentPane().add(topPanelOwner);
//		topPanelOwner.setBounds(0,0,1100,40);
//		getContentPane().add(calendarOwnerPanel);
//		calendarOwnerPanel.setBounds(0,40,1100,700);
//		//set calendar to initial state
//		previousPanel = calendarOwnerPanel;
//		previousButton = calendarOwnerButton;
//		//reset calendar button
//		calendarOwnerButton.setBorder(new LineBorder(Color.WHITE));
//		calendarOwnerButton.setBackground(Color.WHITE);
//		calendarOwnerButton.setOpaque(true);
//		calendarOwnerButton.setForeground(darkGrey);
//		//refresh page
//		refreshData();
//	}
//	
		
		
		
//		private void addSingleServicesButtonActionPerformed(ActionEvent evt) {
//			addError = null;
//			addSuccess = null;
//			FlexiBookApplication.setCurrentLoginUser(FlexiBookApplication.getFlexiBook().getOwner());
//			if (newServiceDowntimeDurationTextField.getText().equals("")||
//					newServiceDowntimeStartTextField.getText().equals("")||
//					newServiceDurationTextField.getText().equals("")||
//					newServiceNameTextField.getText().equals("")) {
//				addError = "one of the fields is empty";
//				
//			}
//			else {
//				try {
//					FlexiBookController.addService(newServiceNameTextField.getText(), Integer.parseInt(newServiceDurationTextField.getText()), 
//							Integer.parseInt(newServiceDowntimeStartTextField.getText()), 
//							Integer.parseInt(newServiceDowntimeDurationTextField.getText()));
//					addSuccess = "Success!";
//				}  catch (InvalidInputException e) {
//					addError = e.getMessage();
//				}
//			}
//				
//			refreshData();
//
//
//		}
		
		// When signUp button is pressed 
		private void SignUpCustomerButtonPerformed(ActionEvent evt) {
			addSignUpError = null;
			addSignUpSuccess = null;
			addLoginError = null;
			addLoginSuccess = null;
			if (textField_1.getText().equals("")||String.valueOf(passwordField.getPassword()).equals("")||
					String.valueOf(passwordField_1.getPassword()).equals("")) {
				textField_1.setText("");
				passwordField.setText("");
				passwordField_1.setText("");
				addSignUpError = "One of the fields is empty";
			}else if(!String.valueOf(passwordField.getPassword()).equals(String.valueOf(passwordField_1.getPassword()))) {
				textField_1.setText("");
				passwordField.setText("");
				passwordField_1.setText("");
				addSignUpError = "The two password inputs do not match. Please try again.";
			}
			else {
				if (!textField_1.getText().equals("owner")) {
					try {
						FlexiBookController.signUpCustomer(textField_1.getText(), String.valueOf(passwordField.getPassword()));
						textField_1.setText("");
						passwordField.setText("");
						passwordField_1.setText("");
						addSignUpSuccess = "Success!";
						logInCustomerButtonActionPerformed(evt);
					}  catch (InvalidInputException e) {
						String errorMessageCatched = e.getMessage();
							addSignUpError = errorMessageCatched;
					}
				}else {
					try {
						FlexiBookController.signUpOwner(textField_1.getText(), String.valueOf(passwordField.getPassword()));
						addSignUpSuccess = "Success!";
						if(textField_1.getText().equals("owner")) {
							if (FlexiBookApplication.getFlexiBook().getBusiness()==null) {
								textField_1.setText("");
								passwordField.setText("");
								passwordField_1.setText("");
								logInOwnerButtonToSetUpActionPerformed(evt);
							}
							else if (FlexiBookApplication.getFlexiBook().getBusiness()!=null) {
								textField_1.setText("");
								passwordField.setText("");
								passwordField_1.setText("");
								logInOwnerButtonActionPerformed(evt);
							}
						}
					}  catch (InvalidInputException e) {
						String errorMessageCatched = e.getMessage();
							addSignUpError = errorMessageCatched;
					}
				}
			}

			refreshLogin();


		}
		
		
		private void loginUserButtonPerformed(ActionEvent evt) {
			addLoginError = null;
			addLoginSuccess = null;
			addSignUpError = null;
			addSignUpSuccess = null;
			if (textField_1.getText().equals("") || String.valueOf(passwordField.getPassword()).equals("")) {
				addLoginError = "one of the fields is empty";
				textField_1.setText("");
				passwordField.setText("");
			} 
			else {
				try {
					FlexiBookController.logIn(textField_1.getText(), String.valueOf(passwordField.getPassword()));
					addLoginSuccess = "Success!";
					if(textField_1.getText().equals("owner")) {
						if (FlexiBookApplication.getFlexiBook().getBusiness()==null) {
							textField_1.setText("");
							passwordField.setText("");
							logInOwnerButtonToSetUpActionPerformed(evt);
						}
						else if (FlexiBookApplication.getFlexiBook().getBusiness()!=null) {
							textField_1.setText("");
							passwordField.setText("");
							logInOwnerButtonActionPerformed(evt);
						}
					}
					else {
						textField_1.setText("");
						passwordField.setText("");
						logInCustomerButtonActionPerformed(evt);
					}
				}  catch (InvalidInputException e) {
					String errorMessageCatched = e.getMessage();
					if (errorMessageCatched.equals("Username/password not found")) {
						addLoginError = "<html>Username/password not found, please try again. <br/>If you don't have an account, please try sign up</html>";
					}
				}
			refreshLogin();
			}
			
		}
		
		
//	/**
//	 * @TODO For Mike After user pressed the owner button on the intial login page
//	 * @param evt
//	 */
//	//method called when log in customer button pressed
//		private void logInSetUpCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {
//			//remove log in panel
//			getContentPane().remove(LoginPane);
//			//add customer top bar and calendar panel to frame
//			getContentPane().add(topPanelCustomer);
//			topPanelCustomer.setBounds(0,0,1100,40);
//			getContentPane().add(calendarCustomerPanel);
//			calendarCustomerPanel.setBounds(0,40,1100,700);
//			//set calendar to initial state
//			previousPanel = calendarCustomerPanel;
//			previousButton = calendarCustomerButton;
//			//reset calendar button
//			calendarCustomerButton.setBorder(new LineBorder(Color.WHITE));
//			calendarCustomerButton.setBackground(Color.WHITE);
//			calendarCustomerButton.setOpaque(true);
//			calendarCustomerButton.setForeground(darkGrey);
//			//refresh page
//			refreshData();
//		}
//		
	/**
	 * @TODO For MIKE merge the logInOwnerButtonToSetUpActionPerformed() with logInCustomerButtonActionPerformed()
	 * @param evt
	 */
	//method called when log in owner button pressed
	private void logInOwnerButtonToSetUpActionPerformed(java.awt.event.ActionEvent evt) {
		//remove log in panel
		getContentPane().remove(LoginPane);
		//add owner top bar and calendar panel to frame
		getContentPane().add(setUpInPanel);
		setUpInPanel.setBounds(0,0,1100,700);
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
		getContentPane().remove(LoginPane);
		//add customer top bar and calendar panel to frame
		getContentPane().add(topPanelCustomer);
		topPanelCustomer.setBounds(0,0,1100,40);
		getContentPane().add(calendarCustomerPanel);
		calendarCustomerPanel.setBounds(0,40,1100,700);
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
		getContentPane().add(infoOwnerPanel);
		infoOwnerPanel.setBounds(0,40,1100,700);
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
		getContentPane().add(infoCustomerPanel);
		infoCustomerPanel.setBounds(0,40,1100,700);
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
		getContentPane().add(singleServicesPanel);
		singleServicesPanel.setBounds(0,40,1100,700);
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
		getContentPane().add(comboServicesPanel);
		comboServicesPanel.setBounds(0,40,1100,700);
		//set this panel as the current panel
		previousPanel = comboServicesPanel;
		//refresh page
		refreshData();
	}
	
	private void deleteSingleServicesButtonActionPerformed(ActionEvent evt) {
		errorMessageSingleService = null; 
		deleteSuccess = null;
		try {
			FlexiBookController.deleteService((String)deleteServiceComboBox.getSelectedItem());
			deleteSuccess = "Success!";
		} catch (InvalidInputException e) {
			errorMessageSingleService = e.getMessage();
		}
		
		refreshData();
	}
	
	private void addSingleServicesButtonActionPerformed(ActionEvent evt) {
		errorMessageSingleService = null;
		addSuccess = null;
		if (newServiceDowntimeDurationTextField.getText().equals("")||
				newServiceDowntimeStartTextField.getText().equals("")||
				newServiceDurationTextField.getText().equals("")||
				newServiceNameTextField.getText().equals("")) {
			errorMessageSingleService = "one of the fields is empty";
			
		}
		else {
			try {
				FlexiBookController.addService(newServiceNameTextField.getText(), Integer.parseInt(newServiceDurationTextField.getText()), 
						Integer.parseInt(newServiceDowntimeStartTextField.getText()), 
						Integer.parseInt(newServiceDowntimeDurationTextField.getText()));
				addSuccess = "Success!";
			}  catch (InvalidInputException e) {
				errorMessageSingleService = e.getMessage();
			}
		}
			
		refreshData();


	}
	
	private void updateSingleServicesButtonActionPerformed(ActionEvent evt) {
		errorMessageSingleService = null;
		updateSuccess = null;
		if (updateDowntimeDurationTextField.getText().equals("")||
				updateDowntimeStartTextField.getText().equals("")||
				updateServiceDurationTextField.getText().equals("")||
				updateServiceNameTextField.getText().equals("")) {
			errorMessageSingleService = "one of the fields is empty";
			
		}
		else {
			try {
				FlexiBookController.updateService((String)updateServiceComboBox.getSelectedItem(), 
						updateServiceNameTextField.getText(), Integer.parseInt(updateServiceDurationTextField.getText()), 
						Integer.parseInt(updateDowntimeDurationTextField.getText()), 
						Integer.parseInt(updateDowntimeStartTextField.getText()));
				updateSuccess = "Success!";
			}catch (InvalidInputException e) {
				errorMessageSingleService = e.getMessage();
			}
		}
		
		
		
		refreshData();
		
	}
	
	private void refreshSingleServiceData() {
		errorMessageSingleServiceLabel.setText(errorMessageSingleService);
		addSuccessLabel.setText(addSuccess);
		deleteSuccessLabel.setText(deleteSuccess);
		updateSuccessLabel.setText(updateSuccess);
		deleteServiceComboBox.removeAllItems();
		updateServiceComboBox.removeAllItems();
		modelModifySingleService.getDataVector().removeAllElements();
		if (!FlexiBookController.getTOServices().isEmpty()) {
			List<TOService> toServices = FlexiBookController.getTOServices();
			for (TOService service : toServices) {
				String name = service.getName();
				String duration = Integer.toString(service.getDuration());
				String downtimeDuration = Integer.toString(service.getDowntimeDuration());
				String downtimeStart = Integer.toString(service.getDowntimeStart());
				Object[] obj = {name, duration, downtimeDuration, downtimeStart};
				modelModifySingleService.addRow(obj);
				
			}
		}
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				deleteServiceComboBox.addItem(service.getName());
				updateServiceComboBox.addItem(service.getName());
			}
		}
		
		
		if (errorMessageSingleService == null || errorMessageSingleService.length() == 0) {
			newServiceDowntimeDurationTextField.setText("");
			newServiceDowntimeStartTextField.setText("");
			newServiceDurationTextField.setText("");
			newServiceNameTextField.setText("");		
		}
		
		if(FlexiBookController.getTOServices().isEmpty()) {
			deleteServiceComboBox.removeAllItems();
			updateServiceComboBox.removeAllItems();
			modelModifySingleService.getDataVector().removeAllElements();
		}
		
		
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
		getContentPane().add(calendarOwnerPanel);
		calendarOwnerPanel.setBounds(0,40,1100,700);
		//set this panel as the current panel
		previousPanel = calendarOwnerPanel;
		//refresh page
		refreshCalendarWeeklyView();
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
		getContentPane().add(calendarCustomerPanel);
		calendarCustomerPanel.setBounds(0,40,1100,700);
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
		getContentPane().add(businessHoursPanel);
		businessHoursPanel.setBounds(0,40,1100,700);
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
		getContentPane().add(businessDetailsPanel);
		businessDetailsPanel.setBounds(0,40,1100,700);
		//set this panel as the current panel
		previousPanel = businessDetailsPanel;
		//refresh page
		refreshData();
	}

	//method called when owner log out button pressed
	private void logOutOwnerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		logOutErrorMessage = null; 
		logOutSuccessMessage = null; 
		try{
			FlexiBookController.logOut();
			logOutSuccessMessage = "Logout Success!";
		}catch (InvalidInputException e){
			String errorMessage = e.getMessage();
			logOutErrorMessage = errorMessage; 
		}
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
		getContentPane().add(LoginPane);
		LoginPane.setBounds(0,0,1100,740);
		//refresh page
		refreshSignOut();
		//refreshData();
	}

	//method called when customer log out button pressed
	private void logOutCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		logOutErrorMessage = null; 
		logOutSuccessMessage = null; 
		try{
			FlexiBookController.logOut();
			logOutSuccessMessage = "Logout Success!";
		}catch (InvalidInputException e){
			String errorMessage = e.getMessage();
			logOutErrorMessage = errorMessage; 
		}
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
		getContentPane().add(LoginPane);
		//refresh page
		refreshSignOut();
		//refreshData();
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
		getContentPane().add(bookAppointmentPanel);
		bookAppointmentPanel.setBounds(0,40,1100,700);
		//set this panel as the current panel
		previousPanel = bookAppointmentPanel;
		//refresh page
		refreshData();
	}
	
	//method called when save button pressed while editing an account
	private void saveAccountInfoActionPerformed(java.awt.event.ActionEvent evt) {
		// clear message
		error = null;
		successful = null;

		if (usernameBox.getText().equals("") || String.valueOf(passwordBox.getPassword()).equals("") || String.valueOf(confirmPasswordBox.getPassword()).equals("")) { 
			error = "Fill in all fields to update your account";
			
		} 
		else if (! String.valueOf(passwordBox.getPassword()).equals(String.valueOf(confirmPasswordBox.getPassword()))) {
			error = "Passwords do not match";
		}
		else {
			try {
				FlexiBookController.updateUserAccount(FlexiBookController.getCurrentLogInUsername(), usernameBox.getText(), String.valueOf(passwordBox.getPassword()));
				successful = "Success!";
			}  catch (InvalidInputException e) {
				error = e.getMessage();
				
			}
			
		}

		refreshAccount();
		
	}
	
	//method called when delete button pressed while editing an account
	private void deleteAccountInfoActionPerformed(java.awt.event.ActionEvent evt) {
		// clear message
		error = null;
		successful = null;
		
		//pop up confirm message?
				
//		// call the controller
		try {
			FlexiBookController.deleteCustomerAccount(FlexiBookController.getCurrentLogInUsername()); //if we need to see error messages, get the username textbox content and refresh
			successful = "Success!";
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
			getContentPane().add(LoginPane);
			
				} catch (InvalidInputException e) {
					error = e.getMessage();
					//display error message
				}
		
		//refresh page
		refreshAccount();
		
	}
	

	
	//--------------------------- Add Appointment part ----------------
	private void updateAppointmentContentActionPerformed(java.awt.event.ActionEvent evt) {
		String serviceName =  serviceNameT.getText();
		String optservicename = updateComboItemNameL.getText();
		String action = (String)updateActionComboBox.getSelectedItem();
		
		Date date = stringToDate(selectedAppDateT.getText());
		
		
		JSpinner.DateEditor editor = new JSpinner.DateEditor(selectTimeSpinner, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        String timeString = "";
		try {
			timeString = formatter.valueToString(selectTimeSpinner.getValue());
		} catch (ParseException e) {
			appSectionError = e.getMessage();
		}
		Time time = stringToTime(timeString);
		
		try {
			FlexiBookController.updateAppointmentContent(serviceName, date, time, action, optservicename);
		} catch (InvalidInputException e) {
			appSectionError = appSectionError + e.getMessage();
		}
		
		System.out.println(serviceName);
		System.out.println(date);
		System.out.println(time);
		System.out.println(action);
		System.out.println(optservicename);
		refreshData();
		refreshAppointmentPage();
		
		
	}
	private void updateAppointmentTimeActionPerformed(java.awt.event.ActionEvent evt) {
		String serviceName =  serviceNameT.getText();
		Date date = stringToDate(selectedAppDateT.getText());
		Date newDate = stringToDate(newUpdateAppDateT.getText());
		
		JSpinner.DateEditor editor = new JSpinner.DateEditor(selectTimeSpinner, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        String timeString = "";
        String newtimeString = "";
    	try {
			timeString = formatter.valueToString(selectTimeSpinner.getValue());
			newtimeString = formatter.valueToString(newTimeSpinner.getValue());
		} catch (ParseException e) {
			appSectionError = e.getMessage();
		}
    	
    	Time time = stringToTime(timeString);
    	Time newtime = stringToTime(newtimeString);
    	
    	try {
			Boolean ret = FlexiBookController.updateAppointmentTime(serviceName, date, time, newDate, newtime);
			if(ret == false) {
	    		appSectionError = appSectionError + "Failed!";
	    	}
		} catch (InvalidInputException e) {
			appSectionError = appSectionError + e.getMessage();
		}
    	
    
		System.out.println(serviceName);
		System.out.println(date);
		System.out.println(time);
		System.out.println(newDate);
		System.out.println(newtime);
		refreshData();
		refreshAppointmentPage();
		
    	
 	
	}

	private void monthlyCalendarButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if(previousCalendarButton != null){
			previousCalendarButton.setBackground(Color.WHITE);
			previousCalendarButton.setForeground(Color.BLACK);
		}
		String numAsString = evt.getActionCommand();
		int num = Integer.parseInt(numAsString);
		JButton tempButton = calendarButtonList.get(num-1);
		tempButton.setBackground(lightBlue);
		tempButton.setForeground(Color.WHITE);
		tempButton.setOpaque(true);
		previousCalendarButton = tempButton;
		calendarDay = num;
		refreshData();
		int tempNum = LocalDate.of(calendarYear,calendarMonth,calendarDay).getDayOfWeek().getValue();
		int tempDay = calendarDay-tempNum+1;
		for(int i = 0; i < 7; i++){
			JLabel n = dayLabelList.get(i);
			if(tempDay < 1){
				if(calendarMonth-1 < 1){
					n.setText(Integer.toString(tempDay+LocalDate.of(calendarYear-1,12,1).lengthOfMonth()));
				} else {
					n.setText(Integer.toString(tempDay+LocalDate.of(calendarYear,calendarMonth-1,1).lengthOfMonth()));
				}
			} else if(tempDay > LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()){
				n.setText(Integer.toString(tempDay-LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()));
			} else {
				n.setText(Integer.toString(tempDay));
			}
			if(tempDay == calendarDay){
				n.setBackground(lightBlue);
				n.setOpaque(true);
				n.setForeground(Color.WHITE);
			} else {
				n.setBackground(Color.WHITE);
				n.setOpaque(true);
				n.setForeground(Color.BLACK);
			}
			tempDay++;
		}
		refreshData();
	}

	private void calendarLeftRightButtonActionPerformed(java.awt.event.ActionEvent evt){
		if(evt.getActionCommand().equals("Left")){
			calendarMonth--;
		} else {
			calendarMonth++;
		}
		if(calendarMonth < 1){
			calendarMonth = 12;
			calendarYear--;
		} else if(calendarMonth > 12){
			calendarMonth = 1;
			calendarYear++;
		}
		monthNameLabel.setText(LocalDate.of(calendarYear,calendarMonth,1).getMonth().getDisplayName(TextStyle.FULL,Locale.CANADA) + " " + calendarYear);
		//reset panel
		calendarMonthlyViewGridPanel.removeAll();

			JLabel n = new JLabel("Mo", SwingConstants.CENTER);
			n.setPreferredSize(new Dimension(50,50));
			n.setFont(new Font("Roboto", Font.BOLD, 16));
			calendarMonthlyViewGridPanel.add(n);
			n = new JLabel("Tu", SwingConstants.CENTER);
			n.setPreferredSize(new Dimension(50,50));
			n.setFont(new Font("Roboto", Font.BOLD, 16));
			calendarMonthlyViewGridPanel.add(n);
			n = new JLabel("We", SwingConstants.CENTER);
			n.setPreferredSize(new Dimension(50,50));
			n.setFont(new Font("Roboto", Font.BOLD, 16));
			calendarMonthlyViewGridPanel.add(n);
			n = new JLabel("Th", SwingConstants.CENTER);
			n.setPreferredSize(new Dimension(50,50));
			n.setFont(new Font("Roboto", Font.BOLD, 16));
			calendarMonthlyViewGridPanel.add(n);
			n = new JLabel("Fr", SwingConstants.CENTER);
			n.setPreferredSize(new Dimension(50,50));
			n.setFont(new Font("Roboto", Font.BOLD, 16));
			calendarMonthlyViewGridPanel.add(n);
			n = new JLabel("Sa", SwingConstants.CENTER);
			n.setPreferredSize(new Dimension(50,50));
			n.setFont(new Font("Roboto", Font.BOLD, 16));
			calendarMonthlyViewGridPanel.add(n);
			n = new JLabel("Su", SwingConstants.CENTER);
			n.setPreferredSize(new Dimension(50,50));
			n.setFont(new Font("Roboto", Font.BOLD, 16));
			calendarMonthlyViewGridPanel.add(n);

		for(int i = 1; i < LocalDate.of(calendarYear,calendarMonth,1).getDayOfWeek().getValue(); i++){
			n = new JLabel("", SwingConstants.CENTER);
			n.setPreferredSize(new Dimension(50,40));
			calendarMonthlyViewGridPanel.add(n);
		}
		JButton b = new JButton();
		for(int i = 1; i < LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()+1; i++){
			b = calendarButtonList.get(i-1);;
			calendarMonthlyViewGridPanel.add(b);
		}
		if(previousCalendarButton != null){
			previousCalendarButton.setBackground(Color.WHITE);
			previousCalendarButton.setForeground(Color.BLACK);
			previousCalendarButton = null;
		}
		calendarDay = -1;
		int tempNum = LocalDate.of(calendarYear,calendarMonth,1).getDayOfWeek().getValue();
		int tempDay = 1-tempNum+1;
		for(int i = 0; i < 7; i++){
			n = dayLabelList.get(i);
			if(tempDay < 1){
				if(calendarMonth-1 < 1){
					n.setText(Integer.toString(tempDay+LocalDate.of(calendarYear-1,12,1).lengthOfMonth()));
				} else {
					n.setText(Integer.toString(tempDay+LocalDate.of(calendarYear,calendarMonth-1,1).lengthOfMonth()));
				}
			} else if(tempDay > LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()){
				n.setText(Integer.toString(tempDay-LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()));
			} else {
				n.setText(Integer.toString(tempDay));
			}
			n.setBackground(Color.WHITE);
			n.setOpaque(true);
			n.setForeground(Color.BLACK);
			tempDay++;
		}
		refreshData();
	}
	
	private void addAppSingleServicePerformed(java.awt.event.ActionEvent evt) {
		
		String serviceName = serviceNameT.getText();
		Date date = stringToDate(newAppDateT.getText());
		
		JSpinner.DateEditor editor = new JSpinner.DateEditor(registerTimeSpinner, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        String timeString = "";
		try {
			timeString = formatter.valueToString(registerTimeSpinner.getValue());
		} catch (ParseException e) {
			appSectionError = e.getMessage();
		}
		Time time = stringToTime(timeString);
		
		
		try {
			FlexiBookController.addAppointmentForService(serviceName, date, time);
		} catch (InvalidInputException e) {
			appSectionError = appSectionError + e.getMessage();
		}
		
		refreshData();
		refreshAppointmentPage();

		
	}
	
	private void addAppServiceComboPerformed(java.awt.event.ActionEvent evt) {
		
		String serviceName = serviceNameT.getText();
		String optServices = optionalServiceNamesT.getText();
		Date date = stringToDate(newAppDateT.getText());
		
		JSpinner.DateEditor editor = new JSpinner.DateEditor(registerTimeSpinner, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        String timeString = "";
		try {
			timeString = formatter.valueToString(registerTimeSpinner.getValue());
		} catch (ParseException e) {
			appSectionError = e.getMessage();
		}
		Time time = stringToTime(timeString);
		
		
		try {
			FlexiBookController.addAppointmentForComboService(serviceName, optServices, date, time);
		} catch (InvalidInputException e) {
			appSectionError = appSectionError + e.getMessage();
		}
		
		refreshData();
		refreshAppointmentPage();
		System.out.println(serviceName);
		System.out.println(date);
		System.out.println(time);
		System.out.println(optServices);
		

		
	}
	
	
	private void cancelAppPerformed(java.awt.event.ActionEvent evt) {
		String serviceName = serviceNameT.getText();
		Date date = stringToDate(selectedAppDateT.getText());
		JSpinner.DateEditor editor = new JSpinner.DateEditor(selectTimeSpinner, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        String timeString = "";
    	try {
			timeString = formatter.valueToString(selectTimeSpinner.getValue());
		} catch (ParseException e) {
			appSectionError = e.getMessage();
		}
    	
    	Time time = stringToTime(timeString);
    	
    	try {
			FlexiBookController.cancelAppointment(serviceName, date, time);
		} catch (InvalidInputException e) {
			appSectionError = appSectionError + e.getMessage();
		}
    	refreshData();
    	refreshAppointmentPage();
    	
		
		
	}
	
	
	private void pickNewDateForUpdatePerformed(java.awt.event.ActionEvent evt) {
		if (newDatePicker.getModel().getValue() != null) {
			newUpdateAppDateT.setText(newDatePicker.getModel().getValue().toString());
		}
	}

	private void pickDateForAddAppPerformed(java.awt.event.ActionEvent evt) {
		if (registerDatePicker.getModel().getValue() != null) {
			newAppDateT.setText(registerDatePicker.getModel().getValue().toString());
		}
		
	}
	private void pickDateForSelectAppPerformed(java.awt.event.ActionEvent evt) {
		if (selectDatePicker.getModel().getValue() != null) {
			selectedAppDateT.setText(selectDatePicker.getModel().getValue().toString());
		}
	}

	private static Date stringToDate(String str) {
		return (Date.valueOf(LocalDate.parse(str, DateTimeFormatter.ISO_DATE)));
	}

	private static Time stringToTime(String str) {
		if (str.charAt(2) != ':') {
			str = "0" + str;
		}
		return (Time.valueOf(LocalTime.parse(str, DateTimeFormatter.ISO_TIME)));
	}
	
	private void refreshAppointmentPage() {
		vAFCCTableModel = new DefaultTableModel(0, 0);
		viewAppForCurCustomerTable.setModel(vAFCCTableModel);
		vAFCCTableModel.setColumnIdentifiers(vAFCCTableColumnNames);
		
		for(TOAppointment appto: FlexiBookController.getTOAppointmentForCurrentCustomer()) {
			String name = appto.getServiceName();
			String startAt = appto.getTimeSlot().getStartDate().toString() + " " + appto.getTimeSlot().getStartTime().toString();
			
			String downTime = "-";
			String startDT = appto.getDownTimeTimeSlot().get(0).getStartDate() + " " + appto.getDownTimeTimeSlot().get(0).getStartTime();
			String endDT = appto.getDownTimeTimeSlot().get(0).getEndDate() + " " + appto.getDownTimeTimeSlot().get(0).getEndTime();
			if(startDT.equals(endDT)) {
				

//				downTime = appto.getDownTimeTimeSlot().get(0).getStartDate() + " " + appto.getDownTimeTimeSlot().get(0).getStartTime() + "->"
//						+ appto.getDownTimeTimeSlot().get(0).getEndDate() + " " + appto.getDownTimeTimeSlot().get(0).getEndTime();
			}else {
				downTime = startDT + "->" + endDT;
			}
			String endAt = appto.getTimeSlot().getEndDate().toString() + " " + appto.getTimeSlot().getEndTime().toString();
			
			Object[] obj = {name, startAt, downTime, endAt};
			vAFCCTableModel.addRow(obj);
			
		}
		
		Dimension d = viewAppForCurCustomerTable.getPreferredSize();
		viewAppForCurCustomerScrollPane.setPreferredSize(d);
		
		
		// Show error Message
		if(appSectionError.equals(" ")) {
			errorMsgLabel.setText("Success");
			errorMsgLabel.setForeground(Color.GREEN);
		}else {
			errorMsgLabel.setText(appSectionError);
			errorMsgLabel.setForeground(Color.RED);
		}
		
		appSectionError = " ";
		
		
	
	}

	private void refreshCalendarWeeklyView(){
		//time bar
			int minHour = 0;
			int maxHour = 0;
			int minute = 0;
			int hour = 0;
			calendarTimes.removeAll();
			calendarBusinessSlots.removeAll();
			List<TOBusinessHour> businessHourList = FlexiBookController.getTOBusinessHour();
			if(businessHourList.size() != 0){
				Time minStartTime = businessHourList.get(0).getStartTime();
				Time maxEndTime = businessHourList.get(0).getEndTime();
					for(TOBusinessHour b: businessHourList){
					if(b.getStartTime().before(minStartTime)){
						minStartTime = b.getStartTime();
					}
					if(b.getEndTime().after(maxEndTime)){
						maxEndTime = b.getEndTime();
					}
				}
				minHour = minStartTime.getHours();
				maxHour = maxEndTime.getHours();;
				hour = minStartTime.getHours();
			}
			double deltaY = 520.0/((maxHour-minHour)*2);
			JLabel p = new JLabel();
			for(int i = 0; i < (maxHour-minHour)*2; i++){
				p = new JLabel(hour + ":" + minute, SwingConstants.RIGHT);
				if(minute < 10){
					p = new JLabel(hour + ":0" + minute, SwingConstants.RIGHT);
				}
				p.setPreferredSize(new Dimension(40,(int)Math.round(deltaY)));
				calendarTimes.add(p);
				p.setBounds(0,(int)Math.round(i*deltaY),40,(int)Math.round(deltaY));
				minute += 30;
				if(minute > 59){
					hour ++;
					minute -= 60;
				}
				if(hour > 12){
					hour -= 12;
				}
			}
			calendarWeeklyViewPanel.add(calendarTimes);
			calendarTimes.setBounds(0,80+35,40,520);
			//add business hours times
			List<TOBusinessHour> bhList = FlexiBookController.getTOBusinessHour();
			int n = 0;
			List<Integer> dayList = new ArrayList<Integer>();
			for(TOBusinessHour bh: bhList){
				if(bh.getDayOfWeek().equals(DayOfWeek.Monday)){
					n = 0;
					dayList.add(0);
				} else if(bh.getDayOfWeek().equals(DayOfWeek.Tuesday)){
					n = 1;
					dayList.add(1);
				} else if(bh.getDayOfWeek().equals(DayOfWeek.Wednesday)){
					n = 2;
					dayList.add(2);
				} else if(bh.getDayOfWeek().equals(DayOfWeek.Thursday)){
					n = 3;
					dayList.add(3);
				} else if(bh.getDayOfWeek().equals(DayOfWeek.Friday)){
					n = 4;
					dayList.add(4);
				} else if(bh.getDayOfWeek().equals(DayOfWeek.Saturday)){
					n = 5;
					dayList.add(5);
				} else if(bh.getDayOfWeek().equals(DayOfWeek.Sunday)){
					n = 6;
					dayList.add(6);
				}
				p = new JLabel("I");
				p.setBackground(new Color(230,230,230));
				p.setOpaque(true);
				p.setForeground(new Color(230,230,230));
				p.setPreferredSize(new Dimension(90,(int)Math.round(deltaY*2*(bh.getStartTime().getHours()-minHour))));
				calendarBusinessSlots.add(p);
				p.setBounds(n*90+50,35+80,90,(int)Math.round(deltaY*2*(bh.getStartTime().getHours()-minHour)));

				p = new JLabel("I");
				p.setBackground(new Color(230,230,230));
				p.setOpaque(true);
				p.setForeground(new Color(230,230,230));
				p.setPreferredSize(new Dimension(90,(int)Math.round(deltaY*2*(maxHour-bh.getEndTime().getHours()))));
				calendarBusinessSlots.add(p);
				p.setBounds(n*90+50,35+80+520-(int)Math.round(deltaY*2*(maxHour-bh.getEndTime().getHours())),90,(int)Math.round(deltaY*2*(maxHour-bh.getEndTime().getHours())));
			}
			for(int i = 0; i < 7; i++){
				if(!dayList.contains(i)){
					p = new JLabel("I");
					p.setBackground(new Color(230,230,230));
					p.setOpaque(true);
					p.setForeground(new Color(230,230,230));
					p.setPreferredSize(new Dimension(90,520));
					calendarBusinessSlots.add(p);
					p.setBounds(i*90+50,35+80,90,520);
				}
			}
			calendarWeeklyViewPanel.add(calendarBusinessSlots);
			calendarBusinessSlots.setBounds(0,0,700,700);
			//add customer appointments
			int actualDay = calendarDay;
			int actualMonth = calendarMonth;
			int actualYear = calendarYear;
			if(calendarDay > 0){
				int tempNum = LocalDate.of(calendarYear,calendarMonth,calendarDay).getDayOfWeek().getValue();
				int tempDay = calendarDay-tempNum+1;
				for(int i = 0; i < 7; i++){
					if(tempDay < 1){
						if(calendarMonth-1 < 1){
							actualDay = tempDay+LocalDate.of(calendarYear-1,12,1).lengthOfMonth();
							actualMonth = 12;
							actualYear -= 1;
						} else {
							actualDay = tempDay+LocalDate.of(calendarYear,calendarMonth-1,1).lengthOfMonth();
							actualMonth -= 1;
						}
					} else if(tempDay > LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()){
						if(calendarMonth+1 > 12){
							actualDay = tempDay-LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth();
							actualMonth = 1;
							actualYear += 1;
						} else {
							actualDay = tempDay-LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth();
							actualMonth += 1;
						}
					}
					for(TOAppointment appointment: FlexiBookController.getTOAppointment()){
						TOTimeSlot ts = appointment.getTimeSlot();
						if(ts.getStartDate().equals(new Date(actualYear, actualMonth, actualDay))){
							p = new JLabel("I");
							p.setBackground(lightBlue);
							p.setOpaque(true);
							p.setForeground(lightBlue);
							int hourStart = ts.getStartTime().getHours();
							int minuteStart = ts.getStartTime().getMinutes();
							int hourEnd = ts.getEndTime().getHours();
							int minuteEnd = ts.getEndTime().getMinutes();
							p.setPreferredSize(new Dimension(90,(int)Math.round((hourEnd+1.0/60*minuteEnd-hourStart-1.0/60*minuteStart)*2*deltaY)));
							calendarWeeklyViewPanel.add(p);
							p.setBounds(i*90+50,35+80+(int)Math.round((hourStart+1.0/60*minuteStart)*2*deltaY),90,(int)Math.round((hourEnd+1.0/60*minuteEnd-hourStart-1.0/60*minuteStart)*2*deltaY));
						}
					}
					tempDay++;
				}
			} else {
				int tempNum = LocalDate.of(calendarYear,calendarMonth,1).getDayOfWeek().getValue();
				int tempDay = 1-tempNum+1;
				for(int i = 0; i < 7; i++){
					if(tempDay < 1){
						if(calendarMonth-1 < 1){
							actualDay = tempDay+LocalDate.of(calendarYear-1,12,1).lengthOfMonth();
							actualMonth = 12;
							actualYear -= 1;
						} else {
							actualDay = tempDay+LocalDate.of(calendarYear,calendarMonth-1,1).lengthOfMonth();
							actualMonth -= 1;
						}
					} else if(tempDay > LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth()){
						if(calendarMonth+1 > 12){
							actualDay = tempDay-LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth();
							actualMonth = 1;
							actualYear += 1;
						} else {
							actualDay = tempDay-LocalDate.of(calendarYear,calendarMonth,1).lengthOfMonth();
							actualMonth += 1;
						}
					}
					for(TOAppointment appointment: FlexiBookController.getTOAppointment()){
						TOTimeSlot ts = appointment.getTimeSlot();
						if(ts.getStartDate().equals(new Date(actualYear, actualMonth, actualDay))){
							p = new JLabel("I");
							p.setBackground(lightBlue);
							p.setOpaque(true);
							p.setForeground(lightBlue);
							int hourStart = ts.getStartTime().getHours();
							int minuteStart = ts.getStartTime().getMinutes();
							int hourEnd = ts.getEndTime().getHours();
							int minuteEnd = ts.getEndTime().getMinutes();
							p.setPreferredSize(new Dimension(90,(int)Math.round((hourEnd+1.0/60*minuteEnd-hourStart-1.0/60*minuteStart)*2*deltaY)));
							calendarWeeklyViewPanel.add(p);
							p.setBounds(i*90+50,35+80+(int)Math.round((hourStart+1.0/60*minuteStart)*2*deltaY),90,(int)Math.round((hourEnd+1.0/60*minuteEnd-hourStart-1.0/60*minuteStart)*2*deltaY));
						}
					}
					tempDay++;
				}
			}
	}
	

}

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
import ca.mcgill.ecse.flexibook.controller.TOBusiness;
import ca.mcgill.ecse.flexibook.controller.TOBusinessHour;

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
	private JPanel calendarMonthlyViewPanel;
	private JPanel calendarMonthlyViewGridPanel;
	private JPanel calendarMonthlyViewTopPanel;
	
	
	// initial login panel
	//private JPanel LoginPane;
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
	private JButton logInOwnerButton;
	private JButton logInCustomerButton;
	private JLabel logINTextLable;
	
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
	private ImageIcon calendarLeftIcon;
	private ImageIcon calendarRightIcon;

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
	private Color lightBlue = new Color(31,184,252);

	private String error;
	
	
	/**
	 * Appointment page
	 */
	private JTextField newAppTimeT;
	private JTextField newAppDateT;
	private JTextField optionalServiceNamesT; // optional service name
	private JTextField serviceNameT; // service Name
	private JTextField selectedAppTimeT;
	private JTextField selectedAppDateT;
	private JTextField newUpdateAppDateT;
	private JTextField newUpdateAppTimeT;
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
	private JLabel errorMsgLabel;
	
	private double initLogInPageScalingFactor = 740/490;
	/**
	 * Appointment page end
	 */


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
//		logInOwnerButton = new JButton();
//		logInOwnerButton.setText("Owner");
//		logInOwnerButton.setPreferredSize(new Dimension(200, 40));
//		logInOwnerButton.setBorder(new LineBorder(darkGrey));
//		logInOwnerButton.setBackground(Color.WHITE);
//		logInOwnerButton.setOpaque(true);
//		logInOwnerButton.setForeground(darkGrey);
//		//initialize customer log in button
//		logInCustomerButton = new JButton();
//		logInCustomerButton.setText("Customer");
//		logInCustomerButton.setPreferredSize(new Dimension(200, 40));
//		logInCustomerButton.setBorder(new LineBorder(darkGrey));
//		logInCustomerButton.setBackground(Color.WHITE);
//		logInCustomerButton.setOpaque(true);
//		logInCustomerButton.setForeground(darkGrey);
//		
//		//initialize Text JLabel 
//		logINTextLable = new JLabel("I'm A/An:  ");
//		logINTextLable.setHorizontalAlignment(SwingConstants.LEFT);
//		logINTextLable.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 20));
//		logINTextLable.setBounds(155, 313, 115, 21);
//		
//		LoginPane.add(logINTextLable);
//		LoginPane.add(logInOwnerButton);
//		LoginPane.add(logInCustomerButton);
		LoginPane = new JPanel();
		LoginPane.setBackground(new Color(66, 135, 245));
		LoginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(LoginPane);
		LoginPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, (int)(346*initLogInPageScalingFactor), (int)(490*initLogInPageScalingFactor));
		LoginPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("KeepToo");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setForeground(new Color(240, 248, 255));
		lblNewLabel.setBounds((int)(139*initLogInPageScalingFactor), (int)(305*initLogInPageScalingFactor), (int)(84*initLogInPageScalingFactor), (int)(27*initLogInPageScalingFactor));
		panel.add(lblNewLabel);
		
//		JLabel label = new JLabel("");
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
//		label.setBounds(-38, 0, 420, 275);
//		label.setVerticalAlignment(SwingConstants.TOP);
//		// label.setIcon(new ImageIcon(FlexiBookPage.class.getResource("/images/bg.jpg")));
//		panel.add(label);
		
		JLabel lblWeGotYou = new JLabel("....We got you....");
		lblWeGotYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeGotYou.setForeground(new Color(240, 248, 255));
		lblWeGotYou.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWeGotYou.setBounds((int)(111*initLogInPageScalingFactor),(int)(343*initLogInPageScalingFactor), (int)(141*initLogInPageScalingFactor), (int)(27*initLogInPageScalingFactor));
		panel.add(lblWeGotYou);
		
		Button logInOwnerButton = new Button("Owner");
		logInOwnerButton.setForeground(Color.WHITE);
		logInOwnerButton.setBackground(new Color(241, 57, 83));
		logInOwnerButton.setBounds((int)(395*initLogInPageScalingFactor), (int)(363*initLogInPageScalingFactor), (int)(283*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
		LoginPane.add(logInOwnerButton);
		
		Button logInCustomerButton = new Button("Customer");
		logInCustomerButton.setForeground(Color.WHITE);
		logInCustomerButton.setBackground(new Color(241, 57, 83));
		logInCustomerButton.setBounds((int)(395*initLogInPageScalingFactor), (int)(363*initLogInPageScalingFactor+(int)(36*initLogInPageScalingFactor)+20 ), (int)(283*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
		LoginPane.add(logInCustomerButton);
		
		textField = new JTextField();
		textField.setBounds((int)(395*initLogInPageScalingFactor), (int)(83*initLogInPageScalingFactor), (int)(283*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
		LoginPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setBounds((int)(395*initLogInPageScalingFactor), (int)(132*initLogInPageScalingFactor), (int)(114*initLogInPageScalingFactor), (int)(14*initLogInPageScalingFactor));
		LoginPane.add(lblUsername);
		
//		JLabel lblEmail = new JLabel("EMAIL");
//		lblEmail.setBounds(395, 132, 54, 14);
//		LoginPane.add(lblEmail);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds((int)(395*initLogInPageScalingFactor), (int)(157*initLogInPageScalingFactor), (int)(283*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
		LoginPane.add(textField_1);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds((int)(395*initLogInPageScalingFactor),(int)(204*initLogInPageScalingFactor), (int)(96*initLogInPageScalingFactor), (int)(14*initLogInPageScalingFactor));
		LoginPane.add(lblPassword);
		
		JLabel lblRepeatPassword = new JLabel("REPEAT PASSWORD");
		lblRepeatPassword.setBounds((int)(395*initLogInPageScalingFactor), (int)(275*initLogInPageScalingFactor), (int)(133*initLogInPageScalingFactor), (int)(14*initLogInPageScalingFactor));
		LoginPane.add(lblRepeatPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds((int)(395*initLogInPageScalingFactor), (int)(229*initLogInPageScalingFactor), (int)(283*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
		LoginPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds((int)(395*initLogInPageScalingFactor), (int)(293*initLogInPageScalingFactor), (int)(283*initLogInPageScalingFactor), (int)(36*initLogInPageScalingFactor));
		LoginPane.add(passwordField_1);
		
		JLabel lbl_close = new JLabel("Back");
		lbl_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				System.exit(0);
			}
		});
		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_close.setForeground(new Color(241, 57, 83));
		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_close.setBounds((int)(691*initLogInPageScalingFactor), (int)(0*initLogInPageScalingFactor), (int)(37*initLogInPageScalingFactor), (int)(27*initLogInPageScalingFactor));
		LoginPane.add(lbl_close);
		// Mike add this end ---
		
		
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
					//logInCustomerButtonActionPerformed(evt);
					logInOwnerButtonToSetUpActionPerformed(evt);
					
				}
			});
		}
		
		else {
			logInOwnerButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					logInOwnerButtonActionPerformed(evt);
					//logInCustomerButtonActionPerformed(evt);
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
	
	
	/**
	 * @TODO For Mike Login Customer page
	 */
	//initialize Login Customer page
	private void initLogInCustomerPanel(){
		infoOwnerPanel = new JPanel();
		infoLabel = new JLabel("Info Page");
		infoOwnerPanel.setPreferredSize(new Dimension(1100,700));
		infoOwnerPanel.setBackground(Color.WHITE);
		infoOwnerPanel.setOpaque(true);
		infoOwnerPanel.setForeground(Color.WHITE);
		infoOwnerPanel.add(infoLabel);

		//TO DO
	}
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
		//time bar
		/*
		ArrayList<TOBusinessHour> businessHourList = FlexiBookController.getTOBusinessHour();
		Time minStartTime;
		Time maxEndTime;
		for(TOBusinessHour b: businessHourList){
			if(minStartTime == null){
				minStartTime = b.getStartTime();
				maxStartTime = b.getEndTime();
			}
			if(b.getStartTime().before(minStartTime)){
				minStartTime = b.getStartTime();
			}
			if(b.getEndTime().after(maxStartTime){
				maxStartTime = b.getEndTime();
			}
		}
		*/
		JPanel calendarTimes = new JPanel();
		calendarTimes.setLayout(null);
		calendarTimes.setPreferredSize(new Dimension(40,520));
		calendarTimes.setBackground(Color.WHITE);
		int minHour = 9;
		int maxHour = 17;
		int minute = 0;
		int hour = 9;
		double deltaY = 520.0/((maxHour-minHour)*2+1);
		for(int i = 0; i < (maxHour-minHour)*2+1; i++){
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
		calendarRightButton = new JButton();
		calendarRightButton.setPreferredSize(new Dimension(30,30));
		calendarRightButton.setText("Right");
		calendarRightButton.setIcon(calendarRightIcon);
		calendarRightButton.setBorder(BorderFactory.createEmptyBorder());
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
						c.setBackground(Color.GRAY);
					}
				}
				return c;
			}
		};
		viewAppForCurCustomerTable.setBounds(700, 10, 350, 550);
		//bookAppointmentPanel.add(viewAppForCurCustomerTable);
		
		viewAppForCurCustomerScrollPane = new JScrollPane(viewAppForCurCustomerTable);
		bookAppointmentPanel.add(viewAppForCurCustomerScrollPane);
		//this.add(viewAppForCurCustomerScrollPane);
		//Dimension d = viewAppForCurCustomerTable.getPreferredSize();
		viewAppForCurCustomerScrollPane.setPreferredSize(new Dimension(10, 400));
		viewAppForCurCustomerScrollPane.setBounds(1050, 50, 20, 400);
		viewAppForCurCustomerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
//		vAFCCTableModel = new DefaultTableModel(0, 0);
//		vAFCCTableModel.setColumnIdentifiers(vAFCCTableColumnNames);
//		viewAppForCurCustomerTable.setModel(vAFCCTableModel);
		
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
			//refresh page
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
		getContentPane().add(LoginPane);
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
		getContentPane().add(bookAppointmentPanel);
		bookAppointmentPanel.setBounds(0,40,1100,700);
		//set this panel as the current panel
		previousPanel = bookAppointmentPanel;
		//refresh page
		refreshData();
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
			FlexiBookController.updateAppointmentTime(serviceName, date, time, newDate, newtime);
		} catch (InvalidInputException e) {
			appSectionError = appSectionError + e.getMessage();
		}
    	
    	
		System.out.println(serviceName);
		System.out.println(date);
		System.out.println(time);
		System.out.println(newDate);
		System.out.println(newtime);
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
		
		refreshAppointmentPage();
		System.out.println(serviceName);
		System.out.println(date);
		System.out.println(time);
		System.out.println(optServices);
		

		
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
		vAFCCTableModel.setColumnIdentifiers(vAFCCTableColumnNames);
		viewAppForCurCustomerTable.setModel(vAFCCTableModel);
		
		for(TOAppointment appto: FlexiBookController.getTOAppointmentForCurrentCustomer()) {
			String name = appto.getServiceName();
			String startAt = appto.getTimeSlot().getStartDate().toString() + " " + appto.getTimeSlot().getStartTime().toString();
			String downTime = "-";
			if(appto.getDownTimeTimeSlot().size() != 0) {
				downTime = appto.getDownTimeTimeSlot().get(0).getStartDate() + " " + appto.getDownTimeTimeSlot().get(0).getStartTime() + "->"
						+ appto.getDownTimeTimeSlot().get(0).getEndDate() + " " + appto.getDownTimeTimeSlot().get(0).getEndTime();
			}
			String endAt = appto.getTimeSlot().getEndDate().toString() + " " + appto.getTimeSlot().getEndTime().toString();
			
			Object[] obj = {name, startAt, downTime, endAt};
			vAFCCTableModel.addRow(obj);
			
		}
		
		Dimension d = viewAppForCurCustomerTable.getPreferredSize();
		viewAppForCurCustomerScrollPane.setPreferredSize(new Dimension(d.width, 550));
		
		
		// Show error Message
		errorMsgLabel.setText(appSectionError);
		appSectionError = " ";
		

//			for (TODailyOverviewItem item : BtmsController.getDailyOverview((Date) overviewDatePicker.getModel().getValue())) {
//				String busText = item.getLicencePlate();
//				String shiftText = "---";
//				String driverText = "---";
//				if (item.isInRepairShop()) {
//					busText = busText + " (in repair)";
//				}
//				if (item.getShift() != null) {
//					shiftText = item.getShift();
//				}
//				if (item.getName() != null) {
//					driverText = "#" + item.getId() + " " + item.getName();
//					if (item.isSick()) {
//						driverText = driverText + " (sick)";
//					}
//				}
//				Object[] obj = {item.getNumber(), busText, shiftText, driverText};
//				overviewDtm.addRow(obj);
//			}
		
		
		
//		Dimension d = overviewTable.getPreferredSize();
//		overviewScrollPane.setPreferredSize(new Dimension(d.width, HEIGHT_OVERVIEW_TABLE));
		
	
	}
	

}

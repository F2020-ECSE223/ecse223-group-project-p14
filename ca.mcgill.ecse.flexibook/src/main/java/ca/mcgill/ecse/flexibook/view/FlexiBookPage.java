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

import javax.swing.border.LineBorder;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.model.Service;

public class FlexiBookPage extends JFrame {

	private static final long serialVersionUID = -4426310869335015542L;

	//top bar
	private JPanel topPanel;
	//panel for each button on top bar
	private JPanel singleServicesPanel;
	private JPanel comboServicesPanel;
	private JPanel calendarPanel;
	private JPanel businessHoursPanel;
	private JPanel businessDetailsPanel;
	
	//top bar buttons
	private JButton singleServicesButton;
	private JButton comboServicesButton;
	private JButton calendarButton;
	private JButton businessHoursButton;
	private JButton businessDetailsButton;

	//page labels
	private JLabel singleServicesLabel;
	private JLabel comboServicesLabel;
	private JLabel calendarLabel;
	private JLabel businessHoursLabel;
	private JLabel businessDetailsLabel;

	//tracking last page
	private JButton previousButton;
	private JPanel previousPanel;

	//color of top bar
	private Color darkGrey = new Color(80,80,80);


	/** Creates new form BtmsPage */
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
		setPreferredSize(new Dimension(1000,640));
		setResizable(false);

		//initialize top bar layout
		FlowLayout topLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
		topPanel = new JPanel();
		topPanel.setLayout(topLayout);
		topPanel.setPreferredSize(new Dimension(1000,40));

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

		//add buttons to top bar
		topPanel.add(singleServicesButton);
		topPanel.add(comboServicesButton);
		topPanel.add(calendarButton);
		topPanel.add(businessHoursButton);
		topPanel.add(businessDetailsButton);

		//set calendar as initial page
		previousButton = calendarButton;
		previousPanel = calendarPanel;

		//add top bar and calendar panel to frame
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		c.ipadx = 1000;
		getContentPane().add(topPanel, c);
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 570;
		c.ipadx = 1000;
		getContentPane().add(calendarPanel, c);

		pack();

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

	}

	//initialize single services panel
	private void initSingleServicesPanel(){
		singleServicesPanel = new JPanel();
		singleServicesLabel = new JLabel("Single Service Page");
		singleServicesPanel.setPreferredSize(new Dimension(1000,600));
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
		comboServicesPanel.setPreferredSize(new Dimension(1000,600));
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
		calendarPanel.setPreferredSize(new Dimension(1000,600));
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
		businessHoursPanel.setPreferredSize(new Dimension(1000,600));
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
		businessDetailsPanel.setPreferredSize(new Dimension(1000,600));
		businessDetailsPanel.setBackground(Color.WHITE);
		businessDetailsPanel.setOpaque(true);
		businessDetailsPanel.setForeground(Color.WHITE);
		businessDetailsPanel.add(businessDetailsLabel);

		//TO DO
	}

	//refresh frame
	private void refreshData() {
		pack();
		repaint();
	}

	
	//method called when single service button pressed
	private void singleServicesButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message and basic input validation
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
		previousButton = singleServicesButton;
		singleServicesButton.setBorder(new LineBorder(Color.WHITE));
		singleServicesButton.setBackground(Color.WHITE);
		singleServicesButton.setOpaque(true);
		singleServicesButton.setForeground(darkGrey);
		getContentPane().remove(previousPanel);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 1000;
		c.ipady = 570;
		getContentPane().add(singleServicesPanel, c);
		previousPanel = singleServicesPanel;
		refreshData();
	}

	//method called when combo service button pressed
	private void comboServicesButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//reset previous button to dark grey background
		previousButton.setBorder(new LineBorder(darkGrey));
		previousButton.setBackground(darkGrey);
		previousButton.setOpaque(true);
		previousButton.setForeground(Color.WHITE);
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
		c.ipadx = 1000;
		c.ipady = 570;
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
		c.ipadx = 1000;
		c.ipady = 570;
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
		c.ipadx = 1000;
		c.ipady = 570;
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
		c.ipadx = 1000;
		c.ipady = 570;
		getContentPane().add(businessDetailsPanel, c);
		//set this panel as the current panel
		previousPanel = businessDetailsPanel;
		//refresh page
		refreshData();
	}

}
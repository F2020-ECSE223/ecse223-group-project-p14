package ca.mcgill.ecse.flexibook.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.Color;

public class ku extends JFrame {

	private JPanel contentPane;
	private JTextField newAppTimeT;
	private JTextField newAppDateT;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField selectedAppTimeT;
	private JTextField selectedAppDateT;
	private JTextField newUpdateAppDateT;
	private JTextField newUpdateAppTimeT;
	private JTextField updateComboItemNameL;
	private JTable customerAppointmentTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ku frame = new ku();
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
	public ku() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel bookAppointmentPanel = new JPanel();
		bookAppointmentPanel.setBounds(0, 0, 1100, 600);
		contentPane.add(bookAppointmentPanel);
		bookAppointmentPanel.setLayout(null);
		
		newAppTimeT = new JTextField();
		newAppTimeT.setBounds(174, 145, 86, 20);
		newAppTimeT.setText("HH:MM");
		newAppTimeT.setColumns(10);
		bookAppointmentPanel.add(newAppTimeT);
		
		JLabel registerTime = new JLabel("Time of New Appointment");
		registerTime.setBounds(22, 148, 123, 14);
		bookAppointmentPanel.add(registerTime);
		
		newAppDateT = new JTextField();
		newAppDateT.setBounds(174, 120, 86, 20);
		newAppDateT.setText("YYYY-MM-DD");
		newAppDateT.setColumns(10);
		bookAppointmentPanel.add(newAppDateT);
		
		JButton addAppForSingleServiceB = new JButton("Add new appointment (Single Service)");
		addAppForSingleServiceB.setBounds(22, 186, 215, 23);
		bookAppointmentPanel.add(addAppForSingleServiceB);
		
		JLabel comboAppPanelLabel = new JLabel("Select optional combo items");
		comboAppPanelLabel.setBounds(415, 79, 171, 17);
		comboAppPanelLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bookAppointmentPanel.add(comboAppPanelLabel);
		
		JLabel optionalServiceNamesLabel = new JLabel("Optional Service Names");
		optionalServiceNamesLabel.setBounds(415, 123, 113, 14);
		bookAppointmentPanel.add(optionalServiceNamesLabel);
		
		textField_2 = new JTextField();
		textField_2.setBounds(564, 120, 86, 20);
		textField_2.setColumns(10);
		bookAppointmentPanel.add(textField_2);
		
		JButton addAppForComboB = new JButton("Add new appointment (Combo)");
		addAppForComboB.setBounds(415, 186, 181, 23);
		bookAppointmentPanel.add(addAppForComboB);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(113, 12, 120, 27);
		bookAppointmentPanel.add(textField_3);
		
		JLabel selectedServiceNameLabel = new JLabel("Service Name");
		selectedServiceNameLabel.setBounds(22, 0, 81, 50);
		bookAppointmentPanel.add(selectedServiceNameLabel);
		
		JLabel errorMsgLabel = new JLabel("err");
		errorMsgLabel.setBounds(439, 18, 46, 14);
		bookAppointmentPanel.add(errorMsgLabel);
		
		JLabel registerDate = new JLabel("Date of New Appointment");
		registerDate.setBounds(22, 123, 124, 14);
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
		oldDate.setBounds(277, 307, 160, 14);
		bookAppointmentPanel.add(oldDate);
		
		selectedAppTimeT = new JTextField();
		selectedAppTimeT.setText("HH:MM");
		selectedAppTimeT.setColumns(10);
		selectedAppTimeT.setBounds(447, 332, 86, 20);
		bookAppointmentPanel.add(selectedAppTimeT);
		
		selectedAppDateT = new JTextField();
		selectedAppDateT.setText("YYYY-MM-DD");
		selectedAppDateT.setColumns(10);
		selectedAppDateT.setBounds(447, 304, 86, 20);
		bookAppointmentPanel.add(selectedAppDateT);
		
		JLabel oldTime = new JLabel("Time of the Appointment");
		oldTime.setBounds(277, 335, 123, 14);
		bookAppointmentPanel.add(oldTime);
		
		JLabel newDateLabel = new JLabel("New Date");
		newDateLabel.setBounds(19, 385, 160, 14);
		bookAppointmentPanel.add(newDateLabel);
		
		newUpdateAppDateT = new JTextField();
		newUpdateAppDateT.setText("YYYY-MM-DD");
		newUpdateAppDateT.setColumns(10);
		newUpdateAppDateT.setBounds(174, 382, 86, 20);
		bookAppointmentPanel.add(newUpdateAppDateT);
		
		newUpdateAppTimeT = new JTextField();
		newUpdateAppTimeT.setText("HH:MM");
		newUpdateAppTimeT.setColumns(10);
		newUpdateAppTimeT.setBounds(174, 410, 86, 20);
		bookAppointmentPanel.add(newUpdateAppTimeT);
		
		JLabel newTimeLabel = new JLabel("New Time");
		newTimeLabel.setBounds(19, 413, 123, 14);
		bookAppointmentPanel.add(newTimeLabel);
		
		JButton updateTimeB = new JButton("Update to new time");
		updateTimeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		updateTimeB.setBounds(18, 456, 215, 23);
		bookAppointmentPanel.add(updateTimeB);
		
		JComboBox<String> updateActionComboBox = new JComboBox<String>();
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
		chosenComboItemL.setBounds(415, 413, 113, 14);
		bookAppointmentPanel.add(chosenComboItemL);
		
		updateComboItemNameL = new JTextField();
		updateComboItemNameL.setColumns(10);
		updateComboItemNameL.setBounds(564, 410, 86, 20);
		bookAppointmentPanel.add(updateComboItemNameL);
		
		JButton updateContentB = new JButton("Update to new service content");
		updateContentB.setBounds(415, 456, 215, 23);
		bookAppointmentPanel.add(updateContentB);
		
		customerAppointmentTable = new JTable();
		customerAppointmentTable.setToolTipText("");
		customerAppointmentTable.setRowSelectionAllowed(false);
		customerAppointmentTable.setBackground(Color.WHITE);
		customerAppointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerAppointmentTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		));
		customerAppointmentTable.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		customerAppointmentTable.setBounds(712, 538, 332, -517);
		bookAppointmentPanel.add(customerAppointmentTable);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(1060, 12, 17, 520);
		bookAppointmentPanel.add(scrollBar);
	}
}

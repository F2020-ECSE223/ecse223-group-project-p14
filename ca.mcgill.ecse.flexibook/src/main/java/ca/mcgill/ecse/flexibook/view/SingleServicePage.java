package ca.mcgill.ecse.flexibook.view;

import java.awt.EventQueue;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.checkerframework.common.value.qual.StringVal;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOService;
import ca.mcgill.ecse.flexibook.model.Owner;
import ca.mcgill.ecse.flexibook.model.Service;
import ca.mcgill.ecse.flexibook.model.User;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class SingleServicePage {

	private JFrame frame;
	private JTextField newServiceNameTextField;
	private JTextField newServiceDurationTextField;
	private JTextField newServiceDowntimeDurationTextField;
	private JTextField newServiceDowntimeStartTextField;
	private JTable existingServiceTable;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private String error = null;
	private JLabel errorMessageLabel;
	private JLabel successMessageLabel; 
	private String success = null;
	private HashMap<Integer, TOService> services;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SingleServicePage window = new SingleServicePage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SingleServicePage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel addServicePane = new JPanel();
		addServicePane.setLayout(null);
		tabbedPane.addTab("Add Service", null, addServicePane, null);

		JLabel lblNewLabel = new JLabel("New Service Name");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(43, 100, 115, 21);
		addServicePane.add(lblNewLabel);

		newServiceNameTextField = new JTextField();
		newServiceNameTextField.setColumns(10);
		newServiceNameTextField.setBounds(267, 97, 130, 26);
		addServicePane.add(newServiceNameTextField);

		JLabel lblNewLabel_1 = new JLabel("New Service Duration");
		lblNewLabel_1.setBounds(43, 128, 134, 16);
		addServicePane.add(lblNewLabel_1);

		newServiceDurationTextField = new JTextField();
		newServiceDurationTextField.setColumns(10);
		newServiceDurationTextField.setBounds(267, 123, 130, 26);
		addServicePane.add(newServiceDurationTextField);

		JLabel lblNewLabel_1_1 = new JLabel("New Service Downtime Duration");
		lblNewLabel_1_1.setBounds(43, 151, 212, 21);
		addServicePane.add(lblNewLabel_1_1);

		newServiceDowntimeDurationTextField = new JTextField();
		newServiceDowntimeDurationTextField.setColumns(10);
		newServiceDowntimeDurationTextField.setBounds(267, 148, 130, 26);
		addServicePane.add(newServiceDowntimeDurationTextField);

		JLabel lblNewLabel_1_1_1 = new JLabel("New Service Downtime Start");
		lblNewLabel_1_1_1.setBounds(43, 176, 212, 21);
		addServicePane.add(lblNewLabel_1_1_1);

		newServiceDowntimeStartTextField = new JTextField();
		newServiceDowntimeStartTextField.setColumns(10);
		newServiceDowntimeStartTextField.setBounds(267, 173, 130, 26);
		addServicePane.add(newServiceDowntimeStartTextField);

		JButton confirmAddServiceButton = new JButton("Confirm");
		confirmAddServiceButton.setBounds(164, 197, 117, 29);
		addServicePane.add(confirmAddServiceButton);
		String[] col = {"name","duration","downtimeDrtn","downtimeStart"};
		existingServiceTable = new JTable();
	
		existingServiceTable.setBounds(43, 20, 350, 78);
		addServicePane.add(existingServiceTable);

		JLabel lblNewLabel_2 = new JLabel("Existing Services");
		lblNewLabel_2.setBounds(43, 0, 180, 16);
		addServicePane.add(lblNewLabel_2);
		
		errorMessageLabel = new JLabel("");
 		errorMessageLabel.setForeground(Color.RED);
		errorMessageLabel.setBounds(6, 202, 163, 24);
		addServicePane.add(errorMessageLabel);
		
		successMessageLabel = new JLabel("");
		successMessageLabel.setForeground(Color.GREEN);
		successMessageLabel.setBounds(277, 202, 61, 16);
		addServicePane.add(successMessageLabel);

		JPanel deleteServicePane = new JPanel();
		deleteServicePane.setLayout(null);
		tabbedPane.addTab("Delete Service", null, deleteServicePane, null);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(87, 76, 262, 48);
		deleteServicePane.add(comboBox);

		JLabel lblNewLabel_3 = new JLabel("Select the service you want to delete:");
		lblNewLabel_3.setBounds(30, 16, 280, 48);
		deleteServicePane.add(lblNewLabel_3);

		JButton confirmDeleteServiceButton = new JButton("Confirm");
		confirmDeleteServiceButton.setBounds(144, 164, 117, 29);
		deleteServicePane.add(confirmDeleteServiceButton);

		JPanel updateServicePane = new JPanel();
		updateServicePane.setLayout(null);
		tabbedPane.addTab("Update Service", null, updateServicePane, null);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(100, 53, 235, 27);
		updateServicePane.add(comboBox_1);

		JLabel lblNewLabel_3_1 = new JLabel("Select the service you want to update:");
		lblNewLabel_3_1.setBounds(45, 25, 312, 16);
		updateServicePane.add(lblNewLabel_3_1);

		JLabel lblNewLabel_4 = new JLabel("New Service Name");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_4.setBounds(40, 95, 115, 21);
		updateServicePane.add(lblNewLabel_4);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(264, 92, 130, 26);
		updateServicePane.add(textField_4);

		JLabel lblNewLabel_1_2 = new JLabel("New Service Duration");
		lblNewLabel_1_2.setBounds(40, 123, 134, 16);
		updateServicePane.add(lblNewLabel_1_2);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(264, 118, 130, 26);
		updateServicePane.add(textField_5);

		JLabel lblNewLabel_1_1_2 = new JLabel("New Service Downtime Duration");
		lblNewLabel_1_1_2.setBounds(40, 146, 212, 21);
		updateServicePane.add(lblNewLabel_1_1_2);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(264, 143, 130, 26);
		updateServicePane.add(textField_6);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("New Service Downtime Start");
		lblNewLabel_1_1_1_1.setBounds(40, 171, 212, 21);
		updateServicePane.add(lblNewLabel_1_1_1_1);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(264, 168, 130, 26);
		updateServicePane.add(textField_7);

		JButton confirmUpdateServiceButton = new JButton("Confirm");
		confirmUpdateServiceButton.setBounds(161, 192, 117, 29);
		updateServicePane.add(confirmUpdateServiceButton);


		confirmAddServiceButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				singleServicesButtonActionPerformed(evt);
			}		
		});

	}
	
	private void refreshData () {
		errorMessageLabel.setText(error);
		successMessageLabel.setText(success);
		if (error == null || error.length() == 0) {
			newServiceDowntimeDurationTextField.setText("");
			newServiceDowntimeStartTextField.setText("");
			newServiceDurationTextField.setText("");
			newServiceNameTextField.setText("");
			
			services = new HashMap<Integer, TOService> ();
			int index = 0;
			for (TOService service : FlexiBookController.getTOServices() ) {
				services.put(index, service);
				
				index++;
			}
			
		}
	}

	private void singleServicesButtonActionPerformed(ActionEvent evt) {
		error = null;
		Owner owner = new Owner("owner", "123", FlexiBookApplication.getFlexiBook());
 		FlexiBookApplication.setCurrentLoginUser(owner);
		try {
			FlexiBookController.addService(newServiceNameTextField.getText(), Integer.parseInt(newServiceDurationTextField.getText()), 
					Integer.parseInt(newServiceDowntimeStartTextField.getText()), 
					Integer.parseInt(newServiceDowntimeDurationTextField.getText()));
			success = "Success!";
		}  catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		refreshData();
		
	
	}
	
}

package ca.mcgill.ecse.flexibook.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.TOService;
import java.awt.Font;

public class ServiceTest {
	
	// singe service page 
		private JPanel singleServicesPanel;
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

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServiceTest window = new ServiceTest();
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
	public ServiceTest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		
//		confirmAddServiceButton.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//					addSingleServicesButtonActionPerformed(evt);			
//			}		
//		});
//		
//		confirmDeleteServiceButton.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				if (!FlexiBookController.getTOServices().isEmpty()) {
//					deleteSingleServicesButtonActionPerformed(evt);
//				}
//			}		
//		});
//		
//		confirmUpdateServiceButton.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				if (!FlexiBookController.getTOServices().isEmpty()) {
//					updateSingleServicesButtonActionPerformed(evt);
//				}
//			}		
//		});

	}
}


package ca.mcgill.ecse.flexibook.view;

import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

//import org.checkerframework.checker.signature.qual.InternalForm;
//import org.checkerframework.common.value.qual.StringVal;
//import com.jgoodies.forms.builder.ListViewBuilder;


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
import javax.swing.JScrollPane;
import javax.swing.table.*;

public class SingleServicePage {

	private JFrame frame;
	private JTextField newServiceNameTextField;
	private JTextField newServiceDurationTextField;
	private JTextField newServiceDowntimeDurationTextField;
	private JTextField newServiceDowntimeStartTextField;
	private JTable existingServiceAddTable;
	private JTable existingServiceDeleteTable;
	private JComboBox<String> deleteServiceComboBox;
	private JTextField updateServiceNameTextField;
	private JTextField updateServiceDurationTextField;
	private JTextField updateDowntimeDurationTextField;
	private JTextField updateDowntimeStartTextField;
	private String addError = null;
	private String deleteError = null;
	private String updateError = null;
	private JLabel successMessageAddServiceLabel;
	private JLabel errorMessageAddServiceLabel; 
	private JLabel successMessageDeleteServiceLabel;
	private JLabel errorMessageDeleteServiceLabel; 
	private String addSuccess = null;
	private String deleteSuccess = null;
	private String updateSuccess = null;
	DefaultTableModel modelAddService;
	DefaultTableModel modelDeleteService;
	DefaultTableModel modelUpdateService;
	Object[] row;
	private JTable existingServiceUpdateTable;
	JComboBox<String> updateServiceComboBox;
	JLabel updateErrorLabel;
	JLabel updateSuccessLabel;

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
		frame.setBounds(100, 100, 704, 551);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel addServicePane = new JPanel();
		addServicePane.setLayout(null);
		tabbedPane.addTab("Add Service", null, addServicePane, null);

		JLabel lblNewLabel = new JLabel("New Service Name");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(155, 313, 115, 21);
		addServicePane.add(lblNewLabel);

		newServiceNameTextField = new JTextField();
		newServiceNameTextField.setColumns(10);
		newServiceNameTextField.setBounds(379, 310, 130, 26);
		addServicePane.add(newServiceNameTextField);

		JLabel lblNewLabel_1 = new JLabel("New Service Duration");
		lblNewLabel_1.setBounds(155, 341, 134, 16);
		addServicePane.add(lblNewLabel_1);

		newServiceDurationTextField = new JTextField();
		newServiceDurationTextField.setColumns(10);
		newServiceDurationTextField.setBounds(379, 336, 130, 26);
		addServicePane.add(newServiceDurationTextField);

		JLabel lblNewLabel_1_1 = new JLabel("New Service Downtime Duration");
		lblNewLabel_1_1.setBounds(155, 364, 212, 21);
		addServicePane.add(lblNewLabel_1_1);

		newServiceDowntimeDurationTextField = new JTextField();
		newServiceDowntimeDurationTextField.setColumns(10);
		newServiceDowntimeDurationTextField.setBounds(379, 361, 130, 26);
		addServicePane.add(newServiceDowntimeDurationTextField);

		JLabel lblNewLabel_1_1_1 = new JLabel("New Service Downtime Start");
		lblNewLabel_1_1_1.setBounds(155, 389, 212, 21);
		addServicePane.add(lblNewLabel_1_1_1);

		newServiceDowntimeStartTextField = new JTextField();
		newServiceDowntimeStartTextField.setColumns(10);
		newServiceDowntimeStartTextField.setBounds(379, 386, 130, 26);
		addServicePane.add(newServiceDowntimeStartTextField);

		JButton confirmAddServiceButton = new JButton("Confirm");
		confirmAddServiceButton.setBounds(283, 424, 117, 29);
		addServicePane.add(confirmAddServiceButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 42, 594, 259);
		addServicePane.add(scrollPane);



		existingServiceAddTable = new JTable();
		modelAddService = new DefaultTableModel();
		Object[] col = {"Name","ServiceDuration","ServiceDowntimeDuration","ServiceDowntimeStart"};
		row = new Object[0];
		modelAddService.setColumnIdentifiers(col);
		existingServiceAddTable.setModel(modelAddService);
		scrollPane.setViewportView(existingServiceAddTable);

		JLabel lblNewLabel_2 = new JLabel("Existing Services");
		lblNewLabel_2.setBounds(50, 15, 106, 16);
		addServicePane.add(lblNewLabel_2);

		successMessageAddServiceLabel = new JLabel("");
		successMessageAddServiceLabel.setForeground(Color.RED);
		successMessageAddServiceLabel.setBounds(168, 15, 472, 24);
		addServicePane.add(successMessageAddServiceLabel);

		errorMessageAddServiceLabel = new JLabel("");
		errorMessageAddServiceLabel.setForeground(Color.GREEN);
		errorMessageAddServiceLabel.setBounds(412, 437, 201, 16);
		addServicePane.add(errorMessageAddServiceLabel);


		confirmAddServiceButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
					addSingleServicesButtonActionPerformed(evt);			
			}		
		});


		modelDeleteService = new DefaultTableModel();
		modelDeleteService.setColumnIdentifiers(col);


		JPanel deleteServicePane = new JPanel();
		deleteServicePane.setLayout(null);
		tabbedPane.addTab("Delete Service", null, deleteServicePane, null);

		deleteServiceComboBox = new JComboBox<String>();
		deleteServiceComboBox.setBounds(200, 388, 262, 48);
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				deleteServiceComboBox.addItem(service.getName());
			}
		}
		deleteServicePane.add(deleteServiceComboBox);

		JLabel lblNewLabel_3 = new JLabel("Select the service you want to delete:");
		lblNewLabel_3.setBounds(25, 366, 252, 38);
		deleteServicePane.add(lblNewLabel_3);

		JButton confirmDeleteServiceButton = new JButton("Confirm");
		confirmDeleteServiceButton.setBounds(269, 448, 117, 29);
		deleteServicePane.add(confirmDeleteServiceButton);

		confirmDeleteServiceButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (!FlexiBookController.getTOServices().isEmpty()) {
					deleteSingleServicesButtonActionPerformed(evt);
				}
			}		
		});

		JLabel lblNewLabel_2_1 = new JLabel("Existing Services");
		lblNewLabel_2_1.setBounds(35, 30, 106, 16);
		deleteServicePane.add(lblNewLabel_2_1);

		errorMessageDeleteServiceLabel = new JLabel("");
		errorMessageDeleteServiceLabel.setForeground(Color.RED);
		errorMessageDeleteServiceLabel.setBounds(153, 22, 472, 24);
		deleteServicePane.add(errorMessageDeleteServiceLabel);

		JScrollPane existingServiceDeletescrollPane = new JScrollPane();
		existingServiceDeletescrollPane.setBounds(34, 54, 580, 300);
		deleteServicePane.add(existingServiceDeletescrollPane);

		existingServiceDeleteTable = new JTable();
		existingServiceDeleteTable.setModel(modelDeleteService);
		existingServiceDeletescrollPane.setViewportView(existingServiceDeleteTable);

		successMessageDeleteServiceLabel = new JLabel("");
		successMessageDeleteServiceLabel.setForeground(Color.GREEN);
		successMessageDeleteServiceLabel.setBounds(398, 448, 184, 24);
		deleteServicePane.add(successMessageDeleteServiceLabel);


		JPanel updateServicePane = new JPanel();
		updateServicePane.setLayout(null);
		tabbedPane.addTab("Update Service", null, updateServicePane, null);

		updateServiceComboBox = new JComboBox<String>();
		updateServiceComboBox.setBounds(205, 298, 235, 27);
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				updateServiceComboBox.addItem(service.getName());
			}
		}
		updateServicePane.add(updateServiceComboBox);

		JLabel lblNewLabel_3_1 = new JLabel("Select the service you want to update:");
		lblNewLabel_3_1.setBounds(150, 270, 312, 16);
		updateServicePane.add(lblNewLabel_3_1);

		JLabel lblNewLabel_4 = new JLabel("New Service Name");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_4.setBounds(145, 340, 115, 21);
		updateServicePane.add(lblNewLabel_4);

		updateServiceNameTextField = new JTextField();
		updateServiceNameTextField.setColumns(10);
		updateServiceNameTextField.setBounds(369, 337, 130, 26);
		updateServicePane.add(updateServiceNameTextField);

		JLabel lblNewLabel_1_2 = new JLabel("New Service Duration");
		lblNewLabel_1_2.setBounds(145, 368, 134, 16);
		updateServicePane.add(lblNewLabel_1_2);

		updateServiceDurationTextField = new JTextField();
		updateServiceDurationTextField.setColumns(10);
		updateServiceDurationTextField.setBounds(369, 363, 130, 26);
		updateServicePane.add(updateServiceDurationTextField);

		JLabel lblNewLabel_1_1_2 = new JLabel("New Service Downtime Duration");
		lblNewLabel_1_1_2.setBounds(145, 391, 212, 21);
		updateServicePane.add(lblNewLabel_1_1_2);

		updateDowntimeDurationTextField = new JTextField();
		updateDowntimeDurationTextField.setColumns(10);
		updateDowntimeDurationTextField.setBounds(369, 388, 130, 26);
		updateServicePane.add(updateDowntimeDurationTextField);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("New Service Downtime Start");
		lblNewLabel_1_1_1_1.setBounds(145, 416, 212, 21);
		updateServicePane.add(lblNewLabel_1_1_1_1);

		updateDowntimeStartTextField = new JTextField();
		updateDowntimeStartTextField.setColumns(10);
		updateDowntimeStartTextField.setBounds(369, 413, 130, 26);
		updateServicePane.add(updateDowntimeStartTextField);

		JButton confirmUpdateServiceButton = new JButton("Confirm");
		confirmUpdateServiceButton.setBounds(266, 437, 117, 29);
		updateServicePane.add(confirmUpdateServiceButton);
		
		JScrollPane existingServiceUpdateScrollPane = new JScrollPane();
		existingServiceUpdateScrollPane.setBounds(46, 40, 575, 229);
		updateServicePane.add(existingServiceUpdateScrollPane);
		
		existingServiceUpdateTable = new JTable();
		modelUpdateService = new DefaultTableModel();
		modelUpdateService.setColumnIdentifiers(col);
		existingServiceUpdateTable.setModel(modelUpdateService);
		existingServiceUpdateScrollPane.setViewportView(existingServiceUpdateTable);
		
		JLabel lblNewLabel_5 = new JLabel("Existing Service");
		lblNewLabel_5.setBounds(49, 19, 134, 16);
		updateServicePane.add(lblNewLabel_5);
		
		updateErrorLabel = new JLabel("");
		updateErrorLabel.setForeground(Color.RED);
		updateErrorLabel.setBounds(205, 19, 412, 16);
		updateServicePane.add(updateErrorLabel);
		
		updateSuccessLabel = new JLabel("");
		updateSuccessLabel.setForeground(Color.GREEN);
		updateSuccessLabel.setBounds(395, 442, 104, 16);
		updateServicePane.add(updateSuccessLabel);

		if (!FlexiBookController.getTOServices().isEmpty()) {
			List<TOService> toServices = FlexiBookController.getTOServices();
			for (TOService service : toServices) {
				String name = service.getName();
				String duration = Integer.toString(service.getDuration());
				String downtimeDuration = Integer.toString(service.getDowntimeDuration());
				String downtimeStart = Integer.toString(service.getDowntimeStart());
				Object[] obj = {name, duration, downtimeDuration, downtimeStart};
				modelAddService.addRow(obj);
				modelDeleteService.addRow(obj);
				modelUpdateService.addRow(obj);
			}
		}
		confirmUpdateServiceButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (!FlexiBookController.getTOServices().isEmpty()) {
					updateSingleServicesButtonActionPerformed(evt);
				}
			}		
		});

	}

	private void refreshData () {
		successMessageAddServiceLabel.setText(addError);
		errorMessageAddServiceLabel.setText(addSuccess);
		successMessageDeleteServiceLabel.setText(deleteSuccess);
		errorMessageDeleteServiceLabel.setText(deleteError);
		updateErrorLabel.setText(updateError); 
		updateSuccessLabel.setText(updateSuccess);
		deleteServiceComboBox.removeAllItems();
		updateServiceComboBox.removeAllItems();
		modelAddService.getDataVector().removeAllElements();
		modelDeleteService.getDataVector().removeAllElements();
		modelUpdateService.getDataVector().removeAllElements();
		if (!FlexiBookController.getTOServices().isEmpty()) {
			List<TOService> toServices = FlexiBookController.getTOServices();
			for (TOService service : toServices) {
				String name = service.getName();
				String duration = Integer.toString(service.getDuration());
				String downtimeDuration = Integer.toString(service.getDowntimeDuration());
				String downtimeStart = Integer.toString(service.getDowntimeStart());
				Object[] obj = {name, duration, downtimeDuration, downtimeStart};
				modelAddService.addRow(obj);
				modelDeleteService.addRow(obj);
				modelUpdateService.addRow(obj);
			}
		}
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				deleteServiceComboBox.addItem(service.getName());
				updateServiceComboBox.addItem(service.getName());
			}
		}
		
		
		if (addError == null || addError.length() == 0) {
			newServiceDowntimeDurationTextField.setText("");
			newServiceDowntimeStartTextField.setText("");
			newServiceDurationTextField.setText("");
			newServiceNameTextField.setText("");		
		}
		
		if(FlexiBookController.getTOServices().isEmpty()) {
			deleteServiceComboBox.removeAllItems();
			updateServiceComboBox.removeAllItems();
			modelAddService.getDataVector().removeAllElements();
			modelDeleteService.getDataVector().removeAllElements();
			modelUpdateService.getDataVector().removeAllElements();
		}
		
		
	}

	private void addSingleServicesButtonActionPerformed(ActionEvent evt) {
		addError = null;
		addSuccess = null;
		FlexiBookApplication.setCurrentLoginUser(FlexiBookApplication.getFlexiBook().getOwner());
		if (newServiceDowntimeDurationTextField.getText().equals("")||
				newServiceDowntimeStartTextField.getText().equals("")||
				newServiceDurationTextField.getText().equals("")||
				newServiceNameTextField.getText().equals("")) {
			addError = "one of the fields is empty";
			
		}
		else {
			try {
				FlexiBookController.addService(newServiceNameTextField.getText(), Integer.parseInt(newServiceDurationTextField.getText()), 
						Integer.parseInt(newServiceDowntimeStartTextField.getText()), 
						Integer.parseInt(newServiceDowntimeDurationTextField.getText()));
				addSuccess = "Success!";
			}  catch (InvalidInputException e) {
				addError = e.getMessage();
			}
		}
			
		refreshData();


	}

	private void deleteSingleServicesButtonActionPerformed(ActionEvent evt) {
		deleteError = null; 
		deleteSuccess = null;
		FlexiBookApplication.setCurrentLoginUser(FlexiBookApplication.getFlexiBook().getOwner());
		try {
			FlexiBookController.deleteService((String)deleteServiceComboBox.getSelectedItem());
			deleteSuccess = "Success!";
		} catch (InvalidInputException e) {
			deleteError = e.getMessage();
		}
		
		refreshData();
	}
	
	private void updateSingleServicesButtonActionPerformed(ActionEvent evt) {
		updateError = null;
		updateSuccess = null;
		FlexiBookApplication.setCurrentLoginUser(FlexiBookApplication.getFlexiBook().getOwner());
		if (updateDowntimeDurationTextField.getText().equals("")||
				updateDowntimeStartTextField.getText().equals("")||
				updateServiceDurationTextField.getText().equals("")||
				updateServiceNameTextField.getText().equals("")) {
			updateError = "one of the fields is empty";
			
		}
		else {
			try {
				FlexiBookController.updateService((String)updateServiceComboBox.getSelectedItem(), 
						updateServiceNameTextField.getText(), Integer.parseInt(updateServiceDurationTextField.getText()), 
						Integer.parseInt(updateDowntimeDurationTextField.getText()), 
						Integer.parseInt(updateDowntimeStartTextField.getText()));
				updateSuccess = "Success!";
			}catch (InvalidInputException e) {
				updateError = e.getMessage();
			}
		}
		
		
		
		refreshData();
		
	}
}

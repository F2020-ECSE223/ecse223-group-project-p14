package ca.mcgill.ecse.flexibook.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

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

import org.checkerframework.checker.formatter.qual.Format;
import org.checkerframework.common.value.qual.StringVal;

import com.jgoodies.forms.builder.ListViewBuilder;

import ca.mcgill.ecse.flexibook.application.FlexiBookApplication;
import ca.mcgill.ecse.flexibook.controller.FlexiBookController;
import ca.mcgill.ecse.flexibook.controller.InvalidInputException;
import ca.mcgill.ecse.flexibook.controller.TOComboItem;
import ca.mcgill.ecse.flexibook.controller.TOService;
import ca.mcgill.ecse.flexibook.controller.TOServiceCombo;
import ca.mcgill.ecse.flexibook.model.ComboItem;

import javax.swing.JList;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class ServiceComboTest {
	
	private JPanel serviceComboPanel;
	private JTextField newServiceComboNameTextField;
	private JComboBox<String> deleteServiceComboBox;
	private JTextField updateServiceNameTextField;
	private JTextField updateServiceDurationTextField;
	private JTextField updateDowntimeDurationTextField;
	private JTextField updateDowntimeStartTextField;
	private String errorMessageServiceCombo = null;
	private JLabel deleteSuccessLabel;
	private JLabel defineServiceComboSuccessLabel;
	private JLabel errorMessageServiceComboLabel; 
	private String defineComboSuccess = null;
	private String deleteComboSuccess = null;
	private String updateComboSuccess = null;
	Object[] row;
	private JTable existingServiceComboTable;
	JComboBox<String> updateServiceComboBox;
	JLabel updateErrorLabel;
	JLabel updateSuccessLabel;
	DefaultTableModel modelModifySingleServiceCombo;
	JList<String> newComboItemList;
	JComboBox<String> MainServiceComboBox;
	JList<String> newMandatoryList;
	DefaultListModel<String> newComboListModel;
	DefaultListModel<String> newMandatoryComboItemListModel;
	private JFrame frame;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServiceComboTest window = new ServiceComboTest();
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
	public ServiceComboTest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(700,1100,1100,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		serviceComboPanel = new JPanel();
		serviceComboPanel.setPreferredSize(new Dimension(1100,700));
		serviceComboPanel.setBackground(Color.WHITE);
		serviceComboPanel.setOpaque(true);
		serviceComboPanel.setForeground(Color.WHITE);
		serviceComboPanel.setLayout(null);
		frame.getContentPane().add(serviceComboPanel);
		
		JScrollPane serviceComboScrollPane = new JScrollPane();
		serviceComboScrollPane.setBounds(479, 6, 524, 404);
		serviceComboPanel.add(serviceComboScrollPane);
		
		
		errorMessageServiceComboLabel = new JLabel("");
		errorMessageServiceComboLabel.setForeground(Color.RED);
		errorMessageServiceComboLabel.setBounds(47, 17, 366, 16);
		serviceComboPanel.add(errorMessageServiceComboLabel);
		
		existingServiceComboTable = new JTable();
		modelModifySingleServiceCombo = new DefaultTableModel();
		Object[] col = {"Name","MainService","ComboItem","isMandatory"};
		modelModifySingleServiceCombo.setColumnIdentifiers(col);
		existingServiceComboTable.setModel(modelModifySingleServiceCombo);
		
		serviceComboScrollPane.setViewportView(existingServiceComboTable);
		
		
		newServiceComboNameTextField = new JTextField();
		newServiceComboNameTextField.setColumns(10);
		newServiceComboNameTextField.setBounds(254, 78, 130, 26);
		serviceComboPanel.add(newServiceComboNameTextField);

		JLabel newMainServiceLabel = new JLabel("New Main Service");
		newMainServiceLabel.setBounds(35, 111, 134, 16);
		serviceComboPanel.add(newMainServiceLabel);

		JLabel newComboItemLabel = new JLabel("New Combo Items");
		newComboItemLabel.setBounds(35, 139, 202, 16);
		serviceComboPanel.add(newComboItemLabel);

		JLabel newMandatoryLabel = new JLabel("New Mandatory Combo Items");
		newMandatoryLabel.setBounds(35, 231, 202, 16);
		serviceComboPanel.add(newMandatoryLabel);

		JButton confirmDefineServiceComboButton = new JButton("Confirm");
		confirmDefineServiceComboButton.setBounds(163, 314, 95, 29);
		serviceComboPanel.add(confirmDefineServiceComboButton);
		
		JLabel newServiceComboNameLabel = new JLabel("New Service Combo Name");
		newServiceComboNameLabel.setBounds(35, 83, 176, 16);
		serviceComboPanel.add(newServiceComboNameLabel);
		
		defineServiceComboSuccessLabel = new JLabel("");
		defineServiceComboSuccessLabel.setForeground(Color.GREEN);
		defineServiceComboSuccessLabel.setBounds(256, 327, 157, 16);
		serviceComboPanel.add(defineServiceComboSuccessLabel);
		
		deleteServiceComboBox = new JComboBox<String>();
		deleteServiceComboBox.setBounds(674, 545, 262, 48);
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				deleteServiceComboBox.addItem(service.getName());
			}
		}
		serviceComboPanel.add(deleteServiceComboBox);
		
		JLabel selectDeleteServiceLabel = new JLabel("Select the service you want to delete:");
		selectDeleteServiceLabel.setBounds(684, 500, 252, 38);
		serviceComboPanel.add(selectDeleteServiceLabel);
		
		JButton confirmDeleteServiceButton = new JButton("Confirm");
		confirmDeleteServiceButton.setBounds(750, 625, 95, 29);
		serviceComboPanel.add(confirmDeleteServiceButton);
		
		deleteSuccessLabel = new JLabel("");
		deleteSuccessLabel.setForeground(Color.GREEN);
		deleteSuccessLabel.setBounds(853, 638, 150, 16);
		serviceComboPanel.add(deleteSuccessLabel);
		
		updateServiceComboBox = new JComboBox<String>();
		updateServiceComboBox.setBounds(106, 479, 235, 27);
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				updateServiceComboBox.addItem(service.getName());
			}
		}
		serviceComboPanel.add(updateServiceComboBox);
		
		JLabel selectServiceUpdateLabel = new JLabel("Select the service you want to update:");
		selectServiceUpdateLabel.setBounds(51, 451, 312, 16);
		serviceComboPanel.add(selectServiceUpdateLabel);
		
		JLabel updateServiceNameLabel = new JLabel("New Service Name");
		updateServiceNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		updateServiceNameLabel.setBounds(46, 521, 115, 21);
		serviceComboPanel.add(updateServiceNameLabel);
		
		updateServiceNameTextField = new JTextField();
		updateServiceNameTextField.setColumns(10);
		updateServiceNameTextField.setBounds(270, 525, 130, 26);
		serviceComboPanel.add(updateServiceNameTextField);
		
		JLabel updateServiceDurationLabel = new JLabel("New Service Duration");
		updateServiceDurationLabel.setBounds(46, 549, 134, 16);
		serviceComboPanel.add(updateServiceDurationLabel);
		
		updateServiceDurationTextField = new JTextField();
		updateServiceDurationTextField.setColumns(10);
		updateServiceDurationTextField.setBounds(270, 551, 130, 26);
		serviceComboPanel.add(updateServiceDurationTextField);
		
		JLabel updateServiceDowntimeDurationLabel = new JLabel("New Service Downtime Duration");
		updateServiceDowntimeDurationLabel.setBounds(46, 572, 212, 21);
		serviceComboPanel.add(updateServiceDowntimeDurationLabel);
		
		updateDowntimeDurationTextField = new JTextField();
		updateDowntimeDurationTextField.setColumns(10);
		updateDowntimeDurationTextField.setBounds(270, 576, 130, 26);
		serviceComboPanel.add(updateDowntimeDurationTextField);
		
		JLabel updateServiceDowntimeStartLabel = new JLabel("New Service Downtime Start");
		updateServiceDowntimeStartLabel.setBounds(46, 597, 212, 21);
		serviceComboPanel.add(updateServiceDowntimeStartLabel);
		
		updateDowntimeStartTextField = new JTextField();
		updateDowntimeStartTextField.setColumns(10);
		updateDowntimeStartTextField.setBounds(270, 601, 130, 26);
		serviceComboPanel.add(updateDowntimeStartTextField);
		
		JButton confirmUpdateServiceButton = new JButton("Confirm");
		confirmUpdateServiceButton.setBounds(166, 625, 117, 29);
		serviceComboPanel.add(confirmUpdateServiceButton);
		
		updateSuccessLabel = new JLabel("");
		updateSuccessLabel.setForeground(Color.GREEN);
		updateSuccessLabel.setBounds(296, 638, 104, 16);
		serviceComboPanel.add(updateSuccessLabel);
		
		JLabel defineServiceComboIcon = new JLabel("Define Service Combo");
		defineServiceComboIcon.setForeground(Color.BLUE);
		defineServiceComboIcon.setFont(new Font("Kokonor", Font.PLAIN, 20));
		defineServiceComboIcon.setBounds(106, 45, 235, 26);
		serviceComboPanel.add(defineServiceComboIcon);
		
		JLabel updateServiceCombo = new JLabel("Update Service Combo");
		updateServiceCombo.setFont(new Font("Kokonor", Font.PLAIN, 20));
		updateServiceCombo.setForeground(Color.BLUE);
		updateServiceCombo.setBounds(150, 394, 207, 45);
		serviceComboPanel.add(updateServiceCombo);
		
		JLabel deleteServiceIcon = new JLabel("Delete Service Combo");
		deleteServiceIcon.setFont(new Font("Kokonor", Font.PLAIN, 20));
		deleteServiceIcon.setForeground(Color.BLUE);
		deleteServiceIcon.setBounds(732, 451, 176, 38);
		serviceComboPanel.add(deleteServiceIcon);
		
		newComboListModel = new DefaultListModel<String>();
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				newComboListModel.addElement(service.getName());
			}
		}
		
		
		MainServiceComboBox = new JComboBox<String>();
		MainServiceComboBox.setBounds(254, 107, 133, 26);
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				MainServiceComboBox.addItem(service.getName());
			}
		}
		
		serviceComboPanel.add(MainServiceComboBox);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(254, 231, 130, 62);
		serviceComboPanel.add(scrollPane_1);
		
		newMandatoryComboItemListModel = new DefaultListModel<String>();
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				newMandatoryComboItemListModel.addElement(service.getName());
			}
		}
		newMandatoryList = new JList<String>(newMandatoryComboItemListModel);
		scrollPane_1.setViewportView(newMandatoryList);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(254, 143, 130, 55);
		serviceComboPanel.add(scrollPane);
		newComboItemList = new JList<String>(newComboListModel);
		scrollPane.setViewportView(newComboItemList);
		newComboItemList.setBackground(Color.WHITE);
		
		confirmDefineServiceComboButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
					defineServiceComboButtonActionPerformed(evt);			
			}		
		});
		
		refreshServiceComboData();

	}
	
	public void defineServiceComboButtonActionPerformed(ActionEvent evt) {
		errorMessageServiceCombo = null;
		defineComboSuccess = null;
		FlexiBookApplication.setCurrentLoginUser(FlexiBookApplication.getFlexiBook().getOwner());
		List<Boolean> isMandatory = new ArrayList<Boolean>();
		for (String comboItem:newComboItemList.getSelectedValuesList()) {
			if (newComboItemList.getSelectedValuesList().contains(comboItem)) {
				isMandatory.add(true);
			}
			else {
				isMandatory.add(false);
			}
		}
		try {
			FlexiBookController.defineServiceCombo(newServiceComboNameTextField.getText(), 
					(String) MainServiceComboBox.getSelectedItem(), newComboItemList.getSelectedValuesList()
					, isMandatory);
			defineComboSuccess = "Success!";
		} catch (InvalidInputException e) {
			errorMessageServiceCombo = e.getMessage();
		}
		
		refreshServiceComboData();
	}
	
	
	public void refreshServiceComboData() {
		errorMessageServiceComboLabel.setText(errorMessageServiceCombo);
		defineServiceComboSuccessLabel.setText(defineComboSuccess);
		MainServiceComboBox.removeAllItems();
		modelModifySingleServiceCombo.getDataVector().removeAllElements();
		String comboItemString = null;
		String isMandatoryString = null;
		if (!FlexiBookController.getTOServiceCombos().isEmpty()) {
			for (TOServiceCombo serviceCombo : FlexiBookController.getTOServiceCombos()) {
				String name = serviceCombo.getName();
				String mainService = serviceCombo.getMainService().getServiceName();
				for (TOComboItem comboItem:serviceCombo.getServices()) {
					comboItemString = comboItemString + "," + comboItem.getServiceName();
					if (comboItem.getIsMandatory() == true) {
						isMandatoryString = isMandatoryString + "," + "ture";
					}
					else {
						isMandatoryString = isMandatoryString + "," + "false";
					}
				}

				Object[] obj = {name, mainService, comboItemString, isMandatoryString};
				modelModifySingleServiceCombo.addRow(obj);
			
			}
		}
		
		if (!FlexiBookController.getTOServices().isEmpty()) {
			for (TOService service:FlexiBookController.getTOServices()) {
				MainServiceComboBox.addItem(service.getName());
			}
		}
		
		
		
		
	}
}

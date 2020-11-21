package ca.mcgill.ecse.flexibook.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class SingleServicePage {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

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
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(267, 97, 130, 26);
		addServicePane.add(textField);
		
		JLabel lblNewLabel_1 = new JLabel("New Service Duration");
		lblNewLabel_1.setBounds(43, 128, 134, 16);
		addServicePane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(267, 123, 130, 26);
		addServicePane.add(textField_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("New Service Downtime Duration");
		lblNewLabel_1_1.setBounds(43, 151, 212, 21);
		addServicePane.add(lblNewLabel_1_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(267, 148, 130, 26);
		addServicePane.add(textField_2);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("New Service Downtime Start");
		lblNewLabel_1_1_1.setBounds(43, 176, 212, 21);
		addServicePane.add(lblNewLabel_1_1_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(267, 173, 130, 26);
		addServicePane.add(textField_3);
		
		JButton confirmAddServiceButton = new JButton("Confirm");
		confirmAddServiceButton.setBounds(164, 197, 117, 29);
		addServicePane.add(confirmAddServiceButton);
		
		table = new JTable();
		table.setBounds(43, 20, 350, 78);
		addServicePane.add(table);
		
		JLabel lblNewLabel_2 = new JLabel("Existing Services");
		lblNewLabel_2.setBounds(43, 0, 180, 16);
		addServicePane.add(lblNewLabel_2);
		
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
		
		JButton confirmAddServiceButton_1 = new JButton("Confirm");
		confirmAddServiceButton_1.setBounds(161, 192, 117, 29);
		updateServicePane.add(confirmAddServiceButton_1);
	}

}

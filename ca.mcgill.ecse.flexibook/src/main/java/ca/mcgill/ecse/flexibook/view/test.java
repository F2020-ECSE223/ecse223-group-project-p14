package ca.mcgill.ecse.flexibook.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class test {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
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
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100,1100,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		FlowLayout infoOwnerLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
		JPanel infoOwnerPanel = new JPanel();
		infoOwnerPanel.setLayout(infoOwnerLayout);
		infoOwnerPanel.setPreferredSize(new Dimension(1100,600));
		infoOwnerPanel.setBackground(Color.WHITE);
		infoOwnerPanel.setOpaque(true);
		infoOwnerPanel.setForeground(Color.WHITE);
		
		JLabel infoLabel = new JLabel("Manage your Account");
		infoLabel.setBounds(0, 0, 0, 0);
		infoLabel.setPreferredSize(new Dimension(200, 40));
		infoLabel.setBackground(Color.WHITE);
		infoLabel.setOpaque(true);
		infoLabel.setForeground(Color.darkGray);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(0, 0, 0, 0);
		usernameLabel.setBackground(Color.WHITE);
		usernameLabel.setOpaque(true);
		usernameLabel.setForeground(Color.darkGray);
		usernameLabel.setPreferredSize(new Dimension(200, 40));
		
		JTextField usernameBox = new JTextField("Owner"); 
		usernameBox.setBounds(0, 0, 0, 0);
		usernameBox.setColumns(10);
		usernameBox.setPreferredSize(new Dimension(200, 40));
		
		JLabel passwordLabel = new JLabel("New Password");
		passwordLabel.setBounds(0, 0, 0, 0);
		passwordLabel.setBackground(Color.WHITE);
		passwordLabel.setOpaque(true);
		passwordLabel.setForeground(Color.darkGray);
		passwordLabel.setPreferredSize(new Dimension(200, 40));
		
		JTextField passwordBox = new JTextField(); 
		passwordBox.setBounds(0, 171, 270, -171);
		passwordBox.setColumns(10);
		passwordBox.setPreferredSize(new Dimension(200, 40));
		
		JButton saveAccountButton = new JButton("Save");
		saveAccountButton.setBounds(537, 250, 97, 46);
		saveAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		//saveAccountButton.setBounds(x, y, 50, 30);
		saveAccountButton.setBorder(new LineBorder(Color.darkGray));
		saveAccountButton.setBackground(Color.darkGray);
		saveAccountButton.setOpaque(true);
		saveAccountButton.setForeground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		frame.getContentPane().add(infoLabel);
		frame.getContentPane().add(usernameLabel);
		frame.getContentPane().add(usernameBox);
		frame.getContentPane().add(passwordLabel);
		frame.getContentPane().add(passwordBox);
		frame.getContentPane().add(saveAccountButton);
		
	}

}

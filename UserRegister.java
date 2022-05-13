package com.project.login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.xdevapi.Statement;

import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRegister extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtNumber;
	private JPasswordField txtPassword ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserRegister frame = new UserRegister();
					frame.setVisible(true);
					frame.setTitle("User Registration");
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection ;
	ResultSet resultset ;
	PreparedStatement preparestatement ;
	
	public boolean isRegistered(String number) {
		String query = "SELECT * FROM users";
		try {
			preparestatement = connection.prepareStatement(query);
			resultset = preparestatement.executeQuery();
			while (resultset.next())
			{
				String dataNumber =resultset.getString("PHNUMBER");
				if(dataNumber.equals(number))
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
public boolean isNumberValid(String phNumber) {
	if(phNumber.matches("^\\d{10}$") && phNumber.length() == 10)
		return true;
	else
		return false;
}

// database Connection
	public void connection() {
		try {
		String url = "jdbc:mysql://localhost:3306/storedb" ;
		String userName = "root";
		String password ="root2";
		Class.forName("com.mysql.jdbc.Driver");
			
			connection =DriverManager.getConnection(url,userName,password);
			System.out.println("Database Connection Successfull");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void clear() {
		txtUserName.setText("");
		txtNumber.setText("");
		txtPassword.setText("");
	}

	/**
	 * Create the frame.
	 */
	public UserRegister() {
		connection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 544, 298);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.desktop);
		panel.setBounds(0, 0, 168, 316);
		contentPane.add(panel);
		
		JLabel lblUsername_1 = new JLabel("UserName");
		lblUsername_1.setForeground(new Color(255, 140, 0));
		lblUsername_1.setFont(new Font("Consolas", Font.BOLD, 18));
		lblUsername_1.setBackground(SystemColor.desktop);
		lblUsername_1.setBounds(185, 63, 96, 22);
		contentPane.add(lblUsername_1);
		
	    txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Consolas", Font.BOLD, 16));
		txtPassword.setBounds(291, 143, 216, 28);
		contentPane.add(txtPassword);
		
		JLabel lblUsername_1_1_1 = new JLabel("Ph Number");
		lblUsername_1_1_1.setForeground(new Color(255, 140, 0));
		lblUsername_1_1_1.setFont(new Font("Consolas", Font.BOLD, 18));
		lblUsername_1_1_1.setBackground(SystemColor.desktop);
		lblUsername_1_1_1.setBounds(185, 107, 96, 22);
		contentPane.add(lblUsername_1_1_1);
		
		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setForeground(new Color(255, 140, 0));
		lblPassword_1.setFont(new Font("Consolas", Font.BOLD, 18));
		lblPassword_1.setBackground(SystemColor.desktop);
		lblPassword_1.setBounds(185, 144, 96, 28);
		contentPane.add(lblPassword_1);
		
		txtUserName = new JTextField();
		txtUserName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					txtNumber.requestFocus();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		txtUserName.setForeground(Color.BLACK);
		txtUserName.setFont(new Font("Consolas", Font.BOLD, 16));
		txtUserName.setColumns(10);
		txtUserName.setBounds(291, 61, 216, 28);
		contentPane.add(txtUserName);
		
		txtNumber = new JTextField();
		txtNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					txtPassword.requestFocus();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				String phNumber =txtNumber.getText();
				if(phNumber.matches("^\\d{10}$") && phNumber.length() == 10) {
					txtNumber.setBackground(Color.green);
				}
				else {
					txtNumber.setBackground(Color.red);
				}
			}
		});
		txtNumber.setForeground(Color.BLACK);
		txtNumber.setFont(new Font("Consolas", Font.BOLD, 16));
		txtNumber.setColumns(10);
		txtNumber.setBounds(291, 100, 216, 28);
		contentPane.add(txtNumber);
		
		
		
		JLabel lblNewLabel = new JLabel("Create New Account");
		lblNewLabel.setBackground(SystemColor.activeCaption);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 21));
		lblNewLabel.setBounds(235, 11, 230, 28);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("CREATE");
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName =txtUserName.getText();
				String number =txtNumber.getText();
				String password =txtPassword.getText();
				if(userName==null || userName.isEmpty() || userName.trim().isEmpty())
				{
					JOptionPane.showMessageDialog(null,"Enter Your Name");
					txtUserName.requestFocus();
					return;
				}
				if(number==null || number.isEmpty() || number.trim().isEmpty()) 
					{
						JOptionPane.showMessageDialog(null,"Enter Your Phone Number");
						txtNumber.requestFocus();
						return;
					}
				if(password==null || password.isEmpty() || password.trim().isEmpty()) 		
				{
				JOptionPane.showMessageDialog(null,"Enter Your Password");
					txtPassword.requestFocus();
					return;
				}
				boolean choice = false ;
				 if(isRegistered(number)==false)
				 {
					 if(isNumberValid(number))
					 {
						String qry ="INSERT INTO users (USERNAME,PHNUMBER,PASSWORD) VALUES(?,?,?) ";
						try {
							preparestatement = connection.prepareStatement(qry);
							preparestatement.setString(1,userName);
							preparestatement.setString(2, number);
							preparestatement.setString(3, password);
							preparestatement.executeUpdate();
							JOptionPane.showMessageDialog(null, "Account Created Successfully");
							clear();
							txtPassword.setText("");
							choice = true ;
							dispose();
							Login loginPage1 =new Login();
							loginPage1.setVisible(true);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					 }
					 else {
						 JOptionPane.showMessageDialog(null,"Phone Number should be 10 digits and Numbers Only ");
						 txtNumber.setText("");
						 txtNumber.requestFocus();
						 return;
					 }
				}
				if(choice == false)
				{
					JOptionPane.showMessageDialog(null,"This Number Already Registered");
					txtNumber.setText("");
					txtNumber.requestFocus();
					return;		
				}
				try {
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBackground(SystemColor.desktop);
		btnNewButton.setFont(new Font("Consolas", Font.BOLD, 15));
		btnNewButton.setBounds(291, 190, 96, 34);
		contentPane.add(btnNewButton);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login login3 = new Login();
				login3.setVisible(true);
				
			}
		});
		btnLogin.setFont(new Font("Consolas", Font.BOLD, 15));
		btnLogin.setBackground(SystemColor.activeCaption);
		btnLogin.setBounds(418, 190, 89, 34);
		contentPane.add(btnLogin);
		
		
	}
}

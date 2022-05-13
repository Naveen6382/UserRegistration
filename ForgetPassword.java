package com.project.login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.xdevapi.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
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

public class ForgetPassword extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JPasswordField txtPassword;
	private JTextField txtNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgetPassword frame = new ForgetPassword();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection  ;
	ResultSet resultset ;
	PreparedStatement preparestatement ;
	
	public boolean isRegistered(String number) {
		String qry = "SELECT * FROM users";
		try {
			preparestatement = connection.prepareStatement(qry);
			resultset = preparestatement.executeQuery();
			while (resultset.next())
			{
				String dataNumber =resultset.getString("PHNUMBER");
				if(dataNumber.equals(number));
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
			
	}

	public void connect() {
		try {
			String url = "jdbc:mysql://localhost:3306/storedb" ;
			String userName = "root" ;
			String password ="root2";
			Class.forName("com.mysql.jdbc.Driver");
				
				connection =DriverManager.getConnection(url,userName,password);
				System.out.println("Database Connection Successfull");
			}catch (Exception e2) {
				e2.printStackTrace();
			}
	}
	
	

	/**
	 * Create the frame.
	 */
	
	public ForgetPassword() {
		connect();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 370);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textInactiveText);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.text);
		panel.setLayout(null);
		panel.setBounds(96, 11, 518, 289);
		contentPane.add(panel);
		
		JLabel lblUsername_1 = new JLabel("UserName");
		lblUsername_1.setForeground(new Color(255, 140, 0));
		lblUsername_1.setFont(new Font("Consolas", Font.BOLD, 18));
		lblUsername_1.setBackground(SystemColor.desktop);
		lblUsername_1.setBounds(23, 66, 96, 22);
		panel.add(lblUsername_1);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setForeground(new Color(255, 140, 0));
		lblNewPassword.setFont(new Font("Consolas", Font.BOLD, 18));
		lblNewPassword.setBackground(SystemColor.desktop);
		lblNewPassword.setBounds(23, 145, 134, 28);
		panel.add(lblNewPassword);
		
		txtUserName = new JTextField();
		txtUserName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					txtNumber.requestFocus();
				}
			}
		});
		txtUserName.setForeground(Color.BLACK);
		txtUserName.setFont(new Font("Consolas", Font.BOLD, 16));
		txtUserName.setColumns(10);
		txtUserName.setBounds(167, 66, 216, 28);
		panel.add(txtUserName);
		
		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Consolas", Font.BOLD, 18));
		txtPassword.setBounds(167, 145, 216, 28);
		panel.add(txtPassword);
		
		JButton btnForgetPass = new JButton("Change");
		btnForgetPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name =txtUserName.getText();
				String pass =txtPassword.getText();
				String number =txtNumber.getText();
				if(name==null || name.isEmpty() || name.trim().isEmpty())
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
				if(pass==null || pass.isEmpty() || pass.trim().isEmpty()) 		
				{
				JOptionPane.showMessageDialog(null,"Enter Your Password");
					txtPassword.requestFocus();
					return;
				}
				if (isRegistered(number)) {
					boolean choice = false ;
					try {
						String qry ="SELECT * FROM users";
						preparestatement = connection.prepareStatement(qry);
							resultset = preparestatement.executeQuery();
								while (resultset.next())
								{
									String dataNumber =resultset.getString("PHNUMBER");
									String dataName =resultset.getString("USERNAME");
									int id =resultset.getInt("ID");
									if(name.equals(dataName)&&number.equals(dataNumber))
									{
										choice = true;
										String qry2 ="UPDATE users SET PASSWORD=? WHERE ID=?";
										preparestatement =connection.prepareStatement(qry2);
										preparestatement.setString(1, pass);
										preparestatement.setInt(2, id);
										preparestatement.executeUpdate();
										JOptionPane.showMessageDialog(null, "Password Changed Successfully..");
										setVisible(false);
										Login loginPage2 = new Login();
										loginPage2.setVisible(true);
										break;
									}
									
								}
								if(choice==false) {
									JOptionPane.showMessageDialog(null, "UserName or Number Wrong");
								}
					}catch (Exception e3) {
						e3.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "This Number Not Registered");
					txtNumber.setText("");
					txtNumber.requestFocus();
					return;
				}
			}
		});
		btnForgetPass.setFont(new Font("Consolas", Font.BOLD, 18));
		btnForgetPass.setBackground(new Color(255, 140, 0));
		btnForgetPass.setBounds(166, 197, 217, 28);
		panel.add(btnForgetPass);
		
		JLabel lblUsername_1_1 = new JLabel("Ph Number");
		lblUsername_1_1.setForeground(new Color(255, 140, 0));
		lblUsername_1_1.setFont(new Font("Consolas", Font.BOLD, 18));
		lblUsername_1_1.setBackground(SystemColor.desktop);
		lblUsername_1_1.setBounds(23, 110, 96, 22);
		panel.add(lblUsername_1_1);
		
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
		txtNumber.setBounds(167, 105, 216, 28);
		panel.add(txtNumber);
		
		JLabel lblForgetPassword = new JLabel("FORGET PASSWORD");
		lblForgetPassword.setForeground(SystemColor.desktop);
		lblForgetPassword.setFont(new Font("Consolas", Font.BOLD, 21));
		lblForgetPassword.setBounds(151, 11, 232, 28);
		panel.add(lblForgetPassword);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Login lo2 = new Login();
				lo2.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Consolas", Font.BOLD, 18));
		btnNewButton.setBackground(new Color(255, 140, 0));
		btnNewButton.setBounds(23, 199, 112, 26);
		panel.add(btnNewButton);

	}
}

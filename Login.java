package com.project.login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

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
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection ;
	ResultSet resultset ;
	PreparedStatement preparestatement ;
	
	// database Connection

	public void connect() {
		String url = "jdbc:mysql://localhost:3306/storedb" ;
		String userName = "root" ;
		String password ="root2";
		try {
		Class.forName("com.mysql.jdbc.Driver");
		connection =DriverManager.getConnection(url,userName,password);
		System.out.println("Database Connection Successfull");
		}catch (Exception e) {
		e.printStackTrace();
			}
		}

	public boolean isRegistered(String number) {
		String qry = "SELECT * FROM users";
		try {
			preparestatement =  connection.prepareStatement(qry);
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
	
	public void clear() {
		txtUserName.setText("");
		txtNumber.setText("");
		txtPassword.setText("");
	}
	/**
	 * Create the frame.
	 */
	public Login() {
		connect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 328);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 204, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(163, 0, 368, 289);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername_1 = new JLabel("UserName");
		lblUsername_1.setBounds(23, 66, 96, 22);
		lblUsername_1.setForeground(new Color(255, 140, 0));
		lblUsername_1.setFont(new Font("Consolas", Font.BOLD, 18));
		lblUsername_1.setBackground(SystemColor.desktop);
		panel.add(lblUsername_1);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(255, 140, 0));
		lblPassword.setFont(new Font("Consolas", Font.BOLD, 18));
		lblPassword.setBackground(SystemColor.desktop);
		lblPassword.setBounds(23, 143, 96, 28);
		panel.add(lblPassword);
		
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
		txtUserName.setForeground(SystemColor.menuText);
		txtUserName.setFont(new Font("Consolas", Font.BOLD, 16));
		txtUserName.setColumns(10);
		txtUserName.setBounds(129, 64, 216, 28);
		panel.add(txtUserName);
		
		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Consolas", Font.BOLD, 18));
		txtPassword.setBounds(129, 143, 216, 28);
		panel.add(txtPassword);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName =txtUserName.getText();
				String password =txtPassword.getText();
				String number =txtNumber.getText();
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
				String query ="SELECT * FROM users";
				try {
					preparestatement = connection.prepareStatement(query);
						resultset = preparestatement.executeQuery();
						if(isRegistered(number)) 
						{
							while (resultset.next())
							{
								String dataNumber =resultset.getString("PHNUMBER");
								String dataName =resultset.getString("USERNAME");
								String dataPassword =resultset.getString("PASSWORD");
								int id =resultset.getInt("ID");
							
								if(userName.equals(dataName)&&number.equals(dataNumber)&&password.equals(dataPassword))
								{
									JOptionPane.showMessageDialog(null, "Login Successfull !!!");
									choice = true ;
									dispose();
									MainPage mainPage = new MainPage(dataName,id);
									mainPage.setVisible(true);
								}
							}
							if(choice==false) {
								JOptionPane.showMessageDialog(null,"Incorrect userName or password or number");
							}
						}
						else {
							JOptionPane.showMessageDialog(null,"This Number Not Registered");
							txtNumber.setText("");
							txtNumber.requestFocus();
							return ;
						}
					}
					 catch (SQLException e1) {
						e1.printStackTrace();
						}
				
			}
		});
		btnNewButton.setFont(new Font("Consolas", Font.BOLD, 18));
		btnNewButton.setBackground(new Color(255, 140, 0));
		btnNewButton.setBounds(23, 195, 96, 28);
		panel.add(btnNewButton);
		
		JButton btnForgetPass = new JButton("Forget Password");
		btnForgetPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			ForgetPassword fp = new ForgetPassword();
			fp.setVisible(true);
			}
		});
		btnForgetPass.setFont(new Font("Consolas", Font.BOLD, 18));
		btnForgetPass.setBackground(new Color(255, 140, 0));
		btnForgetPass.setBounds(128, 195, 217, 28);
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
		txtNumber.setBounds(129, 103, 216, 28);
		panel.add(txtNumber);
		
		JLabel lblNewLabel = new JLabel("LOGIN YOUR ACCOUNT");
		lblNewLabel.setBounds(61, 11, 232, 28);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(SystemColor.desktop);
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 21));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
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
				boolean choice = false ;
				if(isRegistered(number)) {
					try {
						String qry2 ="SELECT * FROM users";
						preparestatement = connection.prepareStatement(qry2);
						resultset= preparestatement.executeQuery();
					while (resultset.next())
					{
						
						String dataNumber = resultset.getString("PHNUMBER");
						String dataName =resultset.getString("USERNAME");
						String dataPassword =resultset.getString("PASSWORD");
						int id =resultset.getInt("ID");
						if(name.equals(dataName)&&number.equals(dataNumber)&&pass.equals(dataPassword))
						{
							choice = true ;
							int opt =JOptionPane.showConfirmDialog(null, "Are You Sure To Delete Your Account","DELETE",JOptionPane.YES_NO_CANCEL_OPTION);
							if(opt == 0) 
							{
								 String qry ="DELETE FROM users WHERE ID=?";
							     preparestatement = connection.prepareStatement(qry);
							     preparestatement.setInt(1,id);
							     preparestatement.executeUpdate();
								 JOptionPane.showMessageDialog(null, "Account Deleted Successfully");
                                 clear();
                                 txtUserName.requestFocus();
                                 return;
							}
						}
					}
						} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(choice==false) {
						JOptionPane.showMessageDialog(null,"Incorrect userName or password or number");
					}
				}
				else {
					JOptionPane.showMessageDialog(null ,"This Number Not Registered");
					txtNumber.setText("");
					txtNumber.requestFocus();
					return;
				}
			}
		});
		btnDelete.setFont(new Font("Consolas", Font.BOLD, 18));
		btnDelete.setBackground(SystemColor.desktop);
		btnDelete.setBounds(23, 234, 96, 28);
		panel.add(btnDelete);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				UserRegister userregister =new UserRegister();
				userregister.setVisible(true);
			}
		});
		btnCreateAccount.setFont(new Font("Consolas", Font.BOLD, 18));
		btnCreateAccount.setBackground(SystemColor.desktop);
		btnCreateAccount.setBounds(128, 234, 217, 28);
		panel.add(btnCreateAccount);
		
	
	}
}

package com.project.login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPage extends JFrame {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainPage(String Name,int Id) {
		String msg ="Welcome "+Name+"...";
		JOptionPane.showMessageDialog(null,msg);
		
		getContentPane().setForeground(SystemColor.menu);
		getContentPane().setFont(new Font("Consolas", Font.BOLD, 21));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JTextArea showTxt = new JTextArea();
		showTxt.setFont(new Font("Consolas", Font.PLAIN, 28));
		showTxt.setForeground(SystemColor.text);
		showTxt.setBackground(SystemColor.inactiveCaptionText);
		showTxt.setBounds(10, 85, 627, 42);
		showTxt.setText("Welcome "+Name+"....Your Id is : "+Id);
		getContentPane().add(showTxt);
		
		JButton Logout = new JButton("LOGOUT");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opt =JOptionPane.showConfirmDialog(null, "Are You Sure To Logout","Logout",JOptionPane.YES_NO_CANCEL_OPTION);
				if(opt == 0) 
				{
				JOptionPane.showMessageDialog(null,"Logout successfully");
				setVisible(false);
				Login lo =new Login();
				lo.setVisible(true);
				}
			}
		});
		Logout.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		Logout.setFont(new Font("Consolas", Font.BOLD, 20));
		Logout.setBackground(SystemColor.desktop);
		Logout.setBounds(392, 187, 128, 42);
		getContentPane().add(Logout);
		setBackground(SystemColor.inactiveCaption);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 369);
	}
}

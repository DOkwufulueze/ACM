/*
* Automated Counselling Machine
* Author: Daniel Okwufulueze
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Verify extends JPanel{
	private JPanel login = new JPanel();
	private JLabel user = new JLabel("Username"), pswd = new JLabel("Password");
	private JTextField usern = new JTextField();
	private JPasswordField pswdn = new JPasswordField();
	private JTextArea tell = new JTextArea("Enter your Username and Password");
	private static JButton ok = new JButton("Ok"), cancl = new JButton("Cancel"), logout = new JButton("Log Out"); 
	private Font f0 = new Font(("Serif"), Font.BOLD, 15);
	private Act act=new Act();
	private SQLConnector sqlConnector = new SQLConnector();
	private String query, update;
	private JButton btn = ACM.adminLogin;
	private String uname = "";
	private String pwd = "";
	
	public Verify(){
		setBackground(Color.WHITE);
		setLayout(null);
		setVisible(true);
		
		login.setLayout(null);
		login.setBounds(00,00,300,250);
		tell.setBounds(30, 30, 300, 40);
		tell.setEditable(false);
		tell.setForeground(Color.MAGENTA);
		tell.setFont(f0);

		user.setBounds(30, 80, 100, 20);
		usern.setBounds(140, 80, 120, 20);

		pswd.setBounds(30, 130, 100, 20);
		pswdn.setBounds(140, 130, 120, 20);

		ok.setBounds(30, 180, 80, 20);
		ok.addActionListener(act);
		cancl.addActionListener(act);
		cancl.setBounds(120, 180, 80, 20);
		
		logout.addActionListener(act);
		logout.setBounds(120, 180, 80, 20);
		if(ACM.isLoggedIn()){
			logout.setVisible(true);
			login.setVisible(false);
		}
		else{
			logout.setVisible(false);
			login.setVisible(true);
		}
		
		login.add(tell); login.add(user); login.add(usern); login.add(pswd); login.add(pswdn); login.add(ok); login.add(cancl);
		add(login);add(logout);
	}
	
	public class Act implements ActionListener{
		public void actionPerformed(ActionEvent evt){
			Object obj = evt.getSource();
			if(obj==ok){
				uname=usern.getText();
				pwd=pswdn.getText();
				Connection con = sqlConnector.getConnection();
				if(con==null){
					JOptionPane.showMessageDialog(null,":::Unable to Connect to database for now.");
				}
				else{
					query="SELECT * FROM admin WHERE username = '"+uname+"' AND password = md5('"+pwd+"')";
					update="UPDATE logged SET username='"+uname+"', logged='1' WHERE username = '' AND logged = '0'";
					try{
						ResultSet res = sqlConnector.executeQuery(query);
						if(res.next()){
							sqlConnector.executeUpdate(update);
							JOptionPane.showMessageDialog(null,"Welcome. You're logged in as "+uname);
							login.setVisible(false);
							logout.setVisible(true);
							ACM.showAdminArea();
						}
						else{
							JOptionPane.showMessageDialog(null,"Invalid Username or Password");
						}
					}
					catch(Exception exc){
						
					}
				}
			}
			
			if(obj==cancl){
				setVisible(false);
				ACM.showAdminLoginButton();
			}
			
			if(obj==logout){
				Connection con = sqlConnector.getConnection();
				if(con==null){
					JOptionPane.showMessageDialog(null,":::Unable to Connect to database for now.");
				}
				else{
					query="SELECT * FROM logged ";
					try{
						ResultSet res=sqlConnector.executeQuery(query);
						if(res.next()){
							String un = res.getString("username");
							update="UPDATE logged SET username='', logged='0' WHERE username = '"+un+"' AND logged = '1'";
							sqlConnector.executeUpdate(update);
							JOptionPane.showMessageDialog(null,un+" logged out.");
							login.setVisible(true);
							setVisible(true);
							logout.setVisible(false);
							ACM.hideAdminArea();
						}
					}
					catch(Exception exc){
						
					}
				}
			}
		}
	}
}

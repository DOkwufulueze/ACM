/*
* Automated Counselling Machine
* Author: Daniel Okwufulueze
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ACM extends JFrame{
	static JButton adminLogin = new JButton("Admin");
	static JButton useCounsellor = new JButton("Use Counsellor");
	static JButton n = new JButton("Close");
	private ButtonGroup g1 = new ButtonGroup();
	static JPanel p = new JPanel();
	private JPanel pln = new JPanel();
	private static JPanel right = new JPanel();
	private static AdminArea adminArea = new AdminArea();
	private static Verify vrf = new Verify();
	private static CounsellorPanel counsellorPanel = new CounsellorPanel();
	static JScrollPane pa = new JScrollPane(p);
	private Font font1 = new Font("Serif", Font.BOLD, 30);
	private BorderLayout bord = new BorderLayout();
	private Act act=new Act();
	private static int x, y;
	private static SQLConnector sqlConnector = new SQLConnector();
	private static String query, update;
	
	
	// Constructor for the ACM Class
	public ACM(){
		setSize(920, 650);
		setLayout(bord);
		p.setLayout(null);
		setLocation(200,100); 
		setResizable(false);
		pa.setBounds(2, 2, 918, 648);
		pa.setVisible(true);
		add(pa, bord.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminLogin.setBounds(5, 20, 130, 20);
		useCounsellor.setBounds(5, 50, 130, 20);
		n.setBounds(5, 80, 130, 20);
		vrf.setBounds(05,170,300,300);
		vrf.setVisible(true);
		counsellorPanel.setBounds(02,00,610,620);
		counsellorPanel.setVisible(false);
		adminArea.setBounds(02,00,610,620);
		adminArea.setVisible(false);
		right.setBounds(310,03,610,620);
		right.setBackground(Color.BLUE);
		right.setLayout(null);
		right.setVisible(true);
		adminLogin.addActionListener(act);
		useCounsellor.addActionListener(act);
		n.addActionListener(act);
		p.setBackground(Color.WHITE);
		
		if(isLoggedIn()){
			adminArea.setVisible(true);
			counsellorPanel.setVisible(false);
			vrf.setVisible(true);
		}
		else{
			adminArea.setVisible(false);
		}
		right.add(adminArea);
		right.add(counsellorPanel);
		p.add(right);
		p.add(adminLogin);
		p.add(useCounsellor);
		p.add(n);
		p.add(vrf);
		p.setVisible(true);
		setVisible(true);
	}

	public static boolean isLoggedIn(){
		Connection con = sqlConnector.getConnection();
		if(con==null){
			JOptionPane.showMessageDialog(null,":::Unable to Connect to database for now.");
		}
		else{
			query="SELECT * FROM logged WHERE username !='' AND logged='1' ";
			try{
				ResultSet res = sqlConnector.executeQuery(query);
				if(res.next()){
					return true;
				}
			}
			catch(Exception exc){
				
			}
		}
		return false;
	}
	
	public class Act implements ActionListener{
		public void actionPerformed(ActionEvent evt){
			Object obj=evt.getSource();
			if(obj==adminLogin){
				if(isLoggedIn()){
					adminArea.setVisible(false);
					counsellorPanel.setVisible(false);
					adminArea = new AdminArea();
					adminArea.refreshQueryTable();
					adminArea.setBounds(02,00,610,620);
					adminArea.setVisible(true);
					right.add(adminArea);
				}
				else{
					adminArea.setVisible(false);
				}
			}
			
			if(obj==useCounsellor){
				adminArea.setVisible(false);
				counsellorPanel.setVisible(false);
				counsellorPanel = new CounsellorPanel();
				counsellorPanel.setBounds(02,00,610,620);
				counsellorPanel.setVisible(true);
				right.add(counsellorPanel);
			}
			
			if(obj==n){
				JOptionPane.showMessageDialog(null, "Thanks Bye.");
				System.exit(0);
			}
		}
	}
	
	public static void showAdminLoginButton(){
		adminLogin.setVisible(true);
	}
	
	public static void hideAdminLoginButton(){
		adminLogin.setVisible(false);
	}
	
	public static void showCounsellorPanel(){
		adminArea.setVisible(false);
		counsellorPanel.setVisible(true);
	}
	
	public static void hideCounsellorPanel(){
		counsellorPanel.setVisible(false);
	}
	
	public static void showAdminArea(){
		adminArea.setVisible(true);
		counsellorPanel.setVisible(false);
	}
	
	public static void hideAdminArea(){
		adminArea.setVisible(false);
	}
	//Application Entry Point
	public static void main(String[] args){
		new ACM();
	}

}// ACM close
   
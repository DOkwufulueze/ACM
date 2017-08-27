/*
* Automated Counselling Machine
* Author: Daniel Okwufulueze
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;


public class CounsellorPanel extends JPanel{
	JButton n = new JButton("Close"), getCounsel = new JButton("Get Counsel");
	private JPanel pn = new JPanel();
	private JScrollPane sp ;
	private Act act=new Act();
	private int x, y;
	private static String query, query1, query2, dept, fac;
	private static Object tblData[][]={{"NO QUERY YET","","",false}}, cols[]={"Query","Department","Faculty","Tick if 'Yes'"};
	private static JTable tbl;
	private static JScrollPane scrollpane;
	private static SQLConnector sqlConnector=new SQLConnector();
	private static CreateCounsellorTable cct ;
	private JTextArea counsel=new JTextArea();
	private Font font1 = new Font("Serif", Font.BOLD, 15);
    
	// Constructor for the CounsellorPanel Class
	public CounsellorPanel(){
		setLayout(null);
		setVisible(true);
		setBackground(Color.GREEN);
		counsel.setBounds(05,450,500,100);
		counsel.setForeground(Color.BLUE);
		counsel.setFont(font1);
		counsel.setWrapStyleWord(true);
		counsel.setLineWrap(true);
		counsel.setEditable(false);
		add(counsel);
		add(counsel);
		getCounsel.setBounds(5, 580, 130, 20);
		getCounsel.addActionListener(act);
		add(getCounsel);
		
		n.setBounds(150, 580, 130, 20);
		n.addActionListener(act);
		add(n);
		cct = new CreateCounsellorTable();
		cct.setBounds(05,10,600,560);
		add(cct);
	}
	
	public class Act implements ActionListener{
		public void actionPerformed(ActionEvent evt){
			Object obj = evt.getSource();
			if(obj==n){
				setVisible(false);
			}
			
			if(obj==getCounsel){
				counsel.setText(cct.getCounsel());
			}
		}
	}
}

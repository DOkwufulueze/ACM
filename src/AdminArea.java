/*
* Automated Counselling Machine
* Author: Daniel Okwufulueze
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.*;

public class AdminArea extends JPanel{
	private JButton sbm = new JButton("Submit"), refr = new JButton("Refresh Table");
	private JLabel queryLabel = new JLabel("Query"), deptLabel = new JLabel("Department"), facLabel = new JLabel("Faculty");
	private JTextField queryTextField = new JTextField();
	private JTextArea tell = new JTextArea("Enter Query and Department Combination");
	private Font font1 = new Font("Serif", Font.BOLD, 20);
    private Act act=new Act();
	private int x, y;
	private static SQLConnector sqlConnector=new SQLConnector();
	private static String query, query1, query2, dept, fac, tblData[][]={{"NO QUERY YET","NO DEPARTMENT YET","NO FACULTY YET"}}, cols[]={"Query","Department","Faculty"};
	private static DefaultTableModel model;
	private static JTable tbl;
	private static JScrollPane scrollpane;
	private Rectangle rec=new Rectangle(5,150,130,20);
	private static ArrayList<String> tableData = new ArrayList() ;
	
	private static String depts[];
	private static String facs[];
	private static JComboBox selDepts;
	private static JComboBox selFacs;
	
	// Constructor for the AdminArea Class
	public AdminArea(){
		setLayout(null);
		setVisible(true);
		setBackground(Color.GREEN);
		showQueryTable();
		queryLabel.setBounds(rec);
		rec.translate(0,30);
		facLabel.setBounds(rec);
		rec.translate(0,30);
		deptLabel.setBounds(rec);
		rec.translate(140,-60);
		rec.setSize(400,20);
		queryTextField.setBounds(rec);
		fillComboBox();
		tell.setBounds(25, 25, 400, 30);
		tell.setForeground(Color.BLUE);
		tell.setFont(font1);
		tell.setWrapStyleWord(true);
		tell.setLineWrap(true);
		tell.setEditable(false);
		sbm.setBounds(rec);
		sbm.addActionListener(act);
		rec.translate(0,300);
		refr.setBounds(rec);
		refr.addActionListener(act);
		add(queryLabel);add(queryTextField);add(deptLabel);add(facLabel);add(tell);add(sbm);add(refr);
	}
	
	public class Act implements ActionListener{
		public void actionPerformed(ActionEvent evt){
			Object obj = evt.getSource();
			if(obj==sbm){
				query=queryTextField.getText();
				dept=selDepts.getSelectedItem().toString();
				fac=selFacs.getSelectedItem().toString();
				Connection con = sqlConnector.getConnection();
				if(con==null){
					JOptionPane.showMessageDialog(null,":::Unable to Connect to database for now.");
				}
				else{
					query="INSERT INTO queries(query,faculty,department) VALUES('"+query+"','"+fac+"','"+dept+"')";
					try{
						sqlConnector.executeUpdate(query);
						refreshQueryTable();
					}
					catch(Exception exc){
						System.out.println("ACT.actionPerformed: "+exc);
					}
				}
			}
			
			if(obj==refr){
				refreshQueryTable();
			}
		}
	}
	
	public void refreshQueryTable(){
		Connection con = sqlConnector.getConnection();
		String quer, dep, fc, rslt;
		int i=0;
		if(con==null){
			JOptionPane.showMessageDialog(null,":::Unable to Connect to database for now.");
		}
		else{
			ArrayList<String> tableData = new ArrayList<String>();
			query="SELECT * FROM queries";
			try{
				ResultSet res = sqlConnector.executeQuery(query);
				while(res.next()){ 
						quer=res.getString("query");
						dep=res.getString("department");
						fc=res.getString("faculty");
						tableData.add(quer);tableData.add(dep);tableData.add(fc);
				}
				int arraySize=tableData.size(), rows=arraySize/3;
				if(arraySize!=0){
					tblData=new String[rows][3];
					int l=0;
					for ( int j = 0; j < rows; j++ ){
						for(int k=0;k<3;k++){
							tblData[j][k]=tableData.get(l);
							l++;
						}
					}
				}
				model = new DefaultTableModel(tblData, cols);
				tbl=new JTable(model){
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
			}
			catch(Exception exc){
				System.out.println("refreshQueryTable: "+exc);
			}
		}
		scrollpane.setVisible(false);
		remove(scrollpane);
		scrollpane = new JScrollPane(tbl,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.setBounds(05,300,580,200);
		add(scrollpane);
		scrollpane.setVisible(true);
	}
	
	public void showQueryTable(){
		Connection con = sqlConnector.getConnection();
		String quer, dep, fc, rslt;
		int i=0;
		if(con==null){
			JOptionPane.showMessageDialog(null,":::Unable to Connect to database for now.");
		}
		else{
			query="SELECT * FROM queries";
			try{
				ResultSet res = sqlConnector.executeQuery(query);
				while(res.next()){ 
						quer=res.getString("query");
						dep=res.getString("department");
						fc=res.getString("faculty");
						tableData.add(quer);tableData.add(dep);tableData.add(fc);
				}
				int arraySize=tableData.size(), rows=arraySize/3;
				if(arraySize!=0){
					tblData=new String[rows][3];
					int l=0;
					for ( int j = 0; j < rows; j++ ){
						for(int k=0;k<3;k++){
							tblData[j][k]=tableData.get(l);
							l++;
						}
					}
				}
				tbl=new JTable(tblData,cols);
			}
			catch(Exception exc){
				System.out.println("refreshQueryTable: "+exc);
			}
		}
		scrollpane = new JScrollPane(tbl,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.setBounds(05,300,580,200);
		add(scrollpane);
		
	}
	
	public String[] fillComboBox(){
		Connection con = sqlConnector.getConnection();
		ArrayList<String> ansDept = new ArrayList<String>();
		ArrayList<String> ansFac = new ArrayList<String>();
		
		int i=0, arraySize=0;
		if(con==null){
			JOptionPane.showMessageDialog(null,":::Unable to Connect to database for now.");
		}
		else{
			query1="SELECT DISTINCT faculty FROM departments ORDER BY faculty ASC";
			query2="SELECT DISTINCT department FROM departments  ORDER BY department ASC";
			try{
				ResultSet res = sqlConnector.executeQuery(query1);
				while(res.next()){
					ansFac.add(res.getString("faculty"));
					i++;
				}
				arraySize=ansFac.size();
				facs=new String[arraySize];
				for(int j=0;j<arraySize;j++){
					facs[j]=ansFac.get(j);
				}
				rec.translate(0,30);
				rec.setSize(150,20);
				selFacs=new JComboBox(facs);
				selFacs.setBounds(rec);
				rec.translate(0,30);
				add(selFacs);
		
				
				res = sqlConnector.executeQuery(query2);
				while(res.next()){
					ansDept.add(res.getString("department"));
					i++;
				}
				arraySize=ansDept.size();
				depts=new String[arraySize];
				for(int j=0;j<arraySize;j++){
					depts[j]=ansDept.get(j);
				}
				selDepts=new JComboBox(depts);
				selDepts.setBounds(rec);
				rec.translate(-140,30);
				add(selDepts);
			}
			catch(Exception exc){
				
			}
		}
		return null;
	}
}

/*
* Automated Counselling Machine
* Author: Daniel Okwufulueze
*/

import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.awt.*;

public class CreateCounsellorTable extends JPanel {
	private static JTable table;
	private static DefaultTableModel model;
	private static SQLConnector sqlConnector=new SQLConnector();
	private static String query;
	ArrayList<Object> depts, facs ;
	private static int arraySize, rows;
	
    public CreateCounsellorTable() {
		setBackground(Color.WHITE);
    Object tblData[][]={{"NO QUERY YET",false}}, cols[]={"Query","Check if 'Yes' else Uncheck"}, deptsData[], facsData[];
		
		Connection con = sqlConnector.getConnection();
		String quer, dep, fc, rslt;
		int i=0;
		if(con==null){
			JOptionPane.showMessageDialog(null,":::Unable to Connect to database for now.");
		}
		else{
			ArrayList<Object> tableData = new ArrayList<Object>();
			depts = new ArrayList<Object>();
			facs = new ArrayList<Object>();
			query="SELECT * FROM queries";
			try{
				ResultSet res = sqlConnector.executeQuery(query);
				while(res.next()){ 
						quer=res.getString("query");
						dep=res.getString("department");
						fc=res.getString("faculty");
						tableData.add(quer);
						tableData.add(false);
						depts.add(dep);
						facs.add(fc);
				}
				arraySize=tableData.size();
				rows=arraySize/2;
				if(arraySize!=0){
					tblData=new Object[rows][2];
					deptsData=new Object[rows];
					facsData=new Object[rows];
					int l=0;
					for ( int j = 0; j < rows; j++ ){
						for(int k=0;k<2;k++){
							tblData[j][k]=tableData.get(l);
							l++;
						}
						deptsData[j]=depts.get(j);
						facsData[j]=facs.get(j);
					}
				}
			}
			
			catch(Exception exc){
				System.out.println("refreshQueryTable (CreateCounsellorTable.java): "+exc);
			}
		}
		model = new DefaultTableModel(tblData, cols);
        table = new JTable(model) {

            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
			
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column!=1)
					return false;
				else
					return true;
			}
        };
        JScrollPane scrollpane = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollpane);
  }
	
	public String getCounsel(){
		int favourableDept=0;
		int favourableFac=0;
		int index=0, count=0, maxDept, minDept, maxFac, minFac;
		String maxDeptStr="", minDeptStr="", maxFacStr="", minFacStr="";
		ArrayList<String> storedFacs = new ArrayList<String>();
		ArrayList<String> storedDepts = new ArrayList<String>();
		ArrayList<Integer> storedFacsNum = new ArrayList<Integer>();
		ArrayList<Integer> storedDeptsNum = new ArrayList<Integer>();
		boolean yesNo,flag=false;
		for (int m=0; m<rows; m++){
			yesNo=Boolean.parseBoolean(table.getValueAt(m,1).toString());
			if(yesNo){
				flag=true;
				String dept = depts.get(m).toString();
				String fac = facs.get(m).toString();
				if(!storedDepts.contains(dept)){
					storedDepts.add(dept);
					storedDeptsNum.add(1);
				}
				else{
					index=storedDepts.indexOf(dept);
					count=Integer.parseInt(storedDeptsNum.get(index).toString());
					count++;
					storedDeptsNum.set(index,count);
				}
				if(!storedFacs.contains(fac)){
					storedFacs.add(fac);
					storedFacsNum.add(1);
				}
				else{
					index=storedFacs.indexOf(fac);
					count=Integer.parseInt(storedFacsNum.get(index).toString());
					count++;
					storedFacsNum.set(index,count);
				}
			}
		}
		if(flag){
			maxDept=getMax(storedDeptsNum);
			maxDeptStr=storedDepts.get(storedDeptsNum.indexOf(maxDept)).toString();
			
			minDept=getMin(storedDeptsNum);
			minDeptStr=storedDepts.get(storedDeptsNum.indexOf(minDept)).toString();
			
			
			maxFac=getMax(storedFacsNum);
			maxFacStr=storedFacs.get(storedFacsNum.indexOf(maxFac)).toString();
			
			minFac=getMin(storedDeptsNum);
			if(minFac==maxFac||minDept==maxDept){
				return "Most appropriate field of study: "+maxFacStr+" ("+maxDeptStr+")";
			}
			else{
				String maxDept2="", maxFac2="";
				ArrayList<String> facDepts = new ArrayList<String>();
				Connection con = sqlConnector.getConnection();
				String quer, dpt, fac, rslt, query2;
				int i=0;
				if(con==null){
					JOptionPane.showMessageDialog(null,":::Unable to Connect to database for now.");
				}
				else{
					query="SELECT * FROM queries WHERE faculty='"+maxFacStr+"'";
					query2="SELECT * FROM queries WHERE department='"+maxDeptStr+"'";
					try{
						ResultSet res = sqlConnector.executeQuery(query);
						int tempCount=0; int maxCount=0; 
						while(res.next()){ 
							dpt=res.getString("department");
							facDepts.add(dpt);
							if(storedDepts.contains(dpt)){
								tempCount=countElement(storedDepts,dpt);
								if(tempCount>maxCount){
									maxCount=tempCount;
									maxDept2=dpt;
								}
							}
						}
						
						res = sqlConnector.executeQuery(query2);
						while(res.next()){ 
							fac=res.getString("faculty");
							maxFac2=fac;
						}
					}
					catch(Exception exc){
						
					}
				}
				if(maxFacStr.equals(maxFac2)&&maxDeptStr.equals(maxDept2)){
					return "Most appropriate field of study: "+maxFacStr+" ("+maxDeptStr+") \n\n\nLeast appropriate field of study: "+minFacStr+" ("+minDeptStr+")";
				}
				else{
					return "The following fields are equally appropriate: \n"+maxFacStr+" ("+maxDept2+") AND "+maxFac2+" ("+maxDeptStr+")\n\n\nLeast appropriate field of study: "+minFacStr+" ("+minDeptStr+")";
				}
			}
		}
		else{
			return "You didn't answer any of the questions.";
		}
	}
	
	public int getMax(ArrayList list){
		int max = Integer.MIN_VALUE;
		for(int i=0; i<list.size(); i++){
			if(Integer.parseInt(list.get(i).toString()) > max){
				max = Integer.parseInt(list.get(i).toString());
			}
		}
		return max;
	}
	
	public int getMin(ArrayList list){
		int min = Integer.MAX_VALUE;
		for(int i=0; i<list.size(); i++){
			if(Integer.parseInt(list.get(i).toString()) < min){
				min = Integer.parseInt(list.get(i).toString());
			}
		}
		return min;
	}
	
	public int countElement(ArrayList<String> itemList, Object item) {
		int count = 0;
		for (Object i : itemList) {
			if (i.equals(item)) {
			  count++;
			}
		}
		return count;
	}
}

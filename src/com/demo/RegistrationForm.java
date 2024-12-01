package com.demo;

import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RegistrationForm {
	
	
	
	static DefaultTableModel tableModel;
	public static void main(String[] args) {
		JFrame frame=new JFrame("Registration Form");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,500);
		frame.setLayout(null);
		
		JLabel labelmain=new JLabel("Registration Form");
		labelmain.setBounds(300, 20, 200, 25);
		frame.add(labelmain);
		
		
		JLabel labelId=new JLabel("ID");
		labelId.setBounds(20, 60, 100, 25);
		frame.add(labelId);
		
		JTextField textfieldId=new JTextField();
		textfieldId.setBounds(120,60,150,25);
		frame.add(textfieldId);	
		
		
		JLabel labelName=new JLabel("Name");
		labelName.setBounds(20, 100, 100, 25);
		frame.add(labelName);
		
		JTextField textfieldName=new JTextField();
		textfieldName.setBounds(120,100,150,25);
		frame.add(textfieldName);
		
		
		JLabel labelGender=new JLabel("Gender");
		labelGender.setBounds(20, 140, 100, 25);
		frame.add(labelGender);
		
		JRadioButton btnMale=new JRadioButton("Male");
		btnMale.setBounds(120,140,70,25);
		frame.add(btnMale);
		JRadioButton btnFemale=new JRadioButton("Female");
		btnFemale.setBounds(200,140,70,25);
		frame.add(btnFemale);
		
		ButtonGroup btngroup=new ButtonGroup();
		btngroup.add(btnMale);
		btngroup.add(btnFemale);
		
		
		JLabel labelAddress=new JLabel("Address");
		labelAddress.setBounds(20, 180, 100, 25);
		frame.add(labelAddress);
		
		JTextField textfieldAddress=new JTextField();
		textfieldAddress.setBounds(120,180,150,25);
		frame.add(textfieldAddress);
		
		
		JLabel labelContact=new JLabel("Contact");
		labelContact.setBounds(20, 220, 100, 25);
		frame.add(labelContact);
		
		JTextField textfieldContact=new JTextField();
		textfieldContact.setBounds(120,220,150,25);
		frame.add(textfieldContact);
		
		JButton btnExit=new JButton("Exit");
		btnExit.setBounds(20, 260, 100, 25);
		frame.add(btnExit);
		JButton btnRegister=new JButton("Register");
		btnRegister.setBounds(130, 260, 100, 25);
		frame.add(btnRegister);
		JButton btnDelete=new JButton("Delete");
		btnDelete.setBounds(20, 300, 100, 25);
		frame.add(btnDelete);
		JButton btnUpdate=new JButton("Update");
		btnUpdate.setBounds(130, 300, 100, 25);
		frame.add(btnUpdate);
		JButton btnReset=new JButton("Reset");
		btnReset.setBounds(70, 340, 100, 25);
		frame.add(btnReset);
		
		tableModel=new DefaultTableModel(new String[] {"ID","Name","Gender","Address","Contact"},0);
		JTable tableRegister=new JTable(tableModel);
		JScrollPane scrollPane=new JScrollPane(tableRegister);
		scrollPane.setBounds(300, 60, 550, 300);
		frame.add(scrollPane);
		
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try(Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/newdb","root","123456");
					PreparedStatement statement=connection.prepareStatement("insert into students values(?,?,?,?,?)")){
				
					int id=Integer.parseInt(textfieldId.getText());
					String name=textfieldName.getText();
					String gender=btnMale.isSelected()?"Male":"Female";
					String address=textfieldAddress.getText();
					String contact=textfieldContact.getText();
					
					statement.setInt(1, id);
					statement.setString(2, name);
					statement.setString(3, gender);
					statement.setString(4, address);
					statement.setString(5, contact);
					
					statement.executeUpdate();
					loadData();

					textfieldId.setText("");
					textfieldName.setText("");
					btngroup.clearSelection();
					textfieldAddress.setText("");
					textfieldContact.setText("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
						
				
			}
			
		});
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try(Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/newdb","root","123456");
						PreparedStatement statement=connection.prepareStatement("delete from students where id=?")){
					
						int id=Integer.parseInt(textfieldId.getText());
						
						statement.setInt(1, id);
						
						statement.executeUpdate();
						loadData();
						textfieldId.setText("");
						textfieldName.setText("");
						btngroup.clearSelection();
						textfieldAddress.setText("");
						textfieldContact.setText("");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
			
		});
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try(Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/newdb","root","123456");
						PreparedStatement statement=connection.prepareStatement("update students set name=?,gender=?,address=?,contact=? where id=?")){
					
						int id=Integer.parseInt(textfieldId.getText());
						String name=textfieldName.getText();
						String gender=btnMale.isSelected()?"Male":"Female";
						String address=textfieldAddress.getText();
						String contact=textfieldContact.getText();
						
						statement.setString(1, name);
						statement.setString(2, gender);
						statement.setString(3, address);
						statement.setString(4, contact);
						statement.setInt(5, id);
						
						statement.executeUpdate();
						loadData();
						textfieldId.setText("");
						textfieldName.setText("");
						btngroup.clearSelection();
						textfieldAddress.setText("");
						textfieldContact.setText("");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			
		});
		btnReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textfieldId.setText("");
				textfieldName.setText("");
				btngroup.clearSelection();
				textfieldAddress.setText("");
				textfieldContact.setText("");		
			}		
		});
		
		loadData();
		frame.setVisible(true);
	}
	
	public static void loadData() {
		try (
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/newdb","root","123456");
			PreparedStatement statement=connection.prepareStatement("select * from students");
			ResultSet resultSet=statement.executeQuery()){
			tableModel.setRowCount(0);
			
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				String gender=resultSet.getString("gender");
				String address=resultSet.getString("address");
				String contact=resultSet.getString("contact");
				
				Object[] row= {id,name,gender,address,contact};
				tableModel.addRow(row);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	
	
}












package app;

import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;

public class ToDoList {
		JFrame frame;
		JLabel label;
		JPanel MainPanel;
		CardLayout cardlayout;
	    PendingPanel p;
		CompletedPanel c;
		DatabaseConnection d;
		JPanel NavPanel;
		JTabbedPane tabs;
		
		public ToDoList() {
		
			frame=new JFrame("MY TO-DO LIST");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400,500);
			frame.setLocationRelativeTo(null);
		
			c = new CompletedPanel();
			p = new PendingPanel(c);
			
			tabs = new JTabbedPane();
			tabs.addTab("Pending Tasks", p);
			tabs.addTab("Completed Tasks", c);
			
			frame.add(tabs,BorderLayout.CENTER);
			frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		    try {
		        UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		new ToDoList();
	}

}

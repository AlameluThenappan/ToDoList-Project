package app;

import javax.swing.*;
import java.awt.*;

public class CompletedPanel extends JPanel {
	JPanel taskpanel;
	JLabel noTasksLabel;
	DatabaseConnection db = new DatabaseConnection();
	
	public CompletedPanel() {
		setLayout(new BorderLayout());
		
		taskpanel=new JPanel();
		taskpanel.setLayout(new BoxLayout(taskpanel,BoxLayout.Y_AXIS));
		taskpanel.setBackground(null);
		
		noTasksLabel = new JLabel("No Completed Tasks",SwingConstants.CENTER);
		noTasksLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
		taskpanel.add(noTasksLabel);
		
		JScrollPane scrollpane = new JScrollPane(taskpanel);
		scrollpane.setBackground(null);
		add(scrollpane, BorderLayout.CENTER);	
		
		for (Task t : db.getCompletedTasks()) {
			taskpanel.remove(noTasksLabel);
	        addTask(t);
	    }
	}
	
	public void addTask(Task task) {
		if(noTasksLabel.getParent() !=null){ taskpanel.remove(noTasksLabel);}
		
		JPanel taskrow = new JPanel(new BorderLayout(10,0));
		taskrow.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
		taskrow.setBackground(null);
		taskrow.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createMatteBorder(0, 0, 1, 0, UIManager.getColor("Component.borderColor"))));
	
		JLabel taskLabel = new JLabel(task.getdescription());
		taskLabel.setBackground(null);
	
		JButton deleteButton = new JButton("Delete"); 
		deleteButton.setBackground(null);
		
		deleteButton.addActionListener(e ->{
			db.deleteTask(task.getId());
			taskpanel.remove(taskrow);
			if(taskpanel.getComponentCount()==0) {taskpanel.add(noTasksLabel);}
			taskpanel.revalidate();
			taskpanel.repaint();			
		});
		
		taskrow.add(taskLabel,BorderLayout.WEST);
		taskrow.add(deleteButton,BorderLayout.EAST);
		taskpanel.add(taskrow);
		taskpanel.revalidate();
		taskpanel.repaint();
	}
}

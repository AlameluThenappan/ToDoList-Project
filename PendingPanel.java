package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class PendingPanel extends JPanel {

	JPanel tasks;
	CompletedPanel completedPanel;
	JLabel noTasksLabel;
	DatabaseConnection db=new DatabaseConnection();
	
	public PendingPanel(CompletedPanel completedPanel) {
		this.completedPanel = completedPanel;
		setLayout(new BorderLayout());
	
		tasks = new JPanel();
		tasks.setLayout(new BoxLayout(tasks,BoxLayout.Y_AXIS));
		tasks.setBackground(null);
		
		noTasksLabel = new JLabel("No Pending Tasks",SwingConstants.CENTER);
		noTasksLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
		tasks.add(noTasksLabel);
		
		JScrollPane scroll = new JScrollPane(tasks);
		scroll.setBackground(null);
		add(scroll,BorderLayout.CENTER);

		JButton addButton = new JButton("+ Add Task");
		addButton.addActionListener(e -> showInputField());
		
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.setBackground(null);
		bottomPanel.add(addButton);
		add(bottomPanel,BorderLayout.SOUTH);
		
		for (Task t : db.getPendingTasks()) {
			tasks.remove(noTasksLabel);
	        addTask(t);
	    }
	}
	
	private void showInputField() {
        JTextField inputField = new JTextField();
        tasks.add(inputField);
        tasks.revalidate();
        tasks.repaint();
        inputField.requestFocus();

        inputField.addActionListener( e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                tasks.remove(inputField);
                addTasktodb(text);
            } else {
                tasks.remove(inputField);
            }
            tasks.revalidate();
            tasks.repaint();
        });
    }
	
	private void addTasktodb(String description ) {
		if(noTasksLabel.getParent() !=null){ tasks.remove(noTasksLabel);}
		int taskId = db.insertTask(description);
		Task task=new Task(taskId,description,"Pending");
		addTask(task);	
	}
	
	private void addTask(Task task) {
		
		JPanel row = new JPanel(new BorderLayout(10,0));
		row.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
		row.setBackground(null);
		row.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createMatteBorder(0, 0, 1, 0, UIManager.getColor("Component.borderColor"))));
		
		JCheckBox checkbox = new JCheckBox(task.getdescription());
		checkbox.setBackground(null);
		checkbox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                tasks.remove(row);
                db.markCompleted(task.getId());
                task.setstatus("Completed");
                completedPanel.addTask(task);
                if(tasks.getComponentCount()==0) {tasks.add(noTasksLabel);}
                tasks.revalidate();
                tasks.repaint();
            }
        });
	
		JButton editButton = new JButton("Edit");
		JButton deleteButton = new JButton("Delete");
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,0));
		buttonPanel.setBackground(null);
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);
		
		row.add(checkbox,BorderLayout.WEST);
		row.add(buttonPanel,BorderLayout.EAST);
		
		deleteButton.addActionListener(e ->{
			tasks.remove(row);
			db.deleteTask(task.getId());
			if(tasks.getComponentCount()==0) {tasks.add(noTasksLabel);}
			tasks.revalidate();
			tasks.repaint();
		});
		
		editButton.addActionListener(e ->{
			showEditField(row,checkbox,buttonPanel,task);
		});
		tasks.add(row);
		tasks.revalidate();
		tasks.repaint();		
	}
	
	private void showEditField(JPanel row, JCheckBox checkbox,JPanel buttonPanel,Task task) {
		row.removeAll();
	    JTextField editField = new JTextField(task.getdescription(),15);
	    row.setLayout(new BorderLayout(10,0));
	    
		row.add(editField,BorderLayout.WEST);
		row.add(buttonPanel,BorderLayout.EAST);
	    row.revalidate();
	    row.repaint();

	    editField.requestFocus();

	    editField.addActionListener(e -> {
	        String updated = editField.getText().trim();
	        if (!updated.isEmpty()) {
	        	task.setdescription(updated);
	        	db.updateTask(task.getId(), updated);
	            checkbox.setText(updated);
	        }
	        row.removeAll();
	        row.setLayout(new BorderLayout(10,0));
	        row.add(checkbox,BorderLayout.WEST);
	        row.add(buttonPanel,BorderLayout.EAST);
	        
	        row.revalidate();
	        row.repaint();
	    });
	}	
}

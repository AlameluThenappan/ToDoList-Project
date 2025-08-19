package app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/todolist?useSSL=false&serverTimezone=UTC";
    private static final String USER = "user"; // or "root"
    private static final String PASS = "Alamelu@2506";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public int insertTask(String description) {
        String sql = "INSERT INTO tasks (description, status) VALUES (?, 'PENDING')";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, description);
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); 
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return -1;
    }

    public void markCompleted(int taskId) {
        String sql = "UPDATE tasks SET status='COMPLETED' WHERE task_id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(int taskId, String newDescription) {
        String sql = "UPDATE tasks SET description=? WHERE task_id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newDescription);
            ps.setInt(2, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE task_id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Task> getPendingTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT task_id,description, status FROM tasks WHERE status='PENDING'";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tasks.add(new Task(rs.getInt("task_id"), rs.getString("description"), rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> getCompletedTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT task_id, description, status FROM tasks WHERE status='COMPLETED'";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tasks.add(new Task(rs.getInt("task_id"), rs.getString("description"), rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }


}
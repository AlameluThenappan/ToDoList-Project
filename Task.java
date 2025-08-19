package app;

public class Task {
	private int id;
	private String description;
	private String status;
	
	public Task(int id, String description,String status) {
		this.id=id;
		this.description=description;
		this.status=status;
	}
	
	public int getId() {return id;}
	public String getdescription() {return description;}
	public String getstatus() {return status;}
	public void setdescription(String description) {this.description=description;}
	public void setstatus(String status) {this.status=status;}

}

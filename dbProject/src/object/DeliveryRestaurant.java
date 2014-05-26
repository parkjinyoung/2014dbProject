package object;

public class DeliveryRestaurant {
	
	private String resname;
	private String rating;
	private String group;
	private String time;
	private String menu;
	
	public DeliveryRestaurant(){}
	public DeliveryRestaurant(String resname){
		this.resname = resname;
		this.rating = "4";
	}
	
	public DeliveryRestaurant(String resname, String rating, String group,
			String time, String menu) {
		this.resname = resname;
		this.rating = rating;
		this.group = group;
		this.time = time;
		this.menu = menu;
	}
	public String getResname() {
		return resname;
	}
	public void setResname(String resname) {
		this.resname = resname;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	
	
	
}

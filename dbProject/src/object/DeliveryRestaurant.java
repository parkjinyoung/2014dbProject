package object;

public class DeliveryRestaurant {
	
	private String dno;
	private String cafe;
	private String rating;
	private String grouping;
	private String hours;
	private String menu_url;
	
	public DeliveryRestaurant(){}
	public DeliveryRestaurant(String cafe){
		this.cafe = cafe;
	}
	
	public DeliveryRestaurant(String dno, String cafe, String rating,
			String grouping, String hours, String menu_url) {
		this.dno = dno;
		this.cafe = cafe;
		this.rating = rating;
		this.grouping = grouping;
		this.hours = hours;
		this.menu_url = menu_url;
	}
	public String getDno() {
		return dno;
	}
	public void setDno(String dno) {
		this.dno = dno;
	}
	public String getCafe() {
		return cafe;
	}
	public void setCafe(String cafe) {
		this.cafe = cafe;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getGrouping() {
		return grouping;
	}
	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	
	
	
	
}

package object;

public class SnuMenu {
	private String mno;
	private String menu;
	private String rating;
	private String cafe;
	private int price;
	private String time;
	public String getCafe() {
		return cafe;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public void setCafe(String cafe) {
		this.cafe = cafe;
	}
	public SnuMenu(){
		
	}
	public SnuMenu(String menu, String cafe, String rating, int price, String time){
		this.menu=menu;
		this.cafe=cafe;
		this.rating=rating;
		this.price=price;
		this.time = time;
	}
	public SnuMenu(String cafe, String menu, String rating){
		this.cafe = cafe;
		this.menu = menu;
		this.rating = rating;
		this.price = 0;
		this.time = null;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMno() {
		return mno;
	}
	public void setMno(String mno) {
		this.mno = mno;
	}

}

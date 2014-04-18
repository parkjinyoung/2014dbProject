package object;

public class Comment {
	String userid;
	String cafe;
	String menu;
	String comment;
	String insertdate;
	
	float rating;
	int recom;
	int nonrecom;
	
	public Comment(String uid, String cafe, String menu, String comment, float rating, String insertdate){
		this.userid = uid;
		this.cafe = cafe;
		this.menu = menu;
		this.comment = comment;
		this.rating = rating;
		this.recom = 0;
		this.insertdate = insertdate;
	}
	
	public String getInsertdate() {
		return insertdate;
	}

	public void setInsertdate(String insertdate) {
		this.insertdate = insertdate;
	}

	public int getNonrecom() {
		return nonrecom;
	}

	public void setNonrecom(int nonrecom) {
		this.nonrecom = nonrecom;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCafe() {
		return cafe;
	}
	public void setCafe(String cafe) {
		this.cafe = cafe;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public int getRecom() {
		return recom;
	}
	public void setRecom(int recom) {
		this.recom = recom;
	}
	
}

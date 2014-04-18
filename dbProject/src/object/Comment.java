package object;

import java.util.Date;

public class Comment {
	String cafe;
	String menu;
	String user_id;
	String comment;
	String rating;
	int recommend;
	int nonrecommend;

	public int getNonrecommend() {
		return nonrecommend;
	}

	public void setNonrecommend(int nonrecommend) {
		this.nonrecommend = nonrecommend;
	}

	Date date;

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Comment() {

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

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
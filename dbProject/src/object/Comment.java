package object;


public class Comment {
	String mno;
	String cafe;
	String menu;
	String uno;
	String comment;
	String rating;
	String date;
	int recommend;
	int unrecommend;
	String eno;
	public Comment(){
		
	}
	
	public Comment(String cafe, String menu, String nickname, String comment,
			String rating, String date, int recommend, int unrecommend){
		this.cafe = cafe;
		this.menu = menu;
		this.uno = nickname;
		this.comment = comment;
		this.rating = rating;
		this.date = date;
		this.eno="0";
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
	public String getNickname() {
		return uno;
	}
	public void setNickname(String nickname) {
		this.uno = nickname;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public int getUnrecommend() {
		return unrecommend;
	}
	public void setUnrecommend(int nonrecommend) {
		this.unrecommend = nonrecommend;
	}

	public String getEno() {
		return eno;
	}

	public void setEno(String eno) {
		this.eno = eno;
	}

	
}
package object;

public class RecComment {
	String rec_nickname;
	String eval_nickname;
	boolean recommend;
	String menu;
	String cafe;
	
	public String getRec_nickname() {
		return rec_nickname;
	}

	public void setRec_nickname(String rec_nickname) {
		this.rec_nickname = rec_nickname;
	}

	public String getEval_nickname() {
		return eval_nickname;
	}

	public void setEval_nickname(String eval_nickname) {
		this.eval_nickname = eval_nickname;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getCafe() {
		return cafe;
	}

	public void setCafe(String cafe) {
		this.cafe = cafe;
	}

	public RecComment(){
		/*
		 * rec_id는 유저(추천하는사람) id,  
		 * eval_id는 평가를 작성한 사람 id,
		 *  recommend는 boolean값 추천이면 true 비추면 false
		 *  return 값 : SetRec은 메시지 보내줌
		 */
	}
	
	public boolean isRecommend() {
		return recommend;
	}
	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}
}

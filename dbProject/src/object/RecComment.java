package object;

public class RecComment {
	String rec_nick;
	String eval_nick;
	String recommend;
	String menu;
	String cafe;
	
	public RecComment(String rec_nickname, String eval_nickname,
			String recommend, String menu, String cafe) {
		this.rec_nick = rec_nickname;
		this.eval_nick = eval_nickname;
		this.recommend = recommend;
		this.menu = menu;
		this.cafe = cafe;
	}

	public String getRec_nick() {
		return rec_nick;
	}

	public void setRec_nick(String rec_nickname) {
		this.rec_nick = rec_nickname;
	}

	public String getEval_nick() {
		return eval_nick;
	}

	public void setEval_nick(String eval_nickname) {
		this.eval_nick = eval_nickname;
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
		 * rec_id�� ����(��õ�ϴ»��) id,  
		 * eval_id�� �򰡸� �ۼ��� ��� id,
		 *  recommend�� String�� ��õ�̸� true ���߸� false
		 *  return �� : SetRec�� �޽��� ������
		 */
	}
	
	public String isRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
}

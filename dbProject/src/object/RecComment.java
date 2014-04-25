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
		 * rec_id�� ����(��õ�ϴ»��) id,  
		 * eval_id�� �򰡸� �ۼ��� ��� id,
		 *  recommend�� boolean�� ��õ�̸� true ���߸� false
		 *  return �� : SetRec�� �޽��� ������
		 */
	}
	
	public boolean isRecommend() {
		return recommend;
	}
	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}
}

package object;

public class RecComment {
	String rec_id;
	String eval_id;
	boolean recommend;
	String menu;
	String cafe;
	
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
	
	public String getRec_id() {
		return rec_id;
	}
	public void setRec_id(String rec_id) {
		this.rec_id = rec_id;
	}
	public String getEval_id() {
		return eval_id;
	}
	public void setEval_id(String eval_id) {
		this.eval_id = eval_id;
	}
	public boolean isRecommend() {
		return recommend;
	}
	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}
}

package object;

public class SnuMenu {
	private String menu;
	private String eval;
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public SnuMenu(String menu, String eval){
		this.menu = menu;
		this.setEval(eval);
	}
	public String getEval() {
		return eval;
	}
	public void setEval(String eval) {
		this.eval = eval;
	}

}

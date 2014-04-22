package object;

public class SnuMenu {
	private String menu;
	private String eval;
	private String cafe;
	private int price;
	private String classify;
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
	public SnuMenu(String menu, String cafe, String eval, int price, String classify){
		this.menu=menu;
		this.cafe=cafe;
		this.eval=eval;
		this.price=price;
		this.classify=classify;
	}
	public SnuMenu(String cafe, String menu, String eval){
		this.cafe = cafe;
		this.menu = menu;
		this.eval = eval;
	}
	public String getEval() {
		return eval;
	}
	public void setEval(String eval) {
		this.eval = eval;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}

}

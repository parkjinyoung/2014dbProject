package object;

import java.util.ArrayList;

public class MenuRecommend {
	private String uno;
	private ArrayList<String> favorites;
	public String getUno() {
		return uno;
	}
	public void setUno(String uno) {
		this.uno = uno;
	}
	public ArrayList<String> getVisibleres() {
		return favorites;
	}
	public void setVisibleres(ArrayList<String> visibleres) {
		this.favorites = visibleres;
	}
}

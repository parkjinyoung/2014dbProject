package object;

import java.util.ArrayList;

public class MenuRecommend {
	private String uno;
	private ArrayList<SnuMenu> visibleres;
	public String getUno() {
		return uno;
	}
	public void setUno(String uno) {
		this.uno = uno;
	}
	public ArrayList<SnuMenu> getVisibleres() {
		return visibleres;
	}
	public void setVisibleres(ArrayList<SnuMenu> visibleres) {
		this.visibleres = visibleres;
	}
}

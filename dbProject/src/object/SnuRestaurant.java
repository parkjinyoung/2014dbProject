package object;

import java.util.ArrayList;

public class SnuRestaurant {
	private String name;
	private ArrayList<SnuMenu> mymenu;
	// ?ì—…?œê°„	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SnuRestaurant(String name, ArrayList<SnuMenu> mymenu){
		this.name = name;
		this.setMymenu(new ArrayList<SnuMenu>());
		this.setMymenu(mymenu);
	}
	public ArrayList<SnuMenu> getMymenu() {
		return mymenu;
	}
	public void setMymenu(ArrayList<SnuMenu> mymenu) {
		this.mymenu = mymenu;
	}
}

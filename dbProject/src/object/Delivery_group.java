package object;

import java.util.ArrayList;

public class Delivery_group {
	String group_name;
	//분류에 해당하는 배달음식점 리스트
	ArrayList<DeliveryRestaurant> myres;
	
	public Delivery_group(){}
	public Delivery_group(String group_name, ArrayList<DeliveryRestaurant> myres) {
		this.group_name = group_name;
		this.myres = myres;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public ArrayList<DeliveryRestaurant> getMyres() {
		return myres;
	}
	public void setMyres(ArrayList<DeliveryRestaurant> myres) {
		this.myres = myres;
	}
	
}

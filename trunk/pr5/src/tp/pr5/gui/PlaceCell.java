package tp.pr5.gui;

import javax.swing.JButton;

import tp.pr5.PlaceInfo;

public class PlaceCell extends JButton {
	
	/**
	 * Constructor por defecto
	 */
	public PlaceCell () {
		this.timesVisited = 0;
	}
	
	public String placeDescription(){
		return this.place.toString();
	}
	
	public void setName(String name){
		this.setText(name);
	}
	
	public void addVisited(){
		this.timesVisited++;
	}
	
	public void minusVisited(){
		this.timesVisited--;
	}
	
	public void setPlace(PlaceInfo initialPlace){
		this.place = initialPlace;
	}
	
	public boolean isVisited() {
		return this.timesVisited != 0;
	}
	
	public PlaceInfo getPlaceInfo() {
		return this.place;
	}
	
	private int timesVisited;
	private PlaceInfo place;
	
	private static final long serialVersionUID = 1L;
}

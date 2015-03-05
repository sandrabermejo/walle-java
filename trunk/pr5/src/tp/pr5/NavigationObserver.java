package tp.pr5;

public interface NavigationObserver {

	public void headingChanged(Direction newHeading);
	
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading);
	
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place);
	
	public void placeScanned(PlaceInfo placeDescription);
	
	public void placeHasChanged(PlaceInfo placeDescription);
}

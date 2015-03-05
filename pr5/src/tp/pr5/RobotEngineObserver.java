package tp.pr5;


public interface RobotEngineObserver{

	public void raiseError(String msg);
	
	public void communicationHelp(String help);
	
	public void engineOff(boolean atShip);
	
	public void communicationCompleted();
	
	public void robotUpdate(int fuel, int recycledMaterial);
	
	public void robotSays(String message);
}

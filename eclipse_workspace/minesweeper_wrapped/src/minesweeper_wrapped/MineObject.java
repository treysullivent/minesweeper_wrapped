package minesweeper_wrapped;

public class MineObject {

	private float xOff;
	private float yOff;
	private float zOff;
	
	private boolean selected = false;
	
	
	public MineObject(float x, float y, float z, boolean isChosen) {
		xOff = x;
		yOff = y;
		zOff = z;
		
		selected = isChosen;
	}
	
	// getters
	
	public float getXOffset() {
		return xOff;
	}
	
	public float getYOffset() {
		return yOff;
	}
	
	public float getZOffset() {
		return zOff;
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	// setters
	
	public void toggleSelected() 
	{
		selected = !selected;
	}
}

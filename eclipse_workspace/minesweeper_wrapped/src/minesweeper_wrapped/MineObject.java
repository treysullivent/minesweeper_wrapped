package minesweeper_wrapped;

// mineObject class for constructing the cube with
// here we can put the variables like flagged and if it is a number or bomb

public class MineObject {

	private float xOff;
	private float yOff;
	private float zOff;
	
	private boolean selected = false;
	
	private boolean hasBeenMined = false;
	
	private boolean flagged = false;
	private int numAdjacentBombs;
	
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
	
	public boolean getHasBeenMined()
	{
		return hasBeenMined;
	}
	
	public boolean getHasBeenFlagged()
	{
		return flagged;
	}
	// setters
	
	public void toggleSelected() 
	{
		selected = !selected;
	}
	
	public void toggleMined() 
	{
		hasBeenMined = true;
	}
	
	public void toggleFlagged()
	{
		flagged = true;
	}
}

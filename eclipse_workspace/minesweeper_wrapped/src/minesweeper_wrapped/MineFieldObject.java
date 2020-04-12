package minesweeper_wrapped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class MineFieldObject {

	private static ArrayList<MineObject> minefield = new ArrayList<MineObject>();
	private static float xIndexSelected;
	private static float yIndexSelected;
	private static float zIndexSelected;
	
	public MineFieldObject() {
		
	xIndexSelected = -0.67f;
	yIndexSelected = -0.67f;
	zIndexSelected = 0.67f;
	// bottom
	minefield.add(new MineObject(-0.67f, -0.67f, 0.67f, true));
	minefield.add(new MineObject(0f, -0.67f, 0.67f, false));
	minefield.add(new MineObject(0.67f, -0.67f, 0.67f, false));
	
	minefield.add(new MineObject(-0.67f, -0.67f, 0f, false));
	minefield.add(new MineObject(0f, -0.67f, 0f, false));
	minefield.add(new MineObject(0.67f, -0.67f, 0f, false));
	
	minefield.add(new MineObject(-0.67f, -0.67f, -0.67f, false));
	minefield.add(new MineObject(0f, -0.67f, -0.67f, false));
	minefield.add(new MineObject(0.67f, -0.67f, -0.67f, false));
	
	// end bottom
	
	// middle
	minefield.add(new MineObject(-0.67f, 0f, 0.67f, false));
	minefield.add(new MineObject(0f, 0f, 0.67f, false));
	minefield.add(new MineObject(0.67f, 0f, 0.67f, false));
	
	minefield.add(new MineObject(-0.67f, 0f, 0f, false));
	minefield.add(new MineObject(0f, 0f, 0f, false));
	minefield.add(new MineObject(0.67f, 0f, 0f, false));
	
	minefield.add(new MineObject(-0.67f, 0f, -0.67f, false));
	minefield.add(new MineObject(0f, 0f, -0.67f, false));
	minefield.add(new MineObject(0.67f, 0f, -0.67f, false));
	
	// end middle
	
	// top 
	minefield.add(new MineObject(-0.67f, 0.67f, 0.67f, false));
	minefield.add(new MineObject(0f, 0.67f, 0.67f, false));
	minefield.add(new MineObject(0.67f, 0.67f, 0.67f, false));
	
	minefield.add(new MineObject(-0.67f, 0.67f, 0f, false));
	minefield.add(new MineObject(0f, 0.67f, 0f, false));
	minefield.add(new MineObject(0.67f, 0.67f, 0f, false));
	
	minefield.add(new MineObject(-0.67f, 0.67f, -0.67f, false));
	minefield.add(new MineObject(0f, 0.67f, -0.67f, false));
	minefield.add(new MineObject(0.67f, 0.67f, -0.67f, false));
	// end top
	}
	
	public ArrayList<MineObject> getMineField()
	{
		return minefield;
	}
	
	public static void changeSelectedIndex(char axis, boolean add) {
		switch(axis) 
		{
		case 'y':
			if(add) 
			{
				if(yIndexSelected == 0.67f) break;
				yIndexSelected += 0.67f;
				
				//checkMembership(xIndexSelected, yIndexSelected, zIndexSelected);
				break;
			}
			
			if(yIndexSelected == -0.67f) break;
			yIndexSelected -= 0.67f;
			break;
			
		case 'x':
			if(add) 
			{
				if(xIndexSelected == 0.67f) break;
				xIndexSelected += 0.67f;
				
				//checkMembership(xIndexSelected, yIndexSelected, zIndexSelected);
				break;
			}
			
			if(xIndexSelected == -0.67f) break;
			xIndexSelected -= 0.67f;
			break;
			
		case 'z':
			if(add) 
			{
				if(zIndexSelected == 0.67f) break;
				zIndexSelected += 0.67f;
				
				//checkMembership(xIndexSelected, yIndexSelected, zIndexSelected);
				break;
			}
			
			if(zIndexSelected == -0.67f) break;
			zIndexSelected -= 0.67f;
			break;
			
		}
		
		changeSelectedMine(xIndexSelected, yIndexSelected, zIndexSelected);
		
	}
	
	//private methods for data manipulation
	
	private static void checkMembership(float x, float y, float z) {
		
	}
	
	private static void changeSelectedMine(float x, float y, float z) 
	{
		clearAllSelected();
		
		int length = minefield.size();
		
		for(int i = 0; i < length; i++) 
		{
			if(minefield.get(i).getXOffset() == xIndexSelected) 
			{
				if(minefield.get(i).getYOffset() == yIndexSelected) 
				{
					if(minefield.get(i).getZOffset() == zIndexSelected) 
					{
						minefield.get(i).toggleSelected();
					}
				}
			}
		}
	}
	
	private static void clearAllSelected()
	{
		int length = minefield.size();
		
		for(int i = 0; i < length; i++) 
		{
			if(minefield.get(i).getSelected()) minefield.get(i).toggleSelected();
		}
	}
}

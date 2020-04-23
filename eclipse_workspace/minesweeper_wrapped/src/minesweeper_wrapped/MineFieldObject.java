package minesweeper_wrapped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.*;
import java.math.*;

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
		minefield.add(new MineObject(-0.67f, -0.67f, 0.67f, true, 0));
		minefield.add(new MineObject(0f, -0.67f, 0.67f, false, 2));
		minefield.add(new MineObject(0.67f, -0.67f, 0.67f, false, 10)); // 10 == bomb

		minefield.add(new MineObject(-0.67f, -0.67f, 0f, false, 0));
		minefield.add(new MineObject(0f, -0.67f, 0f, false, 2));
		minefield.add(new MineObject(0.67f, -0.67f, 0f, false, 2));

		minefield.add(new MineObject(-0.67f, -0.67f, -0.67f, false, 0));
		minefield.add(new MineObject(0f, -0.67f, -0.67f, false, 0));
		minefield.add(new MineObject(0.67f, -0.67f, -0.67f, false, 0));

		// end bottom

		// middle
		minefield.add(new MineObject(-0.67f, 0f, 0.67f, false, 1));
		minefield.add(new MineObject(0f, 0f, 0.67f, false, 3));
		minefield.add(new MineObject(0.67f, 0f, 0.67f, false, 10));

		minefield.add(new MineObject(-0.67f, 0f, 0f, false, 2));
		minefield.add(new MineObject(0f, 0f, 0f, false, 0));
		minefield.add(new MineObject(0.67f, 0f, 0f, false, 3));

		minefield.add(new MineObject(-0.67f, 0f, -0.67f, false, 2));
		minefield.add(new MineObject(0f, 0f, -0.67f, false, 2));
		minefield.add(new MineObject(0.67f, 0f, -0.67f, false, 1));

		// end middle

		// top 
		minefield.add(new MineObject(-0.67f, 0.67f, 0.67f, false, 1));
		minefield.add(new MineObject(0f, 0.67f, 0.67f, false, 2));
		minefield.add(new MineObject(0.67f, 0.67f, 0.67f, false, 2));

		minefield.add(new MineObject(-0.67f, 0.67f, 0f, false, 2));
		minefield.add(new MineObject(0f, 0.67f, 0f, false, 10));
		minefield.add(new MineObject(0.67f, 0.67f, 0f, false, 2));

		minefield.add(new MineObject(-0.67f, 0.67f, -0.67f, false, 10));
		minefield.add(new MineObject(0f, 0.67f, -0.67f, false, 2));
		minefield.add(new MineObject(0.67f, 0.67f, -0.67f, false, 1));
		// end top
	}

	public ArrayList<MineObject> getMineField()
	{
		return minefield;
	}

	//changes which block is currently selected
	// will check if the adjacent block is accessible. If not, will try and move to the next block in the path
	// sometimes blocks become inaccessible because of their location. Has not been fixed yet!!!
	// not the most elegant solution but works for a demo
	public static void changeSelectedIndex(char axis, boolean add) {
		switch(axis) 
		{
		case 'y':
			if(add) 
			{
				if(yIndexSelected == 0.67f) break;

				if(checkMembership(xIndexSelected, yIndexSelected + 0.67f, zIndexSelected) == false) 
				{
					if(checkMembership(xIndexSelected , yIndexSelected + 1.34f, zIndexSelected) == true)
					{
						yIndexSelected += 1.34f;
						break;
					}

					changeSelectedMine(0);
					break;
				}

				yIndexSelected += 0.67f;
				break;
			}

			if(yIndexSelected == -0.67f) break;

			if(checkMembership(xIndexSelected, yIndexSelected - 0.67f, zIndexSelected) == false)
			{
				if(checkMembership(xIndexSelected , yIndexSelected - 1.34f, zIndexSelected) == true)
				{
					yIndexSelected -= 1.34f;
					break;
				}
				changeSelectedMine(0);
				break;
			}

			yIndexSelected -= 0.67f;
			break;

		case 'x':
			if(add) 
			{
				if(xIndexSelected == 0.67f) break;

				if(checkMembership(xIndexSelected + 0.67f, yIndexSelected, zIndexSelected) == false) 
				{
					if(checkMembership(xIndexSelected  + 1.34f, yIndexSelected, zIndexSelected) == true)
					{
						xIndexSelected += 1.34f;
						break;
					}
					changeSelectedMine(0);
					break;
				}

				xIndexSelected += 0.67f;
				break;
			}

			if(xIndexSelected == -0.67f) break;

			if(checkMembership(xIndexSelected - 0.67f, yIndexSelected, zIndexSelected) == false) 
			{
				if(checkMembership(xIndexSelected  - 1.34f, yIndexSelected, zIndexSelected) == true)
				{
					xIndexSelected -= 1.34f;
					break;
				}
				changeSelectedMine(0);
				break;
			}

			xIndexSelected -= 0.67f;
			break;

		case 'z':
			if(add) 
			{
				if(zIndexSelected == 0.67f) break;

				if(checkMembership(xIndexSelected , yIndexSelected, zIndexSelected + 0.67f) == false) 
				{
					if(checkMembership(xIndexSelected , yIndexSelected, zIndexSelected + 1.34f) == true)
					{
						zIndexSelected += 1.34f;
						break;
					}
					changeSelectedMine(0);
					break;
				}

				zIndexSelected += 0.67f;

				break;
			}

			if(zIndexSelected == -0.67f) break;

			if(checkMembership(xIndexSelected , yIndexSelected, zIndexSelected - 0.67f) == false)
			{
				if(checkMembership(xIndexSelected , yIndexSelected, zIndexSelected - 1.34f) == true)
				{
					zIndexSelected -= 1.34f;
					break;
				}
				changeSelectedMine(0);
				break;
			}

			zIndexSelected -= 0.67f;
			break;

		}

		changeSelectedMine(0);

	}

	// change a block to having been mined
	// will stop it from being drawn
	public static void changeMinedStatus() 
	{

		int length = minefield.size();

		for(int i = 0; i < length; i++) 
		{
			if(minefield.get(i).getXOffset() == xIndexSelected) 
			{
				if(minefield.get(i).getYOffset() == yIndexSelected) 
				{
					if(minefield.get(i).getZOffset() == zIndexSelected) 
					{
						minefield.get(i).toggleMined();
						changeSelectedMine(i);
						return;
					}
				}
			}
		}
	}

	public static void changeFlaggedStatus()
	{
		int length = minefield.size();

		for(int i = 0; i < length; i++) 
		{
			if(minefield.get(i).getXOffset() == xIndexSelected) 
			{
				if(minefield.get(i).getYOffset() == yIndexSelected) 
				{
					if(minefield.get(i).getZOffset() == zIndexSelected) 
					{
						minefield.get(i).toggleFlagged();
						changeSelectedMine(i);
						return;
					}
				}
			}
		}
	}

	//private methods for data manipulation

	// check if the block at location x, y, z has been mined or not
	private static boolean checkMembership(float x, float y, float z) {
		int length = minefield.size();

		for(int i = 0; i < length; i++) 
		{
			if(minefield.get(i).getXOffset() == x) 
			{
				if(minefield.get(i).getYOffset() == y) 
				{
					if(minefield.get(i).getZOffset() == z) 
					{
						if(minefield.get(i).getHasBeenMined() || minefield.get(i).getHasBeenFlagged())
							return false;
					}
				}
			}
		}
		// will return false if out of bounds;
		if(Math.abs(x) > 0.67f || Math.abs(y) > 0.67f || Math.abs(z) > 0.67f) return false;

		// if the mind exists in the field;
		return true;
	}

	// changes the selected mine to the index selected 
	private static void changeSelectedMine(int index) 
	{
		clearAllSelected();
		int length = minefield.size();

		// if the current index is already mined, find a new block to select
		if(checkMembership(xIndexSelected, yIndexSelected, zIndexSelected) == false) 
		{
			//if(index == length - 1) index = 0;
			for(int i = 0 ; i < length; i++)
			{
				if(minefield.get(i).getHasBeenMined() == false)
				{
					minefield.get(i).toggleSelected();
					xIndexSelected = minefield.get(i).getXOffset();
					yIndexSelected = minefield.get(i).getYOffset();
					zIndexSelected = minefield.get(i).getZOffset();
					return;
				}
			}

		}


		for(int i = 0; i < length; i++) 
		{
			if(minefield.get(i).getXOffset() == xIndexSelected) 
			{
				if(minefield.get(i).getYOffset() == yIndexSelected) 
				{
					if(minefield.get(i).getZOffset() == zIndexSelected) 
					{
						minefield.get(i).toggleSelected();
						return;
					}
				}
			}
		}
	}


	// clears selected status from all mines
	private static void clearAllSelected()
	{
		int length = minefield.size();

		for(int i = 0; i < length; i++) 
		{
			if(minefield.get(i).getSelected()) minefield.get(i).toggleSelected();
		}
	}
}

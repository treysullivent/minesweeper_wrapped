package minesweeper_wrapped;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Java Minesweeper Game
 *
 * Author: Jan Bodnar Website: http://zetcode.com Source:
 * https://github.com/janbodnar/Java-Minesweeper-Game.git
 */

public class Board extends JPanel {

	private final int NUM_IMAGES = 13;
	private final int CELL_SIZE = 50;

	private final int COVER_FOR_CELL = 10;
	private final int FLAG_FOR_CELL = 10;
	private final int EMPTY_CELL = 0;
	private final int MINE_CELL = 9;
	private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;			//Default = 19 (9-19)
	private final int FLAGGED_MINE_CELL = COVERED_MINE_CELL + FLAG_FOR_CELL;	//Default = 29 (19-29)

	private final int DRAW_MINE = 9;
	private final int DRAW_COVER = 10;
	private final int DRAW_FLAG = 11;
	private final int DRAW_WRONG_FLAG = 12;

	private final int N_MINES = 40;
	private final int N_ROWS = 16;
	private final int N_COLS = 16;
	private final int N_CELLS = N_ROWS * N_COLS;

	
	

	private final int BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
	private final int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;

	private int[] field;
	private boolean inGame;
	private int flagsLeft;
	private Image[] img; 	// Container for all images used on the board.

	private final JLabel statusbar;

	public Board(JLabel statusbar) {

		this.statusbar = statusbar;
		initBoard();
	}

	private void initBoard() {

		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

		img = new Image[NUM_IMAGES];

		// Load all images into the program.
		for (int i = 0; i < NUM_IMAGES; i++)
		{

			String path = "src/resources/" + i + ".png";
			img[i] = (new ImageIcon(path)).getImage().getScaledInstance(CELL_SIZE, CELL_SIZE,
					Image.SCALE_SMOOTH);
		}

		addMouseListener(new MinesAdapter());
		newGame();
	}

	private void newGame() {

		int cell;

		Random random = new Random();
		inGame = true;
		flagsLeft = N_MINES;

		
		field = new int[N_CELLS];

		for (int i = 0; i < N_CELLS; i++)
		{
			field[i] = COVER_FOR_CELL;
		}

		statusbar.setText(Integer.toString(flagsLeft));

		int i = 0;

		while (i < N_MINES)
		{

			int position = (int) (N_CELLS * random.nextDouble());

			if ((position < N_CELLS) && (field[position] != COVERED_MINE_CELL))
			{

				int current_col = position % N_COLS;
				field[position] = COVERED_MINE_CELL;
				i++;

				if (current_col > 0)
				{
					cell = position - 1 - N_COLS;
					if (cell >= 0)
					{
						if (field[cell] != COVERED_MINE_CELL)
						{
							field[cell] += 1;
						}
					}
					cell = position - 1;
					if (cell >= 0)
					{
						if (field[cell] != COVERED_MINE_CELL)
						{
							field[cell] += 1;
						}
					}

					cell = position + N_COLS - 1;
					if (cell < N_CELLS)
					{
						if (field[cell] != COVERED_MINE_CELL)
						{
							field[cell] += 1;
						}
					}
				}

				cell = position - N_COLS;
				if (cell >= 0)
				{
					if (field[cell] != COVERED_MINE_CELL)
					{
						field[cell] += 1;
					}
				}

				cell = position + N_COLS;
				if (cell < N_CELLS)
				{
					if (field[cell] != COVERED_MINE_CELL)
					{
						field[cell] += 1;
					}
				}

				if (current_col < (N_COLS - 1))
				{
					cell = position - N_COLS + 1;
					if (cell >= 0)
					{
						if (field[cell] != COVERED_MINE_CELL)
						{
							field[cell] += 1;
						}
					}
					cell = position + N_COLS + 1;
					if (cell < N_CELLS)
					{
						if (field[cell] != COVERED_MINE_CELL)
						{
							field[cell] += 1;
						}
					}
					cell = position + 1;
					if (cell < N_CELLS)
					{
						if (field[cell] != COVERED_MINE_CELL)
						{
							field[cell] += 1;
						}
					}
				}
			}
		}
	}

	private void uncover_empty_adjacent_cells(int j) {

		int current_col = j % N_COLS;
		int cell;

		if (current_col > 0)
		{
			cell = j - N_COLS - 1;
			if (cell >= 0)
			{
				if (field[cell] > MINE_CELL)
				{
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
					{
						uncover_empty_adjacent_cells(cell);
					}
				}
			}

			cell = j - 1;
			if (cell >= 0)
			{
				if (field[cell] > MINE_CELL)
				{
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
					{
						uncover_empty_adjacent_cells(cell);
					}
				}
			}

			cell = j + N_COLS - 1;
			if (cell < N_CELLS)
			{
				if (field[cell] > MINE_CELL)
				{
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
					{
						uncover_empty_adjacent_cells(cell);
					}
				}
			}
		}

		cell = j - N_COLS;
		if (cell >= 0)
		{
			if (field[cell] > MINE_CELL)
			{
				field[cell] -= COVER_FOR_CELL;
				if (field[cell] == EMPTY_CELL)
				{
					uncover_empty_adjacent_cells(cell);
				}
			}
		}

		cell = j + N_COLS;
		if (cell < N_CELLS)
		{
			if (field[cell] > MINE_CELL)
			{
				field[cell] -= COVER_FOR_CELL;
				if (field[cell] == EMPTY_CELL)
				{
					uncover_empty_adjacent_cells(cell);
				}
			}
		}

		if (current_col < (N_COLS - 1))
		{
			cell = j - N_COLS + 1;
			if (cell >= 0)
			{
				if (field[cell] > MINE_CELL)
				{
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
					{
						uncover_empty_adjacent_cells(cell);
					}
				}
			}

			cell = j + N_COLS + 1;
			if (cell < N_CELLS)
			{
				if (field[cell] > MINE_CELL)
				{
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
					{
						uncover_empty_adjacent_cells(cell);
					}
				}
			}

			cell = j + 1;
			if (cell < N_CELLS)
			{
				if (field[cell] > MINE_CELL)
				{
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
					{
						uncover_empty_adjacent_cells(cell);
					}
				}
			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		int cells_to_uncover = 0;

		for (int i = 0; i < N_ROWS; i++)
		{

			for (int j = 0; j < N_COLS; j++)
			{

				int cell = field[(i * N_COLS) + j];

				if (inGame && cell == MINE_CELL)	// If a mine is revealed, the game ends
				{
					inGame = false;
				}

				if (!inGame)	//Show all mines and incorrect flags
				{

					if (cell == COVERED_MINE_CELL)
					{
						cell = DRAW_MINE;
					}
					else if (cell == FLAGGED_MINE_CELL)
					{
						cell = DRAW_FLAG;
					}
					else if (cell > COVERED_MINE_CELL)
					{
						cell = DRAW_WRONG_FLAG;
					}
					else if (cell > MINE_CELL)
					{
						cell = DRAW_COVER;
					}

				}
				
				else 	
				{
					if (cell > COVERED_MINE_CELL)	// Flagged cell
					{
						cell = DRAW_FLAG;
					}
					else if (cell > MINE_CELL)		// Covered Cell
					{
						cell = DRAW_COVER;
						cells_to_uncover++;
					}
				}

				g.drawImage(img[cell], (j * CELL_SIZE), (i * CELL_SIZE), this);
			}
		}

		// Check if the game has ended
		if (cells_to_uncover == 0 && inGame)
		{
			inGame = false;
			statusbar.setText("Game won");
		}
		else if (!inGame)
		{
			statusbar.setText("Game lost");
		}
	}

	private class MinesAdapter extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {

			int x = e.getX();
			int y = e.getY();

			int cCol = x / CELL_SIZE;
			int cRow = y / CELL_SIZE;

			boolean doRepaint = false;

			if (!inGame)
			{
				newGame();
				repaint();
			}

			System.out.println(field[(cRow * N_COLS) + cCol]);
			
			if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE))
			{
				if (e.getButton() == MouseEvent.BUTTON3 /* Right Click */)	// Flag mode
				{
					if (field[(cRow * N_COLS) + cCol] > MINE_CELL)	//If the user clicked a cell that is covered or flagged
					{
						
						doRepaint = true;

						if (field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL)		// If the user clicked an unflagged cell.
						{
							if (flagsLeft > 0)
							{
								field[(cRow * N_COLS) + cCol] += FLAG_FOR_CELL;
								flagsLeft--;
								String msg = Integer.toString(flagsLeft);
								statusbar.setText(msg);
							}
							else
							{
								statusbar.setText("No flags left");
							}
						}
						
						else	// If the user clicked an flagged cell.
						{

							field[(cRow * N_COLS) + cCol] -= FLAG_FOR_CELL;
							flagsLeft++;
							String msg = Integer.toString(flagsLeft);
							statusbar.setText(msg);
						}
					}
				}

				else		// Regular "dig" mode, I assume
				{
					if (field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL)	//If the cell is flagged
					{
						return;
					}

					
					if ((field[(cRow * N_COLS) + cCol] > MINE_CELL)	&& (field[(cRow * N_COLS) + cCol] < FLAGGED_MINE_CELL))		// If the cell is covered
					{
						field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL;
						doRepaint = true;

						if (field[(cRow * N_COLS) + cCol] == MINE_CELL)
						{
							inGame = false;
						}

						if (field[(cRow * N_COLS) + cCol] == EMPTY_CELL)
						{
							uncover_empty_adjacent_cells((cRow * N_COLS) + cCol);
						}
					}
				}

				if (doRepaint)
				{
					repaint();
				}
			}
		}
	}
}

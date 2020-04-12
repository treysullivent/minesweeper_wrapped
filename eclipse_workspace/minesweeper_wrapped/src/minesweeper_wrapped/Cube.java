package minesweeper_wrapped;

import java.awt.*;
import java.awt.event.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Cube implements GLEventListener {

	public static DisplayMode dm, dm_old;
	private GLU glu = new GLU();
	private static float xquad = 0.0f;
	private static float yquad = 0.0f;
	private static float zquad = 0.0f;

	private static float xrot = 0.0f;
	private static float yrot = 0.0f;
	private static float zrot = 0.0f;


	final static GLProfile profile = GLProfile.get(GLProfile.GL2);
	final private static int CANVAS_HEIGHT = 800;
	final private static int CANVAS_WIDTH = 800;
	
	static GLCapabilities capabilities = new GLCapabilities(profile);
	final static GLCanvas glcanvas = new GLCanvas(capabilities);
	final static FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
	
	// this is the main minefield that gets manipulated
	// I think it is easier to abstract the data this way but feel free to move things around as you see fit
	private static MineFieldObject mainCube = new MineFieldObject();
	

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0f, 0f, -5.0f);

		// Rotate The Cube On X, Y & Z		
		gl.glRotatef(xquad, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(yquad, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(zquad, 0.0f, 0.0f, 1.0f);
		
		// draw the cubes in the field
		mainCube.getMineField().forEach((n) -> drawCube(gl, n.getXOffset(), n.getYOffset(), n.getZOffset(), n.getSelected(), n.getHasBeenMined()) ); 
		
		// still drawing the lines ebcause it looks good
		drawLines(gl);
		
		gl.glFlush();
		
		xquad += xrot;
		yquad += yrot;
		zquad += zrot;

	}

	

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		final GL2 gl = drawable.getGL().getGL2();
		if (height <= 0)
			height = 1;
		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, h, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	

	public static void main(String[] args) {
		
		Cube cube = new Cube();
		glcanvas.addGLEventListener(cube);
		glcanvas.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		
		final JFrame frame = new JFrame(" Multicolored cube");
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);

		KeyListener keyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				{
					xrot = 1f;
					yrot = 0.0f;
					zrot = 0.0f;

				}
				
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
				{
					xrot = -1f;
					yrot = 0.0f;
					zrot = 0.0f;
					
				}
				
				if (e.getKeyCode() == KeyEvent.VK_UP)
				{
					yrot = 1f;
				    xrot = 0.0f;
					zrot = 0.0f;
					
				}
				
				if (e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					yrot = -1f;
					xrot = 0.0f;
					zrot = 0.0f;
					
				}
				
				if (e.getKeyCode() == KeyEvent.VK_Z)
				{
					yrot = 0.0f;
					xrot = 0.0f;
					zrot = -1f;
				
				}
				
				if (e.getKeyCode() == KeyEvent.VK_X)
				{
					yrot = 0.0f;
					xrot = 0.0f;
					zrot = 1f;
					
				}
				
				// y translation
				if (e.getKeyCode() == KeyEvent.VK_W)
				{
					MineFieldObject.changeSelectedIndex('y', true);
					
				}
				
				if (e.getKeyCode() == KeyEvent.VK_S)
				{
					MineFieldObject.changeSelectedIndex('y', false);	
				}
				
				// x translation
				if (e.getKeyCode() == KeyEvent.VK_D)
				{
					MineFieldObject.changeSelectedIndex('x', true);
					
				}
				
				if (e.getKeyCode() == KeyEvent.VK_A)
				{
					MineFieldObject.changeSelectedIndex('x', false);	
				}
				
				// z translation
				if (e.getKeyCode() == KeyEvent.VK_Q)
				{
					MineFieldObject.changeSelectedIndex('z', false);
					
				}
				
				if (e.getKeyCode() == KeyEvent.VK_E)
				{
					MineFieldObject.changeSelectedIndex('z', true);	
				}
				
				// this will make a block "mined" and stop it from being drawn on the cube
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					
				{
					MineFieldObject.changeMinedStatus();	
				}

			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				yrot = 0.0f;
				xrot = 0.0f;
				zrot = 0.0f;
				//animator.start();
			}
			 
		};
		
		MouseListener mouseListener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Point p = e.getPoint();
				
				int w = CANVAS_WIDTH;
				int h = CANVAS_HEIGHT;
				

				//top third
				if(p.y >= 0 && p.y < (h/3.0))
				{
					//left third
					if(p.x >= 0 && p.x < (w/3.0))
					{
					}
					
					//center third
					else if(p.x >= (w/3.0) && p.x < (2*w/3.0))
					{
						yquad += 45f;

					}
					
					
					//right third
					else if(p.x >= (2*w/3.0) && p.x <= w)
					{

					}
				}
				
				//middle third
				else if(p.y >= (h/3.0) && p.y < (2*h/3.0))
				{
					//left third
					if(p.x >= 0 && p.x < (w/3.0))
					{
						xquad += -45f;
					}
					
					//center third
					else if(p.x >= (w/3.0) && p.x < (2*w/3.0))
					{
					}
					
					
					//right third
					else if(p.x >= (2*w/3.0) && p.x <= w)
					{
						xquad += 45f;
					}
				}
				
				
				//bottom third
				else if(p.y >= (2*h/3.0) && p.y <= h)
				{
					//left third
					if(p.x >= 0 && p.x < (w/3.0))
					{
					}
					
					//center third
					else if(p.x >= (w/3.0) && p.x < (2*w/3.0))
					{
						yquad += -45f;
					}
					
					
					//right third
					else if(p.x >= (2*w/3.0) && p.x <= w)
					{
					}				
				}
					
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};

		glcanvas.addKeyListener(keyListener);
		glcanvas.addMouseListener(mouseListener);


		animator.start();
	}
	
	public void drawLines(GL2 gl)
	{
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f(255f, 255f, 255f);
		gl.glVertex3f(-0.33f, 1.0f, 1.0f); //Front face lines
		gl.glVertex3f(-0.33f, -1.0f, 1.0f);
		gl.glVertex3f(0.33f, 1.0f, 1.0f);
		gl.glVertex3f(0.33f, -1.0f, 1.0f);
		gl.glVertex3f(1.0f, 0.33f, 1.0f);
		gl.glVertex3f(-1.0f, 0.33f, 1.0f);
		gl.glVertex3f(1.0f, -0.33f, 1.0f);
		gl.glVertex3f(-1.0f, -0.33f, 1.0f);
		
		gl.glVertex3f(0.33f, 1.0f, -1.0f); // Top face lines
		gl.glVertex3f(0.33f, 1.0f, 1.0f);
		gl.glVertex3f(-0.33f, 1.0f, -1.0f);
		gl.glVertex3f(-0.33f, 1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 0.33f);
		gl.glVertex3f(-1.0f, 1.0f, 0.33f);
		gl.glVertex3f(1.0f, 1.0f, -0.33f);
		gl.glVertex3f(-1.0f, 1.0f, -0.33f);
		
		gl.glVertex3f(-1.0f, -0.33f, 1.0f); //Left face lines
		gl.glVertex3f(-1.0f, -0.33f, -1.0f);
		gl.glVertex3f(-1.0f, 0.33f, 1.0f);
		gl.glVertex3f(-1.0f, 0.33f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 0.33f);
		gl.glVertex3f(-1.0f, -1.0f, 0.33f);
		gl.glVertex3f(-1.0f, -1.0f, -0.33f);
		gl.glVertex3f(-1.0f, 1.0f, -0.33f);
		
		gl.glVertex3f(1.0f, 0.33f, -1.0f); //Right face lines
		gl.glVertex3f(1.0f, 0.33f, 1.0f);
		gl.glVertex3f(1.0f, -0.33f, -1.0f);
		gl.glVertex3f(1.0f, -0.33f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 0.33f);
		gl.glVertex3f(1.0f, -1.0f, 0.33f);
		gl.glVertex3f(1.0f, 1.0f, -0.33f);
		gl.glVertex3f(1.0f, -1.0f, -0.33f);
		
		gl.glVertex3f(0.33f, -1.0f, -1.0f); //Back face lines
		gl.glVertex3f(0.33f, 1.0f, -1.0f);
		gl.glVertex3f(-0.33f, -1.0f, -1.0f);
		gl.glVertex3f(-0.33f, 1.0f, -1.0f);
		gl.glVertex3f(1.0f, 0.33f, -1.0f);
		gl.glVertex3f(-1.0f, 0.33f, -1.0f);
		gl.glVertex3f(1.0f, -0.33f, -1.0f);
		gl.glVertex3f(-1.0f, -0.33f, -1.0f);
		
		gl.glVertex3f(0.33f, -1.0f, 1.0f); //Bottom face lines
		gl.glVertex3f(0.33f, -1.0f, -1.0f);
		gl.glVertex3f(-0.33f, -1.0f, 1.0f);
		gl.glVertex3f(-0.33f, -1.0f, -1.0f);
		gl.glVertex3f(1.0f, -1.0f, 0.33f);
		gl.glVertex3f(-1.0f, -1.0f, 0.33f);
		gl.glVertex3f(1.0f, -1.0f, -0.33f);
		gl.glVertex3f(-1.0f, -1.0f, -0.33f);
		
		gl.glEnd();
	}
	
	public void drawCube(GL2 gl, float xOff, float yOff, float zOff, boolean selected, boolean mined) {
		
			// doesn't draw the cube if it's been mined
				if(mined) return;
				
				gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
				
				if(selected) gl.glColor3f(1.0f, 0.6f, 0f); // make orange if selected
				
				else gl.glColor3f(0.8f, 0.8f, 0f); // yellow color for unselected mine
				
				
				gl.glVertex3f(0.33f + xOff, 0.33f + yOff, -0.33f + zOff); // Top Right Of The Quad (Top)
				gl.glVertex3f(-0.33f + xOff, 0.33f + yOff, -0.33f + zOff); // Top Left Of The Quad (Top)
				gl.glVertex3f(-0.33f + xOff, 0.33f + yOff, 0.33f + zOff); // Bottom Left Of The Quad (Top)
				gl.glVertex3f(0.33f + xOff, 0.33f + yOff, 0.33f + zOff); // Bottom Right Of The Quad (Top)
				
				// bottom 
				gl.glVertex3f(0.33f + xOff, -0.33f + yOff, 0.33f + zOff); // Top Right Of The Quad 
				gl.glVertex3f(-0.33f + xOff, -0.33f + yOff, 0.33f + zOff); // Top Left Of The Quad 
				gl.glVertex3f(-0.33f + xOff, -0.33f + yOff, -0.33f + zOff); // Bottom Left Of The Quad 
				gl.glVertex3f(0.33f + xOff, -0.33f + yOff, -0.33f + zOff); // Bottom Right Of The Quad
				
				// front 
				gl.glVertex3f(0.33f + xOff, 0.33f + yOff, 0.33f + zOff); 
				gl.glVertex3f(-0.33f + xOff, 0.33f + yOff, 0.33f + zOff);  
				gl.glVertex3f(-0.33f + xOff, -0.33f + yOff, 0.33f + zOff); 
				gl.glVertex3f(0.33f + xOff, -0.33f + yOff, 0.33f + zOff); 
				
				 //  back 
				gl.glVertex3f(0.33f + xOff, -0.33f + yOff, -0.33f + zOff);  
				gl.glVertex3f(-0.33f + xOff, -0.33f + yOff, -0.33f + zOff);  
				gl.glVertex3f(-0.33f + xOff, 0.33f + yOff, -0.33f + zOff); 
				gl.glVertex3f(0.33f + xOff, 0.33f + yOff, -0.33f + zOff); 
				
				//left
				gl.glVertex3f(-0.33f + xOff, 0.33f + yOff, 0.33f + zOff);  
				gl.glVertex3f(-0.33f + xOff, 0.33f + yOff, -0.33f + zOff);  
				gl.glVertex3f(-0.33f + xOff, -0.33f + yOff, -0.33f + zOff); 
				gl.glVertex3f(-0.33f + xOff, -0.33f + yOff, 0.33f + zOff); 
				
				//  right
				gl.glVertex3f(0.33f + xOff, 0.33f + yOff, -0.33f + zOff);  
				gl.glVertex3f(0.33f + xOff, 0.33f + yOff, 0.33f + zOff);  
				gl.glVertex3f(0.33f + xOff, -0.33f + yOff, 0.33f + zOff); 
				gl.glVertex3f(0.33f + xOff, -0.33f + yOff, -0.33f + zOff); 
				
				
				
				gl.glEnd(); // Done Drawing The Quad
	}
	
	
}
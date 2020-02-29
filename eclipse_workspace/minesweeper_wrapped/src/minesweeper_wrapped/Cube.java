package minesweeper_wrapped;

import java.awt.DisplayMode;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import javax.swing.JFrame;
import com.jogamp.opengl.util.FPSAnimator;

public class Cube implements GLEventListener {
	public static DisplayMode dm, dm_old;
	private GLU glu = new GLU();
	private float xquad = 0.0f;
	private float yquad = 0.0f;
	private static float xrot = 0.0f;
	private static float yrot = 0.0f;

	final static GLProfile profile = GLProfile.get(GLProfile.GL2);
	static GLCapabilities capabilities = new GLCapabilities(profile);
	final static GLCanvas glcanvas = new GLCanvas(capabilities);
	final static FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0f, 0f, -5.0f);
		// Rotate The Cube On X, Y & Z
		gl.glRotatef(xquad, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(yquad, 1.0f, 0.0f, 0.0f);
		//giving different colors to different sides
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
		gl.glColor3f(1f, 0f, 0f); // red color
		gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
		gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
		gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left Of The Quad (Top)
		gl.glVertex3f(1.0f, 1.0f, 1.0f); // Bottom Right Of The Quad (Top)
		gl.glColor3f(0f, 1f, 0f); // green color
		gl.glVertex3f(1.0f, -1.0f, 1.0f); // Top Right Of The Quad
		gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Top Left Of The Quad
		gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad
		gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad
		gl.glColor3f(0f, 0f, 1f); // blue color
		gl.glVertex3f(1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Front)
		gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Front)
		gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad
		gl.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad
		gl.glColor3f(1f, 1f, 0f); // yellow (red + green)
		gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad
		gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad
		gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Back)
		gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Back)
		gl.glColor3f(1f, 0f, 1f); // purple (red + green)
		gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Left)
		gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Left)
		gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad
		gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad
		gl.glColor3f(0f, 1f, 1f); // sky blue (blue +green)
		gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Right)
		gl.glVertex3f(1.0f, 1.0f, 1.0f); // Top Left Of The Quad
		gl.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad
		gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad
		gl.glEnd(); // Done Drawing The Quad
		gl.glFlush();
		xquad += xrot;
		yquad += yrot;
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
		//final GLProfile profile = GLProfile.get(GLProfile.GL2);
		//GLCapabilities capabilities = new GLCapabilities(profile);
		// The canvas
		//final GLCanvas glcanvas = new GLCanvas(capabilities);
		Cube cube = new Cube();
		glcanvas.addGLEventListener(cube);
		glcanvas.setSize(400, 400);
		final JFrame frame = new JFrame(" Multicolored cube");
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);

		KeyListener keyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(KeyEvent.getKeyText(e.getKeyCode()));
				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				{
					xrot = 0.5f;
					yrot = 0.0f;
					animator.start();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
				{
					xrot = -0.5f;
					yrot = 0.0f;
					animator.start();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_UP)
				{
					yrot = 0.5f;
				    xrot = 0.0f;
					animator.start();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					yrot = -0.5f;
					xrot = 0.0f;
					animator.start();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				animator.stop();
			}

		};

		glcanvas.addKeyListener(keyListener);

		//		final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
		//		animator.start();
	}
}
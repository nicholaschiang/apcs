// Flag project started by Josh Paley, finished by Nicholas Chiang.

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;

import javax.swing.JApplet;

public class Flag extends JApplet {
	private final int STRIPES = 13;
	private final int STARS = 50;

	// SCALE FACTORS (A through L)
	//
	// Note: Constants in Java should always be ALL_CAPS, even
	// if we are using single letters to represent them
	//
	// NOTE 2: Do not delete or change the names of any of the
	// variables given here
	//
	// Set the constants to exactly what is specified in the documentation
	// REMEMBER: These are scale factors.  They are not numbers of pixels.
	// You will use these and the width and height of the Applet to figure
	// out how to draw the parts of the flag (stripes, stars, field).
	private final double A = 1.0;  // Hoist (width) of flag
	private final double B = 1.9;  // Fly (length) of flag
	private final double C = 0.5385;  // Hoist of Union
	private final double D = 0.76;  // Fly of Union
	private final double E = 0.054;  // See flag specification
	private final double F = 0.054;  // See flag specification
	private final double G = 0.063;  // See flag specification
	private final double H = 0.063;  // See flag specification
	private final double K = 0.0616;  // Diameter of star
	private final double L = 0.0769;  // Width of stripe

	// You will need to set values for these in paint()
	private double flag_width;   // width of flag in pixels
	private double flag_height;  // height of flag in pixels
	private double stripe_height; // height of an individual stripe in pixels

	// init() will automatically be called when an applet is run
	public void init() {
		// Choice of width = 1.9 * height to start off
		// 760 : 400 is ratio of FLY : HOIST
		setSize(760, 400);
		repaint();
	}

	// paint() will be called every time a resizing of an applet occurs
	public void paint(Graphics g) {
		flag_width = getHeight() * B > getWidth() ? getWidth() : getHeight() * B;
		flag_height = flag_width / B;
		stripe_height = flag_height * L;
		drawBackground(g);
		drawStripes(g);
		drawField(g);
		drawStars(g);
	}

	private void drawBackground(Graphics g) {
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void drawStripes(Graphics g) {
		for (int idx = 0; idx < STRIPES; idx++) {
			g.setColor(idx % 2 == 0 ? Color.WHITE : Color.RED);
			g.fillRect(0, (int) stripe_height * idx, (int) flag_width, (int) stripe_height);
		}
	}

	public void drawField(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, (int) (flag_height * D), (int) (flag_height * C));
	}

	public void drawStars(Graphics g) {
    g.setColor(Color.WHITE);
		for (int rows = 0; rows < 9; rows++) {
			boolean oddRow = rows % 2 == 0;
			for (int cols = 0; cols < (oddRow ? 6 : 5); cols++) {
				double x = G * flag_height * (oddRow ? 1 : 2) + cols * flag_height * H * 2;
				double y = E * flag_height + rows * flag_height * F;
				double radius = flag_height * K / 2;
				((Graphics2D) g).fill(createDefaultStar(radius, x, y));
			}
		}
	}
	
	private static Shape createDefaultStar(double radius, double centerX, double centerY) {
		return createStar(centerX, centerY, radius / 2.63, radius, 5, Math.toRadians(-18));
	}

	private static Shape createStar(double centerX, double centerY, double innerRadius, double outerRadius, int numRays, double startAngleRad) {
		Path2D path = new Path2D.Double();
		double deltaAngleRad = Math.PI / numRays;
		for (int i = 0; i < numRays * 2; i++) {
			double angleRad = startAngleRad + i * deltaAngleRad;
			double ca = Math.cos(angleRad);
			double sa = Math.sin(angleRad);
			double relX = ca;
			double relY = sa;
			if ((i & 1) == 0) {
				relX *= outerRadius;
				relY *= outerRadius;
			} else {
				relX *= innerRadius;
				relY *= innerRadius;
			}
			if (i == 0) {
				path.moveTo(centerX + relX, centerY + relY);
			} else {
				path.lineTo(centerX + relX, centerY + relY);
			}
		}
		path.closePath();
		return path;
	}
}

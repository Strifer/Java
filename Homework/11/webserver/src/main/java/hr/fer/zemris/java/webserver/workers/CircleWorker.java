package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class models a 200x200 .png image of a circle with a random color.
 * 
 * @author Filip Džidić
 *
 */
public class CircleWorker implements IWebWorker {
	/** the circle's height */
	private final static int HEIGHT = 200;
	/** the circle's width */
	private final static int WIDTH = 200;

	/**
	 * Creates a circle, generates its random color and shows it on the scren.
	 */
	public void processRequest(RequestContext context) {
		BufferedImage bim = new BufferedImage(HEIGHT, WIDTH,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);

		g2d.setColor(new Color((float) Math.random(), (float) Math.random(),
				(float) Math.random()));
		g2d.fillOval(0, 0, WIDTH, HEIGHT);
		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.setMimeType("image/png");
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

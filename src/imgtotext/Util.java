package imgtotext;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Util {

	/**
	 * Saves the last time recorded for timer.
	 */
	private static long lastTime = System.currentTimeMillis();

	private Util() {
		// Do nothing.
	}

	/**
	 * Returns the amount of time that's passed since it was last called.
	 * 
	 * @return time since this method was last called
	 */
	public static long timer() {
		if (lastTime == 0)
			return 0;
		long duration = System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		return duration;
	}

	public static int[][][] biToRGB(BufferedImage src) {
		int[][][] rgb = new int[src.getWidth()][src.getHeight()][3];
		for (int x = 0; x < src.getWidth(); x++) {
			for (int y = 0; y < src.getHeight(); y++) {
				rgb[x][y][0] = new Color(src.getRGB(x, y)).getRed();
				rgb[x][y][1] = new Color(src.getRGB(x, y)).getGreen();
				rgb[x][y][2] = new Color(src.getRGB(x, y)).getBlue();
			}
		}
		return rgb;
	}

	public static BufferedImage rgbToBI(int[][][] src) {
		if (src == null || src.length == 0)
			throw new IllegalArgumentException("src is empty.");

		int x;
		int y;
		BufferedImage bi = new BufferedImage(src.length, src[0].length, BufferedImage.TYPE_INT_RGB);
		int[] rgb = new int[src.length * src[0].length];
		for (int i = 0; i < rgb.length; i++) {
			x = i % src.length;
			y = i / src.length;
			int red = src[x][y][0];
			int green = src[x][y][1];
			int blue = src[x][y][2];
			bi.setRGB(x, y, new Color(red, green, blue).getRGB());
		}

		return bi;
	}

	public static int[][] rgbToBW(int[][][] src) {
		int[][] bw = new int[src.length][src[0].length];

		for (int x = 0; x < src.length; x++) {
			for (int y = 0; y < src[0].length; y++) {
				// TODO round to nearest number (not round down).
				bw[x][y] = (src[x][y][0] + src[x][y][1] + src[x][y][2]) / 3;
			}
		}
		return bw;
	}

	public static int[][][] bwToRGB(int[][] src) {
		int[][][] rgb = new int[src.length][src[0].length][3];

		for (int x = 0; x < src.length; x++) {
			for (int y = 0; y < src[0].length; y++) {
				rgb[x][y][0] = src[x][y];
				rgb[x][y][1] = src[x][y];
				rgb[x][y][2] = src[x][y];
			}
		}

		return rgb;
	}

	public static boolean save(String name, BufferedImage img) {
		String path = "images/" + name;
		if (name.contains("/") || name.contains("\\"))
			path = name;
		try {
			ImageIO.write(img, "jpg", new File(path + ".jpg"));
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static BufferedImage load(String name) {

		String path = "images/" + name;
		if (name.contains("/") || name.contains("\\"))
			path = name;
		
		BufferedImage src;
		try {
			 src = ImageIO.read(new File(path));
		} catch (IOException e) {
			throw new IllegalArgumentException(path + " does not exist.");
		}

		return src;
	}

	public static void display(BufferedImage img) {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(img)));
		frame.pack();
		frame.setVisible(true);
	}
	
	public static String getPathName(String path) {
		String name = "";
		for (int i = path.length() - 1; i >= 0; i--) {
			String c = "" + path.charAt(i);
			if (c.equals("/") || c.equals("\\"))
				break;
			name = c + name;
		}
		
		return name;
	}
}

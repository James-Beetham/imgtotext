package imgtotext;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

	public static void rgbToBi(int[][][] src) {
		if (src == null || src.length == 0)
			throw new IllegalArgumentException("src is empty.");

		int[] rgb = new int[src.length * src[0].length];
		for (int i = 0; i < rgb.length; i++) {
			int x = i / src.length;
			int y = i % src.length;
			int red = src[x][y][0];
			int green = src[x][y][1];
			int blue = src[x][y][2];
			rgb[i] = new Color(red, green, blue).getRGB();
		}

		DataBufferInt buffer = new DataBufferInt(rgb, rgb.length);

		int[] bandMasks = {0xFF0000, 0xFF00, 0xFF, 0xFF000000};
		WritableRaster raster = Raster.createPackedRaster(buffer, src.length,
				src[0].length, src.length, bandMasks, null);

		System.out.println("raster: " + raster);

		ColorModel cm = ColorModel.getRGBdefault();
		BufferedImage image = new BufferedImage(cm, raster,
				cm.isAlphaPremultiplied(), null);

		System.err.println("image: " + image);

		// return bm;
	}
}

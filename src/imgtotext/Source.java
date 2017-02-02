package imgtotext;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Source extends RGBImg{
	private String path;

	public Source(String path) {
		super(Util.getPathName(path), Util.biToRGB(Util.load(path)));
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	
	@Override
	public String getDesc() {
		return desc + ".src";
	}
	
	/**
	 * Merges levels a (inclusive) through b (exclusive).
	 * 
	 * @param a
	 *            start value
	 * @param b
	 *            end value
	 */
	public void mergeLevels(int a, int b) {
		

	}

}

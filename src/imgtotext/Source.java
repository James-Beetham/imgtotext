package imgtotext;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Source {
	private int[][][] source;
	private int[][] bw;
	private String path;
	private BufferedImage src;

	public Source(String path) {
		this.path = "images/";
		try {
			System.out.println("Retrieving image...");
			src = ImageIO.read(new File(this.path + path));
			source = new int[src.getWidth()][src.getHeight()][3];

			System.out.println("Loading source image...");
			for (int x = 0; x < src.getWidth(); x++) {
				for (int y = 0; y < src.getHeight(); y++) {
					source[x][y][0] = new Color(src.getRGB(x, y)).getRed();
					source[x][y][1] = new Color(src.getRGB(x, y)).getGreen();
					source[x][y][2] = new Color(src.getRGB(x, y)).getBlue();
				}
			}

			System.out.println("Loading BW image...");
			bw = new int[source.length][source[0].length];
			for (int x = 0; x < bw.length; x++) {
				for (int y = 0; y < bw[0].length; y++) {
					// System.out.print("1: " + source[x][y][0] + ",");
					// System.out.print("1: " + source[x][y][1] + ",");
					// System.out.print("1: " + source[x][y][2]);
					bw[x][y] = (source[x][y][0] + source[x][y][1]
							+ source[x][y][2]) / 3;
				}
			}

		} catch (IOException e) {
			System.out.println("Couldn't find file");
			e.printStackTrace();
		}
		System.out.println("Done Constructing!");
	}

	public Source(String path, int[][] bw, int[][][] source,
			BufferedImage src) {
		this.path = path;
		this.bw = bw;
		this.source = source;
		this.src = src;
	}

	public Source(String path, BufferedImage src) {
		this.path = path;
		this.src = src;
		source = new int[src.getWidth()][src.getHeight()][3];

		System.out.println("Loading source image...");
		for (int x = 0; x < src.getWidth(); x++) {
			for (int y = 0; y < src.getHeight(); y++) {
				source[x][y][0] = new Color(src.getRGB(x, y)).getRed();
				source[x][y][1] = new Color(src.getRGB(x, y)).getGreen();
				source[x][y][2] = new Color(src.getRGB(x, y)).getBlue();
			}
		}

		System.out.println("Loading BW image...");
		bw = new int[source.length][source[0].length];
		for (int x = 0; x < bw.length; x++) {
			for (int y = 0; y < bw[0].length; y++) {
				// System.out.print("1: " + source[x][y][0] + ",");
				// System.out.print("1: " + source[x][y][1] + ",");
				// System.out.print("1: " + source[x][y][2]);
				bw[x][y] = (source[x][y][0] + source[x][y][1] + source[x][y][2])
						/ 3;
			}
		}
		System.out.println("Done Constructing!");
	}

	public void save() {
		save("bw");
	}

	// public void save(String name) {
	// System.out.print("-Saving " + name + "...");
	//
	// BufferedImage img = new BufferedImage(imgSrc.length, imgSrc[0].length,
	// BufferedImage.TYPE_INT_ARGB);
	//
	// for (int x = 0; x < imgSrc.length; x++) {
	// for (int y = 0; y < imgSrc[0].length; y++) {
	// //img.setRGB(x, y,new Color(imgSrc[x][y], imgSrc[x][y],
	// imgSrc[x][y]).getRGB());
	//
	// img.setRGB(x, y,
	// new Color(20, 50, 100)
	// .getRGB());
	// }
	// }
	//
	// try {
	// ImageIO.write(img, "jpg",
	// new File(srcFile.getParentFile().getAbsolutePath() + "/"
	// + name + ".jpg"));
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// System.out.println(" Done!");
	//
	// }

	public void save(String name) {
		System.out.print("Saving " + name + "...");
		ColorModel cm = src.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = src.copyData(null);
		BufferedImage bm = new BufferedImage(cm, raster, isAlphaPremultiplied,
				null);

		for (int x = 0; x < bw.length; x++) {
			for (int y = 0; y < bw[0].length; y++) {
				bm.setRGB(x, y,
						new Color(bw[x][y], bw[x][y], bw[x][y]).getRGB());
			}
		}

		try {
			ImageIO.write(bm, "jpg", new File(path + name + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(" Done!");

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
		for (int x = 0; x < bw.length; x++) {
			for (int y = 0; y < bw[0].length; y++) {
				if (bw[x][y] >= a && bw[x][y] < b)
					bw[x][y] = a;
			}
		}
	}
	
	//TODO line?

	public int[] getBWLevels() {
		int[] levels = new int[256];
		for (int x = 0; x < bw.length; x++) {
			for (int y = 0; y < bw[0].length; y++) {
				levels[bw[x][y]]++;
			}
		}
		return levels;
	}

	public int[][][] getSource() {
		return source;
	}

	public int[][] getBW() {
		return bw;
	}

	public String getPath() {
		return path;
	}

	public BufferedImage getSrc() {
		return src;
	}

}

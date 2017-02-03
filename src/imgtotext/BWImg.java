package imgtotext;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BWImg implements Img {

	private String desc;
	private int[][] img;

	public BWImg(String desc, int[][] img) {
		this.desc = desc;
		this.img = img.clone();
	}

	public String getDesc() {
		return desc + ".bw";
	}

	public int[][] getImg() {
		return img;
	}

	public int getWidth() {
		return img.length;
	}
	
	public int getHeight() {
		return img[0].length;
	}
	
	@Override
	/**
	 * Splits the image between two points. Starts from x1, y1 and copies
	 * towards x2, y2 (if points are switched, it flips the image).
	 * 
	 * @param x1
	 *            first pixel's x value (inclusive)
	 * @param y1
	 *            first pixel's y value (inclusive)
	 * @param x2
	 *            second pixel's x value (inclusive)
	 * @param y2
	 *            second pixel's y value (inclusive)
	 * @return 2d array of integer arrays with RGB values stored
	 */
	public Img split(int x1, int y1, int x2, int y2) {
		if (x1 > x2) {
			int temp = x1;
			x1 = x2;
			x2 = temp;
		}
		if (y1 > y2) {
			int temp = y1;
			y1 = y2;
			y2 = temp;
		}

		int[][] section = new int[x2 - x1 + 1][y2 - y1 + 1];
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				section[x - x1][y - y1] = img[x][y];
			}
		}

		return new BWImg(
				desc + ".s(" + x1 + "," + y1 + "|" + x2 + "," + y2 + ")",
				section);
	}

	@Override
	public Img mergeLevels(int[] list) {
		int[][] newImg = new int[img.length][img[0].length];
		int a;
		int b;
		for (int i = 0; i < list.length - 1; i += 2) {
			a = list[i];
			b = list[i + 1];

			for (int x = 0; x < img.length; x++) {
				for (int y = 0; y < img[0].length; y++) {
					if (img[x][y] >= a && img[x][y] < b)
						newImg[x][y] = a;
				}
			}
		}

		return new BWImg(desc + ".m", newImg);
	}

	public BWImg getWriting() {
		int[][] points = new int[img.length][img[0].length];
		for (int x = 0; x < img.length; x++) {
			System.out.print(" " + x);

			for (int y = 0; y < img[0].length; y++) {

				int[][] area = ((BWImg) split(
						Math.max(0, x - Util.SEARCH_AREA_RADIUS),
						Math.max(0, y - Util.SEARCH_AREA_RADIUS),
						Math.min(x + Util.SEARCH_AREA_RADIUS, img.length),
						Math.min(y + Util.SEARCH_AREA_RADIUS, img[0].length)))
								.getImg();

				// get slopes
				double[] levels = getCircLevels(x, y, Util.SEARCH_AREA_RADIUS);

				for (int i = 0; i < levels.length; i++) {
					// if (levels[i] != 0)
					// System.out.print(levels[i] + ", ");
				}
				// System.out.println(":" + levels.length);

				double[] diff = new double[levels.length + 1];
				int count = 0;
				diff[0] = 0;
				diff[diff.length - 1] = 255;
				for (int i = 0; i < levels.length - 1; i++) {
					diff[i + 1] = levels[i + 1] - levels[i];
					if (i != 0 && ((diff[i - 1] > 0 && diff[i] < 0)
							|| (diff[i - 1] < 0 && diff[i] > 0)))
						count++;
				}

				// get points of transition
				int[] zeros = new int[count];
				count = 0;
				for (int i = 1; i < diff.length; i++) {
					if ((diff[i - 1] > 0 && diff[i] < 0)
							|| (diff[i - 1] < 0 && diff[i] > 0)) {
						zeros[count] = i + 1;
						count++;
					}
				}

				for (int i = 0; i < zeros.length; i++) {
					// System.out.print(zeros[i] + ", ");
				}
				// System.out.println(":" + zeros.length);

				int num = img[x][y];
				for (int i = 1; i < zeros.length; i++) {
					if (zeros[i] > num) {
						if (i % 2 == 0)
							num = zeros[i - 1];
						else
							num = zeros[i];
						break;
					}
				}

				points[x][y] = num;

				// print levels and 0 points
				// for (int i = 0; i < levels.length; i++)
				// {
				// System.out.println(levels[i]);
				// }
				// System.out.println("-----");
				// for (int i = 0; i < zeros.length; i++)
				// {
				// System.out.println(zeros[i]);
				// }
				// System.exit(0);
			}

		}

		return new BWImg("write", points);

	}

	public int[] getLevels() {
		int[][] src = img;
		int[] list = new int[256];
		for (int x = 0; x < src.length; x++) {
			for (int y = 0; y < src[0].length; y++) {
				list[src[x][y]]++;
			}
		}

		return list;
	}

	private double[] getCircLevels(int centerX, int centerY, int maxR) {
		double[] list = new double[256];

		int[][] circles = new int[maxR - 1][];

		Set<Point> pointsListed = new HashSet<>();
		ArrayList<ArrayList<Point>> points = new ArrayList<>();
		for (int r = 0; r < maxR; r++) {
			points.add(new ArrayList<>());
			for (int x = 0; x < img.length; x++) {
				for (int y = 0; y < img[0].length; y++) {
					if (r + 1 >= Math.pow(x + centerX, 2)
							+ Math.pow(y + centerY, 2)
							&& pointsListed.add(new Point(x, y))) {
						points.get(r).add(new Point(x, y));
					}
				}
			}

			for (Point p : points.get(r)) {
				list[img[p.x][p.y]] += 1.0 / r;
			}

		}

		return list;
	}

}

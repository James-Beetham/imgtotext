package imgtotext;

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

		return new BWImg(desc + ".s(" + x1 + "," + y1 + "|" + x2 + "," + y2 + ")", section);
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

	public boolean[][] getWriting() {
		boolean[][] points = new boolean[img.length][img[0].length];
		for (int x = 0; x < img.length; x++) {
			for (int y = 0; y < img[0].length; y++) {
				x = 450;
				y = 1200;

				int[][] area = ((BWImg) split(Math.max(0, x - Util.SEARCH_AREA_RADIUS),
						Math.max(0, y - Util.SEARCH_AREA_RADIUS), Math.min(x + Util.SEARCH_AREA_RADIUS, img.length),
						Math.min(y + Util.SEARCH_AREA_RADIUS, img[0].length))).getImg();

				// get slopes
				int[] levels = getLevels(area);
				int[] diff = new int[levels.length - 1];
				int count = 0;
				for (int i = 0; i < diff.length; i++) {
					diff[i] = levels[i + 1] - levels[i];
					if (i != 0 && ((diff[i - 1] > 0 && diff[i] < 0) || (diff[i - 1] < 0 && diff[i] > 0)))
						count++;
				}

				// get points of transition
				int[] zeros = new int[count];
				count = 0;
				for (int i = 1; i < diff.length; i++) {
					if ((diff[i - 1] > 0 && diff[i] < 0) || (diff[i - 1] < 0 && diff[i] > 0)) {
						zeros[count] = i + 1;
						count++;
					}
				}
				// print levels and 0 points
				for (int i = 0; i < levels.length; i++) {
					System.out.println(levels[i]);
				}
				System.out.println("-----");
				for (int i = 0; i < zeros.length; i++) {
					System.out.println(zeros[i]);
				}
				System.exit(0);
			}

		}

		return points;

	}

	private int[] getLevels(int[][] src) {
		int[] list = new int[256];
		for (int x = 0; x < src.length; x++) {
			for (int y = 0; y < src[0].length; y++) {
				if (src[x][y] == 0)
					System.out.println(":" + x + "," + y);
				list[src[x][y]]++;
			}
		}

		return list;
	}

}

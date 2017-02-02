package imgtotext;

public class BWImg {

	private String desc;
	private int[][] img;
	
	public BWImg(String desc, int[][] img) {
		this.desc = desc;
		this.img = img;
	}
	
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
	public int[][] split(int x1, int y1, int x2, int y2) {
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

		int[][] section = new int[x2 - x1][y2 - y1];

		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				section[x][y] = img[x][y];
			}
		}

		return section;
	}
	
}

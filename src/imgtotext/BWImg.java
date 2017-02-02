package imgtotext;

public class BWImg implements Img {

	private String desc;
	private int[][] img;

	public BWImg(String desc, int[][] img) {
		this.desc = desc;
		this.img = img;
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

		int[][] section = new int[x2 - x1][y2 - y1];

		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				section[x][y] = img[x][y];
			}
		}

		return new BWImg(desc + ".s(" + x1 + "," + y1 + "|" + x2 + "," + y2 + ")", section);
	}

	@Override
	public Img mergeLevels(int[] list) {
		int a;
		int b;
		for (int i = 0; i < list.length - 1; i += 2) {
			a = list[i];
			b = list[i + 1];
			
			for (int x = 0; x < img.length; x++) {
				for (int y = 0; y < img[0].length; y++) {
					if (img[x][y] >= a && img[x][y] < b)
						img[x][y] = a;
				}
			}
		}

		return null;
	}

}

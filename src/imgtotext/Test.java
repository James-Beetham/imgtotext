package imgtotext;

public class Test {
	public static void main(String[] args) {

		// int[][][] src = new int[3000][3000][3];
		// for (int x = 0; x < 3000; x++) {
		// for (int y = 0; y < 3000; y++) {
		// src[x][y][0] = 10;
		// src[x][y][1] = 12;
		// src[x][y][2] = 14;
		// }
		// }
		//
		// Img img = new Img("Test", src);

		Source s = new Source("test1.jpg");
		Util.rgbToBi(s.getSource());
		
		// s.mergeLevels(140, 200);
		// s.mergeLevels(115, 140);
		// s.mergeLevels(70, 115);
		// s.mergeLevels(0, 70);
		// s.save("testing");

		// int[] levels = s.getBWLevels();
		// for (int i = 0; i < levels.length; i++) {
		// System.out.println(levels[i]);
		// }
	}
}

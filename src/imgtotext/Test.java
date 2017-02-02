package imgtotext;

public class Test {
	public static void main(String[] args) {
		RGBImg img = Util.biToRGB(Util.load("test1.jpg"));
		RGBImg img2 = Util.biToRGB(Util.load("test1.jpg"));
		BWImg test = Util.rgbToBW(img);
		BWImg test2 = Util.rgbToBW(img2);
		BWImg bw = (BWImg) test.split(250, 1000, 750, 1400);
		// bw = (BWImg) bw.mergeLevels(new int[] { 1, 118, 118, 255 });
		Util.save("bwTest", Util.bwToBI(bw));

		test2.getWriting();

	}
}

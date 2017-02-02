package imgtotext;

public class Test {
	public static void main(String[] args) {
		Util.timer();
		System.out.print("[Loading]");
		RGBImg img = Util.biToRGB(Util.load("test1.jpg"));
		System.out.print(" (" + Util.timer() + "ms)\n[converting]");
		BWImg test = Util.rgbToBW(img);
		System.out.print(" (" + Util.timer() + "ms)\n[splitting]");
		BWImg bw = (BWImg) test.split(300, 1100, 700, 1200);
		// bw = (BWImg) bw.mergeLevels(new int[] { 1, 118, 118, 255 });
		// Util.save("bwTest", Util.bwToBI(bw));

		System.out.print(" (" + Util.timer() + "ms)\n[altering]");
		for (int i = 0; i < 1; i++) {
			bw = bw.getWriting();
		}
		System.out.print(" (" + Util.timer() + "ms)\n[saving]");
		Util.save("smartWrite", Util.bwToBI(bw));
		System.out.println(" (" + Util.timer() + "ms)");
	}
}
